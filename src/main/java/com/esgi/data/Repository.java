package com.esgi.data;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.core.exceptions.helpers.SQLExceptionEnum;
import com.esgi.core.exceptions.helpers.SQLExceptionParser;
import com.esgi.data.sql.QueryResult;
import com.esgi.data.sql.SQLColumnValue;
import com.esgi.data.sql.SQLHelper;
import com.esgi.data.sql.SQLValueBinder;
import com.esgi.data.sql.SQLWhereCondition;
import com.esgi.data.sql.builder.SQLBuilder;
import com.esgi.data.sql.builder.SQLSelectBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toCollection;

public abstract class Repository<T extends Model> {
    protected final String connectionString;
    private final String tableName;

    protected static final String ENABLE_FOREIGN_KEYS_STATEMENT = "PRAGMA foreign_keys = ON;";

    public Repository(String tableName) {
        this.connectionString = DataConfig.getDbConnectionString();
        this.tableName = tableName;

        DataConfig.getInstance().initDb();
    }

    protected abstract T parseSQLResult(ResultSet result) throws SQLException;


    public T getById(Integer id) throws NotFoundException, InternalErrorException {
        return this.getFirstWhereColumnEquals("id", id);
    }

    protected T getFirstWhereColumnEquals(String columnName, Object value) throws NotFoundException, InternalErrorException {
        var condition = SQLWhereCondition.makeEqualCondition(columnName, value);
        return this.findFirstWhere(List.of(condition));
    }

    protected T findFirstWhere(List<SQLWhereCondition<?>> conditions) throws NotFoundException, InternalErrorException {
        try(var result = this.executeWhereAllQuery(conditions)) {
            boolean noRecordFound = !result.getResultSet().next();
            if (noRecordFound) {
                throw new NotFoundException("No records found with these conditions");
            }

            return parseSQLResult(result.getResultSet());
        } catch (SQLException e) {
            throw new InternalErrorException(e);
        }
    }

    public List<T> getAll() throws InternalErrorException {
        String sql = SQLBuilder.selectAll().from(tableName).build();

        try (var result = this.executeQuery(sql, List.of())) {
            return resultSetToList(result.getResultSet());
        } catch (SQLException e) {
            throw new InternalErrorException(e);
        }
    }

    protected List<T> getAllWhereColumnEquals(String columnName, Object value) throws InternalErrorException {
        var condition = SQLWhereCondition.makeEqualCondition(columnName, value);

        return this.getAllWhere(List.of(condition));
    }

    protected List<T> getAllWhere(List<SQLWhereCondition<?>> conditions) throws InternalErrorException {
        try(var result = this.executeWhereAllQuery(conditions)) {
            return resultSetToList(result.getResultSet());
        } catch (SQLException e) {
            throw new InternalErrorException(e);
        }
    }


    protected List<T> resultSetToList(ResultSet result) throws SQLException {
        List<T> models = new ArrayList<>();
        while(result.next()) {
            models.add(parseSQLResult(result));
        }
        return models;
    }

    protected QueryResult executeWhereAllQuery(List<SQLWhereCondition<?>> conditions) throws SQLException {
        String sql = SQLBuilder.selectAll()
                .from(tableName)
                .whereAll(conditions)
                .build();

        return this.executeQuery(sql, conditions);
    }

    protected QueryResult executeQuery(String sql, List<SQLWhereCondition<?>> conditions) throws SQLException {
        var valueBinders = this.valueBindersFromColumnValues(conditions);
        var statement = this.prepareSql(sql, valueBinders);
        return new QueryResult(statement, statement.executeQuery());
    }

    public List<Integer> getListById(Integer entityId, String entityIdColumn, String relatedIdColumn, String joinTableName)
            throws SQLException {
        List<Integer> listIds = new ArrayList<>();

        String sql = "SELECT " + relatedIdColumn + " FROM "+ joinTableName +" WHERE " + entityIdColumn + " = ?";

        try (var conn = DriverManager.getConnection(connectionString);
             var statement = conn.prepareStatement(sql)) {
            statement.setInt(1, entityId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    listIds.add(resultSet.getInt(relatedIdColumn));
                }
            }
        }
        return listIds;
    }

    protected Integer executeCreate(List<SQLColumnValue<?>> columnValues) throws SQLException {
        var columns = columnValues.stream().map(SQLColumnValue::getColumnName).toList();
        String sql = SQLSelectBuilder.insertInto(tableName)
                .columns(columns)
                .build();

        try (var conn = DriverManager.getConnection(connectionString);
             var statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            var valueBinders = this.valueBindersFromColumnValues(columnValues);
            SQLHelper.bindValues(statement, valueBinders);

            statement.execute();
            return SQLHelper.retrieveGeneratedKey(statement);
        }
    }

    protected void executeUpdate(List<SQLColumnValue<?>> columnValues, Integer whereId)
            throws NotFoundException, SQLException {
        var columns = columnValues.stream().map(SQLColumnValue::getColumnName).toList();
        var whereIdEqualsCondition = SQLWhereCondition.makeEqualCondition("id", whereId);


        String sql = SQLBuilder.update(tableName)
                .columns(columns)
                .where(whereIdEqualsCondition)
                .build();

        var valueBinders = valueBindersFromColumnValues(columnValues);
        valueBinders.add(whereIdEqualsCondition.makeValueBinder());

        int rowsUpdated = this.executeUpdate(sql, valueBinders);
        if (rowsUpdated == 0) {
            throw new NotFoundException("Record with id '%d' not found in table '%s'".formatted(whereId, tableName));
        }
    }

    public void deleteById(Integer id) throws NotFoundException, SQLException, ConstraintViolationException {
        this.deleteByColumn("id", id);
    }

    protected void deleteByColumn(String column, Object value) throws NotFoundException, SQLException {
        this.deleteWhere(List.of(
                SQLWhereCondition.makeEqualCondition(column, value)
        ));
    }

    protected void deleteWhere(List<SQLWhereCondition<?>> conditions) throws NotFoundException, SQLException {
        String sql = SQLBuilder.deleteFrom(tableName).whereAll(conditions).build();
        var valueBinders = valueBindersFromColumnValues(conditions);

        int rowsDeleted = this.executeUpdate(sql, valueBinders);
        if(rowsDeleted == 0) {
            throw new NotFoundException("No corresponding record found to delete.");
        }
    }

    protected int executeUpdate(String sql, List<SQLValueBinder> valueBinders) throws SQLException {
        try (var statement = this.prepareSql(sql, valueBinders)) {
            return statement.executeUpdate();
        }
    }

    protected PreparedStatement prepareSql(String sql, List<SQLValueBinder> valueBinders) throws SQLException {
        var conn = DriverManager.getConnection(connectionString);

        // must enable foreign keys with sqlite
        this.enableForeignKeys(conn);

        var statement = conn.prepareStatement(sql);
        SQLHelper.bindValues(statement, valueBinders);

        return statement;
    }

    private List<SQLValueBinder> valueBindersFromColumnValues(List<? extends SQLColumnValue<?>> columnValues) {
        return columnValues.stream().map(SQLColumnValue::makeValueBinder).collect(toCollection(ArrayList::new));
    }

    private void enableForeignKeys(Connection connection) throws SQLException {
        boolean notUsingSQLite = !DataConfig.usingSQLite;
        if (notUsingSQLite) {
            return;
        }

        try(var statement = connection.prepareStatement(ENABLE_FOREIGN_KEYS_STATEMENT)) {
            statement.execute();
        }
    }

    protected SQLExceptionEnum parseSqlException(SQLException e) throws InternalErrorException {
        Optional<SQLExceptionEnum> optionalExceptionType = SQLExceptionParser.parse(e);

        boolean exceptionTypeNotFound = optionalExceptionType.isEmpty();
        if (exceptionTypeNotFound) {
            throw new InternalErrorException(e);
        }

        return optionalExceptionType.get();
    }

}


package com.esgi.data;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    protected T getFirstByColumn(String columnName, Object value) throws NotFoundException, InternalErrorException {
        var condition = SQLWhereCondition.makeEqualCondition(columnName, value);
        return this.findFirstWhere(List.of(condition));
    }

    protected List<T> getAllByColumn(String columnName, Object value) throws InternalErrorException {
        var condition = SQLWhereCondition.makeEqualCondition(columnName, value);
        return this.getAllWhere(List.of(condition));
    }

    public T getById(Integer id) throws NotFoundException, InternalErrorException {
        return this.getFirstByColumn("id", id);
    }

    protected T findFirstWhere(List<SQLWhereCondition> conditions) throws NotFoundException, InternalErrorException {
        try(var statement = this.executeSelectWhereQuery(conditions);
            var result = statement.executeQuery()) {
            boolean noRecordFound = !result.next();
            if (noRecordFound) {
                throw new NotFoundException("No records found with these conditions");
            }

            return parseSQLResult(result);
        } catch (SQLException e) {
            throw new InternalErrorException(e);
        }
    }

    public List<T> getAll() {
        String sql = "SELECT * FROM " + tableName;
        try (var conn = DriverManager.getConnection(connectionString);
             var statement = conn.prepareStatement(sql);
             var result = statement.executeQuery()) {

            List<T> entities = new ArrayList<>();
            while(result.next()) {
                entities.add(parseSQLResult(result));
            }
            return entities;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<T> getAllWhere(List<SQLWhereCondition> conditions) throws InternalErrorException {
        try(var statement = this.executeSelectWhereQuery(conditions);
            var result = statement.executeQuery()) {
            List<T> models = new ArrayList<>();
            while(result.next()) {
                models.add(parseSQLResult(result));
            }
            return models;
        } catch (SQLException e) {
            throw new InternalErrorException(e);
        }
    }

    private PreparedStatement executeSelectWhereQuery(List<SQLWhereCondition> conditions) throws SQLException {
        String whereConditionsStatement = SQLHelper.buildWhereConditions(conditions);
        String sql = "SELECT * FROM " + tableName + " WHERE " + whereConditionsStatement;

        var filteredConditions = conditions.stream()
                .filter(condition -> !(condition.value() instanceof SQLNullValue))
                .toList();

        var conn = DriverManager.getConnection(connectionString);
        var statement = conn.prepareStatement(sql);

        var valueBinders = filteredConditions.stream().map(SQLWhereCondition::makeValueBinder).toList();

        SQLHelper.bindValues(statement, valueBinders);

        return statement;
    }

    public List<Integer> getListById(Integer entityId, String entityIdColumn, String relatedIdColumn, String joinTableName) throws SQLException {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listIds;
    }

    protected void executeCreate(Map<String, SQLColumnValueBinder> columnValueBinders, T model)
            throws  SQLException {
        String columns = String.join(",", columnValueBinders.keySet());
        String valuesPlaceholder = SQLHelper.generatePlaceholders(columnValueBinders.keySet());
        String sql = "INSERT INTO " + tableName + " ( " + columns +") VALUES ( " + valuesPlaceholder + " )";

        try (var conn = DriverManager.getConnection(connectionString);
             var statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            SQLHelper.bindValues(statement, columnValueBinders.values().stream().toList());

            statement.execute();
            SQLHelper.retrieveGeneratedKey(statement, model);
        }
    }

    protected void executeUpdate(Map<String, SQLColumnValueBinder> columnValueBinders, Integer whereId)
            throws NotFoundException, SQLException {
        String updateSQL = "UPDATE " + tableName;
        String setValuesSQL = " SET " + SQLHelper.buildSetClause(columnValueBinders.keySet());
        String whereSQL = " WHERE id = ?;";

        String sql = updateSQL + setValuesSQL + whereSQL;

        try (var conn = DriverManager.getConnection(connectionString);
             var statement = conn.prepareStatement(sql)) {

            int index = SQLHelper.bindValues(statement, columnValueBinders.values().stream().toList());
            statement.setInt(index, whereId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new NotFoundException("Record with id '%d' not found in table '%s'".formatted(whereId, tableName));
            }
        }
    }

    public void delete(Integer id) throws NotFoundException, ConstraintViolationException, InternalErrorException {
        this.deleteByColumn("id", id);
    }

    protected void deleteByColumn(String column, Object value)
            throws NotFoundException, ConstraintViolationException {
        this.deleteWhere(List.of(
            SQLWhereCondition.makeEqualCondition(column, value)
        ));
    }

    protected void deleteWhere(List<SQLWhereCondition> conditions)
            throws NotFoundException, ConstraintViolationException {
        String whereConditions = SQLHelper.buildWhereConditions(conditions);

        String sql = "DELETE FROM " + tableName + " WHERE " + whereConditions;

        try (var conn = DriverManager.getConnection(connectionString);
             var statement = conn.prepareStatement(sql)) {

            SQLHelper.bindConditionsValues(statement, conditions);

            // must enable foreign keys with sqlite to make we don't delete a record that's referenced in another table
            this.enableForeignKeys(conn);

            int rowsDeleted = statement.executeUpdate();
            if(rowsDeleted == 0) {
                throw new NotFoundException("No corresponding record found to delete.");
            }

        } catch (SQLException e) {
            throw new ConstraintViolationException("Record is linked to another table !");
        }
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
}

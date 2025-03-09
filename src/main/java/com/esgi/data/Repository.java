package com.esgi.data;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.core.exceptions.helpers.SQLExceptionEnum;
import com.esgi.core.exceptions.helpers.SQLExceptionParser;
import com.esgi.data.books.BookModel;
import com.esgi.data.users.UserModel;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import java.util.stream.Collectors;


public abstract class Repository<T extends Model> {
    protected final String connectionString;

    public Repository() {
        this.connectionString = DataConfig.getDbConnectionString();
    }

    protected abstract String getTableName();

    protected abstract T parseSQLResult(ResultSet result) throws SQLException;

    protected T getFirstByColumn(String columnName, Object value) throws NotFoundException {
        try (var conn = DriverManager.getConnection(connectionString)) {
            String sql = "SELECT * FROM " + getTableName() + " WHERE " + columnName + " = ?";
            var statement = conn.prepareStatement(sql);
            statement.setObject(1, value);

            try (var result = statement.executeQuery()) {
                if(!result.next()) {
                    throw new NotFoundException(this.notFoundErrorMessage(columnName, value));
                }

                return parseSQLResult(result);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public T getById(Integer id) throws NotFoundException {
        return this.getFirstByColumn("id", id);
    }


    public List<Integer> getListById(Integer entityId, String entityIdColumn, String relatedIdColumn, String joinTableName) throws SQLException {
        List<Integer> listIds = new ArrayList<>();

        try (var conn = DriverManager.getConnection(connectionString)){
            String sql = "SELECT " + relatedIdColumn + " FROM "+ joinTableName +" WHERE " + entityIdColumn + " = ?";
            var statement = conn.prepareStatement(sql);
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

    public String generatePlaceholders(Collection<String> argument){
        return argument.stream()
                .map(e -> "?")
                .collect(Collectors.joining(","));
    }

    private <T extends Model> void retrieveGeneratedKey(java.sql.PreparedStatement statement, T model) throws SQLException {
        try (var generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long generatedId = generatedKeys.getLong(1);
                model.setId((int) generatedId);
            }
        }
    }

    protected void executeCreate(Map<String, SQLColumnValueBinder> columnValueBinders,T model) throws  SQLException, ConstraintViolationException {
        try (var conn = DriverManager.getConnection(connectionString)) {
            String insertSQL = "INSERT INTO " + getTableName();
            String setValuesSQL = " ( " + String.join(", ", columnValueBinders.keySet())+") VALUES ( " + this.generatePlaceholders(columnValueBinders.keySet()) + " )";

            String sql = insertSQL + setValuesSQL;

            try (var statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                int index = 1;
                for (Map.Entry<String, SQLColumnValueBinder> entry : columnValueBinders.entrySet()) {
                    entry.getValue().bind(statement, index);
                    index++;
                }
                statement.execute();
                retrieveGeneratedKey(statement, model);
            }
        }
    }

    protected void executeUpdate(Map<String, SQLColumnValueBinder> columnValueBinders, Integer whereId) throws NotFoundException, SQLException {

        try (var conn = DriverManager.getConnection(connectionString)) {
            String updateSQL = "UPDATE " + getTableName();
            String setValuesSQL = " SET " + this.buildSetClause(columnValueBinders.keySet());
            String whereSQL = " WHERE id = ?;";

            String sql = updateSQL + setValuesSQL + whereSQL;

            try (var statement = conn.prepareStatement(sql)) {
                int index = 1;
                for (Map.Entry<String, SQLColumnValueBinder> entry : columnValueBinders.entrySet()) {
                    entry.getValue().bind(statement, index);
                    index++;
                }
                statement.setInt(index, whereId);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 0) {
                    throw new NotFoundException(this.notFoundErrorMessage("id", whereId));
                }
            }
        }
    }

    protected String notFoundErrorMessage(String columnName, Object value) {
        return "Record with %s '%s' not found in table '%s'".formatted(columnName, value, getTableName());
    }

    private String buildSetClause(Collection<String> columns) {
        return columns.stream().map(column -> column + " = ?").collect(Collectors.joining(","));
    }

}

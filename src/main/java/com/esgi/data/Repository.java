package com.esgi.data;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.core.exceptions.helpers.SQLExceptionEnum;
import com.esgi.core.exceptions.helpers.SQLExceptionParser;
import java.lang.reflect.Method;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class Repository<T extends Model> {
    protected final String connectionString;

    public Repository() {
        this.connectionString = DataConfig.getDbConnectionString();
    }

    protected abstract String getTableName();

    protected abstract T parseSQLResult(ResultSet result) throws SQLException;

    public T getById(Integer id) throws NotFoundException {
        try (var conn = DriverManager.getConnection(connectionString)) {
            String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
            var statement = conn.prepareStatement(sql);
            statement.setInt(1, id);

            try (var result = statement.executeQuery()) {
                if(!result.next()) {
                    throw new NotFoundException(String.format("Record with id '%d' not found in table '%s'", id, getTableName()));
                }

                return parseSQLResult(result);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<Integer> getListById(Integer entityId, String entityIdColumn, String relatedIdColumn, String joinTableName) throws SQLException {
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

    public String generatePlaceholders(List<String> argument){
        return argument.stream()
                .map(e -> "?")
                .collect(Collectors.joining(","));
    }

    private static boolean isGetter(Method method) {
        return method.getName().startsWith("get") || method.getName().startsWith("is");
    }

    protected abstract String exceptionMessage(T model);

    private <T> void setPreparedStatementValues(T model, java.sql.PreparedStatement statement) {
        try {
            Method[] methods = model.getClass().getDeclaredMethods();
            int index = 1;

            for (Method method : methods) {

                if (isGetter(method)) {
                    try {
                        Object value = method.invoke(model);
                        statement.setObject(index++, value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private <T extends Model> void retrieveGeneratedKey(java.sql.PreparedStatement statement, T model) throws SQLException {
        try (var generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long generatedId = generatedKeys.getLong(1);
                model.setId((int) generatedId);
            }
        }
    }

    private List<String> extractColumnNames(T model){
        Method[] methods = model.getClass().getDeclaredMethods();
        List<String> columns = new ArrayList<>();
        for (Method method : methods) {

            if (isGetter(method)) {
                if(method.getName().substring(0, 3).equals("get")){
                    columns.add(method.getName().substring(3).toLowerCase());
                }
                else{columns.add(method.getName());}
            }
        }
        return columns;
    }

    public void create(T model) throws ConstraintViolationException {
        List<String> columnsName= extractColumnNames(model);
        String columnsNameString = columnsName.stream()
                .collect(Collectors.joining(","));

        try (var conn = DriverManager.getConnection(connectionString)) {
                String sql = "INSERT INTO " + getTableName() + " (" + columnsNameString + ") VALUES (" + generatePlaceholders(columnsName) + ")";
            var statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            setPreparedStatementValues(model,statement);

            statement.execute();

            retrieveGeneratedKey(statement, model);
        } catch (SQLException e) {
            Optional<SQLExceptionEnum> optionalExceptionType = SQLExceptionParser.parse(e);

            boolean exceptionTypeNotFound = optionalExceptionType.isEmpty();
            if (exceptionTypeNotFound) {
                throw new RuntimeException(e);
            }

            switch (optionalExceptionType.get()) {
                case CONSTRAINT_UNIQUE:
                    throw new ConstraintViolationException(exceptionMessage(model));
                case CONSTRAINT_NOTNULL:
                    throw new ConstraintViolationException("A required field of the "+ model +" is missing.");
            }
        }
    }



}





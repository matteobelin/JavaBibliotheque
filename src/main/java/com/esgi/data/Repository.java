package com.esgi.data;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import org.sqlite.SQLiteException;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public abstract void create(T model) throws ConstraintViolationException;

}

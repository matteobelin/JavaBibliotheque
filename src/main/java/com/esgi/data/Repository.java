package com.esgi.data;

import com.esgi.core.exceptions.NotFoundException;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public abstract void create(T model);
}
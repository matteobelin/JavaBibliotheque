package com.esgi.data;

import com.esgi.core.exceptions.NotFoundException;

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

    public <T> List<T> getListById(Integer id, String searchElement, String columnName, Class<T> clazz) throws SQLException {
        List<T> listIds = new ArrayList<>();

        try (var conn = DriverManager.getConnection(connectionString)){
            String sql = "SELECT " + columnName + " FROM genre_book WHERE " + searchElement + " = ?";
            var statement = conn.prepareStatement(sql);
                statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    if (clazz == Integer.class) {
                        listIds.add(clazz.cast(resultSet.getInt(columnName)));
                    } else if (clazz == String.class) {
                        listIds.add(clazz.cast(resultSet.getString(columnName)));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listIds;
    }



    public abstract void create(T model);
}
package com.esgi.data.users.impl;

import com.esgi.data.Repository;
import com.esgi.data.users.UserModel;
import com.esgi.data.users.UserRepository;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepositoryImpl extends Repository<UserModel> implements UserRepository {
    @Override
    protected String getTableName() {
        return "users";
    }

    @Override
    protected UserModel parseSQLResult(ResultSet resultSet) throws SQLException {
        return new UserModel(
                resultSet.getInt("id"),
                resultSet.getString("email"),
                resultSet.getBoolean("isAdmin"),
                resultSet.getString("name"),
                resultSet.getString("password")
        );
    }

    @Override
    public void create(UserModel user) {
        try (var conn = DriverManager.getConnection(connectionString)) {
            String sql = "INSERT INTO " + getTableName() + " (email, name, password, isAdmin) VALUES (?, ?, ?, ?)";
            var statement = conn.prepareStatement(sql);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPassword());
            statement.setBoolean(4, user.isAdmin());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

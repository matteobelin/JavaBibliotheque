package com.esgi.data.users.impl;

import com.esgi.data.Repository;
import com.esgi.data.users.UserModel;
import com.esgi.data.users.UserRepository;
import com.esgi.domain.users.UserEntity;

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
}

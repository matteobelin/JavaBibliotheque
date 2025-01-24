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
        int idColumn = resultSet.findColumn("id");
        int emailColumn = resultSet.findColumn("email");
        int passwordColumn = resultSet.findColumn("isAdmin");
        int nameColumn = resultSet.findColumn("name");

        return new UserModel(
                resultSet.getInt(idColumn),
                resultSet.getString(emailColumn),
                resultSet.getBoolean(passwordColumn),
                resultSet.getString(nameColumn)
        );
    }
}

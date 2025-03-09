package com.esgi.data.users.impl;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.core.exceptions.helpers.SQLExceptionEnum;
import com.esgi.core.exceptions.helpers.SQLExceptionParser;
import com.esgi.data.Repository;
import com.esgi.data.SQLColumnValueBinder;
import com.esgi.data.users.UserModel;
import com.esgi.data.users.UserRepository;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

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
    public UserModel getByEmail(String email) throws NotFoundException {
       return super.getFirstByColumn("email", email);
    }

    @Override
    public void create(UserModel user) throws ConstraintViolationException {
        try (var conn = DriverManager.getConnection(connectionString)) {
            String sql = "INSERT INTO " + getTableName() + " (email, name, password, isAdmin) VALUES (?, ?, ?, ?)";
            var statement = conn.prepareStatement(sql);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPassword());
            statement.setBoolean(4, user.isAdmin());

            statement.executeUpdate();

        } catch (SQLException e) {
            handleSQLException(e, user.getEmail());
        }
    }

    public void update(UserModel user) throws ConstraintViolationException, NotFoundException {
        var columnValueBinders = getColumnValueBinders(user);

        try {
            super.executeUpdate(columnValueBinders, user.getId());
        } catch (SQLException e) {
            handleSQLException(e, user.getEmail());
        }
    }

    private Map<String, SQLColumnValueBinder> getColumnValueBinders(UserModel user) {
        return Map.of(
        "email", (statement, index) -> statement.setString(index, user.getEmail()),
        "name", (statement, index) -> statement.setString(index, user.getName()),
        "isAdmin", (statement, index) -> statement.setBoolean(index, user.isAdmin())
        );
    }

    private void handleSQLException(SQLException e, String email) throws ConstraintViolationException {
        Optional<SQLExceptionEnum> optionalExceptionType = SQLExceptionParser.parse(e);

        boolean exceptionTypeNotFound = optionalExceptionType.isEmpty();
        if (exceptionTypeNotFound) {
            throw new RuntimeException(e);
        }

        switch (optionalExceptionType.get()) {
            case CONSTRAINT_UNIQUE:
            case CONSTRAINT_INDEX:
                String exceptionMessage = String.format("A user with this email (%s) already exists.", email);
                throw new ConstraintViolationException(exceptionMessage);
            case CONSTRAINT_NOTNULL:
                throw new ConstraintViolationException("A required field of the user is missing.");
        }
    }
}

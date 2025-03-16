package com.esgi.data.users.impl;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.core.exceptions.helpers.SQLExceptionEnum;
import com.esgi.core.exceptions.helpers.SQLExceptionParser;
import com.esgi.data.Repository;
import com.esgi.data.SQLColumnValueBinder;
import com.esgi.data.users.UserModel;
import com.esgi.data.users.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryImpl extends Repository<UserModel> implements UserRepository {

    public static final String TABLE_NAME = "users";

    private static final String EMAIL_COLUMN = "email";

    private static final String PASSWORD_COLUMN = "password";

    private static final String NAME_COLUMN = "name";

    private static final String IS_ADMIN_COLUMN = "isAdmin";

    public UserRepositoryImpl() {
        super(TABLE_NAME);
    }

    @Override
    protected UserModel parseSQLResult(ResultSet resultSet) throws SQLException {
        return new UserModel(
                resultSet.getInt("id"),
                resultSet.getString(EMAIL_COLUMN),
                resultSet.getBoolean(IS_ADMIN_COLUMN),
                resultSet.getString(NAME_COLUMN),
                resultSet.getString(PASSWORD_COLUMN)
        );
    }


    @Override
    public UserModel getByEmail(String email) throws InternalErrorException, NotFoundException {
        return super.getFirstByColumn(EMAIL_COLUMN, email);
    }

    public void update(UserModel user) throws ConstraintViolationException, NotFoundException {
        var columnValueBinders = getColumnValueBinders(user);

        try {
            super.executeUpdate(columnValueBinders, user.getId());
        } catch (SQLException e) {
            handleSQLException(e, user.getEmail());
        }
    }

    public void create(UserModel user) throws ConstraintViolationException {
        var columnValueBinders = getColumnValueBindersCreation(user);

        try{
            super.executeCreate(columnValueBinders,user);
        } catch (SQLException e) {
            handleSQLException(e, user.getEmail());
        }
    }

    private Map<String, SQLColumnValueBinder> getColumnValueBinders(UserModel user) {
        return Map.of(
            EMAIL_COLUMN, (statement, index) -> statement.setString(index, user.getEmail()),
            NAME_COLUMN, (statement, index) -> statement.setString(index, user.getName()),
            IS_ADMIN_COLUMN, (statement, index) -> statement.setBoolean(index, user.isAdmin())
        );
    }


    private Map<String, SQLColumnValueBinder> getColumnValueBindersCreation(UserModel user) {
        return Map.of(
            EMAIL_COLUMN, (statement, index) -> statement.setString(index, user.getEmail()),
            NAME_COLUMN, (statement, index) -> statement.setString(index, user.getName()),
            IS_ADMIN_COLUMN, (statement, index) -> statement.setBoolean(index, user.isAdmin()),
            PASSWORD_COLUMN,(statement, index) -> statement.setString(index, user.getPassword())
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

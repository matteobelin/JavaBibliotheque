package com.esgi.data.users.impl;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.Repository;
import com.esgi.data.sql.SQLColumnValue;
import com.esgi.data.users.UserModel;
import com.esgi.data.users.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
    public UserModel getByEmail(String email) throws NotFoundException {
        return super.getFirstWhereColumnEquals(EMAIL_COLUMN, email);
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
            var id = super.executeCreate(columnValueBinders);
            user.setId(id);
        } catch (SQLException e) {
            handleSQLException(e, user.getEmail());
        }
    }

    public void delete(Integer id) throws NotFoundException, ConstraintViolationException {
        try {
            super.deleteById(id);
        } catch (SQLException e) {
            throw new InternalErrorException(e);
        }
    }

    private List<SQLColumnValue<?>> getColumnValueBinders(UserModel user) {
        return List.of(
            new SQLColumnValue<>(EMAIL_COLUMN, user.getEmail()),
            new SQLColumnValue<>(NAME_COLUMN, user.getName()),
            new SQLColumnValue<>(IS_ADMIN_COLUMN, user.isAdmin())
        );
    }


    private List<SQLColumnValue<?>> getColumnValueBindersCreation(UserModel user) {
        return List.of(
            new SQLColumnValue<>(EMAIL_COLUMN, user.getEmail()),
            new SQLColumnValue<>(NAME_COLUMN, user.getName()),
            new SQLColumnValue<>(IS_ADMIN_COLUMN, user.isAdmin()),
            new SQLColumnValue<>(PASSWORD_COLUMN, user.getPassword())
        );
    }


    private void handleSQLException(SQLException e, String email) throws ConstraintViolationException {
        var exceptionType = super.parseSqlException(e);
        switch (exceptionType) {
            case CONSTRAINT_UNIQUE:
            case CONSTRAINT_INDEX:
                String exceptionMessage = String.format("A user with this email (%s) already exists.", email);
                throw new ConstraintViolationException(exceptionMessage);
            case CONSTRAINT_NOTNULL:
                throw new ConstraintViolationException("A required field of the user is missing.");
        }
    }
}

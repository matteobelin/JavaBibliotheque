package com.esgi.data.authors.impl;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.Repository;
import com.esgi.data.authors.AuthorModel;
import com.esgi.data.authors.AuthorRepository;
import com.esgi.data.sql.SQLColumnValue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AuthorRepositoryImpl extends Repository<AuthorModel> implements AuthorRepository {
    public static final String TABLE_NAME = "authors";

    public static final String NAME_COLUMN = "name";
    
    public AuthorRepositoryImpl() {
        super(TABLE_NAME);
    }

    @Override
    protected AuthorModel parseSQLResult(ResultSet resultSet) throws SQLException {
        return new AuthorModel(
                resultSet.getInt("id"),
                resultSet.getString(NAME_COLUMN)
        );
    }

    public AuthorModel getByName(String name) throws NotFoundException, InternalErrorException {
        return this.getFirstWhereColumnEquals(NAME_COLUMN,name);
    }

    public void create(AuthorModel author) throws ConstraintViolationException, InternalErrorException {
        var columnValues = getColumnValues(author);

        try{
            var id = super.executeCreate(columnValues);
            author.setId(id);
        } catch (SQLException e) {
            handleSQLException(e, author.getName());
        }
    }

    public void update(AuthorModel author) throws ConstraintViolationException, NotFoundException, InternalErrorException {
        var columnValueBinders = getColumnValues(author);

        try {
            super.executeUpdate(columnValueBinders, author.getId());
        } catch (SQLException e) {
            handleSQLException(e, author.getName());
        }
    }

    private List<SQLColumnValue<?>> getColumnValues(AuthorModel author) {
        return List.of(
            new SQLColumnValue<>(NAME_COLUMN, author.getName())
        );
    }

    public void delete(String name) throws NotFoundException, ConstraintViolationException, InternalErrorException {
        try {
            super.deleteByColumn(NAME_COLUMN, name);
        } catch (SQLException e) {
            this.handleSQLException(e, name);
        }
    }

    private void handleSQLException(SQLException e, String name) throws ConstraintViolationException, InternalErrorException {
        var exceptionType = super.parseSqlException(e);
        switch (exceptionType) {
            case CONSTRAINT_UNIQUE:
            case CONSTRAINT_INDEX:
            case CONSTRAINT:
                String exceptionMessage = String.format("An author with this name (%s) already exists.", name);
                throw new ConstraintViolationException(exceptionMessage);
            case CONSTRAINT_NOTNULL:
                throw new ConstraintViolationException("A required field of the author is missing.");
        }
    }
}



package com.esgi.data.authors.impl;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.core.exceptions.helpers.SQLExceptionEnum;
import com.esgi.core.exceptions.helpers.SQLExceptionParser;
import com.esgi.data.Repository;
import com.esgi.data.SQLColumnValueBinder;
import com.esgi.data.authors.AuthorModel;
import com.esgi.data.authors.AuthorRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public class AuthorRepositoryImpl extends Repository<AuthorModel> implements AuthorRepository {
    public static final String TABLE_NAME = "authors";

    public AuthorRepositoryImpl() {
        super(TABLE_NAME);
    }

    @Override
    protected AuthorModel parseSQLResult(ResultSet resultSet) throws SQLException {
        return new AuthorModel(
                resultSet.getInt("id"),
                resultSet.getString("name")
        );

    }

    private Map<String, SQLColumnValueBinder> getColumnValueBinders(AuthorModel author) {
        return Map.of(
                "name", (statement, index) -> statement.setString(index, author.getName())
        );
    }

    public void create(AuthorModel author) throws ConstraintViolationException {
        var columnValueBinders = getColumnValueBinders(author);

        try{
            super.executeCreate(columnValueBinders,author);
        } catch (SQLException e) {
            handleSQLException(e, author.getName());
        }
    }

    public void update(AuthorModel author) throws ConstraintViolationException, NotFoundException {
        var columnValueBinders = getColumnValueBinders(author);

        try {
            super.executeUpdate(columnValueBinders, author.getId());
        } catch (SQLException e) {
            handleSQLException(e, author.getName());
        }
    }

    public void delete(String name) throws NotFoundException, ConstraintViolationException {
        super.deleteByColumn("name", name);
    }

    private void handleSQLException(SQLException e, String name) throws ConstraintViolationException {
        Optional<SQLExceptionEnum> optionalExceptionType = SQLExceptionParser.parse(e);

        boolean exceptionTypeNotFound = optionalExceptionType.isEmpty();
        if (exceptionTypeNotFound) {
            throw new RuntimeException(e);
        }

        switch (optionalExceptionType.get()) {
            case CONSTRAINT_UNIQUE:
            case CONSTRAINT_INDEX:
                String exceptionMessage = String.format("An author with this name (%s) already exists.", name);
                throw new ConstraintViolationException(exceptionMessage);
            case CONSTRAINT_NOTNULL:
                throw new ConstraintViolationException("A required field of the author is missing.");
        }
    }

    public AuthorModel getByName(String name) throws NotFoundException {
        return this.getFirstByColumn("name",name);
    }
}



package com.esgi.data.authors.impl;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.helpers.SQLExceptionEnum;
import com.esgi.core.exceptions.helpers.SQLExceptionParser;
import com.esgi.data.Repository;
import com.esgi.data.authors.AuthorModel;
import com.esgi.data.authors.AuthorRepository;


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AuthorRepositoryImpl extends Repository<AuthorModel> implements AuthorRepository {
    @Override
    protected String getTableName(){
        return "authors";
    }

    @Override
    protected AuthorModel parseSQLResult(ResultSet resultSet) throws SQLException {
        return new AuthorModel(
                resultSet.getInt("id"),
                resultSet.getString("name")
        );

    }

    @Override
    public void create(AuthorModel author) throws ConstraintViolationException {
        try (var conn = DriverManager.getConnection(connectionString)){
            String sql = "INSERT INTO " + getTableName() + " (name) VALUES (?)";
            var statement = conn.prepareStatement(sql);
            statement.setString(1, author.getName());
            statement.execute();
        } catch (SQLException e) {
            Optional<SQLExceptionEnum> optionalExceptionType = SQLExceptionParser.parse(e);

            boolean exceptionTypeNotFound = optionalExceptionType.isEmpty();
            if (exceptionTypeNotFound) {
                throw new RuntimeException(e);
            }

            switch (optionalExceptionType.get()) {
                case CONSTRAINT_UNIQUE:
                    String exceptionMessage = String.format("An author with this name (%s) already exists.", author.getName());
                    throw new ConstraintViolationException(exceptionMessage);
                case CONSTRAINT_NOTNULL:
                    throw new ConstraintViolationException("A required field of the author is missing.");
            }
        }


    }

}

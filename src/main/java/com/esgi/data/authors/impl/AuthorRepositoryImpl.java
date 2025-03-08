package com.esgi.data.authors.impl;

import com.esgi.data.Repository;
import com.esgi.data.authors.AuthorModel;
import com.esgi.data.authors.AuthorRepository;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    protected String exceptionMessage(AuthorModel author) {
        return String.format("An author with this name (%s) already exists.", author.getName());
    }

            switch (optionalExceptionType.get()) {
                case CONSTRAINT_UNIQUE:
                case CONSTRAINT_INDEX:
                    String exceptionMessage = String.format("An author with this name (%s) already exists.", author.getName());
                    throw new ConstraintViolationException(exceptionMessage);
                case CONSTRAINT_NOTNULL:
                    throw new ConstraintViolationException("A required field of the author is missing.");
            }
        }


    }

}

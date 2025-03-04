package com.esgi.data.authors.impl;

import com.esgi.data.Repository;
import com.esgi.data.authors.AuthorModel;
import com.esgi.data.authors.AuthorRepository;
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
    public void create(AuthorModel model) {
        throw new RuntimeException("Not implemented yet");
    }

}

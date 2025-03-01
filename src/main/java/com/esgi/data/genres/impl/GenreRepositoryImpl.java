package com.esgi.data.genres.impl;

import com.esgi.data.Repository;
import com.esgi.data.genres.GenreModel;
import com.esgi.data.genres.GenreRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreRepositoryImpl extends Repository<GenreModel> implements GenreRepository {
    @Override
    public String getTableName(){return "genres";}

    @Override
    protected GenreModel parseSQLResult(ResultSet resultSet) throws SQLException{
    return new GenreModel(
            resultSet.getInt("id"),
            resultSet.getString("name")
    );
    }
}

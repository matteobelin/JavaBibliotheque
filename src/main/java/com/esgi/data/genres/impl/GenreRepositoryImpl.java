package com.esgi.data.genres.impl;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.core.exceptions.helpers.SQLExceptionEnum;
import com.esgi.core.exceptions.helpers.SQLExceptionParser;
import com.esgi.data.Repository;
import com.esgi.data.SQLColumnValueBinder;
import com.esgi.data.genres.GenreModel;
import com.esgi.data.genres.GenreRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public class GenreRepositoryImpl extends Repository<GenreModel> implements GenreRepository {
    public static final String TABLE_NAME = "genres";

    public GenreRepositoryImpl() {
        super(TABLE_NAME);
    }

    @Override
    protected GenreModel parseSQLResult(ResultSet resultSet) throws SQLException{
        return new GenreModel(
                resultSet.getInt("id"),
                resultSet.getString("name")
        );
    }

    private Map<String, SQLColumnValueBinder> getColumnValueBinders(GenreModel genre) {
        return Map.of(
                "name", (statement, index) -> statement.setString(index, genre.getName())
        );
    }

    public void create(GenreModel genre) throws ConstraintViolationException {
        var columnValueBinders = getColumnValueBinders(genre);

        try{
            super.executeCreate(columnValueBinders,genre);
        } catch (SQLException e) {
            handleSQLException(e, genre.getName());
        }
    }

    public GenreModel getByName(String name) throws NotFoundException {
        return this.getFirstByColumn("name",name);
    }

    public void update(GenreModel genre) throws ConstraintViolationException, NotFoundException {
        var columnValueBinders = getColumnValueBinders(genre);

        try {
            super.executeUpdate(columnValueBinders, genre.getId());
        } catch (SQLException e) {
            handleSQLException(e, genre.getName());
        }
    }

    public GenreModel getByName(String name) throws NotFoundException {
        return this.getFirstByColumn("name",name);
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
                String exceptionMessage = String.format("A genre with this name (%s) already exists.", name);
                throw new ConstraintViolationException(exceptionMessage);
            case CONSTRAINT_NOTNULL:
                throw new ConstraintViolationException("A required field of the genre is missing.");
        }
    }
}

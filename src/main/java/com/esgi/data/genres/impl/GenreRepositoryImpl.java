package com.esgi.data.genres.impl;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.Repository;
import com.esgi.data.genres.GenreModel;
import com.esgi.data.genres.GenreRepository;
import com.esgi.data.sql.SQLColumnValue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GenreRepositoryImpl extends Repository<GenreModel> implements GenreRepository {
    public static final String TABLE_NAME = "genres";

    public static final String NAME_COLUMN = "name";

    public GenreRepositoryImpl() {
        super(TABLE_NAME);
    }

    @Override
    protected GenreModel parseSQLResult(ResultSet resultSet) throws SQLException{
        return new GenreModel(
                resultSet.getInt("id"),
                resultSet.getString(NAME_COLUMN)
        );
    }

    private List<SQLColumnValue<?>> getColumnValueBinders(GenreModel genre) {
        return List.of(
                new SQLColumnValue<>(NAME_COLUMN, genre.getName())
        );
    }

    public void create(GenreModel genre) throws ConstraintViolationException, InternalErrorException {
        var columnValueBinders = getColumnValueBinders(genre);

        try{
            var id = super.executeCreate(columnValueBinders);
            genre.setId(id);
        } catch (SQLException e) {
            handleSQLException(e, genre.getName());
        }
    }
    public GenreModel getByName(String name) throws NotFoundException, InternalErrorException {
        return this.getFirstWhereColumnEquals(NAME_COLUMN,name);
    }

    public void update(GenreModel genre) throws ConstraintViolationException, NotFoundException, InternalErrorException {
        var columnValueBinders = getColumnValueBinders(genre);

        try {
            super.executeUpdate(columnValueBinders, genre.getId());
        } catch (SQLException e) {
            this.handleSQLException(e, genre.getName());
        }
    }

    public void delete(String name) throws NotFoundException, ConstraintViolationException, InternalErrorException {
        try {
            super.deleteByColumn(NAME_COLUMN, name);
        } catch (SQLException e) {
            this.handleSQLException(e, name);
        }
    }

    protected void handleSQLException(SQLException e, String genreName) throws ConstraintViolationException, InternalErrorException {
        var exceptionType = super.parseSqlException(e);

        switch (exceptionType) {
            case CONSTRAINT_UNIQUE:
            case CONSTRAINT_INDEX:
            case CONSTRAINT:
                String exceptionMessage = String.format("A genre with this name (%s) already exists.", genreName);
                throw new ConstraintViolationException(exceptionMessage);
            case CONSTRAINT_NOTNULL:
                throw new ConstraintViolationException("A required field of the genre is missing.");
        }
    }
}

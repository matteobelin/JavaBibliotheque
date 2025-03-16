package com.esgi.data.genreBook.impl;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.Repository;
import com.esgi.data.genreBook.GenreBookModel;
import com.esgi.data.genreBook.GenreBookRepository;
import com.esgi.data.sql.SQLColumnValue;
import com.esgi.data.sql.SQLWhereCondition;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.esgi.core.exceptions.helpers.SQLExceptionEnum.CONSTRAINT_NOTNULL;

public class GenreBookRepositoryImpl extends Repository<GenreBookModel> implements GenreBookRepository {

    public static final String TABLE_NAME = "genre_book";

    public static final String GENRE_ID_COLUMN = "genre_id";

    public static final String BOOK_ID_COLUMN = "book_id";

    public GenreBookRepositoryImpl() {
        super(TABLE_NAME);
    }

    @Override
    protected GenreBookModel parseSQLResult(ResultSet result) throws SQLException {
        return new GenreBookModel(
                result.getInt(GENRE_ID_COLUMN),
                result.getInt(BOOK_ID_COLUMN)
        );
    }

    @Override
    public List<GenreBookModel> findAllByGenreId(Integer genreId) throws InternalErrorException {
        return super.getAllWhereColumnEquals(GENRE_ID_COLUMN, genreId);
    }

    @Override
    public List<GenreBookModel> findAllByBookId(Integer bookId) throws InternalErrorException {
        return super.getAllWhereColumnEquals(BOOK_ID_COLUMN, bookId);
    }

    @Override
    public void createGenreBook(GenreBookModel genreBook) throws ConstraintViolationException, InternalErrorException {
        List<SQLColumnValue<?>> columnValues = List.of(
            new SQLColumnValue<>(GENRE_ID_COLUMN, genreBook.getGenreId()),
            new SQLColumnValue<>(BOOK_ID_COLUMN, genreBook.getBookId())
        );

        try {
            var id = super.executeCreate(columnValues);
            genreBook.setId(id);
        } catch (SQLException e) {
            this.handleSQLException(e);
        }
    }

    @Override
    public void deleteGenreBook(GenreBookModel genreBook) throws NotFoundException, ConstraintViolationException, InternalErrorException {
        try {
            super.deleteWhere(List.of(
                SQLWhereCondition.makeEqualCondition(GENRE_ID_COLUMN, genreBook.getGenreId()),
                SQLWhereCondition.makeEqualCondition(BOOK_ID_COLUMN, genreBook.getBookId())
            ));
        } catch (SQLException e) {
            this.handleSQLException(e);
        }
    }

    @Override
    public void deleteAllByGenreId(Integer genreId) throws NotFoundException, ConstraintViolationException, InternalErrorException {
        try {
            super.deleteByColumn(GENRE_ID_COLUMN, genreId);
        } catch (SQLException e) {
            this.handleSQLException(e);
        }
    }

    @Override
    public void deleteAllByBookId(Integer bookId) throws NotFoundException, ConstraintViolationException, InternalErrorException {
        try {
            super.deleteByColumn(BOOK_ID_COLUMN, bookId);
        } catch (SQLException e) {
            this.handleSQLException(e);
        }
    }

    private void handleSQLException(SQLException e) throws ConstraintViolationException, InternalErrorException {
        var exceptionType = super.parseSqlException(e);
        if(exceptionType == CONSTRAINT_NOTNULL){
            throw new ConstraintViolationException("A required field of the genre_book table is missing.");
        }
    }
}

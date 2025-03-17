package com.esgi.data.genreBook.impl;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.Repository;
import com.esgi.data.genreBook.GenreBookModel;
import com.esgi.data.genreBook.GenreBookRepository;
import com.esgi.data.sql.SQLColumnValue;

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
    public void createGenreBook(GenreBookModel genreBook) throws ConstraintViolationException {
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
    public void deleteAllByBookId(Integer bookId) throws NotFoundException, ConstraintViolationException {
        try {
            super.deleteByColumn(BOOK_ID_COLUMN, bookId);
        } catch (SQLException e) {
            this.handleSQLException(e);
        }
    }

    private void handleSQLException(SQLException e) throws ConstraintViolationException {
        var exceptionType = super.parseSqlException(e);
        if(exceptionType == CONSTRAINT_NOTNULL){
            throw new ConstraintViolationException("A required field of the genre_book table is missing.");
        }
    }
}

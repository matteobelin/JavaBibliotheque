package com.esgi.data.genreBook.impl;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.core.exceptions.helpers.SQLExceptionEnum;
import com.esgi.core.exceptions.helpers.SQLExceptionParser;
import com.esgi.data.Repository;
import com.esgi.data.SQLColumnValueBinder;
import com.esgi.data.genreBook.GenreBookModel;
import com.esgi.data.genreBook.GenreBookRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.esgi.core.exceptions.helpers.SQLExceptionEnum.CONSTRAINT_NOTNULL;

public class GenreBookRepositoryImpl extends Repository<GenreBookModel> implements GenreBookRepository {

    public static final String TABLE_NAME = "genre_book";

    private static final String GENRE_ID_COLUMN = "genre_id";

    private static final String BOOK_ID_COLUMN = "book_id";

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
    public List<GenreBookModel> findAllByGenreId(Integer genreId) throws NotFoundException {
        return super.getAllByColumn(GENRE_ID_COLUMN, genreId);
    }

    @Override
    public List<GenreBookModel> findAllByBookId(Integer bookId) throws NotFoundException {
        return super.getAllByColumn(BOOK_ID_COLUMN, bookId);
    }

    @Override
    public void createGenreBook(GenreBookModel genreBook) throws ConstraintViolationException {
        var columnValueBinders = this.makeColumnValueBinders(genreBook);

        try {
            super.executeCreate(columnValueBinders, genreBook);
        } catch (SQLException e) {
            this.handleSQLException(e);
        }
    }

    @Override
    public void deleteGenreBook(GenreBookModel genreBook) throws NotFoundException, ConstraintViolationException {
        super.deleteWhere(Map.of(
            GENRE_ID_COLUMN, (statement, index) -> statement.setInt(index, genreBook.getGenreId()),
            BOOK_ID_COLUMN, (statement, index) -> statement.setInt(index, genreBook.getBookId())
        ));
    }

    @Override
    public void deleteAllByGenreId(Integer genreId) throws NotFoundException, ConstraintViolationException {
        super.deleteByColumn(GENRE_ID_COLUMN, genreId);
    }

    @Override
    public void deleteAllByBookId(Integer bookId) throws NotFoundException, ConstraintViolationException {
        super.deleteByColumn(BOOK_ID_COLUMN, bookId);
       }

    private Map<String, SQLColumnValueBinder> makeColumnValueBinders(GenreBookModel genreBook) {
        return Map.of(
                GENRE_ID_COLUMN, (statement, index) -> statement.setInt(index, genreBook.getGenreId()),
                BOOK_ID_COLUMN, (statement, index) -> statement.setInt(index, genreBook.getBookId())
        );
    }

    private void handleSQLException(SQLException e) throws ConstraintViolationException {
        Optional<SQLExceptionEnum> optionalExceptionType = SQLExceptionParser.parse(e);

        boolean exceptionTypeNotFound = optionalExceptionType.isEmpty();
        if (exceptionTypeNotFound) {
            throw new RuntimeException(e);
        }

        if(optionalExceptionType.get() == CONSTRAINT_NOTNULL){
            throw new ConstraintViolationException("A required field of the genre_book table is missing.");
        }
    }
}

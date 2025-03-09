package com.esgi.data.books.impl;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.core.exceptions.helpers.SQLExceptionEnum;
import com.esgi.core.exceptions.helpers.SQLExceptionParser;
import com.esgi.data.Repository;
import com.esgi.data.SQLColumnValueBinder;
import com.esgi.data.books.BookModel;
import com.esgi.data.books.BookRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.esgi.core.exceptions.helpers.SQLExceptionEnum.CONSTRAINT_NOTNULL;

public class BookRepositoryImpl extends Repository<BookModel> implements BookRepository {
    @Override
    protected String getTableName() {
        return "books";
    }

    @Override
    public BookModel getById(Integer id) throws NotFoundException {
        BookModel book = super.getById(id);
        List<Integer> genreIds;
        try {
            genreIds = getListById(id, "book_id", "genre_id", "genre_book");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        book.setGenreIds(genreIds);
        return book;
    }


    @Override
    protected BookModel parseSQLResult(ResultSet resultSet) throws SQLException {
        int bookId = resultSet.getInt("id");
        String title = resultSet.getString("title");
        int authorId = resultSet.getInt("author_id");

        return new BookModel(bookId, title, authorId, new ArrayList<>());
    }


    private Map<String, SQLColumnValueBinder> getColumnValueBinders(BookModel book) {
        return Map.of(
                "title", (statement, index) -> statement.setString(index, book.getTitle()),
                "author_id", (statement, index) -> statement.setInt(index, book.getAuthorId())
        );
    }

    private Map<String, SQLColumnValueBinder> getColumnValueBindersBookGenre(BookModel book,Integer genreId){
        return Map.of(
                "genre_id", (statement, index) -> statement.setInt(index, genreId),
                "book_id", (statement, index) -> statement.setInt(index, book.getId())
        );
    }

    @Override
    public void create(BookModel book) throws ConstraintViolationException{
        var columnValueBinders = getColumnValueBinders(book);
        try{
            super.executeCreate(columnValueBinders,book);
        } catch (SQLException e) {
            handleSQLException(e);
        }
        setTableName("genre_book");
        for(Integer genreId : book.getGenreIds()){
            columnValueBinders = getColumnValueBindersBookGenre(book,genreId);
            try{
                super.executeCreate(columnValueBinders,book);
            } catch (SQLException e) {
                handleSQLException(e);
            }
        }
    }

    private void handleSQLException(SQLException e) throws ConstraintViolationException {
        Optional<SQLExceptionEnum> optionalExceptionType = SQLExceptionParser.parse(e);

        boolean exceptionTypeNotFound = optionalExceptionType.isEmpty();
        if (exceptionTypeNotFound) {
            throw new RuntimeException(e);
        }

        if(optionalExceptionType.get() == CONSTRAINT_NOTNULL){
                throw new ConstraintViolationException("A required field of the author is missing.");
        }
    }

}

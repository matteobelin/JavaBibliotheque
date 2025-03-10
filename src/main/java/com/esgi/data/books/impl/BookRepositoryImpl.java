package com.esgi.data.books.impl;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.core.exceptions.helpers.SQLExceptionEnum;
import com.esgi.core.exceptions.helpers.SQLExceptionParser;
import com.esgi.data.Repository;
import com.esgi.data.SQLColumnValueBinder;
import com.esgi.data.books.BookModel;
import com.esgi.data.books.BookRepository;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.esgi.core.exceptions.helpers.SQLExceptionEnum.CONSTRAINT_NOTNULL;

public class BookRepositoryImpl extends Repository<BookModel> implements BookRepository {

    @Override
    public BookModel getById(Integer id) throws NotFoundException {
        setTableName("books");
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

    public List<BookModel> getByTitle(String title) throws NotFoundException {
        setTableName("books");
        List<BookModel> books = this.getAllByColumn("title",title);
        List<Integer> genreIds;
        for (BookModel book : books) {
            try {
                genreIds = getListById(book.getId(), "book_id", "genre_id", "genre_book");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            book.setGenreIds(genreIds);
        }
        return books;
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

    public boolean existInDb(BookModel bookModel) throws NotFoundException {
        List<BookModel> books =getByTitle(bookModel.getTitle());
        return books.stream()
                .anyMatch(book -> book.getTitle()
                        .equals(bookModel.getTitle()) && book.getAuthorId().equals(bookModel.getAuthorId()));
    }

    public void create(BookModel book) throws ConstraintViolationException, NotFoundException {
        if(existInDb(book)){
            throw new ConstraintViolationException("A book with this name and this author already exists.");
        }
        setTableName("books");
        var columnValueBinders = getColumnValueBinders(book);
        try{
            super.executeCreate(columnValueBinders,book);
        } catch (SQLException e) {
            handleSQLException(e);
        }
        Integer bookId=book.getId();
        setTableName("genre_book");
        for(Integer genreId : book.getGenreIds()){
            columnValueBinders = getColumnValueBindersBookGenre(book,genreId);
            try{
                super.executeCreate(columnValueBinders,book);
                book.setId(bookId);
            } catch (SQLException e) {
                handleSQLException(e);
            }
        }
    }

    public void update(BookModel book) throws ConstraintViolationException, NotFoundException {
        if(existInDb(book)){
            throw new ConstraintViolationException("A book with this name and this author already exists.");
        }
        setTableName("books");
        var columnValueBinders = getColumnValueBinders(book);
        try {
            super.executeUpdate(columnValueBinders,book.getId());
        } catch (SQLException e) {
            handleSQLException(e);
        }
        Integer bookId=book.getId();
        setTableName("genre_book");
        deleteBookGenre(book.getId());
        for(Integer genreId : book.getGenreIds()){
            columnValueBinders = getColumnValueBindersBookGenre(book,genreId);
            try{
                super.executeCreate(columnValueBinders,book);
                book.setId(bookId);
            } catch (SQLException e) {
                handleSQLException(e);
            }
        }
    }

    public List<BookModel> getAllBook(){
        setTableName("books");
        List<BookModel> books = this.getAll();
        List<Integer> genreIds;
        for (BookModel book : books) {
            try {
                genreIds = getListById(book.getId(), "book_id", "genre_id", "genre_book");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            book.setGenreIds(genreIds);
        }
        return books;

    }


    public void deleteBookGenre(Integer id) throws NotFoundException {
        try (var conn = DriverManager.getConnection(connectionString)) {
            String sql = "DELETE FROM " + getTableName() + " WHERE book_id = ?";
            var statement = conn.prepareStatement(sql);
            statement.setObject(1, id);

            int rowsDeleted = statement.executeUpdate();
            if(rowsDeleted == 0) {
                throw new NotFoundException(this.notFoundErrorMessage("id", id));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
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

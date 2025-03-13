package com.esgi.data.books.impl;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.core.exceptions.helpers.SQLExceptionEnum;
import com.esgi.core.exceptions.helpers.SQLExceptionParser;
import com.esgi.data.Repository;
import com.esgi.data.SQLColumnValueBinder;
import com.esgi.data.books.BookModel;
import com.esgi.data.books.BookRepository;
import com.esgi.data.genreBook.GenreBookModel;
import com.esgi.data.genreBook.GenreBookRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.esgi.core.exceptions.helpers.SQLExceptionEnum.CONSTRAINT_NOTNULL;

public class BookRepositoryImpl extends Repository<BookModel> implements BookRepository {

    public static final String TABLE_NAME = "books";

    private final GenreBookRepository genreBookRepository;

    public BookRepositoryImpl(GenreBookRepository genreBookRepository) {
        super(TABLE_NAME);

        this.genreBookRepository = genreBookRepository;
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

    public List<BookModel> getByTitle(String title) throws NotFoundException {
        return getBooksByColumn("title", title);
    }

    public List<BookModel> getByAuthor(Integer authorId) throws NotFoundException {
        return getBooksByColumn("author_id", authorId);
    }

    private List<BookModel> getBooksByColumn(String column, Object value) throws NotFoundException {
        List<BookModel> books = this.getAllByColumn(column, value);
        for (BookModel book : books) {
            try {
                List<Integer> genreIds = getListById(book.getId(), "book_id", "genre_id", "genre_book");
                book.setGenreIds(genreIds);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return books;
    }

    public List<BookModel> getByGenre(Integer genreId) throws NotFoundException {
        List<GenreBookModel> booksId=genreBookRepository.findAllByGenreId(genreId);
        List<BookModel> books = new ArrayList<>();
        for (GenreBookModel book : booksId) {
            books.add(getById(book.getBookId()));
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

        var columnValueBinders = getColumnValueBinders(book);
        try{
            super.executeCreate(columnValueBinders,book);
        } catch (SQLException e) {
            handleSQLException(e);
        }

        Integer bookId = book.getId();

        for(Integer genreId : book.getGenreIds()){
            var genreBook = new GenreBookModel(genreId, bookId);
            this.genreBookRepository.createGenreBook(genreBook);
        }
    }

    public void update(BookModel book) throws ConstraintViolationException, NotFoundException {
        if(existInDb(book)){
            throw new ConstraintViolationException("A book with this name and this author already exists.");
        }

        var columnValueBinders = getColumnValueBinders(book);
        try {
            super.executeUpdate(columnValueBinders,book.getId());
        } catch (SQLException e) {
            handleSQLException(e);
        }
        Integer bookId = book.getId();

        this.genreBookRepository.deleteAllByBookId(bookId);

        for(Integer genreId : book.getGenreIds()){
            var genreBook = new GenreBookModel(genreId, bookId);
            this.genreBookRepository.createGenreBook(genreBook);
        };
    }

    public void delete(Integer id) throws NotFoundException, ConstraintViolationException {
        this.genreBookRepository.deleteAllByBookId(id);
        super.delete(id);
    }

    private Map<String, SQLColumnValueBinder> getColumnValueBinders(BookModel book) {
        return Map.of(
                "title", (statement, index) -> statement.setString(index, book.getTitle()),
                "author_id", (statement, index) -> statement.setInt(index, book.getAuthorId())
        );
    }

    public List<BookModel> getAllBook(){
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

package com.esgi.data.books.impl;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.Repository;
import com.esgi.data.authors.impl.AuthorRepositoryImpl;
import com.esgi.data.books.BookModel;
import com.esgi.data.books.BookRepository;
import com.esgi.data.genreBook.GenreBookModel;
import com.esgi.data.genreBook.GenreBookRepository;
import com.esgi.data.genreBook.impl.GenreBookRepositoryImpl;
import com.esgi.data.genres.impl.GenreRepositoryImpl;
import com.esgi.data.sql.SQLColumnValue;
import com.esgi.data.sql.SQLComparator;
import com.esgi.data.sql.SQLWhereCondition;
import com.esgi.data.sql.builder.SQLBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.esgi.core.exceptions.helpers.SQLExceptionEnum.CONSTRAINT_NOTNULL;

public class BookRepositoryImpl extends Repository<BookModel> implements BookRepository {

    public static final String TABLE_NAME = "books";


    public static final String TITLE_COLUMN = "title";

    public static final String AUTHOR_ID_COLUMN = "author_id";

    public static final String BOOK_ID_COLUMN = "book_id";

    public static final String GENRE_ID_COLUMN = "genre_id";

    public static final String GENRE_BOOK_TABLE = "genre_book";


    private final GenreBookRepository genreBookRepository;

    public BookRepositoryImpl(GenreBookRepository genreBookRepository) {
        super(TABLE_NAME);

        this.genreBookRepository = genreBookRepository;
    }

    @Override
    public BookModel getById(Integer id) throws NotFoundException, InternalErrorException {
        try {
            BookModel book = super.getById(id);
            List<Integer> genreIds = super.getListById(id, BOOK_ID_COLUMN, GENRE_ID_COLUMN, GENRE_BOOK_TABLE);
            book.setGenreIds(genreIds);
            return book;
        } catch (SQLException e) {
            throw new InternalErrorException(e);
        }
    }

    public List<BookModel> getByTitle(String title) throws InternalErrorException {
        return getBooksByColumn(TITLE_COLUMN, title);
    }

    public List<BookModel> getByAuthor(Integer authorId) throws InternalErrorException {
        return getBooksByColumn(AUTHOR_ID_COLUMN, authorId);
    }

    private List<BookModel> getBooksByColumn(String column, Object value) throws InternalErrorException {
        try {
            List<BookModel> books = this.getAllWhereColumnEquals(column, value);
            for (BookModel book : books) {
                List<Integer> genreIds = super.getListById(book.getId(), BOOK_ID_COLUMN, GENRE_ID_COLUMN, GENRE_BOOK_TABLE);
                book.setGenreIds(genreIds);
            }
            return books;
        } catch (SQLException e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    public List<BookModel> searchBook(String searchValue) throws InternalErrorException {
        String containsValue = "%" + searchValue + "%";
        String titleColumn = TABLE_NAME + "." + TITLE_COLUMN;
        String idColumn = TABLE_NAME + ".id";
        String authorIdColumn = TABLE_NAME + "." + AUTHOR_ID_COLUMN;
        var titleContainsValue = new SQLWhereCondition<>(titleColumn, SQLComparator.LIKE, containsValue);

        String authorTableName = AuthorRepositoryImpl.TABLE_NAME;
        String authorTableIdColumn = AuthorRepositoryImpl.TABLE_NAME + ".id";
        String authorNameColumn = authorTableName  + "." + AuthorRepositoryImpl.NAME_COLUMN;
        var authorContainsValue = new SQLWhereCondition<>(authorNameColumn, SQLComparator.LIKE, containsValue);

        String genreBookTableName = GenreBookRepositoryImpl.TABLE_NAME;
        String genreBookTableBookIdColumn = genreBookTableName + "." + GenreBookRepositoryImpl.BOOK_ID_COLUMN;
        String genreBookTableGenreIdColumn = genreBookTableName + "." + GenreBookRepositoryImpl.GENRE_ID_COLUMN;

        String genreTableName = GenreRepositoryImpl.TABLE_NAME;
        String genreTableIdColumn = genreTableName  + ".id";
        String genreTableNameColumn = genreTableName + "." + GenreRepositoryImpl.NAME_COLUMN;
        var genreContainsValue = new SQLWhereCondition<>(genreTableNameColumn, SQLComparator.LIKE, containsValue);

        var sql = SQLBuilder.selectAll()
                .distinct()
                .from(TABLE_NAME)
                .join(authorTableName).on(authorIdColumn, authorTableIdColumn)
                .join(genreBookTableName).on(idColumn, genreBookTableBookIdColumn)
                .join(genreTableName).on(genreBookTableGenreIdColumn, genreTableIdColumn)
                .where(titleContainsValue)
                .or(authorContainsValue)
                .or(genreContainsValue)
                .build();

        List<SQLWhereCondition<?>> conditions = List.of(
            titleContainsValue,
            authorContainsValue,
            genreContainsValue
        );
        try(var result = super.executeQuery(sql, conditions)) {
            var books = super.resultSetToList(result.getResultSet());
            for (BookModel book : books) {
                List<Integer> genreIds = super.getListById(book.getId(), BOOK_ID_COLUMN, GENRE_ID_COLUMN, GENRE_BOOK_TABLE);
                book.setGenreIds(genreIds);
            }
            return books;
        } catch (SQLException e) {
            throw new InternalErrorException(e);
        }
    }

    @Override
    protected BookModel parseSQLResult(ResultSet resultSet) throws SQLException {
        int bookId = resultSet.getInt("id");
        String title = resultSet.getString(TITLE_COLUMN);
        int authorId = resultSet.getInt(AUTHOR_ID_COLUMN);

        return new BookModel(bookId, title, authorId, new ArrayList<>());
    }

    public boolean existInDb(BookModel bookModel) throws InternalErrorException {
        List<BookModel> books = getByTitle(bookModel.getTitle());
        return books.stream()
                .anyMatch(book -> book.getTitle()
                        .equals(bookModel.getTitle()) && book.getAuthorId().equals(bookModel.getAuthorId()));
    }

    public void create(BookModel book) throws ConstraintViolationException, NotFoundException, InternalErrorException {
        if(existInDb(book)){
            throw new ConstraintViolationException("A book with this name and this author already exists.");
        }

        var columnValueBinders = getColumnValueBinders(book);
        try {
            Integer bookId  = super.executeCreate(columnValueBinders);
            book.setId(bookId);

            for(Integer genreId : book.getGenreIds()){
                var genreBook = new GenreBookModel(genreId, bookId);
                this.genreBookRepository.createGenreBook(genreBook);
            }
        } catch (SQLException e) {
            this.handleSQLException(e);
        }
    }

    public void update(BookModel book) throws ConstraintViolationException, NotFoundException, InternalErrorException {
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

    public void delete(Integer id) throws NotFoundException, InternalErrorException, ConstraintViolationException {
        BookModel book = this.getById(id);

        boolean bookHasGenres = !book.getGenreIds().isEmpty();
        if(bookHasGenres){
            this.genreBookRepository.deleteAllByBookId(id);
        }

        try {
            super.deleteById(id);
        } catch (SQLException e) {
            this.handleSQLException(e);
        }
    }

    private List<SQLColumnValue<?>> getColumnValueBinders(BookModel book) {
        return List.of(
            new SQLColumnValue<>(TITLE_COLUMN, book.getTitle()),
            new SQLColumnValue<>(AUTHOR_ID_COLUMN, book.getAuthorId())
        );
    }

    public List<BookModel> getAllBook() throws InternalErrorException {
        List<BookModel> books = this.getAll();
        List<Integer> genreIds;
        for (BookModel book : books) {
            try {
                genreIds = getListById(book.getId(), BOOK_ID_COLUMN, GENRE_ID_COLUMN, GENRE_BOOK_TABLE);
            } catch (SQLException e) {
                throw new InternalErrorException(e);
            }
            book.setGenreIds(genreIds);
        }
        return books;

    }

    private void handleSQLException(SQLException e) throws ConstraintViolationException, InternalErrorException {
        var exceptionType = super.parseSqlException(e);

        if(exceptionType == CONSTRAINT_NOTNULL){
            throw new ConstraintViolationException("A required field of the book is missing.");
        }
    }
}

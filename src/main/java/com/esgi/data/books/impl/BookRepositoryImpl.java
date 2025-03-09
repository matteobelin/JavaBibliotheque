package com.esgi.data.books.impl;

import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.Repository;
import com.esgi.data.books.BookModel;
import com.esgi.data.books.BookRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    protected String exceptionMessage(BookModel model) {
        return "";
    }

    @Override
    protected BookModel parseSQLResult(ResultSet resultSet) throws SQLException {
        int bookId = resultSet.getInt("id");
        String title = resultSet.getString("title");
        int authorId = resultSet.getInt("author_id");

        return new BookModel(bookId, title, authorId, new ArrayList<>());
    }

    @Override
    protected List<String> extractColumnNames(BookModel bookModel) {
        List<String> columns = super.extractColumnNames(bookModel);
        columns.removeIf(column -> column.equals("genreids"));
        return columns;
    }

    @Override
    protected void setPreparedStatementValues(BookModel bookModel, java.sql.PreparedStatement statement) {
        try {

            Method[] methods = bookModel.getClass().getDeclaredMethods();
            int index = 1;

            for (Method method : methods) {
                if (isGetter(method) && !method.getName().equals("getGenreIds")) {
                    try {
                        Object value = method.invoke(bookModel);
                        statement.setObject(index++, value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(BookModel bookModel) throws ConstraintViolationException {
        super.create(bookModel);
        Integer bookId = bookModel.getId();

        for (Integer genreId : bookModel.getGenreIds()) {
            try (var conn = DriverManager.getConnection(connectionString)) {
                String sql = "INSERT INTO genre_book  (genre_id,book_id) VALUES (?,?)";
                var statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, genreId);
                statement.setInt(2, bookId);
                statement.execute();

            } catch (SQLException e) {
                Optional<SQLExceptionEnum> optionalExceptionType = SQLExceptionParser.parse(e);

                boolean exceptionTypeNotFound = optionalExceptionType.isEmpty();
                if (exceptionTypeNotFound) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}

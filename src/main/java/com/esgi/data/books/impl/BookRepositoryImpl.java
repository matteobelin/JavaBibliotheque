package com.esgi.data.books.impl;

import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.Repository;
import com.esgi.data.books.BookModel;
import com.esgi.data.books.BookRepository;

import java.sql.DriverManager;
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
        List<Integer> genreIds = new ArrayList<>();
        try {
            genreIds = getListById(id, "book_id", "genre_id", "genre_book",Integer.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        book.setGenre_ids(genreIds);
        return book;
    }

    @Override
    protected BookModel parseSQLResult(ResultSet resultSet) throws SQLException {
        int bookId = resultSet.getInt("id");
        String title = resultSet.getString("title");
        int authorId = resultSet.getInt("author_id");

        return new BookModel(bookId, title, authorId, new ArrayList<>());
    }

    @Override
    public void create(BookModel model) {
        throw new RuntimeException("Not implemented yet");
    }

}

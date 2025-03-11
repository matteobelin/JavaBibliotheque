package com.esgi.domain.books;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;

import java.util.List;

public interface BookService {
    BookEntity getBookById(int id) throws NotFoundException;
    void createBook(BookEntity book) throws ConstraintViolationException, NotFoundException;
    void updateBook(BookEntity book) throws ConstraintViolationException, NotFoundException;
    List<BookEntity> getAllBooks();
    void deleteBook(int id) throws NotFoundException,ConstraintViolationException;
    List<BookEntity> getBooksByAuthor(int authorId) throws NotFoundException;
    List<BookEntity> getBooksByTitle(String title) throws NotFoundException;
    List<BookEntity> getBooksByGenre(int genreId) throws NotFoundException;
}

package com.esgi.domain.books;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;

import java.util.List;

public interface BookService {
    BookEntity getBookById(int id) throws NotFoundException, InternalErrorException;
    BookEntity createBook(BookEntity book) throws ConstraintViolationException, NotFoundException, InternalErrorException;
    void updateBook(BookEntity book) throws ConstraintViolationException, NotFoundException, InternalErrorException;
    List<BookEntity> getAllBooks() throws NotFoundException, InternalErrorException;
    void deleteBook(int id) throws NotFoundException, ConstraintViolationException, InternalErrorException;
    List<BookEntity> getBooksByAuthor(int authorId) throws NotFoundException, InternalErrorException;
    List<BookEntity> getBooksByTitle(String title) throws NotFoundException, InternalErrorException;
    List<BookEntity> getBooksByGenre(int genreId) throws NotFoundException, InternalErrorException;
}

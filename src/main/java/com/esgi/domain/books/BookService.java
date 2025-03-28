package com.esgi.domain.books;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;

import java.util.List;

public interface BookService {
    BookEntity getBookById(int id) throws NotFoundException;
    BookEntity createBook(BookEntity book) throws ConstraintViolationException, NotFoundException;
    void updateBook(BookEntity book) throws ConstraintViolationException, NotFoundException;
    List<BookEntity> getAllBooks() throws NotFoundException;
    void deleteBook(int id) throws NotFoundException, ConstraintViolationException;
    List<BookEntity> searchBook(String searchValue) throws NotFoundException;
}

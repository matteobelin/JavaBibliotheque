package com.esgi.domain.books;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;

public interface BookService {
    BookEntity getBookById(int id) throws NotFoundException;
    void createBook(BookEntity book) throws ConstraintViolationException, NotFoundException;
}

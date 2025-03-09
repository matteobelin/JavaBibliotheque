package com.esgi.data.books;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;

public interface BookRepository {
    BookModel getById(Integer id) throws NotFoundException;
    void create(BookModel book) throws ConstraintViolationException;
}

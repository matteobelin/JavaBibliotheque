package com.esgi.domain.books;

import com.esgi.core.exceptions.NotFoundException;

public interface BookService {
    BookEntity getBookById(int id) throws NotFoundException;
}

package com.esgi.data.genreBook;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;

public interface GenreBookRepository {

    void createGenreBook(GenreBookModel genreBook) throws ConstraintViolationException;

    void deleteAllByBookId(Integer bookId) throws NotFoundException, ConstraintViolationException;
}

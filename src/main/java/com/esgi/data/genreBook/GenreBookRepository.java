package com.esgi.data.genreBook;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;

import java.util.List;

public interface GenreBookRepository {

    List<GenreBookModel> findAllByGenreId(Integer genreId) throws NotFoundException;

    List<GenreBookModel> findAllByBookId(Integer bookId) throws NotFoundException;

    void createGenreBook(GenreBookModel genreBook) throws ConstraintViolationException;

    void deleteGenreBook(GenreBookModel genreBook) throws NotFoundException, ConstraintViolationException;

    void deleteAllByGenreId(Integer genreId) throws NotFoundException, ConstraintViolationException;

    void deleteAllByBookId(Integer bookId) throws NotFoundException, ConstraintViolationException;
}

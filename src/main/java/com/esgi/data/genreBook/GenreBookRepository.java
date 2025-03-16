package com.esgi.data.genreBook;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;

import java.util.List;

public interface GenreBookRepository {

    List<GenreBookModel> findAllByGenreId(Integer genreId) throws NotFoundException, InternalErrorException;

    List<GenreBookModel> findAllByBookId(Integer bookId) throws NotFoundException, InternalErrorException;

    void createGenreBook(GenreBookModel genreBook) throws ConstraintViolationException, InternalErrorException;

    void deleteGenreBook(GenreBookModel genreBook) throws NotFoundException, ConstraintViolationException, InternalErrorException;

    void deleteAllByGenreId(Integer genreId) throws NotFoundException, ConstraintViolationException, InternalErrorException;

    void deleteAllByBookId(Integer bookId) throws NotFoundException, ConstraintViolationException, InternalErrorException;
}

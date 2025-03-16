package com.esgi.data.books;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;

import java.util.List;

public interface BookRepository {
    BookModel getById(Integer id) throws NotFoundException, InternalErrorException;
    void create(BookModel book) throws ConstraintViolationException, NotFoundException, InternalErrorException;
    List<BookModel> getByTitle(String title) throws NotFoundException, InternalErrorException;
    List<BookModel> getByAuthor(Integer authorId) throws NotFoundException, InternalErrorException;
    List<BookModel> getByGenre(Integer genreId) throws NotFoundException, InternalErrorException;
    void update(BookModel book) throws NotFoundException, ConstraintViolationException, InternalErrorException;
    List<BookModel> getAllBook();
    void delete(Integer id) throws NotFoundException, ConstraintViolationException, InternalErrorException;

}

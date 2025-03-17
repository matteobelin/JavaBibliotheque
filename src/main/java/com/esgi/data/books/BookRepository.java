package com.esgi.data.books;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;

import java.util.List;

public interface BookRepository {
    BookModel getById(Integer id) throws NotFoundException;
    void create(BookModel book) throws ConstraintViolationException, NotFoundException;
    List<BookModel> getByTitle(String title) throws NotFoundException;
    List<BookModel> getByAuthor(Integer authorId) throws NotFoundException;
    List<BookModel> searchBook(String searchValue) ;
    void update(BookModel book) throws NotFoundException, ConstraintViolationException;
    List<BookModel> getAllBook() ;
    void delete(Integer id) throws NotFoundException, ConstraintViolationException;

}

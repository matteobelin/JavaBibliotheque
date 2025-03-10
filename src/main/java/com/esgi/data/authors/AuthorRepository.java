package com.esgi.data.authors;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;



public interface AuthorRepository {
    AuthorModel getById(Integer id) throws NotFoundException;
    void create(AuthorModel author) throws ConstraintViolationException;
    void update(AuthorModel author) throws ConstraintViolationException,NotFoundException;
    AuthorModel getByName(String name) throws NotFoundException;
    void delete(Integer id) throws NotFoundException;
}

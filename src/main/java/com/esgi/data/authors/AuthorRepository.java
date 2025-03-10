package com.esgi.data.authors;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;



public interface AuthorRepository {
    AuthorModel getById(Integer id) throws NotFoundException;
    void create(AuthorModel author) throws ConstraintViolationException;
    AuthorModel getByName(String name) throws NotFoundException;
}

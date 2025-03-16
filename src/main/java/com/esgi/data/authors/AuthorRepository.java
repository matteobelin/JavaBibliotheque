package com.esgi.data.authors;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;

import java.util.List;


public interface AuthorRepository {
    AuthorModel getById(Integer id) throws NotFoundException, InternalErrorException;
    AuthorModel getByName(String name) throws NotFoundException, InternalErrorException;
    void create(AuthorModel author) throws ConstraintViolationException, InternalErrorException;
    void update(AuthorModel author) throws ConstraintViolationException, NotFoundException, InternalErrorException;
    void delete(String name) throws NotFoundException, ConstraintViolationException, InternalErrorException;
    List<AuthorModel> getAll() throws InternalErrorException;
}

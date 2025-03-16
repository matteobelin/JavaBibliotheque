package com.esgi.data.genres;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;

import java.util.List;

public interface GenreRepository{
    GenreModel getById(Integer id) throws NotFoundException, InternalErrorException;
    List<GenreModel> getAll();
    void create(GenreModel genre) throws ConstraintViolationException;
    void update(GenreModel genre) throws ConstraintViolationException,NotFoundException;
    GenreModel getByName(String name) throws NotFoundException, InternalErrorException;
    void delete(String name) throws NotFoundException, ConstraintViolationException;
}

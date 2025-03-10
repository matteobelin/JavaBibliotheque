package com.esgi.data.genres;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;

public interface GenreRepository{
    GenreModel getById(Integer id) throws NotFoundException;
    void create(GenreModel genre) throws ConstraintViolationException;
    void update(GenreModel genre) throws ConstraintViolationException,NotFoundException;
    GenreModel getByName(String name) throws NotFoundException;
}

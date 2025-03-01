package com.esgi.data.genres;

import com.esgi.core.exceptions.NotFoundException;

public interface GenreRepository{
    GenreModel getById(Integer id) throws NotFoundException;
}

package com.esgi.domain.genres;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;

public interface GenreService {
    GenreEntity getGenreById(int id) throws NotFoundException;
    void createGenre(GenreEntity genre) throws ConstraintViolationException;
    void updateGenre(GenreEntity genre) throws ConstraintViolationException,NotFoundException;
    void deleteGenre(String name) throws NotFoundException,ConstraintViolationException;
}

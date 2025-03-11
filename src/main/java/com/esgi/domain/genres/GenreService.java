package com.esgi.domain.genres;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;

import java.util.List;

public interface GenreService {
    GenreEntity getGenreById(int id) throws NotFoundException;
    List<GenreEntity> getAllGenres();
    void createGenre(GenreEntity genre) throws ConstraintViolationException;
    void updateGenre(GenreEntity genre) throws ConstraintViolationException,NotFoundException;
    void deleteGenre(int id) throws NotFoundException, ConstraintViolationException;
    void deleteGenre(String name) throws NotFoundException,ConstraintViolationException;
}

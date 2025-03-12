package com.esgi.domain.genres;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;

import java.util.List;

public interface GenreService {
    GenreEntity getGenreById(int id) throws NotFoundException;
    List<GenreEntity> getAllGenres();
    GenreEntity createGenre(GenreEntity genre) throws ConstraintViolationException;
    void updateGenre(GenreEntity genre) throws ConstraintViolationException,NotFoundException;
    void deleteGenre(String name) throws NotFoundException,ConstraintViolationException;
    GenreEntity getGenreByName(String name) throws NotFoundException;
}

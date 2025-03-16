package com.esgi.domain.genres;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;

import java.util.List;

public interface GenreService {
    GenreEntity getGenreById(int id) throws NotFoundException, InternalErrorException;
    List<GenreEntity> getAllGenres() throws InternalErrorException;
    GenreEntity createGenre(GenreEntity genre) throws ConstraintViolationException, InternalErrorException;
    void updateGenre(GenreEntity genre) throws ConstraintViolationException, NotFoundException, InternalErrorException;
    void deleteGenre(String name) throws NotFoundException, ConstraintViolationException, InternalErrorException;
    GenreEntity getGenreByName(String name) throws NotFoundException, InternalErrorException;
}

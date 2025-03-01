package com.esgi.domain.genres;

import com.esgi.core.exceptions.NotFoundException;

public interface GenreService {
    GenreEntity getGenreById(int id) throws NotFoundException;
}

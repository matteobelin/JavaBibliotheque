package com.esgi.domain.genres.impl;

import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.genres.GenreModel;
import com.esgi.data.genres.GenreRepository;
import com.esgi.data.genres.impl.GenreRepositoryImpl;

import com.esgi.domain.genres.GenreEntity;
import com.esgi.domain.genres.GenreMapper;
import com.esgi.domain.genres.GenreService;

public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public GenreServiceImpl(GenreRepository genreRepository, GenreMapper genreMapper ) {
        this.genreRepository = new GenreRepositoryImpl();
        this.genreMapper = new GenreMapper();
    }

    @Override
    public GenreEntity getGenreById(int id) throws NotFoundException {
        GenreModel genreModel = genreRepository.getById(id);
        return genreMapper.modelToEntity(genreModel);
    }

}

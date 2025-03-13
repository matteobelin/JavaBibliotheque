package com.esgi.domain.genres.impl;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.genres.GenreModel;
import com.esgi.data.genres.GenreRepository;
import com.esgi.domain.genres.GenreEntity;
import com.esgi.domain.genres.GenreMapper;
import com.esgi.domain.genres.GenreService;

import java.util.List;

public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public GenreServiceImpl(GenreRepository genreRepository, GenreMapper genreMapper ) {
        this.genreRepository =genreRepository;
        this.genreMapper = genreMapper;
    }

    public GenreEntity getGenreById(int id) throws NotFoundException {
        GenreModel genreModel = genreRepository.getById(id);
        return genreMapper.modelToEntity(genreModel);
    }


    public GenreEntity getGenreByName(String name) throws NotFoundException {
        GenreModel genreModel = genreRepository.getByName(name);
        return genreMapper.modelToEntity(genreModel);
    }

    public List<GenreEntity> getAllGenres() {
        var genreModels = this.genreRepository.getAll();
        return this.genreMapper.modelsToEntities(genreModels);

    }


    public GenreEntity createGenre(GenreEntity genreEntity) throws ConstraintViolationException {
        GenreModel genreModel = genreMapper.entityToModel(genreEntity);
        genreRepository.create(genreModel);
        return genreMapper.modelToEntity(genreModel);
    }

    public void updateGenre(GenreEntity genreEntity) throws ConstraintViolationException, NotFoundException {

        GenreModel genreModel = genreMapper.entityToModel(genreEntity);
        genreRepository.update(genreModel);
    }

    public void deleteGenre(String name) throws NotFoundException, ConstraintViolationException {
        genreRepository.delete(name);
    }
}

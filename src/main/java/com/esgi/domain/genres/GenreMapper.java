package com.esgi.domain.genres;

import com.esgi.data.genres.GenreModel;

public class GenreMapper {
    public GenreEntity modelToEntity(GenreModel genreModel) {
        return new GenreEntity(
                genreModel.getId(),
                genreModel.getName()
        );
    }

    public GenreModel entityToModel(GenreEntity genreEntity) {
        return new GenreModel(
                genreEntity.getId(),
                genreEntity.getName()
        );
    }
}

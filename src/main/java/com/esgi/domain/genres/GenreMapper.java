package com.esgi.domain.genres;

import com.esgi.data.genres.GenreModel;

import java.util.ArrayList;
import java.util.List;

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

    public List<GenreEntity> modelsToEntities(List<GenreModel> genreModels) {
        List<GenreEntity> entities = new ArrayList<>();

        for (GenreModel genreModel : genreModels) {
            entities.add(this.modelToEntity(genreModel));
        }

        return entities;
    }
}

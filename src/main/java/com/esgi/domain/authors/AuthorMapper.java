package com.esgi.domain.authors;
import com.esgi.data.authors.AuthorModel;

import java.util.ArrayList;
import java.util.List;

public class AuthorMapper {
    public AuthorEntity modelToEntity(AuthorModel authorModel){
        return new AuthorEntity(
                authorModel.getId(),
                authorModel.getName()
        );
    }

    public AuthorModel entityToModel(AuthorEntity authorEntity){
        return new AuthorModel(
                authorEntity.getId(),
                authorEntity.getName()
        );
    }

    public List<AuthorEntity> modelsToEntities(List<AuthorModel> authorModels){
        List<AuthorEntity> entities = new ArrayList<>();

        for(AuthorModel authorModel : authorModels){
            entities.add(modelToEntity(authorModel));
        }

        return entities;
    }
}

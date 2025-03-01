package com.esgi.domain.authors;
import com.esgi.data.authors.AuthorModel;

public class AuthorMapper {
    public AuthorEntity modelToEntity(AuthorModel authorModel){
        return new AuthorEntity(
                authorModel.getId(),
                authorModel.getName()
        );
    }
}

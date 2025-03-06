package com.esgi.domain.authors;

import com.esgi.data.authors.AuthorModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthorMapperTest {

    private AuthorMapper authorMapper;

    @BeforeEach
    public void setUp() {authorMapper = new AuthorMapper();}

    @Test
    public void authorModel_to_AuthorEntity(){
        //Arrange
        AuthorModel authorModel = makeAuthorModel();
        AuthorEntity expectedEntity = makeAuthorEntity();

        //Act
        AuthorEntity result = authorMapper.modelToEntity(authorModel);

        //Assert
        Assertions.assertThat(result)
                .isNotNull()
                .isEqualTo(expectedEntity);

    }

    @Test
    public void authorEntity_to_AuthorModel(){
        //Arrange
        AuthorModel expectedModel = makeAuthorModel();
        AuthorEntity authorEntity = makeAuthorEntity();

        //Act
        AuthorModel result = authorMapper.entityToModel(authorEntity);

        //Assert
        Assertions.assertThat(result)
                .isNotNull()
                .isEqualTo(expectedModel);

    }


    private AuthorModel makeAuthorModel() {
        return new AuthorModel(2, "J.K. Rowling");
    }

    private AuthorEntity makeAuthorEntity() {
        return new AuthorEntity(2, "J.K. Rowling");
    }


}

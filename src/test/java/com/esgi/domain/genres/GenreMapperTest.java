package com.esgi.domain.genres;



import com.esgi.data.genres.GenreModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GenreMapperTest {

    private GenreMapper genreMapper;

    @BeforeEach
    public void setUp() {genreMapper=new GenreMapper();}

    @Test
    public void genreModel_to_genreEntity() {
        //Arrange
        GenreModel genreModel = makeGenreModel();
        GenreEntity expectedEntity = makeGenreEntity();

        //Act
        GenreEntity result = genreMapper.modelToEntity(genreModel);

        //Assert
        Assertions.assertThat(result)
                .isNotNull()
                .isEqualTo(expectedEntity);
    }


    private GenreModel makeGenreModel() {
        return new GenreModel(2, "J.K. Rowling");
    }

    private GenreEntity makeGenreEntity() {
        return new GenreEntity(2, "J.K. Rowling");
    }
}

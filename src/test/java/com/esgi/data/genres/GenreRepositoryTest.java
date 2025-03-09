package com.esgi.data.genres;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.genres.impl.GenreRepositoryImpl;
import com.esgi.helpers.DatabaseTestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GenreRepositoryTest {
    private GenreRepository genreRepository;

    @BeforeAll
    public static void setUpBeforeAll() {
        com.esgi.helpers.DatabaseTestHelper.initTestDb();
    }

    @BeforeEach
    public void setUp() {
        DatabaseTestHelper.resetTestDb();
        genreRepository = new GenreRepositoryImpl();
    }

    @Test
    public void get_Genre_By_Id_Should_Return_Author() throws Exception{

        //Arrange
        Integer genreId = 1;
        //Act
        GenreModel actual = genreRepository.getById(genreId);

        //Assert
        Assertions.assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", actual.getId());
    }

    @Test
    public void get_Genre_By_Id_When_Not_Found_Should_Throw() {
        // Arrange
        Integer genreId = 0;

        // Act - Assert
        Assertions.assertThatThrownBy(() -> genreRepository.getById(genreId))
                .isInstanceOf(NotFoundException.class);
    }


    @Test
    public void create_Genre_Should_Save_Genre() throws ConstraintViolationException, NotFoundException {
        //Arrange
        GenreModel genre = new GenreModel(
                null,
                "test"
        );

        //Act
        genreRepository.create(genre);
        Integer genreId = genre.getId();
        GenreModel actual = genreRepository.getById(genreId);

        //Assert
        Assertions.assertThat(actual)
                .isNotNull()
                .extracting(GenreModel::getName)
                .isEqualTo(genre.getName());

    }

    @Test
    void genre_With_Existing_Name_Should_Throw() {

        //Arrange
        GenreModel genre = new GenreModel(
                null,
                "Science Fiction"
        );

        //Assert
        Assertions.assertThatThrownBy(() -> genreRepository.create(genre));
    }

    @Test
    void genre_With_Missing_Mandatory_Data_Should_Throw() {
        //Arrange
        GenreModel genre = new GenreModel(
                null,
                null
        );

        //Assert
        Assertions.assertThatThrownBy(() -> genreRepository.create(genre));
    }

}

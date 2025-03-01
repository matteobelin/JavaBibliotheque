package com.esgi.data.genres;

import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.genres.impl.GenreRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GenreRepositoryTest {
    private GenreRepository genreRepository;

    @BeforeAll
    public static void setUpBeforeAll() {
        com.esgi.users.helpers.DatabaseTestHelper.initTestDb();
    }
    @BeforeEach
    public void setUp() {
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

}

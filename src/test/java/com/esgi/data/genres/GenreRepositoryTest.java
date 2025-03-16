package com.esgi.data.genres;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
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
    public void get_Genre_By_Id_Should_Return_Genre() throws Exception{

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
    public void get_Genre_By_Name_Should_Return_Genre() throws Exception{

        //Arrange
        String genreName = "Mystery";
        //Act
        GenreModel actual = genreRepository.getByName(genreName);

        //Assert
        Assertions.assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue("name", actual.getName());
    }


    @Test
    public void get_Genre_By_Name_When_Not_Found_Should_Throw() {
        // Arrange
        String genreName = "test";

        // Act - Assert
        Assertions.assertThatThrownBy(() -> genreRepository.getByName(genreName))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void create_Genre_Should_Save_Genre() throws ConstraintViolationException, NotFoundException, InternalErrorException {
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

    @Test
    void update_should_update_the_genre() throws NotFoundException, ConstraintViolationException, InternalErrorException {
        //Arrange
        GenreModel editedGenre = new GenreModel(
                2,
                "test"
        );

        // Act
        this.genreRepository.update(editedGenre);

        // Assert
        GenreModel genreFromDb = genreRepository.getById(editedGenre.getId());
        Assertions.assertThat(genreFromDb)
                .hasFieldOrPropertyWithValue("name", editedGenre.getName())
                .hasFieldOrPropertyWithValue("id", 2);
    }


    @Test
    void update_should_throw_NotFoundException_when_genre_id_doesnt_exist() {
        //Arrange
        GenreModel genre = new GenreModel(
                20000,
                "Thriller"
        );

        // Act - Assert
        Assertions.assertThatThrownBy(() -> this.genreRepository.update(genre))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void update_should_throw_ConstraintViolationException_when_genre_name_taken() {
        //Arrange
        GenreModel genre = new GenreModel(
                2,
                "Mystery"
        );

        // Act - Assert
        Assertions.assertThatThrownBy(() -> this.genreRepository.update(genre))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void delete_should_delete_genre() throws NotFoundException,ConstraintViolationException {
        // Arrange
        String name = "Comics";

        // Act
        this.genreRepository.delete(name);

        // Assert
        Assertions.assertThatThrownBy(() -> this.genreRepository.getByName(name))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void delete_should_throw_NotFoundException_when_id_not_in_db() {
        // Arrange
        String name = "test";

        // Act - Assert
        Assertions.assertThatThrownBy(() -> this.genreRepository.delete(name))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void delete_should_throw_ConstraintViolationException_when_id_in_other_table() throws ConstraintViolationException {
        // Arrange
        String name = "Biography";

        // Act - Assert
        Assertions.assertThatThrownBy(() -> this.genreRepository.delete(name))
                .isInstanceOf(ConstraintViolationException.class);
    }

}

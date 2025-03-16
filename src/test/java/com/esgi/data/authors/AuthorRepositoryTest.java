package com.esgi.data.authors;


import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.authors.impl.AuthorRepositoryImpl;
import com.esgi.helpers.DatabaseTestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class AuthorRepositoryTest {
    private AuthorRepository authorRepository;

    @BeforeAll
    public static void setUpBeforeAll() {
        com.esgi.helpers.DatabaseTestHelper.initTestDb();
    }

    @BeforeEach
    public void setUp() {
        DatabaseTestHelper.resetTestDb();
        authorRepository = new AuthorRepositoryImpl();
    }

    @Test
    public void get_Author_By_Id_Should_Return_Author() throws Exception{

        //Arrange
        Integer authorId = 1;

        //Act
        AuthorModel actual = authorRepository.getById(authorId);

        //Assert
        Assertions.assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", actual.getId());
    }

    @Test
    public void get_Author_By_Id_When_Not_Found_Should_Throw() {
        // Arrange
        Integer authorId = 0;

        // Act - Assert
        Assertions.assertThatThrownBy(() -> authorRepository.getById(authorId))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void get_Author_By_Name_Should_Return_Author() throws Exception{

        //Arrange
        String authorName = "Isaac Asimov";

        //Act
        AuthorModel actual = authorRepository.getByName(authorName);

        //Assert
        Assertions.assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue("name", actual.getName());
    }

    @Test
    public void get_Author_By_Name_When_Not_Found_Should_Throw() {
        // Arrange
        String authorName = "test";

        // Act - Assert
        Assertions.assertThatThrownBy(() -> authorRepository.getByName(authorName))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void create_Author_Should_Save_Author() throws ConstraintViolationException, NotFoundException, InternalErrorException {
        //Arrange
        AuthorModel author = new AuthorModel(
                null,
                "test"
        );

        //Act
        authorRepository.create(author);
        Integer authorId = author.getId();
        AuthorModel actual = authorRepository.getById(authorId);

        //Assert
        Assertions.assertThat(actual)
                .isNotNull()
                .extracting(AuthorModel::getName)
                .isEqualTo(author.getName());

    }

    @Test
    void author_With_Existing_Name_Should_Throw() {
        //Arrange
        AuthorModel author = new AuthorModel(
                null,
                "Isaac Asimov"
        );

        //Assert
        Assertions.assertThatThrownBy(() -> authorRepository.create(author));
    }

    @Test
    void author_With_Missing_Mandatory_Data_Should_Throw() {
        //Arrange
        AuthorModel author = new AuthorModel(
                null,
                null
        );

        //Assert
        Assertions.assertThatThrownBy(() -> authorRepository.create(author));
    }

    @Test
    void update_should_update_the_author() throws NotFoundException, ConstraintViolationException, InternalErrorException {
        //Arrange
        AuthorModel editedAuthor = new AuthorModel(
                2,
                "test"
        );

        // Act
        this.authorRepository.update(editedAuthor);

        // Assert
        AuthorModel authorFromDb = authorRepository.getById(editedAuthor.getId());
        Assertions.assertThat(authorFromDb)
                .hasFieldOrPropertyWithValue("name", editedAuthor.getName())
                .hasFieldOrPropertyWithValue("id", 2);
    }


    @Test
    void update_should_throw_NotFoundException_when_author_id_doesnt_exist() {
        //Arrange
        AuthorModel author = new AuthorModel(
                20000,
                "Albert Camus"
        );

        // Act - Assert
        Assertions.assertThatThrownBy(() -> this.authorRepository.update(author))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void update_should_throw_ConstraintViolationException_when_author_name_taken() {
        //Arrange
        AuthorModel author = new AuthorModel(
                2,
                "Isaac Asimov"
        );

        // Act - Assert
        Assertions.assertThatThrownBy(() -> this.authorRepository.update(author))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void delete_should_delete_author() throws NotFoundException, ConstraintViolationException, InternalErrorException {
        // Arrange
        String name = "Mickey Mouse";

        // Act
        this.authorRepository.delete(name);

        // Assert
        Assertions.assertThatThrownBy(() -> this.authorRepository.getByName(name))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void delete_should_throw_NotFoundException_when_id_not_in_db() {
        // Arrange
        String name = "not_in_db";

        // Act - Assert
        Assertions.assertThatThrownBy(() -> this.authorRepository.delete(name))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void delete_should_throw_ConstraintViolationException_when_id_in_other_table() {
        // Arrange
        String name = "Isaac Asimov";

        // Act - Assert
        Assertions.assertThatThrownBy(() -> this.authorRepository.delete(name))
                .isInstanceOf(ConstraintViolationException.class);
    }

}




package com.esgi.data.authors;


import com.esgi.core.exceptions.ConstraintViolationException;
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
    public void create_Author_Should_Save_Author() throws ConstraintViolationException, NotFoundException {
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
    void author_User_With_Existing_Name_Should_Throw() {
        //Arrange
        AuthorModel author = new AuthorModel(
                null,
                "Isaac Asimov"
        );

        //Assert
        Assertions.assertThatThrownBy(() -> authorRepository.create(author));
    }

    @Test
    void author_User_With_Missing_Mandatory_Data_Should_Throw() {
        //Arrange
        AuthorModel author = new AuthorModel(
                null,
                null
        );

        //Assert
        Assertions.assertThatThrownBy(() -> authorRepository.create(author));
    }

}




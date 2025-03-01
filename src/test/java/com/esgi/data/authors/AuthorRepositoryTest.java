package com.esgi.data.authors;


import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.authors.impl.AuthorRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class AuthorRepositoryTest {
    private AuthorRepository authorRepository;

    @BeforeAll
    public static void setUpBeforeAll() {
        com.esgi.users.helpers.DatabaseTestHelper.initTestDb();
    }

    @BeforeEach
    public void setUp() {
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
        Integer userId = 0;

        // Act - Assert
        Assertions.assertThatThrownBy(() -> authorRepository.getById(userId))
                .isInstanceOf(NotFoundException.class);
    }

}




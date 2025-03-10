package com.esgi.domain.authors;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.authors.AuthorModel;
import com.esgi.data.authors.AuthorRepository;
import com.esgi.domain.authors.impl.AuthorServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class AuthorServiceTest {
    private AuthorService authorService;
    private AuthorRepository authorRepository;
    private AuthorMapper authorMapper;

    @BeforeEach
    public void setUp() {
        authorMapper = Mockito.mock(AuthorMapper.class);
        authorRepository = Mockito.mock(AuthorRepository.class);
        authorService = new AuthorServiceImpl(authorRepository, authorMapper);
    }

    @Test
    public void get_Author_By_Id_Should_Return_Author() throws NotFoundException {
        //Arrange
        Integer authorId = 2;
        AuthorEntity expectedAuthor = new AuthorEntity(authorId,"J.K. Rowling");
        AuthorModel returnAuthor = new AuthorModel(authorId,"J.K. Rowling");

        Mockito.when(authorRepository.getById(authorId)).thenReturn(returnAuthor);
        Mockito.when(authorMapper.modelToEntity(Mockito.any())).thenReturn(expectedAuthor);

        //Act
        AuthorEntity actualAuthor = authorService.getAuthorById(authorId);

        //Assert
        Assertions.assertThat(actualAuthor)
                .isNotNull()
                .isEqualTo(expectedAuthor);
    }


    @Test
    public void create_Author_Should_Not_Throw() throws ConstraintViolationException {
        //Arrange
        AuthorEntity author = new AuthorEntity(2,"J.K. Rowling");
        AuthorModel expectedAuthor = new AuthorModel(2,"J.K. Rowling");

        Mockito.when(authorMapper.entityToModel(author)).thenReturn(expectedAuthor);
        Mockito.doNothing().when(authorRepository).create(expectedAuthor);

        //Act
        authorService.createAuthor(author);

        //Assert
        Mockito.verify(authorRepository, Mockito.times(1)).create(expectedAuthor);
        Mockito.verify(authorMapper, Mockito.times(1)).entityToModel(author);
    }

    @Test
    public void updateAuthor_should_not_throw() throws NotFoundException, ConstraintViolationException {
        // Arrange
        AuthorEntity author = new AuthorEntity(0, "test");
        AuthorModel expectedAuthor = new AuthorModel(0, "test");
        Mockito.when(authorMapper.entityToModel(author)).thenReturn(expectedAuthor);
        Mockito.doNothing().when(authorRepository).update(expectedAuthor);

        //Act
        authorService.updateAuthor(author);
    }



}

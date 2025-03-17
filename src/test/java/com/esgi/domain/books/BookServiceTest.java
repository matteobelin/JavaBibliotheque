package com.esgi.domain.books;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.books.BookModel;
import com.esgi.data.books.BookRepository;
import com.esgi.domain.authors.AuthorEntity;
import com.esgi.domain.books.impl.BookServiceImpl;
import com.esgi.domain.genres.GenreEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class BookServiceTest {
    private BookService bookService;
    private BookMapper bookMapper;
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        bookMapper = Mockito.mock(BookMapper.class);
        bookRepository = Mockito.mock(BookRepository.class);
        bookService = new BookServiceImpl(bookRepository, bookMapper);
    }


    @Test
    public void get_Book_By_Id_Should_Return_Book() throws NotFoundException {
        //Arrange
        Integer bookId = 1;
        BookEntity expectedBook = makeBookEntity();
        BookModel returnBook = makeBookModel();

        Mockito.when(bookRepository.getById(bookId)).thenReturn(returnBook);
        Mockito.when(bookMapper.modelToEntity(Mockito.any())).thenReturn(expectedBook);

        //Act
        BookEntity actualBook = bookService.getBookById(bookId);

        //Assert
        Assertions.assertThat(actualBook)
                .isNotNull()
                .isEqualTo(expectedBook);
    }

    @Test
    public void create_Book_Should_Not_Throw() throws ConstraintViolationException, NotFoundException {
        //Arrange
        BookEntity book = makeBookEntity();
        BookModel expectedBook = makeBookModel();

        Mockito.when(bookMapper.entityToModel(book)).thenReturn(expectedBook);
        Mockito.doNothing().when(bookRepository).create(expectedBook);

        //Act
        bookService.createBook(book);

        //Assert
        Mockito.verify(bookRepository, Mockito.times(1)).create(expectedBook);
        Mockito.verify(bookMapper, Mockito.times(1)).entityToModel(book);
    }

    @Test
    public void update_Book_Should_Not_Throw() throws ConstraintViolationException, NotFoundException {
        //Arrange
        BookEntity book = makeBookEntity();
        BookModel expectedBook = makeBookModel();

        Mockito.when(bookMapper.entityToModel(book)).thenReturn(expectedBook);
        Mockito.doNothing().when(bookRepository).update(expectedBook);

        //Act
        bookService.updateBook(book);

        //Assert
        Mockito.verify(bookRepository, Mockito.times(1)).update(expectedBook);
        Mockito.verify(bookMapper, Mockito.times(1)).entityToModel(book);
    }

    private BookEntity makeBookEntity() {
        List<GenreEntity> genreEntities = Arrays.asList(new GenreEntity(1,"Science Fiction"));
        return new BookEntity(1,"Foundation",new AuthorEntity(1, "Isaac Asimov"),genreEntities);
    }

    private BookModel makeBookModel() {
        List<Integer> genreIds = Arrays.asList(1);
        return new BookModel(1,"Foundation",1,genreIds);
    }

}

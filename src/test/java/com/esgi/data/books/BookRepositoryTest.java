package com.esgi.data.books;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.authors.AuthorModel;
import com.esgi.data.books.impl.BookRepositoryImpl;
import com.esgi.helpers.DatabaseTestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class BookRepositoryTest {
    private BookRepository bookRepository;

    @BeforeAll
    public static void setUpBeforeAll(){
        com.esgi.helpers.DatabaseTestHelper.initTestDb();
    }

    @BeforeEach
    public void setUp(){
        DatabaseTestHelper.resetTestDb();
        bookRepository = new BookRepositoryImpl();
    }

    @Test
    public void get_Book_by_id_should_return_valid_book() throws NotFoundException {
        // Arrange
        Integer bookId = 1;

        // Act
        BookModel actual = bookRepository.getById(bookId);

        // Assert
        Assertions.assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", bookId)
                .hasFieldOrProperty("title")
                .hasFieldOrProperty("authorId")
                .hasFieldOrProperty("genreIds");
    }

    @Test
    public void get_Book_by_id_should_have_valid_genres() throws NotFoundException {
        // Arrange
        Integer bookId = 1;

        // Act
        BookModel actual = bookRepository.getById(bookId);

        // Assert
        Assertions.assertThat(actual.getGenreIds()).isNotNull().isNotEmpty();
    }

    @Test
    public void get_Book_by_id_should_have_valid_authors() throws NotFoundException {
        // Arrange
        Integer bookId = 1;

        // Act
        BookModel actual = bookRepository.getById(bookId);

        // Assert
        Assertions.assertThat(actual.getAuthorId()).isNotNull();
    }

    @Test
    public void get_Book_By_Id_When_Not_Found_Should_Throw() {
        // Arrange
        Integer bookId = 0;

        //Act & Assert
        Assertions.assertThatThrownBy(() -> bookRepository.getById(bookId))
                .isInstanceOf(NotFoundException.class);
    }



    @Test
    public void create_Book_Should_Save_Book() throws ConstraintViolationException, NotFoundException {
        //Arrange
        BookModel book = new BookModel(
                null,
                "test",
                1,
                new ArrayList<>(Arrays.asList(1, 2, 3))
        );

        //Act
        bookRepository.create(book);
        Integer bookId = book.getId();
        BookModel actual = bookRepository.getById(bookId);

        //Assert
        Assertions.assertThat(actual)
                .isNotNull()
                .extracting(BookModel::getTitle)
                .isEqualTo(book.getTitle());
    }


    @Test
    public void create_Book_Should_Save_Book_And_Genre() throws ConstraintViolationException, NotFoundException {
        //Arrange
        BookModel book = new BookModel(
                null,
                "test",
                1,
                new ArrayList<>(Arrays.asList(1, 2, 3))
        );

        //Act
        bookRepository.create(book);
        Integer bookId = book.getId();
        BookModel actual = bookRepository.getById(bookId);

        //Assert
        Assertions.assertThat(actual)
                .isNotNull()
                .extracting(BookModel::getGenreIds)
                .isEqualTo(book.getGenreIds());
    }

    @Test
    void book_With_Missing_Mandatory_Data_Should_Throw() {
        //Arrange
        BookModel book = new BookModel(
                null,
                null,
                1,
                new ArrayList<>(Arrays.asList(1, 2, 3))
        );

        //Assert
        Assertions.assertThatThrownBy(() -> bookRepository.create(book));
    }

    @Test
    void book_With_Missing_Genre_Mandatory_Data_Should_Throw() {
        //Arrange
        BookModel book = new BookModel(
                null,
                "test",
                1,
                null
        );

        //Assert
        Assertions.assertThatThrownBy(() -> bookRepository.create(book));
    }



}

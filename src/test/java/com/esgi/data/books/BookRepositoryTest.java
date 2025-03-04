package com.esgi.data.books;

import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.books.impl.BookRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookRepositoryTest {
    private BookRepository bookRepository;

    @BeforeAll
    public static void setUpBeforeAll(){
        com.esgi.helpers.DatabaseTestHelper.initTestDb();
    }

    @BeforeEach
    public void setUp(){
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
                .hasFieldOrProperty("author_id")
                .hasFieldOrProperty("genre_ids");
    }

    @Test
    public void get_Book_by_id_should_have_valid_genres() throws NotFoundException {
        // Arrange
        Integer bookId = 1;

        // Act
        BookModel actual = bookRepository.getById(bookId);

        // Assert
        Assertions.assertThat(actual.getGenre_ids()).isNotNull().isNotEmpty();
    }

    @Test
    public void get_Book_by_id_should_have_valid_authors() throws NotFoundException {
        // Arrange
        Integer bookId = 1;

        // Act
        BookModel actual = bookRepository.getById(bookId);

        // Assert
        Assertions.assertThat(actual.getAuthor_id()).isNotNull();
    }

    @Test
    public void get_Book_By_Id_When_Not_Found_Should_Throw() {
        // Arrange
        Integer bookId = 0;

        //Act & Assert
        Assertions.assertThatThrownBy(() -> bookRepository.getById(bookId))
                .isInstanceOf(NotFoundException.class);
    }



}

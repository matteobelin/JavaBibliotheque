package com.esgi.data.books;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.books.impl.BookRepositoryImpl;
import com.esgi.data.genreBook.GenreBookRepository;
import com.esgi.helpers.DatabaseTestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class BookRepositoryTest {

    @InjectMocks
    private BookRepositoryImpl bookRepository;

    @Mock
    private GenreBookRepository genreBookRepository;

    @BeforeAll
    public static void setUpBeforeAll(){
        com.esgi.helpers.DatabaseTestHelper.initTestDb();
    }

    @BeforeEach
    public void setUp(){
        DatabaseTestHelper.resetTestDb();
    }

    @Test
    public void get_Book_by_id_should_return_valid_book() throws NotFoundException, InternalErrorException {
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
    public void get_Book_by_id_should_have_valid_genres() throws NotFoundException, InternalErrorException {
        // Arrange
        Integer bookId = 1;

        // Act
        BookModel actual = bookRepository.getById(bookId);

        // Assert
        Assertions.assertThat(actual.getGenreIds()).isNotNull().isNotEmpty();
    }

    @Test
    public void get_Book_by_id_should_have_valid_book() throws NotFoundException, InternalErrorException {
        // Arrange
        Integer bookId = 1;

        // Act
        BookModel actual = bookRepository.getById(bookId);

        // Assert
        Assertions.assertThat(actual.getId()).isNotNull();
    }

    @Test
    public void get_Book_by_Title_should_have_valid_book() throws NotFoundException, ConstraintViolationException, InternalErrorException {
        // Arrange
        String bookTitle = "Steve Jobs";

        // Act
        List<BookModel> actual = bookRepository.getByTitle(bookTitle);

        // Assert
        Assertions.assertThat(actual).isNotNull()
                .hasSize(2)
                .extracting(BookModel::getId)
                .containsExactly(4, 5);
    }

    @Test
    public void get_Book_by_Title_should_have_valid_genres() throws NotFoundException, InternalErrorException {
        // Arrange
        String bookTitle = "Steve Jobs";

        // Act
        List<BookModel> actual = bookRepository.getByTitle(bookTitle);

        // Assert
        Assertions.assertThat(actual).isNotNull()
                .hasSize(2)
                .extracting(BookModel::getGenreIds)
                .isNotNull();
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
    public void create_Book_Should_Save_Book() throws ConstraintViolationException, NotFoundException, InternalErrorException {
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
    public void create_Book_Should_Save_Book_And_Genre() throws ConstraintViolationException, NotFoundException, InternalErrorException {
        //Arrange
        var genreIds = new ArrayList<>(Arrays.asList(1, 2, 3));

        BookModel book = new BookModel(
                null,
                "test",
                1,
                genreIds
        );

        Mockito.doNothing().when(this.genreBookRepository).createGenreBook(any());

        //Act
        bookRepository.create(book);
        Integer bookId = book.getId();
        BookModel actual = bookRepository.getById(bookId);

        //Assert
        Assertions.assertThat(actual)
                .isNotNull()
                .extracting(BookModel::getGenreIds)
                .isNotNull();
    }

    @Test
    public void update_Book_With_An_Existing_Author_And_Book_Should_Throw() throws NotFoundException, ConstraintViolationException {
        //Arrange
        BookModel book = new BookModel(
                1,
                "Steve Jobs",
                4,
                new ArrayList<>(Arrays.asList(1, 2, 3))
        );

        //Assert
        Assertions.assertThatThrownBy(() -> bookRepository.update(book));
    }

    @Test
    void book_Update_With_Missing_Mandatory_Data_Should_Throw() {
        //Arrange
        BookModel book = new BookModel(
                1,
                null,
                1,
                new ArrayList<>(Arrays.asList(1, 2, 3))
        );

        //Assert
        Assertions.assertThatThrownBy(() -> bookRepository.update(book));
    }

    @Test
    void update_book_With_Missing_Genre_Mandatory_Data_Should_Throw() {
        //Arrange
        BookModel book = new BookModel(
                1,
                "test",
                1,
                null
        );

        //Assert
        Assertions.assertThatThrownBy(() -> bookRepository.create(book));
    }

    @Test
    public void Book_With_An_Existing_Author_And_Book_Should_Throw() {
        //Arrange
        BookModel book = new BookModel(
                1,
                "Steve Jobs",
                4,
                new ArrayList<>(Arrays.asList(1, 2, 3))
        );

        //Assert
        Assertions.assertThatThrownBy(() -> bookRepository.create(book));
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

    @Test
    public void update_Book_Should_Save_Book() throws ConstraintViolationException, NotFoundException, InternalErrorException {
        //Arrange
        BookModel book = new BookModel(
                1,
                "test",
                1,
                new ArrayList<>(Arrays.asList(1, 2, 3))
        );

        //Act
        bookRepository.update(book);
        Integer bookId = book.getId();
        BookModel actual = bookRepository.getById(bookId);

        //Assert
        Assertions.assertThat(actual)
                .isNotNull()
                .extracting(BookModel::getTitle)
                .isEqualTo(book.getTitle());
    }


    @Test
    public void update_Book_Should_Save_Book_And_Genre() throws ConstraintViolationException, NotFoundException, InternalErrorException {
        //Arrange
        BookModel book = new BookModel(
                1,
                "test",
                1,
                new ArrayList<>(Arrays.asList(1, 2, 3))
        );

        //Act
        bookRepository.update(book);
        Integer bookId = book.getId();
        BookModel actual = bookRepository.getById(bookId);

        //Assert
        Assertions.assertThat(actual)
                .isNotNull()
                .extracting(BookModel::getGenreIds)
                .isNotNull();
    }

    @Test
    public void get_All_Book_should_return_BookList(){
        //Arrange
        List<BookModel> booksExpect = getBooks();
        List<BookModel> books;

        //Act
        books=bookRepository.getAllBook();

        //Assert
        Assertions.assertThat(books)
                .isNotNull()
                .isEqualTo(booksExpect);
    }

    @Test
        public void delete_Book_Should_Remove_Book() throws NotFoundException, ConstraintViolationException, InternalErrorException {
            // Arrange
            BookModel book = new BookModel(
                    null,
                    "test",
                    1,
                    new ArrayList<>(Arrays.asList(1, 2, 3))
            );
            bookRepository.create(book);
            Integer bookId = book.getId();

            // Act
            bookRepository.delete(bookId);

            // Assert
            Assertions.assertThatThrownBy(() -> bookRepository.getById(bookId))
                    .isInstanceOf(NotFoundException.class);
        }
    @Test
    public void delete_Book_When_genre_is_Empty_Should_Remove_Book() throws NotFoundException, ConstraintViolationException, InternalErrorException {
        // Arrange
        BookModel book = new BookModel(
                null,
                "test",
                1,
                new ArrayList<>()
        );
        bookRepository.create(book);
        Integer bookId = book.getId();

        // Act
        bookRepository.delete(bookId);

        // Assert
        Assertions.assertThatThrownBy(() -> bookRepository.getById(bookId))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void get_book_by_author_should_Return_bookList() throws NotFoundException, InternalErrorException {
        //Arrange
        List<BookModel> booksExpect = Arrays.asList(new BookModel(
                2,
                "Harry Potter and the Philosopher s Stone",
                2,
                new ArrayList<>(Arrays.asList(2))
        ));
        //Act
        List<BookModel> books=bookRepository.getByAuthor(2);
        //Assert
        Assertions.assertThat(books)
                .isNotNull()
                .isEqualTo(booksExpect);

    }


    @Test
    public void get_book_by_title_should_Return_bookList() throws NotFoundException, InternalErrorException {
        //Arrange
        List<BookModel> booksExpect = Arrays.asList(new BookModel(
                2,
                "Harry Potter and the Philosopher s Stone",
                2,
                new ArrayList<>(Arrays.asList(2))
        ));
        //Act
        List<BookModel> books=bookRepository.getByTitle("Harry Potter and the Philosopher s Stone");
        //Assert
        Assertions.assertThat(books)
                .isNotNull()
                .isEqualTo(booksExpect);
    }

    


    private List<BookModel> getBooks() {
        return Arrays.asList(
                new BookModel(1, "Foundation", 1, new ArrayList<>(Arrays.asList(1))),
                new BookModel(2, "Harry Potter and the Philosopher s Stone", 2, new ArrayList<>(Arrays.asList(2))),
                new BookModel(3, "Murder on the Orient Express", 3, new ArrayList<>(Arrays.asList(3))),
                new BookModel(4, "Steve Jobs", 4, new ArrayList<>(Arrays.asList(4))),
                new BookModel(5, "Steve Jobs", 1, new ArrayList<>(Arrays.asList(4, 3)))
        );
    }


}

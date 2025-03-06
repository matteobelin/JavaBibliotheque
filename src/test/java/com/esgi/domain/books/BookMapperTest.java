package com.esgi.domain.books;

import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.books.BookModel;
import com.esgi.domain.authors.AuthorEntity;
import com.esgi.domain.authors.AuthorService;
import com.esgi.domain.genres.GenreEntity;
import com.esgi.domain.genres.GenreService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class BookMapperTest {

    private BookMapper bookMapper;
    private AuthorService authorService;
    private GenreService genreService;

    @BeforeEach
    public void setUp() {
        authorService = Mockito.mock(AuthorService.class);
        genreService = Mockito.mock(GenreService.class);
        bookMapper = new BookMapper(authorService, genreService);
        }

    @Test
    public void testBookMapperInitialization() {
        assert bookMapper != null;
    }

    @Test
    public void bookModel_to_bookEntity() throws NotFoundException {
        // Arrange
        BookModel bookModel = makeBookModel();

        AuthorEntity authorEntity = new AuthorEntity(1, "Isaac Asimov");
        Mockito.when(authorService.getAuthorById(1)).thenReturn(authorEntity);

        GenreEntity genreEntity = new GenreEntity(1, "Science Fiction");
        Mockito.when(genreService.getGenreById(1)).thenReturn(genreEntity);

        // Act
        BookEntity result = bookMapper.modelToEntity(bookModel);

        // Assert
        Assertions.assertThat(result)
                .isNotNull()
                .isEqualTo(makeBookEntity());
    }

    private BookModel makeBookModel() {
        List<Integer> genreIds = Arrays.asList(1);
        return new BookModel(1,"Foundation",1,genreIds);
    }

    private BookEntity makeBookEntity() {
        List<GenreEntity> genreEntities = Arrays.asList(new GenreEntity(1,"Science Fiction"));
        return new BookEntity(1,"Foundation",new AuthorEntity(1, "Isaac Asimov"),genreEntities);

    }

}

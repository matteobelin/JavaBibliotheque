package com.esgi.domain.genres;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.genres.GenreModel;
import com.esgi.data.genres.GenreRepository;
import com.esgi.domain.genres.impl.GenreServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GenreServiceTest {
    private GenreService genreService;
    private GenreRepository genreRepository;
    private GenreMapper genreMapper;

    @BeforeEach
    public void setUp() {
        genreMapper = Mockito.mock(GenreMapper.class);
        genreRepository= Mockito.mock(GenreRepository.class);
        genreService = new GenreServiceImpl(genreRepository, genreMapper);
    }

    @Test
    public void get_Genre_By_Id_Should_Return_Genre() throws NotFoundException, InternalErrorException {
        //Arrange
        Integer genreId = 1;
        GenreEntity expectedGenre = new GenreEntity(genreId,"Science Fiction");
        GenreModel returnGenre = new GenreModel(genreId,"Science Fiction");

        Mockito.when(genreRepository.getById(genreId)).thenReturn(returnGenre);
        Mockito.when(genreMapper.modelToEntity(Mockito.any())).thenReturn(expectedGenre);

        //Act
        GenreEntity actualGenre = genreService.getGenreById(genreId);

        //Assert
        Assertions.assertThat(actualGenre)
                .isNotNull()
                .isEqualTo(expectedGenre);
    }


    @Test
    public void create_Genre_Should_Not_Throw() throws ConstraintViolationException, InternalErrorException {
        //Arrange
        GenreEntity genre = new GenreEntity(1,"Science Fiction");
        GenreModel expectedGenre = new GenreModel(1,"Science Fiction");

        Mockito.when(genreMapper.entityToModel(genre)).thenReturn(expectedGenre);
        Mockito.doNothing().when(genreRepository).create(expectedGenre);

        //Act
        genreService.createGenre(genre);

        //Assert
        Mockito.verify(genreRepository, Mockito.times(1)).create(expectedGenre);
        Mockito.verify(genreMapper, Mockito.times(1)).entityToModel(genre);
    }

    @Test
    public void updateGenre_should_not_throw() throws NotFoundException, ConstraintViolationException, InternalErrorException {
        // Arrange
        GenreEntity genre = new GenreEntity(0, "test");
        GenreModel expectedGenre = new GenreModel(0, "test");
        Mockito.when(genreMapper.entityToModel(genre)).thenReturn(expectedGenre);
        Mockito.doNothing().when(genreRepository).update(expectedGenre);

        //Act
        genreService.updateGenre(genre);
    }


}

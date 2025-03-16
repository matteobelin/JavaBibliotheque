package com.esgi.domain.books;


import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.books.BookModel;
import com.esgi.domain.authors.AuthorService;
import com.esgi.domain.genres.GenreEntity;
import com.esgi.domain.genres.GenreService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookMapper(AuthorService authorService, GenreService genreService) {
        this.authorService = authorService;
        this.genreService = genreService;
    }

    public BookEntity modelToEntity(BookModel bookModel) throws NotFoundException, InternalErrorException {
        return new BookEntity(
                bookModel.getId(),
                bookModel.getTitle(),
                authorService.getAuthorById(bookModel.getAuthorId()),
                mapGenreIdsToEntities(bookModel.getGenreIds())
        );
    }

    public BookModel entityToModel(BookEntity bookEntity) {
        return new BookModel(
                bookEntity.getId(),
                bookEntity.getTitle(),
                bookEntity.getAuthor().getId(),
                bookEntity.getGenres().stream()
                            .map(GenreEntity::getId)
                            .collect(Collectors.toList())
        );

    }

    private GenreEntity getGenreEntityById(int id) throws NotFoundException, InternalErrorException {
        return genreService.getGenreById(id);
    }

    private List<GenreEntity> mapGenreIdsToEntities(List<Integer> genreIds) throws NotFoundException, InternalErrorException {
        var genreEntities = new ArrayList<GenreEntity>();
        for (Integer genreId : genreIds) {
            GenreEntity genreEntity = getGenreEntityById(genreId);
            genreEntities.add(genreEntity);
        }
        return genreEntities;
    }
}

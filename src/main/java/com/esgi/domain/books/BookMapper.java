package com.esgi.domain.books;


import com.esgi.core.exceptions.NotFoundException;
import com.esgi.data.books.BookModel;
import com.esgi.domain.authors.AuthorService;
import com.esgi.domain.genres.GenreEntity;
import com.esgi.domain.genres.GenreService;
import lombok.SneakyThrows;

import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookMapper(AuthorService authorService, GenreService genreService) {
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @SneakyThrows
    public BookEntity modelToEntity(BookModel bookModel) {
        return new BookEntity(
                bookModel.getId(),
                bookModel.getTitle(),
                authorService.getAuthorById(bookModel.getAuthorId()),
                mapGenreIdsToEntities(bookModel.getGenreIds())
        );
    }

    private GenreEntity getGenreEntityById(int id) {
        try {
            return genreService.getGenreById(id);
        } catch (NotFoundException e) {
            throw new RuntimeException("Genre not found for ID: " + id, e);
        }
    }

    private List<GenreEntity> mapGenreIdsToEntities(List<Integer> genreIds) {
        return genreIds.stream()
                .map(this::getGenreEntityById).collect(Collectors.toList());
    }
}

package com.esgi.presentation.utils;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.domain.genres.GenreEntity;
import com.esgi.domain.genres.GenreService;
import com.esgi.presentation.AppLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class GenreUtils {
    public static List<GenreEntity> getOrCreateGenresByName(List<String> genreNames, GenreService genreService)  {
        var genres = new ArrayList<GenreEntity>();
        for (String genreName : genreNames) {
            try {
                var genre = getOrCreateGenreByName(genreName, genreService);
                genre.ifPresent(genres::add);
            } catch (ConstraintViolationException e) {
                var errorMessageLines = StringUtils.wrapInSmallBox(List.of(
                        e.getMessage(),
                        "-> This genre will not be added to the book"
                ));
                AppLogger.error(errorMessageLines);
            }
        }

        return genres;
    }

    public static Optional<GenreEntity> getOrCreateGenreByName(String genreName, GenreService genreService) throws ConstraintViolationException {
        try {
            var genre = genreService.getGenreByName(genreName);
            return Optional.of(genre);
        } catch (NotFoundException e) {
            return askToCreateGenre(genreName, genreService);
        }
    }

    public static Optional<GenreEntity> askToCreateGenre(String genreName, GenreService genreService)
            throws ConstraintViolationException {
        String confirmationMessage = "The genre '%s' does not exist in the system, do you wish to create it ? (y/n)".formatted(genreName);
        boolean create = AppLogger.askForConfirmation(confirmationMessage);
        if (create) {
            var genre = new GenreEntity();
            genre.setName(genreName);

            return Optional.of(genreService.createGenre(genre));
        }

        return Optional.empty();
    }

}

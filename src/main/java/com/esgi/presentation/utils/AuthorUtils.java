package com.esgi.presentation.utils;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.domain.authors.AuthorEntity;
import com.esgi.domain.authors.AuthorService;
import com.esgi.presentation.AppLogger;

import java.util.Optional;

public final class AuthorUtils {

    public static Optional<AuthorEntity> getOrAskToCreateAuthorByName(String authorName, AuthorService authorService) throws ConstraintViolationException {
        try {
            var author = authorService.getAuthorByName(authorName);
            return Optional.of(author);
        } catch (NotFoundException e) {
            return askToCreateAuthor(authorName, authorService);
        }
    }


    public static Optional<AuthorEntity> askToCreateAuthor(String authorName, AuthorService authorService)
            throws ConstraintViolationException {

        String confirmationMessage = "The author '%s' does not exist in the system, do you wish to create it ? (y/n)".formatted(authorName);
        boolean create = AppLogger.askForConfirmation(confirmationMessage);
        if (create) {
            var author = new AuthorEntity();
            author.setName(authorName);

            return Optional.of(authorService.createAuthor(author));
        }

        return Optional.empty();
    }
}

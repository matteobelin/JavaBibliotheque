package com.esgi.presentation.cli.books.add;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.domain.authors.AuthorEntity;
import com.esgi.domain.authors.AuthorService;
import com.esgi.domain.books.BookEntity;
import com.esgi.domain.books.BookService;
import com.esgi.domain.genres.GenreEntity;
import com.esgi.domain.genres.GenreService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.ExitCode;
import com.esgi.presentation.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AddBookCliCommandNode extends CliCommandNode {

    public static final String NAME = "add";

    public static final String DESCRIPTION = "Add a new book to the library";

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    public AddBookCliCommandNode(BookService bookService, AuthorService authorService, GenreService genreService) {
        super(NAME, DESCRIPTION);

        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }


    @Override
    public ExitCode run(String[] args) {

        var values = this.extractValuesFromArgs(args);

        if (values.size() < 2) {
            AppLogger.error("This command requires at least 2 arguments : NAME and AUTHOR_NAME");
            return ExitCode.ARGUMENT_MISSING;
        }


        try {
            var book = this.makeBookFromValues(values);

            if (book.isEmpty()) {
                AppLogger.warn("The book was not created.");
                return ExitCode.OK;
            }

            this.bookService.createBook(book.get());

            String title = book.get().getTitle();
            String authorName = book.get().getAuthor().getName();
            AppLogger.success("Book with title '%s' by '%s' created ! ".formatted(title, authorName));
        } catch (ConstraintViolationException | NotFoundException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.ARGUMENT_INVALID;
        }

        return ExitCode.OK;
    }

    private Optional<BookEntity> makeBookFromValues(List<String> values) throws ConstraintViolationException {
        String title = values.get(0);

        String authorName = values.get(1);
        var author = this.getAuthorByName(authorName);
        if (author.isEmpty()) {
            return Optional.empty();
        }

        var genres = new ArrayList<GenreEntity>();
        if (values.size() == 3) {
            String genreNames = values.get(2);
            var listGenreNames = Arrays.stream(genreNames.split(",")).map(String::trim).toList();
            genres.addAll(this.getGenresByName(listGenreNames));
        } else if (values.size() > 2) {
            var genreNames = values.subList(2, values.size());
            genres.addAll(this.getGenresByName(genreNames));
        }

        BookEntity book = new BookEntity();
        book.setTitle(title);
        book.setAuthor(author.get());
        book.setGenres(genres);

        return Optional.of(book);
    }

    private Optional<AuthorEntity> getAuthorByName(String authorName) throws ConstraintViolationException {
        try {
            var author = this.authorService.getAuthorByName(authorName);
            return Optional.of(author);
        } catch (NotFoundException e) {
            return askToCreateAuthor(authorName);
        }
    }

    private Optional<AuthorEntity> askToCreateAuthor(String authorName) throws ConstraintViolationException {
        String confirmationMessage = "The author '%s' does not exist in the system, do you wish to create it ? (y/n)".formatted(authorName);
        boolean create = AppLogger.askForConfirmation(confirmationMessage);
        if (create) {
            var author = new AuthorEntity();
            author.setName(authorName);

            return Optional.of(this.authorService.createAuthor(author));
        }

        return Optional.empty();
    }

    private List<GenreEntity> getGenresByName(List<String> genreNames) {
        var genres = new ArrayList<GenreEntity>();
        for (String genreName : genreNames) {
            try {
                var genre = this.getGenreByName(genreName);
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

    private Optional<GenreEntity> getGenreByName(String genreName) throws ConstraintViolationException {
        try {
            var genre = this.genreService.getGenreByName(genreName);
            return Optional.of(genre);
        } catch (NotFoundException e) {
            return askToCreateGenre(genreName);
        }
    }

    private Optional<GenreEntity> askToCreateGenre(String genreName) throws ConstraintViolationException {
        String confirmationMessage = "The genre '%s' does not exist in the system, do you wish to create it ? (y/n)".formatted(genreName);
        boolean create = AppLogger.askForConfirmation(confirmationMessage);
        if (create) {
            var genre = new GenreEntity();
            genre.setName(genreName);

            return Optional.of(this.genreService.createGenre(genre));
        }

        return Optional.empty();
    }
}

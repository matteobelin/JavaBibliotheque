package com.esgi.presentation.cli.books.add;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.domain.authors.AuthorService;
import com.esgi.domain.books.BookEntity;
import com.esgi.domain.books.BookService;
import com.esgi.domain.genres.GenreService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.ExitCode;
import com.esgi.presentation.utils.AuthorUtils;
import com.esgi.presentation.utils.BookUtils;
import com.esgi.presentation.utils.GenreUtils;

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

            var createdBook = this.bookService.createBook(book.get());

            String title = createdBook.getTitle();
            String authorName = createdBook.getAuthor().getName();
            AppLogger.success("Book with title '%s' by '%s' created ! ".formatted(title, authorName));

            var singleBookTable = BookUtils.makeBookTable(List.of(createdBook));
            AppLogger.emptyLine();
            AppLogger.info(singleBookTable);
        } catch (ConstraintViolationException | NotFoundException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.ARGUMENT_INVALID;
        } catch (InternalErrorException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.INTERNAL_ERROR;
        }

        return ExitCode.OK;
    }

    private Optional<BookEntity> makeBookFromValues(List<String> values) throws ConstraintViolationException, InternalErrorException {
        String title = values.get(0);

        String authorName = values.get(1);
        var author = AuthorUtils.getOrAskToCreateAuthorByName(authorName, this.authorService);
        if (author.isEmpty()) {
            return Optional.empty();
        }

        var listGenreNames = extractGenreNamesFromValues(values);
        var genres = GenreUtils.getOrCreateGenresByName(listGenreNames, this.genreService);

        BookEntity book = new BookEntity();
        book.setTitle(title);
        book.setAuthor(author.get());
        book.setGenres(genres);

        return Optional.of(book);
    }

    private List<String> extractGenreNamesFromValues(List<String> values) {
        if (values.size() == 3) {
            String genreNames = values.get(2);
            return Arrays.stream(genreNames.split(",")).map(String::trim).toList();
        }

        if (values.size() > 2) {
            return values.subList(2, values.size());
        }

        return new ArrayList<>();
    }
}

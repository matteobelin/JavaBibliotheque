package com.esgi.presentation.cli.books.edit;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.core.exceptions.OptionRequiresValueException;
import com.esgi.domain.authors.AuthorEntity;
import com.esgi.domain.authors.AuthorService;
import com.esgi.domain.books.BookEntity;
import com.esgi.domain.books.BookService;
import com.esgi.domain.genres.GenreService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.CliCommandNodeOption;
import com.esgi.presentation.cli.ExitCode;
import com.esgi.presentation.cli.options.ValueCliCommandNodeOption;
import com.esgi.presentation.utils.AuthorUtils;
import com.esgi.presentation.utils.BookUtils;
import com.esgi.presentation.utils.GenreUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EditBookCliCommandNode extends CliCommandNode {

    public static final String NAME = "edit";

    public static final String DESCRIPTION = "Edit a book";

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    public EditBookCliCommandNode(BookService bookService, AuthorService authorService, GenreService genreService) {
        super(NAME, DESCRIPTION);
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;

        var  titleOptionValue = new ValueCliCommandNodeOption("title", "t", "Edit the book's title");
        var  authorOptionValue = new ValueCliCommandNodeOption("author", "a", "Change the book's author");
        var  genreOptionValue = new ValueCliCommandNodeOption("genre", "g", "Change the book's genres");

        this.commandOptions.add(titleOptionValue);
        this.commandOptions.add(authorOptionValue);
        this.commandOptions.add(genreOptionValue);
    }

    @Override
    public ExitCode run(String[] args) {
        var values = this.extractValuesFromArgs(args);
        if (values.isEmpty()) {
            AppLogger.error("This command required 1 argument : ID");
            return ExitCode.ARGUMENT_MISSING;
        }

        try {
            int bookId = Integer.parseInt(values.get(0));

            var options = this.extractOptionsFromArgs(args);
            var currentBook = this.bookService.getBookById(bookId);

            BookEntity editedBook = this.makeEditedBook(currentBook, options);

            this.bookService.updateBook(editedBook);

            var singleBookTable = BookUtils.makeBookTable(List.of(editedBook));

            AppLogger.success("The book with id %s has been updated !".formatted(bookId));
            AppLogger.emptyLine();
            AppLogger.info(singleBookTable);
        } catch (NumberFormatException e) {
            AppLogger.error("The ID argument must be a number !");
            return ExitCode.ARGUMENT_INVALID;
        } catch (OptionRequiresValueException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.ARGUMENT_MISSING;
        } catch (NotFoundException | ConstraintViolationException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.ARGUMENT_INVALID;
        } catch (InternalErrorException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.INTERNAL_ERROR;
        }

        return ExitCode.OK;
    }

    private BookEntity makeEditedBook(BookEntity currentBook, List<CliCommandNodeOption> options) throws ConstraintViolationException, InternalErrorException {
        var book = new BookEntity();
        book.setId(currentBook.getId());

        var titleOption = options.stream().filter(this::isTitleOption).findFirst();
        if (titleOption.isPresent()) {
            book.setTitle(titleOption.get().getValue());
        } else {
            book.setTitle(currentBook.getTitle());
        }

        var author = getAuthorFromOptions(options);
        if (author.isPresent()) {
            book.setAuthor(author.get());
        } else {
            book.setAuthor(currentBook.getAuthor());
        }

        var genreOption = options.stream().filter(this::isGenreOption).findFirst();
        if (genreOption.isPresent()) {
            var genreNames = genreOption.get().getValue();
            var listGenreNames = Arrays.stream(genreNames.split(",")).map(String::trim).toList();

            var genres = GenreUtils.getOrCreateGenresByName(listGenreNames, this.genreService);
            book.setGenres(genres);
        } else {
            book.setGenres(currentBook.getGenres());
        }

        return  book;
    }

    private Optional<AuthorEntity> getAuthorFromOptions(List<CliCommandNodeOption> options) throws ConstraintViolationException, InternalErrorException {
        var authorOption = options.stream().filter(this::isAuthorOption).findFirst();
        if (authorOption.isPresent()) {
            var authorName = authorOption.get().getValue();
            return AuthorUtils.getOrAskToCreateAuthorByName(authorName, this.authorService);
        }

        return Optional.empty();
    }

    private boolean isTitleOption(CliCommandNodeOption option) {
        return option.getName().equals("title") &&
               option.getShortName().equals("t") &&
               option.getDescription().equals("Edit the book's title");
    }

    private boolean isAuthorOption(CliCommandNodeOption option) {
        return option.getName().equals("author") &&
                option.getShortName().equals("a") &&
                option.getDescription().equals("Change the book's author");
    }

    private boolean isGenreOption(CliCommandNodeOption option) {
        return option.getName().equals("genre") &&
                option.getShortName().equals("g") &&
                option.getDescription().equals("Change the book's genres");
    }
}

package com.esgi.presentation.cli.books.importation;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.domain.authors.AuthorEntity;
import com.esgi.domain.authors.AuthorService;
import com.esgi.domain.books.BookEntity;
import com.esgi.domain.books.BookService;
import com.esgi.domain.genres.GenreEntity;
import com.esgi.domain.genres.GenreService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.ExitCode;
import com.esgi.presentation.cli.imports.ImportCliCommandNode;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ImportBooksCliCommandNode extends ImportCliCommandNode<BookEntity> {
    public static final String DESCRIPTION = "Import books from a file in Json format";
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    public ImportBooksCliCommandNode(BookService bookService, AuthorService authorService,GenreService genreService) {
        super(DESCRIPTION);
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;

    }

    @Override
    public ExitCode run(String[] args) {

        var values = this.extractValuesFromArgs(args);
        if (values.isEmpty()) {
            AppLogger.error("This command requires 1 argument : FILE_PATH");
            return ExitCode.ARGUMENT_MISSING;
        }

        String filePath = values.get(0);
        boolean isNotAValidPath = !isValidPath(filePath);
        if (isNotAValidPath) {
            AppLogger.error("The FILE_PATH argument must a valid path");
            return ExitCode.ARGUMENT_INVALID;
        }
        try {
            List<BookEntity> books = new ArrayList<>();
            Type listType = new TypeToken<List<BookEntity>>(){}.getType();
            books = super.importData(books, filePath,listType);
            for (BookEntity book : books) {
                checkAndCreateAuthor(book);
                checkAndCreateGenre(book);
                createBook(book);
            }
        } catch (InternalErrorException | IOException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.INTERNAL_ERROR;
        } catch (ConstraintViolationException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.ARGUMENT_INVALID;
        }
        return ExitCode.OK;
    }

    private boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }
        return true;
    }

    private void checkAndCreateAuthor(BookEntity book) throws InternalErrorException, ConstraintViolationException {
        try {
            book.setAuthor(authorService.getAuthorByName(book.getAuthor().getName()));
        } catch (NotFoundException e) {
            var author = new AuthorEntity();
            author.setName(book.getAuthor().getName());

            book.setAuthor(authorService.createAuthor(author));
            AppLogger.success("The author '%s' has been created !".formatted(author.getName()));
        }
    }

    private void checkAndCreateGenre(BookEntity book) throws InternalErrorException, ConstraintViolationException {
        List<GenreEntity> genres = book.getGenres();
        List<GenreEntity> finalGenres = new ArrayList<>();
        for (GenreEntity genre : genres) {
            try{
                finalGenres.add(genreService.getGenreByName(genre.getName()));
            }catch (NotFoundException e) {
                var newGenre = new GenreEntity();
                newGenre.setName(genre.getName());

                finalGenres.add(genreService.createGenre(newGenre));
                AppLogger.success("The genre '%s' has been created !".formatted(genre.getName()));
            }
            book.setGenres(finalGenres);
        }
    }

    public void createBook(BookEntity book) throws InternalErrorException {
        try{
            bookService.createBook(book);
            AppLogger.success("%s by %s was created".formatted(book.getTitle(), book.getAuthor().getName()));
        } catch (ConstraintViolationException|NotFoundException e) {
            AppLogger.error("The book %s by %s already exist".formatted(book.getTitle(), book.getAuthor().getName()));
        }
    }
}

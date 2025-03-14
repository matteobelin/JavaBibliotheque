package com.esgi.presentation.cli.books.export;

import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.domain.books.BookEntity;
import com.esgi.domain.books.BookService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.ExitCode;
import com.esgi.presentation.cli.exports.ExportCliCommandNode;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

public class ExportBooksCliCommandNode  extends ExportCliCommandNode<BookEntity> {

    public static final String DESCRIPTION = "Exports books into a file in Json format";

    private final BookService bookService;

    public ExportBooksCliCommandNode(BookService bookService) {
        super(DESCRIPTION);
        this.bookService = bookService;
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
            var booksToExport = this.bookService.getAllBooks();

            super.export(booksToExport, filePath);

            AppLogger.success("Successfully exported the books to a json file at " + filePath);
        } catch (NotFoundException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.ARGUMENT_INVALID;
        } catch (Exception e) {
            var internalException = new InternalErrorException(e);
            AppLogger.error(internalException.getMessage());
            return ExitCode.INTERNAL_ERROR;
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
}

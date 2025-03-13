package com.esgi.presentation.cli.books.delete;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.domain.books.BookService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.ExitCode;

public class DeleteBookCliCommandNode extends CliCommandNode {

    public static final String NAME = "delete";

    public static final String DESCRIPTION = "Delete a book using it's id";

    private final BookService bookService;

    public DeleteBookCliCommandNode(BookService bookService) {
        super(NAME, DESCRIPTION);
        this.bookService = bookService;
    }

    public ExitCode run(String[] args) {

        var values = this.extractValuesFromArgs(args);

        if (values.isEmpty()) {
            AppLogger.error("This command requires 1 argument : ID (ex : biblio books delete BOOK_ID)");
            return ExitCode.ARGUMENT_MISSING;
        }

        try {
            int bookId = Integer.parseInt(values.get(0));
            this.bookService.deleteBook(bookId);

            AppLogger.success("Book with id %s deleted !".formatted(bookId));
        } catch (NumberFormatException e) {
            AppLogger.error("The ID argument must be a number !");
            return ExitCode.ARGUMENT_INVALID;
        } catch (NotFoundException | ConstraintViolationException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.ARGUMENT_INVALID;
        }

        return ExitCode.OK;
    }
}

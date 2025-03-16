package com.esgi.presentation.cli.books.list;

import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.domain.books.BookService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.ExitCode;
import com.esgi.presentation.utils.BookUtils;

public class ListBookCliCommandNode extends CliCommandNode {

    public static final String NAME = "list";

    public static final String DESCRIPTION = "Lists all books in the library";

    private final BookService bookService;

    public ListBookCliCommandNode(BookService bookService) {
        super(NAME, DESCRIPTION);

        this.bookService = bookService;
    }

    @Override
    public ExitCode run(String[] args) {
        try {
            var books = this.bookService.getAllBooks();

            var table = BookUtils.makeBookTable(books);

            AppLogger.emptyLine();
            AppLogger.info(table);
        } catch (NotFoundException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.INTERNAL_ERROR; // should not happen, could be a foreign key not respected
        } catch (InternalErrorException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.INTERNAL_ERROR;
        }

        return ExitCode.OK;
    }
}

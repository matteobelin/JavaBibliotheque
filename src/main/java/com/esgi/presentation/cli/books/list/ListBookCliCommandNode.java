package com.esgi.presentation.cli.books.list;

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
        var books = this.bookService.getAllBooks();

        var table = BookUtils.makeBookTable(books);

        AppLogger.emptyLine();
        AppLogger.info(table);

        return ExitCode.OK;
    }
}

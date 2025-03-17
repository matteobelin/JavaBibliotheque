package com.esgi.presentation.cli.books.search;

import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.domain.books.BookService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.ExitCode;
import com.esgi.presentation.utils.BookUtils;

public class SearchBookCliCommandNode extends CliCommandNode {

    public static final String NAME = "search";
    public static final String DESCRIPTION = "Search a book";

    private final BookService bookService;

    public SearchBookCliCommandNode(BookService bookService) {
        super(NAME, DESCRIPTION);
        this.bookService = bookService;
    }

    @Override
    public ExitCode run(String[] args)  {

        var values = this.extractValuesFromArgs(args);
        if (values.isEmpty()) {
            AppLogger.error("This command requires 1 argument : SEARCH_TEXT");
            return ExitCode.ARGUMENT_MISSING;
        }

        String searchText = values.get(0);
        try {
            var books = this.bookService.searchBook(searchText);

            var table = BookUtils.makeBookTable(books);

            AppLogger.info(String.format("Found %s books : ", books.size()));
            AppLogger.emptyLine();
            AppLogger.info(table);
        } catch (NotFoundException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.INTERNAL_ERROR;
        }

        return ExitCode.OK;
    }
}

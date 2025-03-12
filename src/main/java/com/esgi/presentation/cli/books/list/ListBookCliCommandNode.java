package com.esgi.presentation.cli.books.list;

import com.esgi.domain.books.BookEntity;
import com.esgi.domain.books.BookService;
import com.esgi.domain.genres.GenreEntity;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.ExitCode;
import com.esgi.presentation.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        var tableHeader = List.of(
            "#",
            "Title",
            "Author",
            "Genres"
        );
        var tableRows = new ArrayList<List<String>>();
        tableRows.add(tableHeader);

        var books = this.bookService.getAllBooks();
        for (int i = 0; i < books.size(); i++) {
            var book = books.get(i);
            var tableRow = this.mapBookToTableRow(i + 1, book);
            tableRows.add(tableRow);
        }

        var table = StringUtils.makeTable(tableRows);
        AppLogger.emptyLine();
        AppLogger.info(table);

        return ExitCode.OK;
    }

    private List<String> mapBookToTableRow(Integer index, BookEntity book) {
        return List.of(
            index.toString(),
            book.getTitle(),
            book.getAuthor().getName(),
            book.getGenres().stream().map(GenreEntity::getName).collect(Collectors.joining(", "))
        );
    }
}

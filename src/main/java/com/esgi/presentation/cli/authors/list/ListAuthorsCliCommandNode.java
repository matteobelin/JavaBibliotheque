package com.esgi.presentation.cli.authors.list;

import com.esgi.domain.authors.AuthorEntity;
import com.esgi.domain.authors.AuthorService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.ExitCode;
import com.esgi.presentation.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ListAuthorsCliCommandNode extends CliCommandNode {

    public static final String NAME = "list";
    public static final String DESCRIPTION = "Lists all authors";

    private final AuthorService authorService;

    public ListAuthorsCliCommandNode(AuthorService authorService) {
        super(NAME, DESCRIPTION);
        this.authorService = authorService;
    }

    @Override
    public ExitCode run(String[] args)  {
        var tableHeader = List.of(
            "#",
            "Name"
        );
        var tableRows = new ArrayList<List<String>>();
        tableRows.add(tableHeader);

        var authors = this.authorService.getAllAuthors();
        for (int i = 0; i < authors.size(); i++) {
            var author = authors.get(i);
            var tableRow = this.mapAuthorToTableRow(i + 1, author);
            tableRows.add(tableRow);
        }

        var table = StringUtils.makeTable(tableRows);
        AppLogger.emptyLine();
        AppLogger.info(table);

        return ExitCode.OK;
    }

    private List<String> mapAuthorToTableRow(Integer i, AuthorEntity author) {
        return List.of(
            i.toString(),
            author.getName()
        );
    }
}

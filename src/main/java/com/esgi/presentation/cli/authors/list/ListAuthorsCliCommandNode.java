package com.esgi.presentation.cli.authors.list;

import com.esgi.domain.authors.AuthorService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.ExitCode;

public class ListAuthorsCliCommandNode extends CliCommandNode {

    public static final String NAME = "list";
    public static final String DESCRIPTION = "Lists all authors";

    private final AuthorService authorService;

    public ListAuthorsCliCommandNode(AuthorService authorService) {
        super(NAME, DESCRIPTION);
        this.authorService = authorService;
    }

    @Override
    public ExitCode run(String[] args) {

        var authors = this.authorService.getAllAuthors();

        for (int index = 0 ; index < authors.size() ; index++) {
            var author = authors.get(index);

            AppLogger.info("%d. %s".formatted(index + 1, author.getName()));
        }

        return ExitCode.OK;
    }
}

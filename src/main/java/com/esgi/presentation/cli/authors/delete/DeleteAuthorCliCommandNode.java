package com.esgi.presentation.cli.authors.delete;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.domain.authors.AuthorService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.ExitCode;

public class DeleteAuthorCliCommandNode extends CliCommandNode {

    public static final String NAME = "delete";
    public static final String DESCRIPTION = "Delete an author";

    private final AuthorService authorService;

    public DeleteAuthorCliCommandNode(AuthorService authorService) {
        super(NAME, DESCRIPTION);
        this.authorService = authorService;
    }

    @Override
    public ExitCode run(String[] args) {
        var values = this.extractValuesFromArgs(args);

        if (values.isEmpty()) {
            AppLogger.error("This commands requires the name of the author to delete : biblio authors delete AUTHOR_NAME");
            return ExitCode.ARGUMENT_MISSING;
        }

        try {
            this.authorService.deleteAuthor(values.get(0));

            AppLogger.success("Author with name %s deleted !".formatted(values.get(0)));
        } catch (NotFoundException | ConstraintViolationException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.ARGUMENT_INVALID;
        }

        return ExitCode.OK;
    }
}

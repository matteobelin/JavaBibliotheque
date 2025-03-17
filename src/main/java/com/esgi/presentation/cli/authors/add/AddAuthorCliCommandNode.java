package com.esgi.presentation.cli.authors.add;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.domain.authors.AuthorEntity;
import com.esgi.domain.authors.AuthorService;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.ExitCode;

public class AddAuthorCliCommandNode extends CliCommandNode {

    public static final String NAME = "add";
    public static final String DESCRIPTION = "Adds the author to the system.";

    private final AuthorService authorService;

    public AddAuthorCliCommandNode(AuthorService authorService) {
        super(NAME, DESCRIPTION);

        this.authorService = authorService;
    }

    @Override
    public ExitCode run(String[] args) {
        var values = this.extractValuesFromArgs(args);

        if (values.isEmpty()) {
            AppLogger.error("This command requires the name of the author as argument : biblio authors add AUTHOR_NAME");
            return ExitCode.ARGUMENT_MISSING;
        }

        var author = new AuthorEntity();
        author.setName(values.get(0));

        try {
            authorService.createAuthor(author);

            AppLogger.success("The author '%s' has been created !".formatted(author.getName()));
        } catch (ConstraintViolationException e) {
            AppLogger.error(e.getMessage());
            return ExitCode.ARGUMENT_INVALID;
        }

        return ExitCode.OK;
    }
}

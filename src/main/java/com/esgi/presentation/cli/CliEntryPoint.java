package com.esgi.presentation.cli;

import com.esgi.domain.auth.AuthService;
import com.esgi.presentation.cli.auth.AuthCliCommandNode;
import com.esgi.presentation.cli.authors.AuthorCliCommandNode;
import com.esgi.presentation.cli.users.UserCliCommandNode;

import java.util.List;

public class CliEntryPoint extends CliCommandNode {
    public static final String NAME = "Biblio";
    public static final String DESCRIPTION = "A command line application for managing a library";

    public CliEntryPoint(AuthService authService,
         AuthCliCommandNode authCliCommand,
         UserCliCommandNode userCliCommand,
         AuthorCliCommandNode authorCliCommand
    ) {
        super(NAME, DESCRIPTION);

        childrenCommands.add(authCliCommand);

        if (authService.isLoggedIn()) {
            childrenCommands.add(userCliCommand);
        }

        if (authService.isLoggedInUserAdmin()) {
            childrenCommands.add(authorCliCommand);
        }

        childrenCommands.add(new HelpCliCommand(List.copyOf(childrenCommands)));
    }
}

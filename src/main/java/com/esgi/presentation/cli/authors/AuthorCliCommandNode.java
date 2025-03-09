package com.esgi.presentation.cli.authors;

import com.esgi.domain.auth.AuthService;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.HelpCliCommand;
import com.esgi.presentation.cli.authors.add.AddAuthorCliCommandNode;
import com.esgi.presentation.cli.authors.delete.DeleteAuthorCliCommandNode;

import java.util.List;

public class AuthorCliCommandNode extends CliCommandNode {

    public static final String NAME = "authors";
    public static final String DESCRIPTION = "Manages the authors in the system.";

    public AuthorCliCommandNode(AuthService authService,
            AddAuthorCliCommandNode addAuthorCommand,
            DeleteAuthorCliCommandNode deleteAuthorCommand
    ) {
        super(NAME, DESCRIPTION);

        if (authService.isLoggedInUserAdmin()) {
            this.childrenCommands.add(addAuthorCommand);
            this.childrenCommands.add(deleteAuthorCommand);
        }

        this.childrenCommands.add(new HelpCliCommand(List.copyOf(getChildrenCommands())));
    }
}

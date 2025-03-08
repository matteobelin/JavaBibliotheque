package com.esgi.presentation.cli;

import com.esgi.presentation.CommandAccessLevel;
import com.esgi.presentation.cli.auth.AuthCliCommandNode;
import com.esgi.presentation.cli.users.UserCliCommandNode;

import java.util.ArrayList;
import java.util.List;

public class CliEntryPoint extends CliCommandNode {
    public static final String NAME = "Biblio";
    public static final String DESCRIPTION = "A command line application for managing a library";
    public static final CommandAccessLevel ACCESS_LEVEL = CommandAccessLevel.PUBLIC;

    public CliEntryPoint(AuthCliCommandNode authCliCommand, UserCliCommandNode userCliCommand) {
        super(NAME, DESCRIPTION, ACCESS_LEVEL);

        childrenCommands.add(authCliCommand);
        childrenCommands.add(userCliCommand);
        childrenCommands.add(new HelpCliCommand(List.copyOf(childrenCommands)));
    }
}

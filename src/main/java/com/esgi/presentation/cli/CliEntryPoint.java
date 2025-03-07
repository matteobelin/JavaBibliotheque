package com.esgi.presentation.cli;

import com.esgi.presentation.CommandAccessLevel;
import com.esgi.presentation.cli.users.UserCliCommandNode;

import java.util.ArrayList;
import java.util.List;

public class CliEntryPoint extends CliCommandNode {
    public static final String NAME = "Biblio";
    public static final String DESCRIPTION = "A command line application for managing a library";
    public static final CommandAccessLevel ACCESS_LEVEL = CommandAccessLevel.PUBLIC;

    List<CliCommandNode> availableChildrenCommands;

    public CliEntryPoint(UserCliCommandNode userCliCommand) {
        super(NAME, DESCRIPTION, ACCESS_LEVEL);

        availableChildrenCommands = new ArrayList<>();
        availableChildrenCommands.add(userCliCommand);

        availableChildrenCommands.add(new HelpCliCommand(List.copyOf(availableChildrenCommands)));
    }

    @Override
    public List<CliCommandNode> getChildrenCommands() {
        return availableChildrenCommands;
    }
}

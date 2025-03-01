package com.esgi.presentation.cli;

import com.esgi.presentation.cli.users.UserCliCommandNode;

import java.util.ArrayList;
import java.util.List;

public class CliEntryPoint extends CliCommandNode {
    List<CliCommandNode> availableChildrenCommands;

    public CliEntryPoint(UserCliCommandNode userCliCommand) {
        availableChildrenCommands = new ArrayList<>();
        availableChildrenCommands.add(userCliCommand);

        availableChildrenCommands.add(new HelpCliCommand(List.copyOf(availableChildrenCommands)));
    }

    @Override
    public String getName() {
        return "Biblio";
    }

    @Override
    public String getDescription() {
        return "A command line application for managing a library";
    }

    @Override
    public List<CliCommandNode> getChildrenCommands() {
        return availableChildrenCommands;
    }
}

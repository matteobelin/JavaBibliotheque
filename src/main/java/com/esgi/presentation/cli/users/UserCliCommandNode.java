package com.esgi.presentation.cli.users;

import com.esgi.domain.users.UserService;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.HelpCliCommand;
import com.esgi.presentation.cli.users.add.AddUserCliCommandNode;

import java.util.ArrayList;
import java.util.List;

public class UserCliCommandNode extends CliCommandNode {
    public static final String NAME = "users";

    private final List<CliCommandNode> childrenCommands;

    public UserCliCommandNode(AddUserCliCommandNode addUserCliCommandNode) {
        childrenCommands = new ArrayList<>();
        childrenCommands.add(addUserCliCommandNode);

        childrenCommands.add(new HelpCliCommand(List.copyOf(childrenCommands)));
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return "Provide commands to manage users in the system";
    }

    @Override
    public List<CliCommandNode> getChildrenCommands() {
        return this.childrenCommands;
    }
}

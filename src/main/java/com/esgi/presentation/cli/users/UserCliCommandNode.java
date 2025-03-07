package com.esgi.presentation.cli.users;

import com.esgi.domain.users.UserService;
import com.esgi.presentation.CommandAccessLevel;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.HelpCliCommand;
import com.esgi.presentation.cli.users.add.AddUserCliCommandNode;

import java.util.ArrayList;
import java.util.List;

public class UserCliCommandNode extends CliCommandNode {
    public static final String NAME = "users";
    public static final String DESCRIPTION = "Provide commands to manage users in the system";
    public static final CommandAccessLevel ACCESS_LEVEL = CommandAccessLevel.USER;

    private final List<CliCommandNode> childrenCommands;

    public UserCliCommandNode(AddUserCliCommandNode addUserCliCommandNode) {
        super(NAME, DESCRIPTION, ACCESS_LEVEL);

        childrenCommands = new ArrayList<>();

        // TODO : check if user has the rights to use admin only commands
        childrenCommands.add(addUserCliCommandNode);

        childrenCommands.add(new HelpCliCommand(List.copyOf(childrenCommands)));
    }

    @Override
    public List<CliCommandNode> getChildrenCommands() {
        return this.childrenCommands;
    }
}

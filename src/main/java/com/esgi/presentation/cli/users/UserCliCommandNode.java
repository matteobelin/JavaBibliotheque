package com.esgi.presentation.cli.users;

import com.esgi.domain.auth.AuthServiceFactory;
import com.esgi.presentation.CommandAccessLevel;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.HelpCliCommand;
import com.esgi.presentation.cli.users.add.AddUserCliCommandNode;

import java.util.List;

public class UserCliCommandNode extends CliCommandNode {
    public static final String NAME = "users";
    public static final String DESCRIPTION = "Provide commands to manage users in the system";
    public static final CommandAccessLevel ACCESS_LEVEL = CommandAccessLevel.USER;

    public UserCliCommandNode(AddUserCliCommandNode addUserCliCommandNode) {
        super(NAME, DESCRIPTION, ACCESS_LEVEL);

        var authService = AuthServiceFactory.getAuthService();
        if(authService.isConnectedUserAdmin()) {
            childrenCommands.add(addUserCliCommandNode);
        }

        childrenCommands.add(new HelpCliCommand(List.copyOf(childrenCommands)));
    }
}

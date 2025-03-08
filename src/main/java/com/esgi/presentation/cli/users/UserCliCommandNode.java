package com.esgi.presentation.cli.users;

import com.esgi.domain.auth.AuthService;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.HelpCliCommand;
import com.esgi.presentation.cli.users.add.AddUserCliCommandNode;

import java.util.List;

public class UserCliCommandNode extends CliCommandNode {
    public static final String NAME = "users";
    public static final String DESCRIPTION = "Provide commands to manage users in the system";

    public UserCliCommandNode(AuthService authService, AddUserCliCommandNode addUserCliCommandNode) {
        super(NAME, DESCRIPTION);

        if(authService.isConnectedUserAdmin()) {
            childrenCommands.add(addUserCliCommandNode);
        }

        childrenCommands.add(new HelpCliCommand(List.copyOf(childrenCommands)));
    }
}

package com.esgi.presentation.cli.users;

import com.esgi.domain.auth.AuthService;
import com.esgi.presentation.cli.CliCommandNode;
import com.esgi.presentation.cli.HelpCliCommand;
import com.esgi.presentation.cli.users.add.AddUserCliCommandNode;
import com.esgi.presentation.cli.users.delete.DeleteUserCliCommandNode;
import com.esgi.presentation.cli.users.edit.EditUserCliCommandNode;

import java.util.List;

public class UserCliCommandNode extends CliCommandNode {
    public static final String NAME = "users";
    public static final String DESCRIPTION = "Provide commands to manage users in the system";

    public UserCliCommandNode(AuthService authService,
          AddUserCliCommandNode addUserCommand,
          EditUserCliCommandNode editUserCommand,
          DeleteUserCliCommandNode deleteUserCommand
    ) {
        super(NAME, DESCRIPTION);

        if(authService.isLoggedInUserAdmin()) {
            childrenCommands.add(addUserCommand);
        }

        if (authService.isLoggedIn()) {
            childrenCommands.add(editUserCommand);
            childrenCommands.add(deleteUserCommand);
        }

        childrenCommands.add(new HelpCliCommand(List.copyOf(childrenCommands)));
    }
}

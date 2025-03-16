package com.esgi.presentation.cli.users;

import com.esgi.domain.auth.AuthServiceFactory;
import com.esgi.presentation.cli.users.add.AddUserCliCommandFactory;
import com.esgi.presentation.cli.users.delete.DeleteUserCliCommandFactory;
import com.esgi.presentation.cli.users.edit.EditUserCliCommandFactory;

public final class UserCliCommandFactory {

    public static UserCliCommandNode makeUserCliCommandNode() {
        var authService = AuthServiceFactory.getAuthService();

        var addUserCliCommandNode = AddUserCliCommandFactory.makeAddUserCliCommandNode();
        var deleteUserCommand = DeleteUserCliCommandFactory.makeDeleteUserCliCommandNode();
        var addUserCommand = AddUserCliCommandFactory.makeAddUserCliCommandNode();
        var editUserCommand = EditUserCliCommandFactory.makeEditUserCliCommand();

        return new UserCliCommandNode(authService, addUserCommand, editUserCommand, deleteUserCommand);
    }
}

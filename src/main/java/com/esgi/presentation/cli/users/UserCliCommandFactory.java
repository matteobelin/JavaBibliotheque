package com.esgi.presentation.cli.users;

import com.esgi.domain.auth.AuthServiceFactory;
import com.esgi.presentation.cli.users.add.AddUserCliCommandFactory;

public final class UserCliCommandFactory {

    public static UserCliCommandNode makeUserCliCommandNode() {
        var authService = AuthServiceFactory.getAuthService();

        var addUserCliCommandNode = AddUserCliCommandFactory.makeAddUserCliCommandNode();

        return new UserCliCommandNode(authService, addUserCliCommandNode);
    }
}

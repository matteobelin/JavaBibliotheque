package com.esgi.presentation.cli.users.delete;

import com.esgi.domain.auth.AuthServiceFactory;
import com.esgi.domain.users.UserServiceFactory;

public final class DeleteUserCliCommandFactory {

    public static DeleteUserCliCommandNode makeDeleteUserCliCommandNode() {
        var authService = AuthServiceFactory.getAuthService();
        var userService = UserServiceFactory.getUserService();

        return new DeleteUserCliCommandNode(authService, userService);
    }
}

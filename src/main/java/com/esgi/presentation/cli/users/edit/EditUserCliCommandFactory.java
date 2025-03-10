package com.esgi.presentation.cli.users.edit;

import com.esgi.domain.auth.AuthServiceFactory;
import com.esgi.domain.users.UserServiceFactory;

public final class EditUserCliCommandFactory {

    public static EditUserCliCommandNode makeEditUserCliCommand() {
        var authService = AuthServiceFactory.getAuthService();
        var userService = UserServiceFactory.getUserService();

        return new EditUserCliCommandNode(authService, userService);
    }
}

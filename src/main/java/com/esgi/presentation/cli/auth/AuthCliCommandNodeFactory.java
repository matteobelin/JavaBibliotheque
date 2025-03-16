package com.esgi.presentation.cli.auth;

import com.esgi.domain.auth.AuthServiceFactory;
import com.esgi.domain.users.UserServiceFactory;
import com.esgi.presentation.cli.auth.login.LoginCliCommandNode;
import com.esgi.presentation.cli.auth.logout.LogoutCliCommandNode;
import com.esgi.presentation.cli.auth.signin.SigninCliCommandNode;

public final class AuthCliCommandNodeFactory {

    public static AuthCliCommandNode makeAuthCliCommandNode() {
        var authService = AuthServiceFactory.getAuthService();

        var loginCommand = new LoginCliCommandNode(authService);
        var logoutCommand = new LogoutCliCommandNode(authService);

        var userService = UserServiceFactory.getUserService();
        var signinCommand = new SigninCliCommandNode(userService);

        return new AuthCliCommandNode(loginCommand, logoutCommand, signinCommand);
    }
}

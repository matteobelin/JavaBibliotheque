package com.esgi.domain.auth;

import com.esgi.domain.auth.impl.AuthServiceImpl;
import com.esgi.domain.serialization.Serializer;
import com.esgi.domain.users.UserService;
import com.esgi.domain.users.UserServiceFactory;
import com.esgi.presentation.AppFolderConfig;

public final class AuthServiceFactory {

    private static AuthService authService;

    public static AuthService getAuthService() {
        if (authService == null) {
            authService = makeAuthService();
        }

        return authService;
    }

    private static AuthService makeAuthService() {
        UserService userService = UserServiceFactory.getUserService();

        String authFilePath = AppFolderConfig.getPathToAuthFile();
        Serializer<AuthCredentials> serializer = new Serializer<>(authFilePath, AuthCredentials.class);

        return new AuthServiceImpl(userService, serializer);
    }
}

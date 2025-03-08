package com.esgi.domain.auth;

import com.esgi.domain.auth.impl.AuthServiceImpl;
import com.esgi.domain.serialization.Serializer;
import com.esgi.domain.users.UserService;
import com.esgi.domain.users.UserServiceFactory;

public final class AuthServiceFactory {
    private static final String PATH_TO_AUTH_FILE = "./src/main/resources/auth.txt";

    private static AuthService authService;

    public static AuthService getAuthService() {
        if (authService == null) {
            authService = makeAuthService();
        }

        return authService;
    }

    private static AuthService makeAuthService() {
        UserService userService = UserServiceFactory.getUserService();
        Serializer<AuthCredentials> serializer = new Serializer<>(PATH_TO_AUTH_FILE, AuthCredentials.class);

        return new AuthServiceImpl(userService, serializer);
    }
}

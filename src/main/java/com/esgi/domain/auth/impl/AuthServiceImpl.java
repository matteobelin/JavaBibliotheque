package com.esgi.domain.auth.impl;

import com.esgi.core.exceptions.IncorrectCredentialsException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.domain.auth.AuthCredentials;
import com.esgi.domain.auth.AuthService;
import com.esgi.domain.serialization.Serializer;
import com.esgi.domain.users.UserEntity;
import com.esgi.domain.users.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final Serializer<AuthCredentials> serializer;

    private UserEntity connectedUser;

    public AuthServiceImpl(UserService userService, Serializer<AuthCredentials> serializer) {
        this.userService = userService;
        this.serializer = serializer;
    }

    public boolean isLoggedIn() {
        return connectedUser != null;
    }

    public boolean isLoggedInUserAdmin() {
        return this.isLoggedIn() && this.connectedUser.isAdmin();
    }

    public UserEntity getLoggedInUser() {
        return connectedUser;
    }

    public UserEntity login(AuthCredentials credentials) throws IncorrectCredentialsException {
        try {
            UserEntity foundUser = userService.getUserByEmail(credentials.email());

            boolean incorrectPassword = !credentials.password().equals(foundUser.getPassword());
            if (incorrectPassword) {
                throw new IncorrectCredentialsException();
            }

            connectedUser = foundUser;

            if (credentials.stayLoggedIn()) {
                this.serializer.serialize(credentials);
            }

            return connectedUser;

        } catch (NotFoundException e) {
            throw new IncorrectCredentialsException();
        } catch (IOException e) {
            throw new InternalErrorException(e);
        }
    }

    public void logout()  {
        connectedUser = null;

        try {
            Files.deleteIfExists(Path.of(serializer.getDestFilePath()));
        } catch (IOException e) {
            throw new InternalErrorException(e);
        }
    }

    public boolean tryToLoginWithSavedCredentials() throws IncorrectCredentialsException {
        try {
            var credentials = this.serializer.deserialize();
            var credentialsWithoutStayLoggedIn = new AuthCredentials(
                    credentials.email(), credentials.password(), false);

            this.login(credentialsWithoutStayLoggedIn);
            return true;
        } catch (IOException | InternalErrorException e) {
            return false;
        }
    }
}

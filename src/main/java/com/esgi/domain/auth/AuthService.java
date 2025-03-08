package com.esgi.domain.auth;

import com.esgi.core.exceptions.IncorrectCredentialsException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.domain.users.UserEntity;

public interface AuthService {
    boolean isLoggedIn();

    boolean isConnectedUserAdmin();

    UserEntity getLoggedInUser();

    UserEntity login(AuthCredentials credentials) throws IncorrectCredentialsException, InternalErrorException;

    boolean tryToLoginWithSavedCredentials() throws IncorrectCredentialsException;

    void logout() throws InternalErrorException;
}

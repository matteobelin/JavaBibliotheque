package com.esgi.domain.auth;

import com.esgi.core.exceptions.IncorrectCredentialsException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.domain.users.UserEntity;

public interface AuthService {
    boolean isLoggedIn();

    boolean isConnectedUserAdmin();

    UserEntity getLoggedInUser();

    UserEntity login(AuthCredentials credentials) throws IncorrectCredentialsException, InternalErrorException;

    void tryToLoginWithSavedCredentials() throws IncorrectCredentialsException, InternalErrorException;

    void logout() throws InternalErrorException;
}

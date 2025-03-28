package com.esgi.domain.auth;

import com.esgi.core.exceptions.IncorrectCredentialsException;
import com.esgi.domain.users.UserEntity;

public interface AuthService {
    boolean isLoggedIn();

    boolean isLoggedInUserAdmin();

    UserEntity getLoggedInUser();

    UserEntity login(AuthCredentials credentials) throws IncorrectCredentialsException;

    boolean tryToLoginWithSavedCredentials() throws IncorrectCredentialsException;

    void logout() ;
}

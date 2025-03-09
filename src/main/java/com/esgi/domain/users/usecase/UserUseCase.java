package com.esgi.domain.users.usecase;

import com.esgi.core.exceptions.InvalidArgumentException;
import com.esgi.domain.users.UserEntity;

public class UserUseCase {
    public static String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    public static void validateUserInformationIsValid(UserEntity user) throws InvalidArgumentException {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new InvalidArgumentException("The email address is mandatory.");
        }

        boolean emailNotValid = !UserUseCase.isEmailValid(user.getEmail());
        if (emailNotValid) {
            throw new InvalidArgumentException("The provided email is not a valid email address.");
        }

        if( user.getName() == null || user.getName().isEmpty() ) {
            user.setName(UserUseCase.parseNameFromEmail(user.getEmail()));
        }
    }

    private static String parseNameFromEmail(String email) {
        return email.split("@")[0].replace('.', ' ');
    }

    private static boolean isEmailValid(String email) {
        return email.matches(EMAIL_REGEX);
    }
}

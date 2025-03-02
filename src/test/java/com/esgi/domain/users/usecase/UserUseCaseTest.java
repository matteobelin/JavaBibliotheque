package com.esgi.domain.users.usecase;

import com.esgi.core.exceptions.InvalidArgumentException;
import com.esgi.data.users.usecase.UserUseCase;
import com.esgi.domain.users.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserUseCaseTest {

    @Test
    public void should_not_throw_when_valid_user() {
        UserEntity user = new UserEntity();
        user.setEmail("user@email.com");
        user.setPassword("password");

        Assertions.assertThatNoException()
                    .isThrownBy(() -> UserUseCase.validateUserIsValidForCreation(user));
    }

    @Test
    public void should_create_name_from_email_when_not_present() throws InvalidArgumentException {
        UserEntity user = new UserEntity();
        user.setEmail("user.name@gmail.com");
        user.setPassword("password");

        UserUseCase.validateUserIsValidForCreation(user);

        Assertions.assertThat(user).hasFieldOrPropertyWithValue("name", "user name");
    }

    @Test
    public void should_throw_when_user_has_no_email() {
        UserEntity user = new UserEntity();

        Assertions.assertThatThrownBy(() -> UserUseCase.validateUserIsValidForCreation(user))
                .isInstanceOf(InvalidArgumentException.class);
    }

    @Test
    public void should_throw_when_user_email_not_valid() {
        UserEntity user = new UserEntity();
        user.setEmail("user.com");

        Assertions.assertThatThrownBy(() -> UserUseCase.validateUserIsValidForCreation(user))
                .isInstanceOf(InvalidArgumentException.class);
    }

}

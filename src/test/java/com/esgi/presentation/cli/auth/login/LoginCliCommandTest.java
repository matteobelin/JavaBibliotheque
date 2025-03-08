package com.esgi.presentation.cli.auth.login;

import com.esgi.core.exceptions.IncorrectCredentialsException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.domain.auth.AuthService;
import com.esgi.domain.users.UserEntity;
import com.esgi.presentation.cli.ExitCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class LoginCliCommandTest {

    @InjectMocks
    LoginCliCommandNode loginCliCommandNode;

    @Mock
    AuthService authService;

    @Test
    public void should_return_OK() throws IncorrectCredentialsException, InternalErrorException {
        // Arrange
        String[] args = { "email", "password", "-s" };

        var user = new UserEntity();
        user.setEmail("email");
        user.setPassword("password");

        Mockito.when(authService.login(any())).thenReturn(user);

        // Act
        var exitCode = this.loginCliCommandNode.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.OK);
    }

    @Test
    public void should_return_ARGUMENT_MISSING_when_value_is_missing() {
        // Arrange
        String[] args = { "email" };

        // Act
        var exitCode = this.loginCliCommandNode.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.ARGUMENT_MISSING);
    }

    @Test
    public void should_return_ARGUMENT_INVALID_when_authService_throw_IncorrectCredentialsException() throws IncorrectCredentialsException, InternalErrorException {
        // Arrange
        String[] args = { "email", "password" };

        Mockito.when(this.authService.login(any()))
                .thenThrow(IncorrectCredentialsException.class);

        // Act
        var exitCode = this.loginCliCommandNode.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.ARGUMENT_INVALID);
    }
}

package com.esgi.domain.auth;

import com.esgi.core.exceptions.IncorrectCredentialsException;
import com.esgi.core.exceptions.InternalErrorException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.domain.auth.impl.AuthServiceImpl;
import com.esgi.domain.serialization.Serializer;
import com.esgi.domain.users.UserEntity;
import com.esgi.domain.users.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserService userService;

    @Mock
    private Serializer<AuthCredentials> serializer;

    @Test
    public void login_should_connect_user() throws NotFoundException, IncorrectCredentialsException, IOException, InternalErrorException {
        // Arrange
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setEmail("test@test.com");
        user.setPassword("password");

        AuthCredentials credentials = new AuthCredentials("test@test.com", "password", true);

        when(this.userService.getUserByEmail(any())).thenReturn(user);
        doNothing().when(this.serializer).serialize(any());

        // Act
        UserEntity result = this.authService.login(credentials);

        // Assert
        Assertions.assertThat(result)
                .isNotNull()
                .isEqualTo(user);

        Assertions.assertThat(this.authService.getLoggedInUser())
                .isNotNull()
                .isEqualTo(user);

        Assertions.assertThat(this.authService.isLoggedIn())
                .isTrue();
    }

    @Test
    public void login_should_throw_when_email_not_found() throws NotFoundException {
        // Arrange
        AuthCredentials credentials = new AuthCredentials("test@test.com", "password", true);

        when(this.userService.getUserByEmail(any())).thenThrow(new NotFoundException("User not found"));

        // Act - Assert
        Assertions.assertThatThrownBy(() -> this.authService.login(credentials))
                .isInstanceOf(IncorrectCredentialsException.class);
    }

    @Test
    public void login_should_throw_when_password_dont_match() throws NotFoundException {
        // Arrange
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setEmail("test@test.com");
        user.setPassword("password1");

        AuthCredentials credentials = new AuthCredentials("test@test.com", "password2", true);

        when(this.userService.getUserByEmail(any())).thenReturn(user);

        // Act - Assert
        Assertions.assertThatThrownBy(() -> this.authService.login(credentials))
                .isInstanceOf(IncorrectCredentialsException.class);
    }

    @Test
    public void logout_should_remove_connected_user() throws NoSuchFieldException, IllegalAccessException, InternalErrorException {
        // Arrange
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setEmail("test@test.com");
        user.setPassword("password");

        // use reflection to set the connected user and simulate a login
        Field connectedUserField = AuthServiceImpl.class.getDeclaredField("connectedUser");
        connectedUserField.setAccessible(true);
        connectedUserField.set(this.authService, user);

        when(serializer.getDestFilePath()).thenReturn("destFilePath");

        // Act
        this.authService.logout();

        // Assert
        Assertions.assertThat(this.authService.isLoggedIn())
                .isFalse();

        Assertions.assertThat(this.authService.getLoggedInUser())
                .isNull();
    }

    @Test
    public void loginWithSavedCredentials_should_connect_user() throws IncorrectCredentialsException, IOException, NotFoundException, InternalErrorException {
        // Arrange
        UserEntity user = new UserEntity();
        user.setId(1);
        user.setEmail("test@test.com");
        user.setPassword("password");

        AuthCredentials credentials = new AuthCredentials("test@test.com", "password", true);

        when(this.serializer.deserialize()).thenReturn(credentials);
        when(this.userService.getUserByEmail(any())).thenReturn(user);

        // Act
        this.authService.tryToLoginWithSavedCredentials();

        // Assert
        Assertions.assertThat(this.authService.getLoggedInUser())
                .isNotNull()
                .isEqualTo(user);
    }

    @Test
    public void loginWithSavedCredentials_should_not_login_when_no_credentials_saved() throws IncorrectCredentialsException, IOException, InternalErrorException {
        // Arrange
        when(this.serializer.deserialize()).thenThrow(new IOException());

        // Act - Assert
       Assertions.assertThatThrownBy(() -> this.authService.tryToLoginWithSavedCredentials())
               .isInstanceOf(InternalErrorException.class);

        Assertions.assertThat(this.authService.getLoggedInUser())
                .isNull();
    }
}

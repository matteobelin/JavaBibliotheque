package com.esgi.presentation.cli.users.edit;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InvalidArgumentException;
import com.esgi.core.exceptions.NotFoundException;
import com.esgi.domain.auth.AuthService;
import com.esgi.domain.users.UserEntity;
import com.esgi.domain.users.UserService;
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
public class EditUserCliCommandNodeTest {

    @InjectMocks
    private EditUserCliCommandNode editUserCliCommandNode;

    @Mock
    private AuthService authService;

    @Mock
    private UserService userService;

    @Test
    public void should_return_OK() throws InvalidArgumentException, ConstraintViolationException, NotFoundException {
        // Arrange
        String[] args = new String[] {"-n", "newName", "-e", "newEmail"};
        var user = new UserEntity();
        user.setEmail("email");
        user.setName("name");
        user.setAdmin(false);

        Mockito.when(this.authService.isLoggedIn()).thenReturn(true);
        Mockito.when(this.authService.getLoggedInUser()).thenReturn(user);

        Mockito.when(this.userService.getUserByEmail(any())).thenReturn(user);
        Mockito.doNothing().when(this.userService).updateUser(any());

        // Act
        var exitCode = this.editUserCliCommandNode.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.OK);
    }

    @Test
    public void update_other_user_should_return_OK_when_user_is_admin() throws InvalidArgumentException, ConstraintViolationException, NotFoundException {
        // Arrange
        String[] args = new String[] {"other.user@email.com", "-n", "newName", "-e", "newEmail"};

        var userToEdit = new UserEntity();
        userToEdit.setEmail("other.user@email.com");
        userToEdit.setName("name");
        userToEdit.setAdmin(false);

        Mockito.when(this.authService.isLoggedIn()).thenReturn(true);
        Mockito.when(this.authService.isLoggedInUserAdmin()).thenReturn(true);

        Mockito.when(this.userService.getUserByEmail(any())).thenReturn(userToEdit);
        Mockito.doNothing().when(this.userService).updateUser(any());

        // Act
        var exitCode = this.editUserCliCommandNode.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.OK);
    }

    @Test
    public void update_other_user_should_return_ACCESS_DENIED_when_user_is_not_admin() throws InvalidArgumentException, ConstraintViolationException, NotFoundException {
        // Arrange
        String[] args = new String[] {"other.user@email.com", "-n", "newName", "-e", "newEmail"};

        var userToEdit = new UserEntity();
        userToEdit.setEmail("other.user@email.com");
        userToEdit.setName("name");
        userToEdit.setAdmin(false);

        Mockito.when(this.authService.isLoggedIn()).thenReturn(true);
        Mockito.when(this.authService.isLoggedInUserAdmin()).thenReturn(false);

        // Act
        var exitCode = this.editUserCliCommandNode.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.ACCESS_DENIED);
    }

    @Test
    public void update_should_return_ACCESS_DENIED_when_user_is_not_logged_in() throws InvalidArgumentException, ConstraintViolationException, NotFoundException {
        // Arrange
        String[] args = new String[] {"-n", "newName", "-e", "newEmail"};

        Mockito.when(this.authService.isLoggedIn()).thenReturn(false);

        // Act
        var exitCode = this.editUserCliCommandNode.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.ACCESS_DENIED);
    }
}

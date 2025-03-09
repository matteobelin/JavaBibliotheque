package com.esgi.presentation.cli.users.delete;

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
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeleteUserCliCommandNodeTest {

    @InjectMocks
    private DeleteUserCliCommandNode deleteUserCliCommandNode;

    @Mock
    private AuthService authService;

    @Mock
    private UserService userService;

    @Test
    public void should_return_ok_when_user_logged_in() throws NotFoundException {
        // Arrange
        String[] args = new String[]{};
        String email = "test@gmail.com";

        var user = new UserEntity();
        user.setId(1);
        user.setEmail(email);

        when(this.authService.isLoggedIn()).thenReturn(true);
        when(this.authService.isLoggedInUserAdmin()).thenReturn(false);
        when(this.authService.getLoggedInUser()).thenReturn(user);
        when(this.userService.getUserByEmail(any())).thenReturn(user);
        doNothing().when(this.userService).deleteUser(1);

        // Act
        var exitCode = this.deleteUserCliCommandNode.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.OK);
    }

    @Test
    public void should_return_ok_when_admin_delete_other_user() throws NotFoundException {
        // Arrange
        String email = "test@gmail.com";
        String[] args = new String[]{email};

        var user = new UserEntity();
        user.setId(1);
        user.setEmail(email);

        when(this.authService.isLoggedIn()).thenReturn(true);
        when(this.authService.isLoggedInUserAdmin()).thenReturn(true);
        when(this.userService.getUserByEmail(any())).thenReturn(user);
        doNothing().when(this.userService).deleteUser(1);

        // Act
        var exitCode = this.deleteUserCliCommandNode.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.OK);
    }

    @Test
    public void should_return_ACCESS_DENIED_when_not_logged_user_run_command() throws NotFoundException {
        // Arrange
        String[] args = new String[]{};

        when(this.authService.isLoggedIn()).thenReturn(false);

        // Act
        var exitCode = this.deleteUserCliCommandNode.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.ACCESS_DENIED);
    }

    @Test
    public void should_return_ACCESS_DENIED_when_not_admin_user_run_command_with_email() throws NotFoundException {
        // Arrange
        String email = "test@gmail.com";
        String[] args = new String[]{email};

        when(this.authService.isLoggedIn()).thenReturn(true);
        when(this.authService.isLoggedInUserAdmin()).thenReturn(false);

        // Act
        var exitCode = this.deleteUserCliCommandNode.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.ACCESS_DENIED);
    }
}

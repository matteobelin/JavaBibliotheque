package com.esgi.presentation.cli.auth.logout;

import com.esgi.domain.auth.AuthService;
import com.esgi.presentation.cli.ExitCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LogoutCliCommandTest {

    @InjectMocks
    private LogoutCliCommandNode logoutCliCommandNode;

    @Mock
    private AuthService authService;

    @Test
    public void should_return_OK()  {
        // Arrange
        String[] args = new String[] {};

        Mockito.when(authService.isLoggedIn()).thenReturn(true);
        Mockito.doNothing().when(authService).logout();

        // Act
        var exitCode = this.logoutCliCommandNode.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.OK);
    }

    @Test
    public void should_return_OK_when_user_not_logged_in()  {
        // Arrange
        String[] args = new String[] {};

        Mockito.when(authService.isLoggedIn()).thenReturn(false);

        // Act
        var exitCode = this.logoutCliCommandNode.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.OK);
    }
}

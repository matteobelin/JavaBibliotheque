package com.esgi.presentation.cli;

import com.esgi.domain.users.UserService;
import com.esgi.domain.users.impl.UserServiceImpl;
import com.esgi.presentation.AppLogger;
import com.esgi.presentation.cli.users.UserCliCommandNode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CliEntryPointTest {

    private CliEntryPoint entryPoint;

    @Mock
    private UserCliCommandNode userCliCommandNode;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        entryPoint = new CliEntryPoint(userCliCommandNode);
    }

    @Test
    public void should_find_user_command() {
        // Arrange
        String[] args = {"users", "add"};
        Mockito.when(userCliCommandNode.getName()).thenReturn("users");
        Mockito.when(userCliCommandNode.run(any())).thenReturn(ExitCode.OK);

        // Act
        ExitCode exitCode = this.entryPoint.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.OK);
    }

    @Test
    public void should_not_find_unknown_command() {
        // Arrange
        String[] args = {"command"};
        Mockito.when(userCliCommandNode.getName()).thenReturn("users");

        // Act
        ExitCode exitCode = this.entryPoint.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.COMMAND_NOT_FOUND);
    }

    @Test
    public void should_have_a_name() {
        // Act
        String commandName = this.entryPoint.getName();

        // Assert
        Assertions.assertThat(commandName)
                .isNotNull()
                .isNotBlank();
    }

    @Test
    public void should_have_a_description() {
        // Act
        String commandDescription = this.entryPoint.getDescription();

        // Assert
        Assertions.assertThat(commandDescription)
                .isNotNull()
                .isNotBlank();
    }
}

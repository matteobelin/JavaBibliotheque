package com.esgi.presentation.cli.users;

import com.esgi.presentation.cli.ExitCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserCliCommandNodeTest {

    @InjectMocks
    private UserCliCommandNode userCliCommandNode;

    @Test
    public void should_find_help_command() {
        // Arrange
        String[] args = {"help"};

        // Act
        ExitCode exitCode = this.userCliCommandNode.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.OK);
    }

    @Test
    public void should_not_find_unknown_command() {
        // Arrange
        String[] args = {"command"};

        // Act
        ExitCode exitCode = this.userCliCommandNode.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.COMMAND_NOT_FOUND);
    }

    @Test
    public void should_have_name_users() {
        // Act
        String commandName = this.userCliCommandNode.getName();

        // Assert
        Assertions.assertThat(commandName).isEqualTo("users");
    }

    @Test
    public void should_have_a_description() {
        // Act
        String commandDescription = this.userCliCommandNode.getDescription();

        // Assert
        Assertions.assertThat(commandDescription)
                .isNotNull()
                .isNotBlank();
    }
}

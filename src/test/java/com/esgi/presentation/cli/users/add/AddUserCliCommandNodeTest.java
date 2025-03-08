package com.esgi.presentation.cli.users.add;

import com.esgi.domain.users.impl.UserServiceImpl;
import com.esgi.presentation.cli.ExitCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AddUserCliCommandNodeTest {

    @InjectMocks
    private AddUserCliCommandNode addUserCliCommandNode;

    @Mock
    private UserServiceImpl userService;

    @Test
    public void should_create_user() {
        // Arrange
        String[] args = {"email", "password"};

        // Act
        ExitCode exitCode = this.addUserCliCommandNode.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.OK);
    }

    @Test
    public void with_valid_option_should_create_user() {
        // Arrange
        String[] args = {"-a", "email", "password"};

        // Act
        ExitCode exitCode = this.addUserCliCommandNode.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.OK);
    }

    @Test
    public void with_missing_args_should_not_create_user() {
        // Arrange
        String[] args = {"email"};

        // Act
        ExitCode exitCode = this.addUserCliCommandNode.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.ARGUMENT_MISSING);
    }

    @Test
    public void with_unknown_option_should_create_user() {
        // Arrange
        String[] args = {"-option", "email", "password"};

        // Act
        ExitCode exitCode = this.addUserCliCommandNode.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.OK);
    }
}

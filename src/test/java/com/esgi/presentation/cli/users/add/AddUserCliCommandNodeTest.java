package com.esgi.presentation.cli.users.add;

import com.esgi.core.exceptions.ConstraintViolationException;
import com.esgi.core.exceptions.InvalidArgumentException;
import com.esgi.domain.users.UserEntity;
import com.esgi.domain.users.impl.UserServiceImpl;
import com.esgi.presentation.cli.ExitCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AddUserCliCommandNodeTest {

    private AddUserCliCommandNode addUserCliCommandNode;

    @Mock
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        this.addUserCliCommandNode = new AddUserCliCommandNode(userService);
    }

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

    @Test
    public void should_have_name_add() {
        // Act
        String commandName = this.addUserCliCommandNode.getName();

        // Assert
        Assertions.assertThat(commandName).isEqualTo("add");
    }

    @Test
    public void should_have_a_description() {
        // Act
        String commandDescription = this.addUserCliCommandNode.getDescription();

        // Assert
        Assertions.assertThat(commandDescription)
                .isNotNull()
                .isNotBlank();
    }
}

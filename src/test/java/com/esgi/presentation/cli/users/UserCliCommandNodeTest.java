package com.esgi.presentation.cli.users;

import com.esgi.domain.users.impl.UserServiceImpl;
import com.esgi.presentation.cli.ExitCode;
import com.esgi.presentation.cli.users.add.AddUserCliCommandNode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserCliCommandNodeTest {

    private UserCliCommandNode userCliCommandNode;

    @Mock
    private AddUserCliCommandNode addUserCliCommandNode;

    @Mock
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        this.userCliCommandNode = new UserCliCommandNode(addUserCliCommandNode);
    }

    @Test
    public void should_find_add_command() {
        // Arrange
        String[] args = {"add"};
        Mockito.when(addUserCliCommandNode.getName()).thenReturn("add");
        Mockito.when(addUserCliCommandNode.run(any())).thenReturn(ExitCode.OK);

        // Act
        ExitCode exitCode = this.userCliCommandNode.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.OK);
    }

    @Test
    public void should_not_find_unknown_command() {
        // Arrange
        String[] args = {"command"};
        Mockito.when(addUserCliCommandNode.getName()).thenReturn("add");

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

package com.esgi.presentation.cli;

import com.esgi.presentation.cli.users.UserCliCommandNode;
import com.esgi.presentation.cli.users.add.AddUserCliCommandNode;
import com.esgi.presentation.cli.options.AdminCommandOption;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class HelpCliCommandTest {

    private HelpCliCommand helpCliCommand;

    @Mock
    private UserCliCommandNode userCliCommandNode;

    @Mock
    private AddUserCliCommandNode addUserCliCommandNode;

    @Mock
    private AdminCommandOption adminCommandOption;

    @BeforeEach
    void setUp() {
        List<CliCommandNode> commands = new ArrayList<>();
        commands.add(userCliCommandNode);
        this.helpCliCommand = new HelpCliCommand(commands);
    }

    @Test
    public void should_write_help_message() {
        // Arrange
        Mockito.when(userCliCommandNode.getName()).thenReturn("name");
        Mockito.when(userCliCommandNode.getDescription()).thenReturn("description");
        Mockito.when(userCliCommandNode.getCommandOptions()).thenReturn(List.of(adminCommandOption));
        Mockito.when(adminCommandOption.getName()).thenReturn("option_name");
        Mockito.when(adminCommandOption.getShortName()).thenReturn("option_short_name");
        Mockito.when(adminCommandOption.getDescription()).thenReturn("option_description");
        Mockito.when(userCliCommandNode.getChildrenCommands()).thenReturn(List.of(addUserCliCommandNode));
        Mockito.when(addUserCliCommandNode.getName()).thenReturn("sub_command_name");
        Mockito.when(addUserCliCommandNode.getDescription()).thenReturn("sub_command_description");
        Mockito.when(addUserCliCommandNode.getChildrenCommands()).thenReturn(List.of());
        Mockito.when(addUserCliCommandNode.getCommandOptions()).thenReturn(List.of());

        // Act
        ExitCode exitCode = this.helpCliCommand.run(new String[] {});

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.OK);
    }

    @Test
    public void with_command_arg_should_write_help_message_for_command() {
        // Arrange
        String[] args = {"name"};
        Mockito.when(userCliCommandNode.getName()).thenReturn("name");
        Mockito.when(userCliCommandNode.getDescription()).thenReturn("description");
        Mockito.when(userCliCommandNode.getCommandOptions()).thenReturn(List.of(adminCommandOption));
        Mockito.when(adminCommandOption.getName()).thenReturn("option_name");
        Mockito.when(adminCommandOption.getShortName()).thenReturn("option_short_name");
        Mockito.when(adminCommandOption.getDescription()).thenReturn("option_description");
        Mockito.when(userCliCommandNode.getChildrenCommands()).thenReturn(List.of(addUserCliCommandNode));
        Mockito.when(addUserCliCommandNode.getName()).thenReturn("sub_command_name");
        Mockito.when(addUserCliCommandNode.getDescription()).thenReturn("sub_command_description");
        Mockito.when(addUserCliCommandNode.getChildrenCommands()).thenReturn(List.of());
        Mockito.when(addUserCliCommandNode.getCommandOptions()).thenReturn(List.of());

        // Act
        ExitCode exitCode = this.helpCliCommand.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.OK);
    }

    @Test
    public void with_unknown_command_arg_should_not_write_help_message() {
        // Arrange
        String[] args = {"command"};
        Mockito.when(userCliCommandNode.getName()).thenReturn("name");

        // Act
        ExitCode exitCode = this.helpCliCommand.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.ARGUMENT_INVALID);
    }

    @Test
    public void should_not_have_child_command() {
        // Act
        List<CliCommandNode> childrenCommands = this.helpCliCommand.getChildrenCommands();

        // Assert
        Assertions.assertThat(childrenCommands).isEmpty();
    }

    @Test
    public void should_have_a_name_help() {
        // Act
        String commandName = this.helpCliCommand.getName();

        // Assert
        Assertions.assertThat(commandName).isEqualTo("help");
    }

    @Test
    public void should_have_a_description() {
        // Act
        String commandDescription = this.helpCliCommand.getDescription();

        // Assert
        Assertions.assertThat(commandDescription)
                .isNotNull()
                .isNotBlank();
    }
}

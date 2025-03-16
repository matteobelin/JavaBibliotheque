package com.esgi.presentation.cli;

import com.esgi.core.exceptions.OptionRequiresValueException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CliCommandNodeTest {
    private static class CliCommandNodeTestImpl extends CliCommandNode {
        public CliCommandNodeTestImpl() {
            super("NAME", "DESCRIPTION");

            this.childrenCommands.add(new ChildCliCommandNodeTest());

            this.commandOptions.add(CliCommandNodeTest.createDummyCommandOption());
        }
    }

    private static class ChildCliCommandNodeTest extends CliCommandNode {
        public ChildCliCommandNodeTest() {
            super("CHILD_NAME", "CHILD_DESCRIPTION");
        }

        @Override
        public ExitCode run(String[] args) {
            return ExitCode.OK;
        }
    }

    private CliCommandNode commandNode;

    @BeforeEach
    public void setUp() {
        commandNode = new CliCommandNodeTestImpl();
    }

    @Test
    public void should_find_child_command() {
        // Arrange
        String[] args = new String[] {"CHILD_NAME"};

        // Act
        ExitCode exitCode = this.commandNode.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.OK);
    }

    @Test
    public void should_not_find_unknown_command() {
        // Arrange
        String[] args = new String[] {"command"};

        // Act
        ExitCode exitCode = this.commandNode.run(args);

        // Assert
        Assertions.assertThat(exitCode).isEqualTo(ExitCode.COMMAND_NOT_FOUND);
    }

    @Test
    public void extractOptionsFromArgs_should_only_extract_options() throws OptionRequiresValueException {
        // Arrange
        String[] args = new String[] { "biblio", "users", "add", "--OPTION_NAME", "VALUE", "email", "password" };

        // Act
        var foundOptions = commandNode.extractOptionsFromArgs(args);

        // Assert
        Assertions.assertThat(foundOptions)
                .hasSize(1)
                .map(CliCommandNodeOption::getName)
                .allMatch(name -> name.equals("OPTION_NAME"));

        Assertions.assertThat(foundOptions)
                .map(CliCommandNodeOption::getValue)
                .allMatch(value -> value.equals("VALUE"));
    }

    @Test
    public void extractOptionsFromArgs_should_throw_when_value_is_missing() {
        // Arrange
        String[] args = new String[] { "biblio", "users", "add", "--OPTION_NAME"};

        // Act - Assert
        Assertions.assertThatThrownBy(() -> commandNode.extractOptionsFromArgs(args))
                .isInstanceOf(OptionRequiresValueException.class);
    }

    private static CliCommandNodeOption createDummyCommandOption() {
        return new CliCommandNodeOption("OPTION_NAME", "OPTION_SHORT_NAME", "OPTION_DESCRIPTION", true) {};
    }
}

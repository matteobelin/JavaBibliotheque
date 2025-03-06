package com.esgi.presentation.cli;

import com.esgi.core.exceptions.OptionRequiresValueException;
import com.esgi.presentation.CommandAccessLevel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CliCommandNodeTest {
    private static class CliCommandNodeTestImpl extends CliCommandNode {
        private final List<CliCommandNodeOption> options = new ArrayList<>();

        public CliCommandNodeTestImpl() {
            super("NAME", "DESCRIPTION", CommandAccessLevel.PUBLIC);
            this.options.add(CliCommandNodeTest.createDummyCommandOption());
        }

        @Override
        public List<CliCommandNode> getChildrenCommands() {
            return List.of();
        }

        @Override
        public List<CliCommandNodeOption> getCommandOptions() {
            return options;
        }
    }

    private CliCommandNode commandNode;

    @BeforeEach
    public void setUp() {
        commandNode = new CliCommandNodeTestImpl();
    }

    @Test
    public void shouldExtractOptionsFromArgs() throws OptionRequiresValueException {
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
    public void extractOptionsFromArgsShouldThrowWhenValueIsMissing() {
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

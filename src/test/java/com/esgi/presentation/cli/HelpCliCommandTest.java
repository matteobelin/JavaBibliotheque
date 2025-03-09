package com.esgi.presentation.cli;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class HelpCliCommandTest {

    @InjectMocks
    private HelpCliCommand helpCliCommand;

    @Test
    public void should_not_have_child_command() {
        // Act
        List<CliCommandNode> childrenCommands = this.helpCliCommand.getChildrenCommands();

        // Assert
        Assertions.assertThat(childrenCommands).isEmpty();
    }
}

package com.esgi.presentation.cli.auth;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthCliCommandNodeTest {

    @InjectMocks
    private AuthCliCommandNode authCliCommandNode;

    @Test
    public void should_have_3_child_commands() {
        // Act
        var childCommands = authCliCommandNode.getChildrenCommands();

        // Assert
        Assertions.assertThat(childCommands).hasSize(3);
    }

}

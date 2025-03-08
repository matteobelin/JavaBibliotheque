package com.esgi.presentation.cli.users.add;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddUserCliCommandFactoryTest {

    @Test
    public void should_return_AddUserCliCommand() {
        var command = AddUserCliCommandFactory.makeAddUserCliCommandNode();

        Assertions.assertThat(command)
                .isNotNull()
                .isInstanceOf(AddUserCliCommandNode.class);
    }
}

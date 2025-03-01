package com.esgi.presentation.cli.options;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdminCommandOptionTest {

    private AdminCommandOption option;

    @BeforeEach
    public void setUp() {
        this.option = new AdminCommandOption("description");
    }

    @Test
    public void should_have_a_name_admin() {
        // Act
        String commandName = this.option.getName();

        // Assert
        Assertions.assertThat(commandName).isEqualTo("admin");
    }

    @Test
    public void should_have_a_short_name_a() {
        // Act
        String commandShortName = this.option.getShortName();

        // Assert
        Assertions.assertThat(commandShortName).isEqualTo("a");
    }

    @Test
    public void should_not_have_a_value() {
        // Act
        String commandValue = this.option.getValue();

        // Assert
        Assertions.assertThat(commandValue).isNull();
    }

    @Test
    public void should_have_a_description() {
        // Act
        String commandDescription = this.option.getDescription();

        // Assert
        Assertions.assertThat(commandDescription).isEqualTo("description");
    }
}

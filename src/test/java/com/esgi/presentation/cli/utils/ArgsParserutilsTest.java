package com.esgi.presentation.cli.utils;

import com.esgi.presentation.cli.CliCommandNodeOption;
import com.esgi.presentation.cli.options.AdminCommandOption;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ArgsParserutilsTest {

    @Test
    public void shouldExtractValuesFromArgs() {
        // Arrange
        String[] args = new String[] {"biblio", "users", "add", "--admin", "email", "password"};

        // Act
        List<String> values = ArgsParserUtils.extractValuesFromArgs(args);

        // Assert
        Assertions.assertThat(values).containsExactly("biblio", "users", "add", "email", "password");
    }

    @Test
    public void shouldFindOptionWithName() {
        // Arrange
        AdminCommandOption adminCommandOption = new AdminCommandOption("test");
        List<CliCommandNodeOption> options = new ArrayList<>();
        options.add(adminCommandOption);

        // Act
        var foundOption = ArgsParserUtils.findOptionByName(options, "--admin");

        // Assert
        Assertions.assertThat(foundOption.isPresent()).isTrue();
        Assertions.assertThat(foundOption.get().getName()).isEqualTo("admin");
    }

    @Test
    public void shouldFindOptionWithShortName() {
        // Arrange
        AdminCommandOption adminCommandOption = new AdminCommandOption("test");
        List<CliCommandNodeOption> options = new ArrayList<>();
        options.add(adminCommandOption);

        // Act
        var foundOption = ArgsParserUtils.findOptionByName(options, "-a");

        // Assert
        Assertions.assertThat(foundOption.isPresent()).isTrue();
        Assertions.assertThat(foundOption.get().getShortName()).isEqualTo("a");
    }

}

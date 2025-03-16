package com.esgi.presentation.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class StringUtilsTest {

    @Test
    public void wrapInBox_should_return_lines_in_a_box() {
        // Arrange
        List<String> lines = List.of(
        "Hello World"
        );

        // Act
        List<String> result = StringUtils.wrapInBox(lines, '*', '/', 3, 1);

        // Assert
        Assertions.assertThat(result).containsExactlyInAnyOrder(
                "/*****************/",
                "/                 /",
                "/   Hello World   /",
                "/                 /",
                "/*****************/"
        );
    }

    @Test
    public void makeTable_should_make_a_table() {
        List<List<String>> data = List.of(
                List.of("column 1", "column 2"),
                List.of("Hello", "World")
        );

        var table = StringUtils.makeTable(data);

        Assertions.assertThat(table).containsExactly(
                " | column 1 | column 2 | ",
                " -----------------------",
                " | Hello    | World    | ",
                " -----------------------"
        );
    }

    @Test
    public void repeatEmptySpace_should_return_empty_spaces() {
        // Arrange
        int count = 3;

        // Act
        String result = StringUtils.repeatEmptySpace(count);

        // Assert
        Assertions.assertThat(result)
                .isBlank().
                hasSize(3);
    }

    @Test
    public void yesNoValueToBoolean_should_return_true_with_yes() {
        String value = "yes";

        boolean result = StringUtils.yesNoValueToBoolean(value);

        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void yesNoValueToBoolean_should_return_true_with_y() {
        String value = "y";

        boolean result = StringUtils.yesNoValueToBoolean(value);

        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void yesNoValueToBoolean_should_return_false_with_no() {
        String value = "no";

        boolean result = StringUtils.yesNoValueToBoolean(value);

        Assertions.assertThat(result).isFalse();
    }
}

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
}

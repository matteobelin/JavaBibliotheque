package com.esgi.presentation.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class StringUtils {

    public static List<String> wrapInSmallBox(List<String> lines) {
        return wrapInBox(lines, '-', '|', 1, 0);
    }

    public static List<String> wrapInLargeBox(List<String> lines) {
        return wrapInBox(lines, '*', '/', 3, 1);
    }

    public static List<String> wrapInBox(List<String> lines, Character horizontalBoxChar, Character verticalBoxChar, int horizontalPadding, int verticalPadding) {
        int longestLineLength = lines.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse("")
                .length();
        int boxLength = longestLineLength + horizontalPadding * 2;

        String padding = repeatEmptySpace(horizontalPadding);
        var formattedLines = lines.stream()
                .map(line -> line + repeatEmptySpace(longestLineLength - line.length()))
                .map(line -> verticalBoxChar + padding + line + padding + verticalBoxChar)
                .collect(Collectors.toCollection(ArrayList::new));

        String emptyLine = verticalBoxChar+ repeatEmptySpace(boxLength) + verticalBoxChar;
        for (int i = 0; i < verticalPadding; i++) {
            formattedLines.add(0, emptyLine);
            formattedLines.add(emptyLine);
        }

        String horizontalBoxLine = horizontalBoxChar.toString().repeat(boxLength);
        String horizontalLine = verticalBoxChar + horizontalBoxLine + verticalBoxChar;

        formattedLines.add(0, horizontalLine);
        formattedLines.add(horizontalLine);

        return formattedLines;
    }

    public static String repeatEmptySpace(int count) {
        return " ".repeat(count);
    }
}

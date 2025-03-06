package com.esgi.presentation.cli.utils;

import com.esgi.presentation.cli.CliCommandNodeOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class ArgsParserUtils {

    public static List<String> extractValuesFromArgs(String[] args) {
        List<String> values = new ArrayList<>();

        for (String arg : args) {
            if (isAnOption(arg)) {
                continue;
            }

            values.add(arg);
        }

        return values;
    }

    public static Optional<CliCommandNodeOption> findOptionByName(
            List<CliCommandNodeOption> options,
            String arg
    ) {
        String optionName = arg.replaceAll("-", "");

        boolean isShortName = !arg.startsWith("--");
        if (isShortName) {
            return options.stream()
                    .filter(option -> option.getShortName().equals(optionName))
                    .findFirst();
        }

        return options.stream()
                .filter(option -> option.getName().equals(optionName))
                .findFirst();
    }

    public static boolean isAnOption(String arg) {
        return arg.startsWith("-");
    }

    public static boolean isNotAnOption(String arg) {
        return !isAnOption(arg);
    }
}

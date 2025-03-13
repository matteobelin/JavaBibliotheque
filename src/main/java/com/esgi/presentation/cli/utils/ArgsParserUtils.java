package com.esgi.presentation.cli.utils;

import com.esgi.presentation.cli.CliCommandNodeOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class ArgsParserUtils {

    public static List<String> extractValuesFromArgs(String[] args, List<CliCommandNodeOption> options) {
        List<String> values = new ArrayList<>();

        for (int i = 0; i < args.length; ++i) {
            String arg = args[i];

            if (isNotAnOption(arg)) {
                values.add(arg);
            } else {
                var foundOption = findOptionByName(options, arg);

                if (foundOption.isPresent() && foundOption.get().requiresValue()) {
                    i++; // skip the value of the option
                }
            }
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

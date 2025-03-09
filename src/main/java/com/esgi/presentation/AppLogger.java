package com.esgi.presentation;

import java.util.List;


public final class AppLogger {

    public static void write(AppLoggerColorEnum color, String message) {
        System.out.println(color.value + message + AppLoggerColorEnum.DEFAULT.value);
    }

    public static void writeLines(AppLoggerColorEnum color, List<String> lines) {
        for (String line : lines) {
            write(color, line);
        }
    }

    public static void emptyLine() {
        info(" ");
    }

    public static void info(String message) {
        AppLogger.write(AppLoggerColorEnum.DEFAULT, message);
    }

    public static void success(String message) {
        AppLogger.write(AppLoggerColorEnum.GREEN, message);
    }

    public static void error(String message) {
        AppLogger.write(AppLoggerColorEnum.RED, message);
    }

    public static void error(List<String> lines) {
        AppLogger.writeLines(AppLoggerColorEnum.RED, lines);
    }

    public static void warn(String message) {
        AppLogger.write(AppLoggerColorEnum.ORANGE, message);
    }
}

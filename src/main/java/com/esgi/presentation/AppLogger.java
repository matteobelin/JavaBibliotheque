package com.esgi.presentation;

import com.esgi.presentation.utils.StringUtils;

import java.util.List;
import java.util.Scanner;


public final class AppLogger {
    private AppLogger() {}

    private static final Scanner scanner = new Scanner(System.in);

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

    public static void info(List<String> lines) {
        AppLogger.writeLines(AppLoggerColorEnum.DEFAULT, lines);
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

    public static void warn(List<String> lines) {
        AppLogger.writeLines(AppLoggerColorEnum.ORANGE, lines);
    }

    public static boolean askForConfirmation(String message) {
        info(message);
        return StringUtils.yesNoValueToBoolean(scanner.nextLine());
    }
}

package com.esgi.presentation.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AppFolderUtils {
    public static final String APP_DATA_DIRECTORY = System.getProperty("user.home") + "/Biblio";

    public static final String PATH_TO_AUTH_FILE = APP_DATA_DIRECTORY + "/auth.txt";

    public static void makeSureAppFolderExists() throws IOException {
        File file = new File(APP_DATA_DIRECTORY);

        if (!file.exists()) {
            Files.createDirectory(Paths.get(APP_DATA_DIRECTORY));
        }
    }
}

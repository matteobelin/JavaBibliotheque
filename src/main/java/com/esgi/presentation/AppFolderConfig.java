package com.esgi.presentation;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

public final class AppFolderConfig {
    private  static final String DATA_FOLDER_NAME = "data";

    public static final String AUTH_FILE_NAME = "auth.biblio";

    private static String pathToAuthFile;

    private final String pathToAppFolder;

    private final String pathToDataFolder;

    public AppFolderConfig() throws URISyntaxException {
        var appFolderUri = AppFolderConfig.class.getProtectionDomain().getCodeSource().getLocation().toURI();

        this.pathToAppFolder = new File(appFolderUri).getParentFile().getAbsolutePath();
        this.pathToDataFolder = pathToAppFolder + File.separator + DATA_FOLDER_NAME;

        pathToAuthFile = this.pathToDataFolder + File.separator + AUTH_FILE_NAME;
    }

    public static String getPathToAuthFile() {
        if (pathToAuthFile == null) {
            throw new RuntimeException("Path to auth file not set, this means the AppFolderConfig class was not instantiated once.");
        }
        return pathToAuthFile;
    }

    public void initAppFolderConfig() throws IOException {
        File appDataFolder = new File(this.pathToDataFolder);

        if (!appDataFolder.exists()) {
            Files.createDirectory(appDataFolder.toPath());
        }
    }
}

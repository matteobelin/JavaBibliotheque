package com.esgi.helpers;

import com.esgi.data.DataConfig;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseTestHelper {

    private static final Logger logger = Logger.getLogger(DatabaseTestHelper.class.getName());

    private static final String createTestDbScript = "scripts/create_test_db.sql";
    private static final String insertTestDataScript = "scripts/insert_test_data.sql";
    private static final String truncateTestDataScript = "scripts/truncate_test_db.sql";

    private static boolean isDbInitialized = false;

    private DatabaseTestHelper() {}

    public static void initTestDb() {
        if (isDbInitialized) {
            return;
        }

        DataConfig.useTestDb();

        logger.info("Creating test database...");
        executeSqlScript(createTestDbScript);

        logger.info("Inserting test data...");
        executeSqlScript(insertTestDataScript);

        isDbInitialized = true;
    }

    public static void resetTestDb() {
        if (!isDbInitialized) {
            initTestDb();
            return;
        }

        DataConfig.useTestDb();

        logger.info("Truncating test database...");
        executeSqlScript(truncateTestDataScript);

        logger.info("Inserting test data...");
        executeSqlScript(insertTestDataScript);
    }

    private static void executeSqlScript(String file) {
        try(var connection = DriverManager.getConnection(DataConfig.getDbConnectionString())) {
            URL fileUrl = getResourceFile(file);
            String script = Files.readString(Path.of(fileUrl.toURI()));
            String[] statements = script.split(";");

            for (String statement : statements) {
                connection.createStatement().execute(statement.trim());
            }

        } catch (SQLException | IOException | URISyntaxException ex) {
            logger.severe("Error during database initialization: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    private static URL getResourceFile(String file) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResource(file);
    }
}

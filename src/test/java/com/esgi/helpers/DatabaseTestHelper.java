package com.esgi.helpers;

import com.esgi.data.DataConfig;

import java.util.logging.Logger;

public class DatabaseTestHelper {

    private static final Logger logger = Logger.getLogger(DatabaseTestHelper.class.getName());

    private static final String createTestDbScript = "scripts/create_test_db.sql";
    private static final String insertTestDataScript = "scripts/insert_test_data.sql";
    private static final String truncateTestDataScript = "scripts/truncate_test_db.sql";

    private static final DataConfig dataConfig = DataConfig.getInstance();

    private static boolean isDbInitialized = false;

    private DatabaseTestHelper() {}

    public static void initTestDb() {
        if (isDbInitialized) {
            return;
        }

        DataConfig.useTestDb();

        logger.info("Creating test database...");
        dataConfig.executeSqlScript(createTestDbScript);

        logger.info("Inserting test data...");
        dataConfig.executeSqlScript(insertTestDataScript);

        isDbInitialized = true;
    }

    public static void resetTestDb() {
        if (!isDbInitialized) {
            initTestDb();
            return;
        }

        DataConfig.useTestDb();

        logger.info("Truncating test database...");
        dataConfig.executeSqlScript(truncateTestDataScript);

        logger.info("Inserting test data...");
        dataConfig.executeSqlScript(insertTestDataScript);
    }
}

package com.esgi.data;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public final class DataConfig {
    private static final Logger logger = Logger.getLogger(DataConfig.class.getName());

    private static final String DB_CONNECTION_STRING = "jdbc:sqlite:bookDB.sqlite";
    private static final String TEST_DB_CONNECTION_STRING = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";

    @Getter
    private static String dbConnectionString = DB_CONNECTION_STRING;

    private static boolean isDbInitialized = false;

    private static DataConfig instance;

    public static boolean usingSQLite = true;

    private DataConfig() {}

    public static DataConfig getInstance() {
        if (instance == null) {
            instance = new DataConfig();
        }
        return instance;
    }

    public void initDb() {
        if (isDbInitialized() || TEST_DB_CONNECTION_STRING.equals(getDbConnectionString())) {
            return;
        }

        executeSqlScript("scripts/create_db.sql");
        executeSqlScript("scripts/insert_admin_user.sql");
        executeSqlScript("scripts/insert_library_data.sql");
    }

    public static void useTestDb() {
        DataConfig.dbConnectionString = TEST_DB_CONNECTION_STRING;
        usingSQLite = false;
    }

    private boolean isDbInitialized() {
        if (isDbInitialized) {
            return true;
        }

        String sql = "SELECT 1 FROM sqlite_master WHERE type='table' AND name='users'";

        try (var conn = DriverManager.getConnection(getDbConnectionString());
             var preparedStatement = conn.prepareStatement(sql);
             var resultSet = preparedStatement.executeQuery()) {

            // If a row is returned, the table exists
            isDbInitialized = resultSet.next();
            return isDbInitialized;
        } catch (SQLException e) {
            return false;
        }
    }

    public void executeSqlScript(String file) {
        try(var connection = DriverManager.getConnection(DataConfig.getDbConnectionString());
            var inputStream = getResourceFileAsStream(file)) {

            if (inputStream == null) {
                throw new RuntimeException("SQL script not found: " + file);
            }

            String script = new String(inputStream.readAllBytes());
            String[] statements = script.split(";");

            for (String statement : statements) {
                connection.createStatement().execute(statement.trim());
            }

        } catch (SQLException | IOException e) {
            logger.severe("Error during database initialization: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private InputStream getResourceFileAsStream(String file) {
        return this.getClass().getClassLoader().getResourceAsStream(file);
    }
}

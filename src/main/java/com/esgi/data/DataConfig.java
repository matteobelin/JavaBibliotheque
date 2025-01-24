package com.esgi.data;

public final class DataConfig {
    private static String dbConnectionString = "jdbc:sqlite:bookDB.sqlite";

    private DataConfig() {}

    public static void useTestDb() {
        DataConfig.dbConnectionString = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    }

    public static String getDbConnectionString() {
        return dbConnectionString;
    }
}

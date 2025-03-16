package com.esgi.data.sql.builder;

public class SQLDeleteBuilder extends SQLBuilder {

    public static final String DELETE = "DELETE";
    public static final String FROM = "FROM";

    public SQLDeleteBuilder(String tableName) {
        super();
        super.append(DELETE)
             .append(FROM)
             .append(tableName);
    }
}

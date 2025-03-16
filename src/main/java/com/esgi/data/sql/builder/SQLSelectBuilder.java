package com.esgi.data.sql.builder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SQLSelectBuilder extends SQLBuilder {

    public static final String SELECT = "SELECT";
    public static final String FROM = "FROM";
    public static final String ASTERISK = "*";

    private final List<String> columns;
    protected String tableName;

    public SQLSelectBuilder(String... columns) {
        super();
        this.columns = Arrays.asList(columns);
    }

    public SQLSelectBuilder(List<String> columns) {
        super();
        this.columns = columns;
    }

    public SQLSelectBuilder from(String table) {
        this.tableName = table;
        var columns = buildColumnsToSelect(table);
        super.append(SELECT)
            .append(columns)
            .append(FROM)
            .append(table);
        return this;
    }

    public SQLJoinBuilder join(String targetTable) {
        return new SQLJoinBuilder(this, targetTable);
    }

    private String buildColumnsToSelect(String table) {
        if (columns.isEmpty()) {
            return buildSelectColumnWithTableName(table, ASTERISK);
        }

        return columns.stream()
                .map(column -> buildSelectColumnWithTableName(table, column))
                .collect(Collectors.joining(COMMA));
    }

    protected String buildSelectColumnWithTableName(String tableName, String column) {
        if (column.contains(".")) {
            return column; // already has the table associated
        }
        return tableName + "." + column;
    }
}

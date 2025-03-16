package com.esgi.data.sql.builder;

import com.esgi.data.sql.SQLComparator;

public class SQLJoinBuilder {

    public static final String JOIN = "JOIN";
    public static final String ON = "ON";

    private final SQLSelectBuilder originalSelect;

    public SQLJoinBuilder(SQLSelectBuilder originalSelect, String targetTableName) {
        this.originalSelect = originalSelect;

        originalSelect.append(JOIN)
                    .append(targetTableName);
    }

    public SQLSelectBuilder on(String sourceColumn, String targetColumn) {
        originalSelect.append(ON)
                .append(sourceColumn)
                .append(SQLComparator.EQUALS.getValue())
                .append(targetColumn);
        return originalSelect;
    }
}

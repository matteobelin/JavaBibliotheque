package com.esgi.data.sql.builder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SQLInsertBuilder extends SQLBuilder {

    public static final String INSERT = "INSERT";

    public static final String INTO = "INTO";

    public static final String VALUES = "VALUES";

    public SQLInsertBuilder(String table) {
        super();
        super.append(INSERT)
            .append(INTO)
            .append(table);
    }

    public SQLBuilder columns(String... columns) {
        return this.columns(Arrays.asList(columns));
    }

    public SQLBuilder columns(List<String> columns) {
        var result = super.append("(")
                .append(String.join(COMMA, columns))
                .append(")")
                .append(VALUES)
                .append("(")
                .append(this.buildValuePlaceholders(columns))
                .append(")");
        return result;
    }

    private String buildValuePlaceholders(List<String> columns) {
        return columns.stream().map(c -> "?").collect(Collectors.joining(COMMA));
    }
}

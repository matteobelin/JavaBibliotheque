package com.esgi.data.sql.builder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SQLUpdateBuilder extends SQLBuilder {

    public static final String UPDATE = "UPDATE";

    public static final String SET = "SET";

    public SQLUpdateBuilder(String tableName) {
        super();
        super.append(UPDATE)
                .append(tableName)
                .append(SET);
    }

    public SQLBuilder columns(String... columns) {
        return this.columns(Arrays.stream(columns).toList());
    }

    public SQLBuilder columns(List<String> columns) {
        var setClause = columns.stream().map(column -> column + " = ?").collect(Collectors.joining(", "));
        return super.append(setClause);
    }
}

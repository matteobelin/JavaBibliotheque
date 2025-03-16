package com.esgi.data.sql.builder;

import com.esgi.data.sql.SQLWhereCondition;

public class SQLWhereBuilder {
    public static final String WHERE = "WHERE";
    public static final String AND = "AND";
    public static final String OR = "OR";

    private final SQLBuilder builder;

    public SQLWhereBuilder(SQLBuilder builder, SQLWhereCondition<?> condition) {
        this.builder = builder;
        this.builder.append(WHERE);
        this.appendCondition(condition);
    }

    public SQLWhereBuilder and(SQLWhereCondition<?> condition) {
        this.builder.append(AND);
        appendCondition(condition);
        return this;
    }

    public SQLWhereBuilder or(SQLWhereCondition<?> condition) {
        this.builder.append(OR);
        appendCondition(condition);
        return this;
    }

    public String build() {
        return this.builder.build();
    }

    private void appendCondition(SQLWhereCondition<?> condition) {
        this.builder.append(condition.buildWithPlaceholder());
    }
}

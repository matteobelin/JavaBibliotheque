package com.esgi.data.sql.builder;

import com.esgi.data.sql.SQLWhereCondition;

import java.util.List;

public abstract class SQLBuilder {

    public static final String COMMA = ", ";

    private final StringBuilder statementBuilder;

    protected SQLBuilder() {
        this.statementBuilder = new StringBuilder();
    }

    public static SQLSelectBuilder select(String... columns) {
        return new SQLSelectBuilder(columns);
    }

    public static SQLSelectBuilder selectAll() {
        return new SQLSelectBuilder();
    }

    public static SQLInsertBuilder insertInto(String tableName) {
        return new SQLInsertBuilder(tableName);
    }

    public static SQLUpdateBuilder update(String tableName) {
        return new SQLUpdateBuilder(tableName);
    }

    public static SQLDeleteBuilder deleteFrom(String tableName) {
        return new SQLDeleteBuilder(tableName);
    }

    public SQLWhereBuilder where(SQLWhereCondition<?> condition) {
        return new SQLWhereBuilder(this, condition);
    }

    public SQLWhereBuilder whereAll(List<SQLWhereCondition<?>> conditions) {
        var whereBuilder = new SQLWhereBuilder(this, conditions.get(0));
        for (int i = 1; i < conditions.size(); i++) {
            whereBuilder.and(conditions.get(i));
        }
        return whereBuilder;
    }

    protected SQLBuilder append(String sql) {
        statementBuilder.append(" ").append(sql);
        return this;
    }

    public String build() {
        return statementBuilder.toString();
    }
}

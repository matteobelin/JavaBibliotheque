package com.esgi.data;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class SQLHelper {
    public static String generatePlaceholders(Collection<String> argument){
        return argument.stream()
                .map(e -> "?")
                .collect(Collectors.joining(","));
    }

    public static String buildSetClause(Collection<String> columns) {
        return columns.stream().map(column -> column + " = ?").collect(Collectors.joining(","));
    }

    public static String buildWhereConditions(List<SQLWhereCondition> whereConditions) {
        return whereConditions.stream()
                .map(SQLWhereCondition::buildWithPlaceholder)
                .collect(Collectors.joining(" AND "));
    }


    public static int bindConditionsValues(PreparedStatement statement, List<SQLWhereCondition> conditions) throws SQLException {
        var valueBinders = conditions.stream().map(SQLWhereCondition::makeValueBinder).toList();
        return bindValues(statement, valueBinders);
    }

    public static int bindValues(PreparedStatement statement, List<SQLColumnValueBinder> columnValueBinders) throws SQLException {
        int index = 1;
        for (var binder : columnValueBinders) {
                binder.bind(statement, index);
                index++;
        }
        return index;
    }

    public static <T extends Model> void retrieveGeneratedKey(java.sql.PreparedStatement statement, T model) throws SQLException {
        try (var generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                long generatedId = generatedKeys.getLong(1);
                model.setId((int) generatedId);
            }
        }
    }
}

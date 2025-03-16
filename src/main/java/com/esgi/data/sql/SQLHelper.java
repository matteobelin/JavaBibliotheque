package com.esgi.data.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public final class SQLHelper {
    public static void bindValues(PreparedStatement statement, List<SQLValueBinder> columnValueBinders) throws SQLException {
        int index = 1;
        for (var binders : columnValueBinders) {
            binders.bind(statement, index);
            index++;
        }
    }

    public static Integer retrieveGeneratedKey(java.sql.PreparedStatement statement) throws SQLException {
        try (var generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        }
        return null;
    }
}

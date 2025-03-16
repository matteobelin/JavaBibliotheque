package com.esgi.data.sql;

import lombok.Getter;

@Getter
public class SQLColumnValue<T> {
    protected final String columnName;
    protected final T value;

    public SQLColumnValue(String columnName, T value) {
        this.columnName = columnName;
        this.value = value;
    }

    public SQLValueBinder makeValueBinder() {
        if (value instanceof SQLNullValue) {
            return (statement, index) -> statement.setNull(index, ((SQLNullValue) value).type());
        }
        if (value instanceof Integer) {
            return (statement, index) -> statement.setInt(index, (Integer) value);
        }
        if (value instanceof String) {
            return (statement, index) -> statement.setString(index, (String) value);
        }
        if (value instanceof Boolean) {
            return (statement, index) -> statement.setBoolean(index, (Boolean) value);
        }
        return (statement, index) -> statement.setObject(index, value);
    }
}

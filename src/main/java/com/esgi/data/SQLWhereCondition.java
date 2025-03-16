package com.esgi.data;

import java.sql.Types;
import java.util.List;

public record SQLWhereCondition(String columnName, SQLComparator comparator, Object value) {

    public static SQLWhereCondition makeEqualCondition(String columnName, Object value) {
        return new SQLWhereCondition(columnName, SQLComparator.EQUALS, value);
    }


    public SQLColumnValueBinder makeValueBinder() {
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

    public String buildWithPlaceholder() {
        if (this.value.equals(new SQLNullValue(Types.DATE))) {
            return this.build("NULL");
        }
        return this.build("?");
    }

    public String buildWithValue() {
        return this.build(this.value().toString());
    }

    public String build(String value) {
        return String.join(" ", List.of(this.columnName(), this.comparator().getValue(), value));
    }




}

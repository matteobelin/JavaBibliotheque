package com.esgi.data.sql;

import lombok.Getter;

import java.sql.Types;
import java.util.List;

@Getter
public class SQLWhereCondition<T> extends SQLColumnValue<T> {
    protected final SQLComparator comparator;

    public SQLWhereCondition(String columnName, SQLComparator comparator, T value) {
        super(columnName, value);
        this.comparator = comparator;
    }

    public static <T> SQLWhereCondition<T> makeEqualCondition(String columnName, T value) {
        return new SQLWhereCondition<T>(columnName, SQLComparator.EQUALS, value);
    }

    public static SQLWhereCondition<SQLNullValue> makeIsNullCondition(String columnName, int sqlType) {
        return new SQLWhereCondition<>(columnName, SQLComparator.IS, new SQLNullValue(sqlType));
    }

    public String buildWithPlaceholder() {
        return this.build("?");
    }

    public String build(String value) {
        return String.join(" ", List.of(this.getColumnName(), this.getComparator().getValue(), value));
    }
}

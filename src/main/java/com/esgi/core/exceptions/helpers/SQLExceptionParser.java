package com.esgi.core.exceptions.helpers;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

public final class SQLExceptionParser {

    public static Optional<SQLExceptionEnum> parse(SQLException e) {
        return Arrays.stream(SQLExceptionEnum.values())
                .filter(exceptionEnum -> e.getMessage().contains(exceptionEnum.name()))
                .findFirst();
    }
}

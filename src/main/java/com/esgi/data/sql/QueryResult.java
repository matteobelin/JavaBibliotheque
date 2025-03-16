package com.esgi.data.sql;

import lombok.Getter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
public class QueryResult implements AutoCloseable {
    private final PreparedStatement statement;
    private final ResultSet resultSet;

    public QueryResult(PreparedStatement statement, ResultSet resultSet) {
        this.statement = statement;
        this.resultSet = resultSet;
    }

    @Override
    public void close() throws SQLException {
        resultSet.close();
        statement.close();
    }
}


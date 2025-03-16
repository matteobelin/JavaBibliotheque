package com.esgi.data.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SQLValueBinder {
    void bind(PreparedStatement statement, int index) throws SQLException;
}

package com.esgi.data;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SQLColumnValueBinder {
    void bind(PreparedStatement statement, int index) throws SQLException;
}

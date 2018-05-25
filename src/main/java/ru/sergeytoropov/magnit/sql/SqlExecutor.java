package ru.sergeytoropov.magnit.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlExecutor<T> {
    T execute(PreparedStatement st) throws SQLException;
}

package ru.sergeytoropov.magnit.sql;

import ru.sergeytoropov.magnit.AppException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Sql {
    private final ConnectionFactory connectionFactory;

    public Sql(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String sql, SqlExecutor<T> executor) throws AppException {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw new AppException("SQL failed: " + e.getMessage(), e);
        }
    }

    public <T> T execute(SqlTransaction<T> executor) throws AppException {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new AppException("Transaction failed: " + e.getMessage(), e);
        }
    }
}

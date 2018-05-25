package ru.sergeytoropov.magnit.storage.impl;

import ru.sergeytoropov.magnit.sql.Sql;
import ru.sergeytoropov.magnit.model.Sequence;
import ru.sergeytoropov.magnit.storage.Storage;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sergeytoropov
 * @since 22.05.18
 */
public class SqlStorage implements Storage {
    private Sql sql;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sql = new Sql(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sql.execute("DELETE FROM \"Test\"", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void save(Sequence sequence) {
        sql.execute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO \"Test\" (\"FIELD\") VALUES (?)")) {
                for (Integer value: sequence) {
                    ps.setInt(1, value);
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }

    @Override
    public List<Integer> getAll() {
        List<Integer> result = new ArrayList<>();
        sql.execute("SELECT \"FIELD\" FROM \"Test\"", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(rs.getInt("FIELD"));
            }
            return null;
        });
        return result;
    }
}

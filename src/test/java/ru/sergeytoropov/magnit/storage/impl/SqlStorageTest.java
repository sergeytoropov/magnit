package ru.sergeytoropov.magnit.storage.impl;

import org.junit.Before;
import org.junit.Test;
import ru.sergeytoropov.magnit.model.Sequence;
import ru.sergeytoropov.magnit.util.Util;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * @author sergeytoropov
 * @since 24.05.18
 */
public class SqlStorageTest {
    private final int SQ_SIZE = 1000;

    private SqlStorage storage;

    @Before
    public void init() {
        storage = Util.newInstanceSqlStorage();
    }

    @Test
    public void clear() {
        storage.clear();
        assertThat(storage.getAll(), empty());
    }

    @Test
    public void saveAndGetAll() {
        storage.clear();
        storage.save(new Sequence(SQ_SIZE));
        assertThat(storage.getAll(), contains(Util.getIncreaseIntegerArray(SQ_SIZE)));
        assertThat(storage.getAll().size(), is(SQ_SIZE));
    }
}
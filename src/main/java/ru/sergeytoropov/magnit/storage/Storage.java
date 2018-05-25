package ru.sergeytoropov.magnit.storage;

import ru.sergeytoropov.magnit.model.Sequence;

import java.util.List;

/**
 * @author sergeytoropov
 * @since 21.05.18
 */
public interface Storage {
    void clear();

    void save(Sequence sequence);

    List<Integer> getAll();
}

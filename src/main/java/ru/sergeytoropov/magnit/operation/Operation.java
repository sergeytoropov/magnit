package ru.sergeytoropov.magnit.operation;

import java.nio.file.Path;

/**
 * @author sergeytoropov
 * @since 23.05.18
 */
public interface Operation {
    long sum(Path transormFile);
}

package ru.sergeytoropov.magnit.util;

import ru.sergeytoropov.magnit.AppConfig;
import ru.sergeytoropov.magnit.storage.impl.SqlStorage;

import java.util.stream.IntStream;

/**
 * @author sergeytoropov
 * @since 24.05.18
 */
public class Util {

    public static Integer[] getIncreaseIntegerArray(int length) {
        Integer[] array = new Integer[length];
        IntStream.range(0, array.length).forEach(index -> array[index] = index + 1);
        return array;
    }

    public static SqlStorage newInstanceSqlStorage() {
        return new SqlStorage(AppConfig.get().getUrl(), AppConfig.get().getUser(), AppConfig.get().getPassword());
    }
}

package ru.sergeytoropov.magnit.transform;

import java.nio.file.Path;
import java.util.List;

/**
 * @author sergeytoropov
 * @since 22.05.18
 */
public interface Transform {
    void toXmlFile(Path inquireFile, List<Integer> sequence);

    void xslt(Path transormFile, Path inquireFile);
}

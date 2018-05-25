package ru.sergeytoropov.magnit.model;

import org.junit.Test;
import ru.sergeytoropov.magnit.util.Util;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

/**
 * @author sergeytoropov
 * @since 24.05.18
 */
public class SequenceTest {
    private final int SQ_SIZE = 1000;
    @Test
    public void sequence() {
        List<Integer> result = new ArrayList<>();
        for (Integer value: new Sequence(SQ_SIZE)) {
            result.add(value);
        }
        assertThat(result, contains(Util.getIncreaseIntegerArray(SQ_SIZE)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidSequence() {
        new Sequence(-10);
    }
}
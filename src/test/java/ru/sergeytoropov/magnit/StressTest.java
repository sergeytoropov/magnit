package ru.sergeytoropov.magnit;

import org.junit.Rule;
import org.junit.Test;
import ru.sergeytoropov.magnit.junitext.Pipe;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author sergeytoropov
 * @since 24.05.18
 */
public class StressTest {

    @Rule
    public Pipe pipe = new Pipe();

    public long calculateSum(long N) {
        if (N < 1) {
            throw new IllegalArgumentException("N < 1");
        }
        if (N == 1) {
            return N;
        }
        if (N % 2 == 0) {
            return (N + 1) * (N / 2);
        } else {
            return ((N + 1) * ((N - 1) / 2)) + ((N + 1) / 2);
        }
    }

    public String resultSum() {
       return String.valueOf(calculateSum(AppConfig.get().getN()));
    }

    @Test
    public void testSum() {
        assertThat(calculateSum(1L), is(1L));
        assertThat(calculateSum(2L), is(3L));
        assertThat(calculateSum(3L), is(6L));
        assertThat(calculateSum(4L), is(10L));
        assertThat(calculateSum(5L), is(15L));
        assertThat(calculateSum(6L), is(21L));
        assertThat(calculateSum(100L), is(5050L));
        assertThat(calculateSum(101L), is(5151L));
        assertThat(calculateSum(1000000L), is(500000500000L));
        assertThat(calculateSum(1000001L), is(500001500001L));
    }

    @Test
    public void stress() throws Exception {
        AppFactory.newInstance().run();
        assertThat(pipe.getOutput(), contains(new String[] {resultSum()}));
    }
}


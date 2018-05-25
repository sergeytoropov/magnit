package ru.sergeytoropov.magnit.model;

import java.util.*;

/**
 * @author sergeytoropov
 * @since 21.05.18
 */
public class Sequence implements Iterable<Integer> {
    private final int N;

    public Sequence(int N) {
        if (N < 1) {
            throw new IllegalArgumentException("Value N < 1");
        }
        this.N = N;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<Integer> {
        int cursor = 0;

        public boolean hasNext() {
            return cursor != N;
        }

        public Integer next() {
            return ++cursor;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

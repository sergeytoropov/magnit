package ru.sergeytoropov.magnit.operation.impl;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author sergeytoropov
 * @since 24.05.18
 */
public class XmlOperationTest {
    private XmlOperation operation;
    private Path transormFile;

    @Before
    public void init() {
        operation = new XmlOperation();
        transormFile = Paths.get(getClass().getClassLoader().getResource("data/500500.xml").getFile());
    }

    @Test
    public void sum() {
        assertThat(operation.sum(transormFile), is(500500L));
    }
}
package ru.sergeytoropov.magnit.transform.impl;

import magnit.sergeytoropov.ru.xml._1.Entries;
import magnit.sergeytoropov.ru.xml._1.Entry;
import org.junit.Before;
import org.junit.Test;
import ru.sergeytoropov.magnit.model.Sequence;
import ru.sergeytoropov.magnit.storage.Storage;
import ru.sergeytoropov.magnit.util.JAXBOperation;
import ru.sergeytoropov.magnit.util.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author sergeytoropov
 * @since 24.05.18
 */
public class XmlTransformTest {
    private final int SQ_SIZE = 1000;

    private Storage storage;

    private XmlTransform transform;

    private Path inquireFile;

    private Path transormFile;

    @Before
    public void init() {
        storage = Util.newInstanceSqlStorage();
        transform = new XmlTransform();
        transform.setJaxbOp(new JAXBOperation(Entries.class, Entry.class));
        inquireFile = Paths.get("target/1.xml");
        transormFile = Paths.get("target/2.xml");
    }

    public String toString(Path path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));
        return reader.lines().reduce("", (acc, str) -> acc = acc + str);
    }

    public Path validFile(String fileName) {
        return Paths.get(getClass().getClassLoader().getResource(fileName).getFile());
    }

    @Test
    public void toXmlFile() throws Exception {
        storage.clear();
        storage.save(new Sequence(SQ_SIZE));
        transform.toXmlFile(inquireFile, storage.getAll());
        assertThat(toString(inquireFile), is(toString(validFile("data/valid1.xml"))));
    }

    @Test
    public void xslt() throws Exception {
        transform.xslt(transormFile, validFile("data/valid1.xml"));
        assertThat(toString(transormFile), is(toString(validFile("data/valid2.xml"))));
    }
}
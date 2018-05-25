package ru.sergeytoropov.magnit.operation.impl;

import ru.sergeytoropov.magnit.AppException;
import ru.sergeytoropov.magnit.operation.Operation;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Iterator;

/**
 * @author sergeytoropov
 * @since 23.05.18
 */
public class XmlOperation implements Operation {

    @Override
    public long sum(Path transformFile) {
        long sum = 0;
        for (Integer value: new Sum(transformFile)) {
            sum += value.longValue();
        }
        return sum;
    }

    private class Sum implements Iterable<Integer> {
        private final Path transformFile;

        public Sum(Path transformFile) {
            this.transformFile = transformFile;
        }

        @Override
        public Iterator<Integer> iterator() {
            return new Itr(transformFile);
        }
    }

    private class Itr implements Iterator<Integer> {
        private final XMLEventReader reader;
        private StartElement element;

        public Itr(Path transformFile) {
            try {
                XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
                reader = xmlInputFactory.createXMLEventReader(new FileInputStream(transformFile.toFile()));
            } catch (FileNotFoundException | XMLStreamException ex) {
                throw new AppException("XMLOperation failed: " + ex.getMessage(), ex);
            }
        }

        public boolean hasNext() {
            try {
                while (reader.hasNext()) {
                    XMLEvent event = reader.nextEvent();
                    if (event.isStartElement()) {
                        element = event.asStartElement();
                        if (element.getName().getLocalPart().equals("entry")) {
                            return true;
                        }
                    }
                }
                return false;
            } catch (XMLStreamException ex) {
                throw new AppException("XMLOperation failed: " + ex.getMessage(), ex);
            }
        }

        public Integer next() {
            try {
                Attribute fieldAttr = element.getAttributeByName(new QName("field"));
                if (fieldAttr != null) {
                    return Integer.parseInt(fieldAttr.getValue());
                }
                throw new AppException("XMLOperation failed: null value");
            } catch (NumberFormatException ex) {
                throw new AppException("XMLOperation failed: attr field not number: " + ex.getMessage(), ex);
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

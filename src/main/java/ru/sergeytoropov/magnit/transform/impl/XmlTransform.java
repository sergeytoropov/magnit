package ru.sergeytoropov.magnit.transform.impl;

import magnit.sergeytoropov.ru.xml._1.Entries;
import magnit.sergeytoropov.ru.xml._1.Entry;
import magnit.sergeytoropov.ru.xml._1.ObjectFactory;
import ru.sergeytoropov.magnit.AppException;
import ru.sergeytoropov.magnit.transform.Transform;
import ru.sergeytoropov.magnit.util.JAXBOperation;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.List;

/**
 * @author sergeytoropov
 * @since 22.05.18
 */
public class XmlTransform implements Transform {

    private JAXBOperation jaxbOp;

    public JAXBOperation getJaxbOp() {
        return jaxbOp;
    }

    public void setJaxbOp(JAXBOperation jaxbOp) {
        this.jaxbOp = jaxbOp;
    }

    @Override
    public void toXmlFile(Path inquireFile, List<Integer> sequence) {
        try {
            jaxbOp.marshall(convert(sequence), new FileWriter(inquireFile.toFile()));
        } catch (IOException ex) {
            throw new AppException("XmlTransform failed: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void xslt(Path transormFile, Path inquireFile) {
        try {
            StreamSource source = new StreamSource(new FileReader(inquireFile.toFile()));
            StreamSource stylesource = new StreamSource(getClass().getClassLoader().getResourceAsStream("xml/magnit.xsl"));

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(stylesource);

            StreamResult result = new StreamResult(new FileWriter(transormFile.toFile()));
            transformer.transform(source, result);

            source.getReader().close();
            result.getWriter().close();
        } catch (TransformerException | IOException ex) {
            throw new AppException("XmlTransform failed: " + ex.getMessage(), ex);
        }
    }

    public Entries convert(List<Integer> sequence) {
        ObjectFactory factory = new ObjectFactory();
        Entries entries = factory.createEntries();

        List<Entry> list = entries.getEntry();
        for (Integer value: sequence) {
            Entry entry = factory.createEntry();
            entry.setField(BigInteger.valueOf(value.longValue()));
            list.add(entry);
        }
        return entries;
    }
}

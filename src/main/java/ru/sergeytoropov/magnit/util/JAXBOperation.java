package ru.sergeytoropov.magnit.util;

import ru.sergeytoropov.magnit.AppException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.Writer;

/**
 * @author sergeytoropov
 * @since 23.05.18
 */
public class JAXBOperation {

    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    public JAXBOperation(Class... classesToBeBound) {
        try {
            JAXBContext ctx = JAXBContext.newInstance(classesToBeBound);

            marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            unmarshaller = ctx.createUnmarshaller();
        } catch (JAXBException ex) {
            throw new AppException("JAXBOperation init failed: " + ex.getMessage(), ex);
        }
    }

    public <T> T unmarshall(Reader reader) {
        try {
            return (T) unmarshaller.unmarshal(reader);
        } catch (JAXBException ex) {
            throw new AppException("JAXBOperation unmarshall failed: " + ex.getMessage(), ex);
        }
    }

    public void marshall(Object instance, Writer writer) {
        try {
            marshaller.marshal(instance, writer);
        } catch (JAXBException ex) {
            throw new AppException("JAXBOperation marshal failed: " + ex.getMessage(), ex);
        }
    }
}

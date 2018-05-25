package ru.sergeytoropov.magnit;

import magnit.sergeytoropov.ru.xml._1.Entries;
import magnit.sergeytoropov.ru.xml._1.Entry;
import ru.sergeytoropov.magnit.operation.impl.XmlOperation;
import ru.sergeytoropov.magnit.storage.impl.SqlStorage;
import ru.sergeytoropov.magnit.transform.impl.XmlTransform;
import ru.sergeytoropov.magnit.util.JAXBOperation;


/**
 * @author sergeytoropov
 * @since 24.05.18
 */
public final class AppFactory {
    public static Runnable newInstance() {
        try {
            App app = new App();

            app.setStorage(new SqlStorage(AppConfig.get().getUrl(), AppConfig.get().getUser(), AppConfig.get().getPassword()));

            XmlTransform transform = new XmlTransform();
            transform.setJaxbOp(new JAXBOperation(Entries.class, Entry.class));
            app.setTransform(transform);

            app.setOperation(new XmlOperation());

            app.setN(AppConfig.get().getN());
            app.setInquireFile(AppConfig.get().getInquireFile());
            app.setTransformFile(AppConfig.get().getTransormFile());

            app.validate();
            return app;
        } catch (Exception ex) {
            throw new AppException("AppFactory failed: create new instance: " + ex.getMessage(), ex);
        }
    }
}

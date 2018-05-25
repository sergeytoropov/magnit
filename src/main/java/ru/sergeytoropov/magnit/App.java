package ru.sergeytoropov.magnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sergeytoropov.magnit.model.Sequence;
import ru.sergeytoropov.magnit.operation.Operation;
import ru.sergeytoropov.magnit.storage.Storage;
import ru.sergeytoropov.magnit.transform.Transform;

import java.nio.file.Path;
import java.util.Objects;

/**
 * @author sergeytoropov
 * @since 23.05.18
 */
public class App implements Runnable {
    private final static Logger log = LoggerFactory.getLogger(App.class);

    private Storage storage = null;

    private Transform transform = null;

    private Operation operation = null;

    private int N = 0;

    private Path inquireFile = null;

    private Path transformFile = null;

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public Path getInquireFile() {
        return inquireFile;
    }

    public void setInquireFile(Path inquireFile) {
        this.inquireFile = inquireFile;
    }

    public Path getTransformFile() {
        return transformFile;
    }

    public void setTransformFile(Path transformFile) {
        this.transformFile = transformFile;
    }

    private void objectIsNotInitialized(String objectName) {
        throw new IllegalArgumentException(String.format("Object %s is not initialized", objectName));
    }

    public void validate() {
        if (Objects.isNull(storage)) {
            objectIsNotInitialized("storage");
        }
        if (Objects.isNull(transform)) {
            objectIsNotInitialized("transform");
        }
        if (Objects.isNull(operation)) {
            objectIsNotInitialized("operation");
        }
        if (N < 1) {
            throw new IllegalArgumentException("Value N < 1");
        }
        if (Objects.isNull(inquireFile)) {
            objectIsNotInitialized("inquireFile");
        }
        if (Objects.isNull(transformFile)) {
            objectIsNotInitialized("transformFile");
        }
    }

    public void initialize() {
        storage.clear();
        storage.save(new Sequence(N));
    }

    public void inquire() {
        transform.toXmlFile(inquireFile, storage.getAll());
    }

    public void transform() {
        transform.xslt(transformFile, inquireFile);
    }

    public void compute() {
        System.out.println(operation.sum(transformFile));
    }

    @Override
    public void run() {
        log.info("initialize ...");
        initialize();
        log.info("inquire ...");
        inquire();
        log.info("transform ...");
        transform();
        log.info("compute ...");
        compute();
        log.info("done.");
    }

    public static void main(String[] args) {
        try {
            AppFactory.newInstance().run();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
        }
    }
}

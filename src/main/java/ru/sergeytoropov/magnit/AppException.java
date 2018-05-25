package ru.sergeytoropov.magnit;

/**
 * @author sergeytoropov
 * @since 22.05.18
 */
public class AppException extends RuntimeException {
    public AppException() {
        super();
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(Throwable cause) {
        super(cause);
    }
}
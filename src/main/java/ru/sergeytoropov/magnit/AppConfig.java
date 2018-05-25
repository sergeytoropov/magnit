package ru.sergeytoropov.magnit;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author sergeytoropov
 * @since 21.05.18
 */
public class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();

    private String url;

    private String user;

    private String password;

    private int N;

    private Path inquireFile;

    private Path transormFile;

    public static AppConfig get() {
        return INSTANCE;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public int getN() {
        return N;
    }

    public Path getInquireFile() {
        return inquireFile;
    }

    public Path getTransormFile() {
        return transormFile;
    }

    private AppConfig() {
        try (InputStream propStream = getClass().getClassLoader().getResourceAsStream("app.properties")) {
            Properties appProps = new Properties();
            appProps.load(propStream);
            url = appProps.getProperty("db.url");
            user = appProps.getProperty("db.user");
            password = appProps.getProperty("db.password");
            N = Integer.valueOf(appProps.getProperty("N"));
            inquireFile = Paths.get(appProps.getProperty("file.inquire"));
            transormFile = Paths.get(appProps.getProperty("file.transform"));
        } catch (Exception ex) {
            throw new AppException("AppConfig failed: " + ex.getMessage(), ex);
        }
    }
}

package main.model.manager;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

public class PathsManager {
    private final static Logger logger = Logger.getLogger("ManagerLogger");
    private static Properties properties;
    static {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("/paths.properties");
            properties = new Properties();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            properties.load(reader);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}

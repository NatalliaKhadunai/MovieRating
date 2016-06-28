package main.model.manager;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Default image manager used as storage of image properties.
 */
public class DefaultImageManager {
    private final static Logger logger = Logger.getLogger("ManagerLogger");
    /** Stored properties */
    private static Properties properties;
    static {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("/defaultImage.properties");
            properties = new Properties();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            properties.load(reader);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * Get property.
     * @param key value, by which property will be invoked.
     * @return value, corresponding to given key.
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static Collection<Object> getValues() {
        return properties.values();
    }
}

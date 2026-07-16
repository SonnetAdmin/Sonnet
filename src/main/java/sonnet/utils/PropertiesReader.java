package sonnet.utils;

import sonnet.utils.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static sonnet.utils.StringUtils.isNullOrEmpty;

public class PropertiesReader {

    private static final String PROPERTIES_DIR = "src/main/resources/properties";
    private static final String TEST_PROPERTIES_DIR = "src/test/resources/properties";
    private static final Logger logger = new Logger();
    private static Properties properties;

    static {
        properties = new Properties();
        loadFromDirectory(PROPERTIES_DIR);
        loadFromDirectory(TEST_PROPERTIES_DIR);
    }

    private static void loadFromDirectory(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".properties")) {
                    Properties propsFromFile = new Properties();
                    try {
                        propsFromFile.load(new FileInputStream(file));
                        properties.putAll(propsFromFile);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    /**
     * Get property Value from property Key.  Properties are read from any file with a .properties suffix
     * in the <i>resources\properties</i> directory
     * @param key
     * @return The String value of the Key
     * @throws IllegalArgumentException if key is null or empty
     */

    public static String get(String key) {
        String value;
        if (isNullOrEmpty(key)) {
            throw new IllegalArgumentException("getProperty() : Invalid key");
        }
        value = properties.getProperty(key);
        if (isNullOrEmpty(value)) {
            logger.warn("No entry for key :"+key);
            value="";
        }
        return value;
    }
}

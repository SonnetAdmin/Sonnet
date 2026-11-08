package sonnet.reference.selenium.utils;

import sonnet.reference.selenium.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static sonnet.reference.selenium.utils.StringUtils.isNullOrEmpty;

/**
 * Utility class for reading properties from .properties files in the resources/properties directory.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     String value = PropertiesReader.get("my.key");
 * </pre>
 */
public class PropertiesReader {

    private static final String PROPERTIES_DIR = "src/main/resources/properties";
    private static final Logger logger = new Logger();
    private static Properties properties = null;

    static {
        properties = new Properties();
        File dir = new File(PROPERTIES_DIR);
        File[] files = dir.listFiles();

        assert files != null;
        for (File file : files) {
            if (file.isFile()) {
                // Ignore other files
                if (file.getName().endsWith(".properties")) {
                    Properties propsFromFile = new Properties();
                    try {
                        propsFromFile.load(new FileInputStream(PROPERTIES_DIR+"/"+file.getName()));
                        properties.putAll(propsFromFile);
                    }
                    catch (IOException e) {
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

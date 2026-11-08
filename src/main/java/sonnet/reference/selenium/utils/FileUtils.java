package sonnet.reference.selenium.utils;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Utility class for file operations, such as reading file contents from the classpath.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     String content = FileUtils.getFileContents("data.txt");
 * </pre>
 */
public class FileUtils {
    public static String getFileContents(String filePath) throws Exception {
        String contents;
        try {
            contents = new String(Files.readAllBytes(
                    Paths.get(ClassLoader.getSystemResource(filePath).toURI())));
        } catch (Exception e) {
            throw new Exception(String.format("Failed to open file: '%s'", filePath));
        }
        return contents;
    }
}

package sonnet.reference.playwright.utils;

import java.nio.file.Files;
import java.nio.file.Paths;

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

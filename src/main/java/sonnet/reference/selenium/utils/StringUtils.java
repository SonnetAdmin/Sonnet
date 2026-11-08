package sonnet.reference.selenium.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

/**
 * Utility class for common string operations, including random string generation and null/empty checks.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     String email = StringUtils.randomEmail();
 *     boolean empty = StringUtils.isNullOrEmpty("");
 * </pre>
 */
public class StringUtils {

    public static String randomAlphabetic(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }
    public static String randomNumeric(int length) {
        return RandomStringUtils.randomNumeric(length);
    }
    public static String randomAlphaNumeric(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static String randomEmail() {
        return randomAlphanumeric(6)+"@"+randomAlphanumeric(6)+".com";
    }

    public static String generateAutoRef(){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("ssSSS");
        return "AUTO-Ref-"+dateFormat.format(date);
    }

    public static boolean isNullOrEmpty( String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isNullOrEmpty( List list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNotNullOrEmpty( String string) {
        return ! isNullOrEmpty(string);
    }

}

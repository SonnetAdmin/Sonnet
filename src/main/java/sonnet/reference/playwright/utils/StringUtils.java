package sonnet.reference.playwright.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;


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

    public static boolean isNullOrEmpty( String string) {
        return string == null || string.isEmpty();
    }
    public static boolean isNullOrEmpty( List list) {
        return list == null || list.isEmpty();
    }
}

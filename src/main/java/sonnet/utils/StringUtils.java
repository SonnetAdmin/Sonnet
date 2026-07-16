package sonnet.utils;

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

    public static boolean toBoolean(String trueOrFalse) {
        // Empty string is false
        if (isNullOrEmpty(trueOrFalse)) {
            return false;
        }

        switch (trueOrFalse.toLowerCase()) {
            case "true", "yes", "y" -> {
                return true;
            }
            case "false", "no", "n" -> {
                return false;
            }
            default -> throw new IllegalArgumentException("Invalid boolean string: " + trueOrFalse);
        }
    }

    public static boolean isTrue(String trueOrFalse) {
        return toBoolean(trueOrFalse);
    }

    public static boolean isFalse(String trueOrFalse) {
        return toBoolean(trueOrFalse);
    }

    // These will throw a NumberFormatException if the string is not a valid number
    public static int toInteger(String integer) {
        if (!isNullOrEmpty(integer)) {
            return Integer.parseInt(integer);
        } else {
            return 0;
        }
    }

    public static float toFloat(String fl) {
        if (!isNullOrEmpty(fl)) {
            return Float.parseFloat(fl);
        } else {
            return 0F;
        }
    }

    public static double toDouble(String dbl) {
        if (!isNullOrEmpty(dbl)) {
            return Double.parseDouble(dbl);
        } else {
            return 0D;
        }
    }

    public static boolean isNumeric(String number) {
        return org.apache.commons.lang3.StringUtils.isNumeric(number);
    }

    public static boolean isAlpha(String string) {
        return string.matches("^[a-zA-Z]*$");
    }

    public static boolean isNull(String string) {
        return string == null;
    }

}

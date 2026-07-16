package sonnet.utils;

import java.util.List;

import static sonnet.utils.StringUtils.isNullOrEmpty;

/**
 * Argument-guard helpers used across the framework.
 *
 * <p>Each guard accepts an optional {@code subject} (e.g. {@code "Target"}) and
 * appends a standard suffix that describes the constraint. This keeps call
 * sites concise and produces consistent error messages:</p>
 *
 * <pre>
 *   throwOnNull(target, "Target")          → "Target must not be null"
 *   throwOnNullOrEmpty(url, "URL")         → "URL must not be null or empty"
 *   throwOnMinus(-3, "waitTime")           → "waitTime must not be negative: -3"
 *   throwOnZeroOrMinus(0, "Index")         → "Index must be greater than zero: 0"
 * </pre>
 */
public class Checks {

    private static final String SUFFIX_NULL          = " must not be null";
    private static final String SUFFIX_NULL_OR_EMPTY = " must not be null or empty";
    private static final String SUFFIX_NEGATIVE      = " must not be negative";
    private static final String SUFFIX_NOT_POSITIVE  = " must be greater than zero";

    public static <N extends Number> void throwOnMinus(N value) {
        throwOnMinus(value, "Value");
    }

    public static <N extends Number> void throwOnMinus(N value, String subject) {
        if (value.doubleValue() < 0) {
            throw new IllegalArgumentException(subject + SUFFIX_NEGATIVE + ": " + value);
        }
    }

    public static <N extends Number> void throwOnZeroOrMinus(N value) {
        throwOnZeroOrMinus(value, "Value");
    }

    public static <N extends Number> void throwOnZeroOrMinus(N value, String subject) {
        if (value.doubleValue() <= 0) {
            throw new IllegalArgumentException(subject + SUFFIX_NOT_POSITIVE + ": " + value);
        }
    }

    @SafeVarargs
    public static <N extends Number> void throwOnZeroOrMinus(N... values) {
        for (N value : values) {
            throwOnZeroOrMinus(value);
        }
    }

    @SafeVarargs
    public static <N extends Number> void throwOnZeroOrMinus(String subject, N... values) {
        for (N value : values) {
            throwOnZeroOrMinus(value, subject);
        }
    }

    public static void throwOnNullOrEmpty(String s) {
        throwOnNullOrEmpty(s, "Value");
    }

    public static void throwOnNullOrEmpty(String s, String subject) {
        if (isNullOrEmpty(s)) {
            throw new IllegalArgumentException(subject + SUFFIX_NULL_OR_EMPTY);
        }
    }

    public static void throwOnNullOrEmpty(List<?> list) {
        throwOnNullOrEmpty(list, "Value");
    }

    public static void throwOnNullOrEmpty(List<?> list, String subject) {
        if (isNullOrEmpty(list)) {
            throw new IllegalArgumentException(subject + SUFFIX_NULL_OR_EMPTY);
        }
    }

    public static void throwOnNullOrEmpty(Object[] array) {
        throwOnNullOrEmpty(array, "Value");
    }

    public static void throwOnNullOrEmpty(Object[] array, String subject) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException(subject + SUFFIX_NULL_OR_EMPTY);
        }
    }

    public static void throwOnNull(Object o) {
        throwOnNull(o, "Value");
    }

    public static void throwOnNull(Object o, String subject) {
        if (o == null) {
            throw new IllegalArgumentException(subject + SUFFIX_NULL);
        }
    }
}

package sonnet.reference.playwright.utils;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateTimeFormatterUtils {
    public static final DateTimeFormatter INPUT_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
            .optionalStart()
            .appendFraction(ChronoField.MILLI_OF_SECOND, 1, 3, true)
            .optionalEnd()
            .appendPattern("X")
            .toFormatter();

    public static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");
    public static final DateTimeFormatter DATE_ONLY_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");
    public static final DateTimeFormatter SIMPLE_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}
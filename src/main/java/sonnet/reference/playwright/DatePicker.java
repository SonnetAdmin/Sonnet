package sonnet.reference.playwright;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import sonnet.reference.playwright.locators.Target;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * Wrapper for HTML5 date input elements ({@code <input type="date">}).
 *
 * <h3>Usage</h3>
 * <pre>{@code
 *   DatePicker dp = new DatePicker(id("dob"));
 *   dp.setDate("25/12/2025");
 *   dp.setDateFormat(Enums.DateFormat.US);
 * }</pre>
 *
 * @since March 2026
 */
public class DatePicker {
    private final Logging logger = Logging.getInstance();
    private final Locator locator;
    private Enums.DateFormat dateFormat = Enums.DateFormat.UK;

    public DatePicker(Target target) {
        Page page = CommonPage.getInstance().getPage();
        this.locator = page.locator(target.getSelector());
    }

    public void setDateFormat(Enums.DateFormat format) {
        Objects.requireNonNull(format, "DateFormat must not be null");
        this.dateFormat = format;
        log("setDateFormat()", format.name());
    }

    public Enums.DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDate(String date) {
        if (date == null || date.isBlank()) {
            throw new IllegalArgumentException("Date must not be null or empty");
        }
        LocalDate parsed = parseUserDate(date);
        log("setDate()", date);
        locator.fill(parsed.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    public void setYear(int year) {
        if (year < 1) {
            throw new IllegalArgumentException("Year must be positive, got " + year);
        }
        LocalDate current = getCurrentDate();
        log("setYear()", String.valueOf(year));
        locator.fill(current.withYear(year).format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    public void setMonth(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be 1-12, got " + month);
        }
        LocalDate current = getCurrentDate();
        log("setMonth()", String.valueOf(month));
        locator.fill(current.withMonth(month).format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    public void setDay(int day) {
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("Day must be 1-31, got " + day);
        }
        LocalDate current = getCurrentDate();
        log("setDay()", String.valueOf(day));
        locator.fill(current.withDayOfMonth(day).format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    public String getDate() {
        String iso = locator.inputValue();
        if (iso == null || iso.isBlank()) return "";
        return LocalDate.parse(iso).format(DateTimeFormatter.ofPattern(dateFormat.getPattern()));
    }

    public String getYear() {
        String iso = locator.inputValue();
        if (iso == null || iso.isBlank()) return "";
        return String.valueOf(LocalDate.parse(iso).getYear());
    }

    public String getMonth() {
        String iso = locator.inputValue();
        if (iso == null || iso.isBlank()) return "";
        return String.valueOf(LocalDate.parse(iso).getMonthValue());
    }

    public String getDay() {
        String iso = locator.inputValue();
        if (iso == null || iso.isBlank()) return "";
        return String.valueOf(LocalDate.parse(iso).getDayOfMonth());
    }

    public void clearDate() {
        log("clearDate()", "");
        locator.fill("");
    }

    private LocalDate parseUserDate(String date) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern(dateFormat.getPattern()));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Invalid date '" + date + "' for format " + dateFormat.name()
                            + " (" + dateFormat.getPattern() + ")", e);
        }
    }

    private LocalDate getCurrentDate() {
        String iso = locator.inputValue();
        if (iso == null || iso.isBlank()) {
            return LocalDate.of(2000, 1, 1);
        }
        return LocalDate.parse(iso);
    }

    private void log(String... args) {
        logger.log(args);
    }
}

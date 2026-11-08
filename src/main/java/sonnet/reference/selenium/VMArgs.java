package sonnet.reference.selenium;

import sonnet.reference.selenium.logger.Logger;

/**
 * Utility class for accessing and managing JVM arguments related to browser automation.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     if (VMArgs.headless) {
 *         // Run browser in headless mode
 *     }
 *     int windowIdx = VMArgs.browserWindowIndex;
 * </pre>
 */
public class VMArgs {

    public static boolean headless;
    public static boolean screenshotOnError;
    public static boolean browserKeepOpen;
    public static int browserWindowIndex;

    static {
        Logger logger = new Logger();

        headless = (System.getProperty("headless.mode", "true").equals("true"));
        screenshotOnError = (System.getProperty("browser.screenshot.onerror", "true").equals("true"));
        browserKeepOpen = (!System.getProperty("browser.keep.open", "false").equals("false"));

        try {
            browserWindowIndex = Integer.parseInt(System.getProperty("browser.window", "0")) - 1;
        } catch (NumberFormatException e) {
            logger.log("Provided browser.window is not a number");
            browserWindowIndex = -1;
        }
    }
}

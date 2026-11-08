package sonnet.reference.playwright.utils;

import lombok.Getter;

/**
 * Centralised access to configuration with the following priority:
 * <ol>
 *   <li>VM arguments passed via {@code -D} flags (highest priority)</li>
 *   <li>Values from {@code core.properties} file</li>
 *   <li>Hardcoded defaults (lowest priority)</li>
 * </ol>
 * <p>Usage: {@code mvn test -Dtrace=true -Dheadless.mode=true -Dminimise=true}
 */
/**
 * Target monitor index (1-based). Defaults to 1 (primary screen).
 * Usage: {@code -Dbrowser.window=2} to open on second monitor.
 */
public class VMArgs {

    private static final String BROWSER_WINDOW = "browser.window";
    private static final String CLOSE_ON_EXIT = "close.on.exit";
    private static final String DEFAULT_TIMEOUT = "default.timeout";
    private static final String HEADLESS = "headless.mode";
    private static final String LOGGED = "logged";
    private static final String MINIMISE = "minimise";
    private static final String NAVIGATION_TIMEOUT = "navigation.timeout";
    private static final String SCREEN_HEIGHT = "screen.height";
    private static final String SCREEN_WIDTH = "screen.width";
    private static final String SLOW_MO = "slow.mo";
    private static final String TRACE = "trace";

    @Getter
    private static int browserWindow;
    @Getter
    private static boolean closeOnExit;
    @Getter
    private static int defaultTimeout;
    @Getter
    private static boolean logged;
    @Getter
    private static boolean headless;
    @Getter
    private static boolean minimised;
    @Getter
    private static int navigationTimeout;
    @Getter
    private static int screenHeight;
    @Getter
    private static int screenWidth;
    @Getter
    private static int slowMo;
    @Getter
    private static boolean traced;
    
    static {
        browserWindow = Integer.parseInt(resolve(BROWSER_WINDOW, "1")) - 1;
        closeOnExit = Boolean.parseBoolean(resolve(CLOSE_ON_EXIT, "true"));
        defaultTimeout = Integer.parseInt(resolve(DEFAULT_TIMEOUT, "30000"));
        headless = Boolean.parseBoolean(resolve(HEADLESS, "false"));
        logged = Boolean.parseBoolean(resolve(LOGGED, "false"));
        minimised = Boolean.parseBoolean(resolve(MINIMISE, "false"));
        navigationTimeout = Integer.parseInt(resolve(NAVIGATION_TIMEOUT, "60000"));
        screenHeight = Integer.parseInt(resolve(SCREEN_HEIGHT, "1080"));
        screenWidth = Integer.parseInt(resolve(SCREEN_WIDTH, "1920"));
        slowMo = Integer.parseInt(resolve(SLOW_MO, "0"));
        traced = Boolean.parseBoolean(resolve(TRACE, "false"));
    }

    /**
     * Resolves a value using the priority chain:
     * 1 ) -D flag e.g. -Dlogged=false
     * 2 ) core.properties e.g. logged=false
     * 3 ) hardcoded default
     */
    private static String resolve(String key, String defaultValue) {
        // From src/main/resources/properties. For test runs and production.
        // From src/test/resources/properties. For JUNIT test runs and production.
        // Test properties file not bundled with production.
        String vmValue = System.getProperty(key);
        if (vmValue != null && !vmValue.isEmpty()) {
            return vmValue;
        }
        //
        String propsValue = PropertiesReader.get(key);
        if (propsValue != null && !propsValue.isEmpty()) {
            return propsValue;
        }
        return defaultValue;
    }
}

package sonnet.reference.playwright;

/**
 * Defines keyboard interaction methods for the Playwright automation framework.
 * <p>
 * Mirrors the Selenium Core KeyInterface, adapted for Playwright's string-based
 * keyboard API. All key names follow the Playwright convention
 * (e.g. "Control", "Shift", "Alt", "Enter", "ArrowDown", "a", "c").
 * </p>
 */
interface KeyInterface {

    // Hold and release

    void hold(String keyToHold);

    void release(String keyToRelease);

    // Alerts

    void alert(String keysToSend);

    void alert(Keyboard.AlertAction action);

    // Compose — text + key entry

    void compose(String content);

    void compose(String key, int repeat);

    void compose(Keyboard.Key key);

    void compose(Keyboard.Key key, int repeat);

    // Other

    void submit();
}

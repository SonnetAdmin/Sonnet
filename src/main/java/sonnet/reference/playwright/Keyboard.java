package sonnet.reference.playwright;

import static sonnet.reference.playwright.utils.Checks.*;

/**
 * Singleton providing keyboard interaction methods for the Playwright automation framework.
 * <p>
 * Mirrors the Selenium Core {@code Keyboard} class, adapted for Playwright's
 * {@link com.microsoft.playwright.Keyboard} API. Key names follow the Playwright convention
 * (e.g. "Control", "Shift", "Alt", "Enter", "ArrowDown", "a", "c").
 * </p>
 */
public class Keyboard implements KeyInterface {

    public enum AlertAction {
        ACCEPT,
        DISMISS
    }

    /**
     * Subset of ASCII control / navigation keys exposed for {@link #compose(Key)}.
     * Values map to Playwright's keyboard key names.
     */
    public enum Key {
        ENTER("Enter"),
        TAB("Tab"),
        ESCAPE("Escape"),
        BACK_SPACE("Backspace"),
        DELETE("Delete"),
        SPACE("Space"),
        HOME("Home"),
        END("End"),
        PAGE_UP("PageUp"),
        PAGE_DOWN("PageDown"),
        ARROW_UP("ArrowUp"),
        ARROW_DOWN("ArrowDown"),
        ARROW_LEFT("ArrowLeft"),
        ARROW_RIGHT("ArrowRight"),
        INSERT("Insert");

        private final String value;

        Key(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private static final String MODIFIER = System.getProperty("os.name").toLowerCase().contains("mac") ? "Meta" : "Control";

    private static Keyboard instance = null;
    private final Logging logger = Logging.getInstance();

    private Keyboard() {}

    public static synchronized Keyboard getInstance() {
        if (instance == null) {
            instance = new Keyboard();
        }
        return instance;
    }

    private void log(String... args) {
        logger.log(args);
    }

    /**
     * Returns the platform-appropriate modifier key ("Meta" on macOS, "Control" elsewhere).
     */
    public static String modifier() {
        return MODIFIER;
    }

    // ── Hold and Release ─────────────────────────────────

    @Override
    public void hold(String keyToHold) {
        log("hold() ", "Key: " + keyToHold);
        CommonPage.getInstance().getPage().keyboard().down(keyToHold);
    }

    @Override
    public void release(String keyToRelease) {
        log("release() ", "Key: " + keyToRelease);
        CommonPage.getInstance().getPage().keyboard().up(keyToRelease);
    }

    /**
     * Presses a key combination atomically (e.g. "Meta+a", "Control+c").
     * Unlike hold/release pairs, this cannot leave a modifier key stuck.
     */
    public void press(String keys) {
        log("press() ", "Keys: " + keys);
        CommonPage.getInstance().getPage().keyboard().press(keys);
    }

    // ── Alerts ───────────────────────────────────────────

    @Override
    public void alert(AlertAction action) {
        log("alert() ", action.name());
        CommonPage.getInstance().getPage().onceDialog(dialog -> {
            switch (action) {
                case ACCEPT -> dialog.accept();
                case DISMISS -> dialog.dismiss();
            }
        });
    }

    @Override
    public void alert(String keysToSend) {
        log("alert() ", keysToSend);
        CommonPage.getInstance().getPage().onceDialog(dialog -> dialog.accept(keysToSend));
    }

    // ── Compose ──────────────────────────────────────────

    @Override
    public void compose(String content) {
        // Delegates to CommonPage so password masking and currentElement state
        // (both private to CommonPage) remain encapsulated.
        CommonPage.getInstance().compose(content);
    }

    @Override
    public void compose(String key, int repeat) {
        throwOnNullOrEmpty(key, "Key");
        throwOnMinus(repeat, "repeat");
        log("compose(key, repeat) ", "Key: " + key + " Repeat: " + repeat);
        for (int i = 0; i < repeat; i++) {
            CommonPage.getInstance().getPage().keyboard().press(key);
        }
    }

    @Override
    public void compose(Key key) {
        throwOnNull(key, "Key");
        log("compose(Key) ", "Key: " + key.name());
        CommonPage.getInstance().getPage().keyboard().press(key.getValue());
    }

    @Override
    public void compose(Key key, int repeat) {
        throwOnNull(key, "Key");
        throwOnMinus(repeat, "repeat");
        log("compose(Key, repeat) ", "Key: " + key.name() + " Repeat: " + repeat);
        for (int i = 0; i < repeat; i++) {
            CommonPage.getInstance().getPage().keyboard().press(key.getValue());
        }
    }

    // ── Submit ───────────────────────────────────────────

    @Override
    public void submit() {
        log("submit()");
        CommonPage.getInstance().getPage().keyboard().press("Enter");
    }
}

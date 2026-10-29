package sonnet;

/**
 * Defines the common page-object operations used across the framework.
 * <p>
 * Implementations typically represent a browser page or a section of a page and
 * provide reusable actions such as opening pages, interacting with elements,
 * collecting data, and asserting page state.
 */
public interface CommonPageInterface {
    /**
     * Represents keyboard actions that can be applied to a page or input field.
     */
    enum Action {
        BACKSPACE,
        DOWN,
        ENTER,
        HOME,
        LEFT,
        RIGHT,
        SPACE,
        TAB,
        UP;
    }

    /**
     * Represents supported element traits that can be queried from a page.
     */
    enum Trait {
        ALT,
//        ATTRIBUTES,
        CLASS,
        CONTENT,
        DISPLAYED,
        ENABLED,
        HEIGHT,
        HREF,
        ID,
        LANG,
        NAME,
        PLACEHOLDER,
        REQUIRED,
        SELECTED,
        STYLE,
        TAG,
        TEXT,
        TEXT_TRIMMED,
        TITLE,
        TYPE,
        VALUE,
        VISIBLE,
        WIDTH,
        XPOS,
        YPOS;
    }

    /**
     * Highlights the specified target on the page.
     *
     * @param target the element or area to highlight
     */
    void box(Target target);

    /**
     * Highlights the current active target.
     */
    void box();

    /**
     * Selects an element by its index within the current set of options or items.
     *
     * @param index the zero-based index to choose
     */
    void choose(int index);

    /**
     * Selects an element by its enum index within the current set of options or items.
     *
     * @param index the zero-based index to choose
     */
    void choose(Enum.Index index);

    /**
     * Selects an element by its enum list index within the current set of options or items.
     *
     * @param index the zero-based index to choose
     */
    void choose(Enum.ListIndex index);

    /**
     * Selects an item by its visible name.
     *
     * @param name the item name to select
     */
    void choose(String name);

    /**
     * Performs a click action on the current active element.
     */
    void click();

    /**
     * Navigates to a target destination from the current context.
     *
     * @param destination the target destination
     */
    void cognate(Target destination);

    /**
     * Navigates from one target to another.
     *
     * @param origin the source target
     * @param destination the destination target
     */
    void cognate(Target origin, Target destination);

    /**
     * Collects the value associated with the provided target.
     *
     * @param target the target to collect data from
     */
    void collect(Target target);

    /**
     * Sends a keyboard action to the currently focused element.
     *
     * @param action the action to perform
     */
    void compose(Action action);

    /**
     * Enters the provided text into the current active input element.
     *
     * @param text the text to type
     */
    void compose(String text);

    /**
     * Ends the current interaction or context.
     */
    void end();

    /**
     * Sets focus to the specified target.
     *
     * @param target the target to focus
     */
    void focus(Target target);

    /**
     * Reads a requested trait value from the current target.
     *
     * @param trait the trait to retrieve
     * @return the trait value as a string, or {@code null} if unavailable
     */
    String get(Trait trait);

    /**
     * Opens the provided URL.
     *
     * @param url the URL to navigate to
     */
    void go(String url);

    /**
     * Logs the specified message for debugging or test reporting.
     *
     * @param message the message to log
     */
    void log(String message);

    /**
     * Opens the page or starting state represented by this page object.
     */
    void open();

    /**
     * Probes the specified target to inspect its state.
     *
     * @param target the target to inspect
     */
    void probe(Target target);

    /**
     * Returns the number of items currently represented by the page object.
     *
     * @return the size of the current collection or page context
     */
    int size();

    /**
     * Submits the current form or action.
     */
    void submit();

    /**
     * Removes the current highlight or active selection from the page.
     */
    void unbox();

    /**
     * Executes the provided performable task.
     *
     * @param runner the performable task to execute
     */
    <T extends Performable> void perform(T runner);

    /**
     * Stores a value associated with the provided key.
     */
    void store(String key, String value);


    /**
     * Retrieves the value associated with the provided key.
     */
    String retrieve(String key);
}

package sonnet.reference.selenium;


import sonnet.reference.selenium.locators.Target;
import sonnet.reference.selenium.performable.Performable;

import java.util.List;

/**
 * Interface defining common page actions and checks for UI automation.
 * Provides chainable commands, element state probes, collection operations, DOM navigation, and more.
 */
public interface CommonPageInterface {

    ////////////////////////
    // Chainable commands //
    ////////////////////////

    /**
     * Sets the browser window to full screen mode.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.fullScreen();
     * </pre>
     */
    void fullScreen();

    /**
     * Maximises the browser window.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.maximise();
     * </pre>
     */
    void maximise(); // Review

    // Browser directives

    /**
     * Opens the default browser and navigates to the base URL.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.open();
     * </pre>
     */
    void open();

    /**
     * Opens the specified browser and navigates to the base URL.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.open(Enums.Browser.CHROME);
     * </pre>
     * @param browser the browser to open
     */
    void open(Enums.Browser browser);

    /**
     * Navigates to the specified URL.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.go("https://example.com");
     * </pre>
     * @param url the URL to navigate to
     */
    void go(String url);

    /**
     * Navigates in the specified direction (e.g., FORWARD, BACK).
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.go(Enums.Direction.BACK);
     * </pre>
     * @param direction the direction to navigate
     */
    void go(Enums.Direction direction);

    /**
     * Refreshes the current page.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.refresh();
     * </pre>
     */
    void refresh();

    /**
     * Closes the current browser window.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.close();
     * </pre>
     */
    void close();

    /**
     * Quits the browser session.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.quit();
     * </pre>
     */
    void quit();

    /**
     * Focuses on the specified target element.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.focus(Target.USERNAME_FIELD);
     * </pre>
     * @param target the element to focus
     */
    void focus(Target target);

    /**
     * Waits for the specified time and focuses on the current element.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.focus(1000);
     * </pre>
     * @param waitTime time to wait in milliseconds
     */
    void focus(int waitTime);

    /**
     * Focuses on the specified target elements in order.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.focus(Target.USERNAME_FIELD, Target.PASSWORD_FIELD);
     * </pre>
     * @param targets the elements to focus
     */
    void focus(Target... targets);

    /**
     * Waits for the specified time and focuses on the given target elements.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.focus(500, Target.USERNAME_FIELD, Target.PASSWORD_FIELD);
     * </pre>
     * @param waitTime time to wait in milliseconds
     * @param targets the elements to focus
     */
    void focus(int waitTime, Target... targets);

    boolean peek(Target target);

    boolean peek(Target target, int waitTime);

    void peek(int waitTime);

    /**
     * Departs from the specified target element (e.g., blur or unfocus).
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.depart(Target.USERNAME_FIELD);
     * </pre>
     * @param target the element to depart from
     */
    void depart(Target target);

    /**
     * Departs from the specified target element and targets group.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.depart(Target.USERNAME_FIELD, Enums.Targets.FORM_FIELDS);
     * </pre>
     * @param target the element to depart from
     * @param targets the group of targets
     */
    void depart(Target target, Enums.Targets targets);

    /**
     * Departs from a list of target elements.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.depart(Arrays.asList(Target.USERNAME_FIELD, Target.PASSWORD_FIELD));
     * </pre>
     * @param targets the list of elements to depart from
     */
    void depart(List<Target> targets);

    /**
     * Marks the specified target as absent (e.g., removes or hides it).
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.absent(Target.POPUP);
     * </pre>
     * @param target the element to mark as absent
     */
    void absent(Target target);

    /**
     * Returns to the origin or starting point in the navigation.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.origin();
     * </pre>
     */
    void origin();

    ////////////////////////
    // PROBE CONDITIONALS //
    ////////////////////////

    /**
     * Probes the specified target for visibility (default state).
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.probe(Target.LOGIN_BUTTON);
     * </pre>
     * @param target the element to probe
     */
    void probe(Target target);

    /**
     * Probes the specified target for a given element state.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.probe(Target.LOGIN_BUTTON, Enums.ElementState.VISIBLE);
     * </pre>
     * @param target the element to probe
     * @param elementState the state to check (e.g., VISIBLE, ENABLED)
     */
    void probe(Target target, Enums.ElementState elementState);

    /**
     * Waits for the specified time and probes the current element.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.probe(1000);
     * </pre>
     * @param waitTime time to wait in milliseconds
     */
    void probe(int waitTime);

    /**
     * Ends the current probe or wait operation.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.end();
     * </pre>
     */
    void end();


    /**
     * Collects the specified target element into a collection for further operations.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.collect(Target.ROW);
     * </pre>
     * @param target the element to collect
     */
    void collect(Target target);

    /**
     * Collects a list of target elements into a collection for further operations.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.collect(Arrays.asList(Target.ROW1, Target.ROW2));
     * </pre>
     * @param targets the list of elements to collect
     */
    void collect(List<Target> targets);

    /**
     * Waits for the specified time and collects elements.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.collect(1);
     * </pre>
     * @param waitTime time to wait in seconds
     */
    void collect(int waitTime);

    /**
     * Finds the required target element relative to the origin (source) element location in the DOM hierarchy.
     * This method traverses up the DOM from the origin element, searching for the required target as a descendant at each level.
     * If multiple matches are found at a level, the first is selected. Throws an exception if not found.
     *
     * <b>Example usage:</b>
     * <pre>
     *     page.cognate(Target.SOURCE, Target.RELATIVE_TARGET);
     * </pre>
     * @param origin the source element to start the search from
     * @param target the target element to find relative to the source
     */
    void cognate(Target origin, Target target);

    /**
     * Finds the required target element relative to the currently focused element.
     * This method traverses up the DOM from the current element, searching for the required target as a descendant at each level.
     * If multiple matches are found at a level, the first is selected. Throws an exception if not found.
     *
     * <b>Example usage:</b>
     * <pre>
     *     page.focus(Target.NAME_CELL);
     *     page.cognate(Target.DELETE_BUTTON);
     * </pre>
     * @param target the target element to find relative to the currently focused element
     */

    void cognate(Target target);
    /**
     * Sets wait time for the cognate operation.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.cognate(1);
     * </pre>
     * @param waitTime time to wait in seconds
     */
    void cognate(int waitTime);

    /**
     * Chooses an element by its index in a collection.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.choose(2);
     * </pre>
     * @param index the index to choose
     */
    void choose(int index);

    /**
     * Chooses an element by its visible text.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.choose("Login");
     * </pre>
     * @param text the text to match
     */
    void choose(String text);

    /**
     * Chooses an element by its visible text, with optional leniency.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.choose("Login", true);
     * </pre>
     * @param text the text to match
     * @param lenient whether to use lenient matching
     */
    void choose(String text, boolean lenient);

    /**
     * Chooses the specified target element.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.choose(Target.LOGIN_BUTTON);
     * </pre>
     * @param target the element to choose
     */
    void choose(Target target);

    /**
     * Chooses an element by its index (Enums.Index) in a collection.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.choose(Enums.Index.FIRST);
     * </pre>
     * @param index the index to choose
     */
    void choose(Enums.Index index);

    /**
     * Chooses an element by its list index (Enums.ListIndex).
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.choose(Enums.ListIndex.SECOND);
     * </pre>
     * @param listIndex the list index to choose
     */
    void choose(Enums.ListIndex listIndex);

    /**
     * Expands the specified target element (e.g., dropdown, accordion).
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.expand(Target.DROPDOWN);
     * </pre>
     * @param target the element to expand
     */
    void expand(Target target);

    /////////////////
    // COLLECTIONS //
    /////////////////

    /**
     * Returns the size of the current collection.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     int count = page.size();
     * </pre>
     * @return the number of elements in the collection
     */
    int size();

    ///////////////////////////////
    // COLLECTION CONTENT CHECKS //
    ///////////////////////////////

    /**
     * Asserts that the specified text is present in the collection.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.present("Success");
     * </pre>
     * @param text the text to check for
     */
    void present(String text);

    /**
     * Asserts that all specified texts are present in the collection.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.present(Arrays.asList("Success", "Done"));
     * </pre>
     * @param textList the list of texts to check for
     */
    void present(List<String> textList);

    /**
     * Asserts that the specified text is absent from the collection.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.absent("Error");
     * </pre>
     * @param text the text to check for absence
     */
    void absent(String text);

    /**
     * Asserts that all specified texts are absent from the collection.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.absent(Arrays.asList("Error", "Failed"));
     * </pre>
     * @param textList the list of texts to check for absence
     */
    void absent(List<String> textList);

    // Element interaction

    /**
     * Clicks the currently focused or chosen element.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.click();
     * </pre>
     */
    void click();

    /**
     * Double-clicks the currently focused or chosen element.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.dblClick();
     * </pre>
     */
    void dblClick();

    /**
     * Clears the value of the currently focused or chosen element.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.clear();
     * </pre>
     */
    void clear();

    /**
     * Hovers over the currently focused or chosen element.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.hover();
     * </pre>
     */
    void hover();

    /**
     * Hovers over the specified target element.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.hover(Target.HELP_ICON);
     * </pre>
     * @param target the element to hover over
     */
    void hover(Target target);

    /**
     * Drags the draggable element and drops it onto the drop zone.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.dragDrop(Target.ITEM, Target.BASKET);
     * </pre>
     * @param draggable the element to drag
     * @param dropZone the element to drop onto
     */
    void dragDrop(Target draggable, Target dropZone);

    /**
     * Drops the currently focused or chosen element onto the drop zone.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.drop(Target.BASKET);
     * </pre>
     * @param dropZone the element to drop onto
     */
    void drop(Target dropZone);

    /**
     * Drags the draggable element onto the currently focused or chosen element.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.drag(Target.ITEM);
     * </pre>
     * @param draggable the element to drag
     */
    void drag(Target draggable);

    // Interactions with return values

    /**
     * Gets the specified trait of the current element.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     String value = page.get(Enums.ElementTrait.VALUE);
     * </pre>
     * @param ElementTrait the trait to retrieve
     * @return the value of the trait
     */
    String get(Enums.ElementTrait ElementTrait);

    /**
     * Waits for the specified time and gets the value of the current element.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.get(1000);
     * </pre>
     * @param getWaitTime time to wait in milliseconds
     */
    void get(int getWaitTime);

    /**
     * Uploads a file to the currently focused or chosen element.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.file("C:/path/to/file.txt");
     * </pre>
     * @param filePath the path to the file
     */
    void file(String filePath);

    /**
     * Gets the current page URL.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     String url = page.getUrl();
     * </pre>
     * @return the URL as a string
     */
    String getUrl();

    /**
     * Gets the current page title.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     String title = page.getTitle();
     * </pre>
     * @return the title as a string
     */
    String getTitle();

    /**
     * Performs a complex action defined by the given Performable object.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.perform(new CustomAction());
     * </pre>
     * @param val the performable action
     * @param <T> the type of performable
     */
    <T extends Performable> void perform(T val);

    // Frames

    /**
     * Switches to the frame with the specified ID or name.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.frame("mainFrame");
     * </pre>
     * @param IdOrName the frame ID or name
     */
    void frame(String IdOrName);

    /**
     * Switches to the frame specified by the target element.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.frame(Target.FRAME);
     * </pre>
     * @param target the frame element
     */
    void frame(Target target);

    /**
     * Switches to the frame at the specified index.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.frame(0);
     * </pre>
     * @param index the frame index
     */
    void frame(int index);

    /**
     * Switches to the default content (main frame).
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.frame();
     * </pre>
     */
    void frame();

    /**
     * Exits the current frame and returns to the parent frame.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.deframe();
     * </pre>
     */
    void deframe();

    // Windows

    /**
     * Switches to the specified window.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.window(Enums.Window.MAIN);
     * </pre>
     * @param window the window to switch to
     */
    void window(Enums.Window window);

    /**
     * Switches to the window at the specified index.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.window(1);
     * </pre>
     * @param index the window index
     */
    void window(int index);

    // Element text checks

    /**
     * Asserts that the element's text matches the specified value.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.matches("Welcome");
     * </pre>
     * @param text the text to match
     */
    void matches(String text);

    /**
     * Asserts that the element's text matches the specified value, with content display option.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.matches("Welcome", Enums.Content.VISIBLE);
     * </pre>
     * @param text the text to match
     * @param displayed the content display option
     */
    void matches(String text, Enums.Content displayed);

    /**
     * Asserts that the element's text contains the specified partial text.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.contains("Welc");
     * </pre>
     * @param partialText the partial text to check
     */
    void contains(String partialText);

    /**
     * Asserts that the element's text contains the specified partial text, with content display option.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.contains("Welc", Enums.Content.VISIBLE);
     * </pre>
     * @param partialText the partial text to check
     * @param displayed the content display option
     */
    void contains(String partialText, Enums.Content displayed);

    // Element states

    /**
     * Asserts that the element is selected.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.selected();
     * </pre>
     */
    void selected();

    /**
     * Asserts that the element is unselected.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.unSelected();
     * </pre>
     */
    void unSelected();

    /**
     * Asserts that the element is enabled.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.enabled();
     * </pre>
     */
    void enabled();

    /**
     * Asserts that the element is disabled.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.disabled();
     * </pre>
     */
    void disabled();

    /**
     * Asserts that the element is clickable.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.clickable();
     * </pre>
     */
    void clickable();

    /**
     * Asserts that the element is unclickable.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.unclickable();
     * </pre>
     */
    void unclickable();

    /**
     * Asserts that the element is visible.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.visible();
     * </pre>
     */
    void visible();

    /**
     * Asserts that the element is hidden.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.hidden();
     * </pre>
     */
    void hidden();

    // Reset Elements and timers

    /**
     * Resets the state of elements and timers.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.reset();
     * </pre>
     */
    void reset();

    // Session storage

    /**
     * Stores a value in session storage with the specified key.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.store("token", "abc123");
     * </pre>
     * @param key the key to store
     * @param value the value to store
     */
    void store(String key, String value);

    /**
     * Stores an object in session storage with the specified key.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.store("user", userObject);
     * </pre>
     * @param key the key to store
     * @param value the object to store
     */
    void store(String key, Object value);

    /**
     * Retrieves a value from session storage by key.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     String token = page.retrieve("token");
     * </pre>
     * @param key the key to retrieve
     * @return the stored value
     */
    String retrieve(String key);

    /**
     * Retrieves an object from session storage by key.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     Object user = page.retrieve("user", new User());
     * </pre>
     * @param key the key to retrieve
     * @param o the object type or default value
     * @return the stored object
     */
    Object retrieve(String key, Object o);

    // Element storage

    /**
     * Stores the currently focused element with the specified key.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.storeFocused("mainField");
     * </pre>
     * @param key the key to store
     */
    void storeFocused(String key);

    /**
     * Retrieves the focused element by key.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.retrieveFocused("mainField");
     * </pre>
     * @param key the key to retrieve
     */
    void retrieveFocused(String key);

    // Console Output

    /**
     * Prints the currently focused element to the console.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.printFocused();
     * </pre>
     */
    void printFocused();

    /**
     * Prints the current collection to the console.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.printCollection();
     * </pre>
     */
    void printCollection();

    /**
     * Logs the specified arguments to the console or log file.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.log("Step started", "User logged in");
     * </pre>
     * @param args the arguments to log
     */
    void log(String... args);

    /**
     * Creates a loop builder for repeated actions on the given page object.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     LoopBuilder builder = page.loop(myPageObject);
     * </pre>
     * @param commonPageObject the page object to loop on
     * @return a LoopBuilder instance
     */
    LoopBuilder loop(CommonPageObject commonPageObject);

    /**
     * Creates a loop builder for repeated actions on the given page object, for a specified number of times.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     LoopBuilder builder = page.loop(myPageObject, 5);
     * </pre>
     * @param commonPageObject the page object to loop on
     * @param times the number of times to repeat
     * @return a LoopBuilder instance
     */
    LoopBuilder loop(CommonPageObject commonPageObject, int times);

    // EVENT GENERATORS

    /**
     * Triggers the specified event on the current element.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.trigger(Enums.Event.CLICK);
     * </pre>
     * @param event the event to trigger
     */
    void trigger( Enums.Event event);

    /**
     * Executes the given JavaScript in the browser context.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     String result = page.javascript("return document.title;");
     * </pre>
     * @param script the JavaScript code to execute
     * @return the result of the script execution
     */
    String javascript(String script);

    /**
     * Enables or disables network logging of the specified type.
     * <p>
     * <b>Example usage:</b>
     * <pre>
     *     page.networkLogging("request");
     * </pre>
     * @param type the type of network logging (e.g., 'request', 'response')
     */
    void networkLogging(String type);

}

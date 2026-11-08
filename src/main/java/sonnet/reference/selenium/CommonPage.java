package sonnet.reference.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v134.network.Network;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import sonnet.reference.selenium.locators.Target;
import sonnet.reference.selenium.logger.Logger;
import sonnet.reference.selenium.performable.Performable;
import sonnet.reference.selenium.utils.Colours;
import sonnet.reference.selenium.utils.ListUtils;
import sonnet.reference.selenium.utils.WindowUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.openqa.selenium.devtools.v134.network.Network.responseReceived;
import static sonnet.reference.selenium.VMArgs.*;
import static sonnet.reference.selenium.utils.StringUtils.isNotNullOrEmpty;
import static sonnet.reference.selenium.utils.StringUtils.isNullOrEmpty;


/**
 * {@inheritDoc}
 */

public class CommonPage implements CommonPageInterface {
    private final Logger logger = new Logger();
    private final Integer FOCUS_ELEMENT_PRESENT_TOLERANCE = 60;
    private final Integer COGNATE_ELEMENT_PRESENT_TOLERANCE = 60;
    private final Integer PEEK_ELEMENT_PRESENT_TOLERANCE = 1;
    private final Integer PROBE_ELEMENT_PRESENT_TOLERANCE = 10;
    private final Integer MAX_TIMEOUT = 40;
    private final Integer SHORT_WAIT = 5;
    private final int CLICK_TIMEOUT = 10;
    private final int CLEAR_ATTEMPTS = 5;
    private final int CLICK_ATTEMPTS = 5;
    private final int COLLECT_POLL_TIMEOUT = 10;
    private final String SCREENSHOTS_DIRECTORY = "screenshots";
    private final String PERFORMABLE = "performable";
    protected boolean execute = true;
    protected static WebElement currentElement;
    private List<WebElement> currentElements = new ArrayList<>();
    private Map<String, WebElement> storedElements = new HashMap<>();
    protected static WebDriver driver;
    private static DevTools devTools;
    private final Map<String, String> sessionMap = new HashMap<>();
    private WebDriverWait cognateWait = null;
    protected static WebDriverWait focusWait = null;
    private WebDriverWait peekWait = null;
    private WebDriverWait probeWait = null;
    private WebDriverWait shortWait = null;
    private final StopWatch stopWatch = new StopWatch();
    protected static By currentElementLocator;
    private JavascriptExecutor javascriptExecutor;
    // Used for choose : FIRST,LAST,NEXT,PREVIOUS
    private int currentListIndex = 0;
    private static CommonPage instance = null;
    private int getWaitTime = 20;
    private WaitService waitService;
    private int collectTimeout = COLLECT_POLL_TIMEOUT;
    private int focusTimeout;

    protected CommonPage() {
        super();
    }

    public static synchronized CommonPage getInstance() {
        if (instance == null) {
            instance = new CommonPage();
        }
        return instance;
    }

    @Override
    public void go(String url) {
        if (execute) {
            try {
                if (url != null && !url.isEmpty()) {
                    log("get() \t", "URL : ", url);
                    driver.get(url);
                } else {
                    takeScreenShotAndExit("URL is null or empty");
                }
            } catch (WebDriverException wde) {
                takeScreenShotAndExit(wde.getMessage());
            }
        }
    }

    @Override
    public void go(Enums.Direction direction) {
        if (execute) {
            log("go(Direction) : " + direction.name() + Colours.RESET);
            try {
                switch (direction) {
                    case BACK:
                        driver.navigate().back();
                        break;
                    case REFRESH:
                        driver.navigate().refresh();
                        break;
                    case FORWARD:
                        driver.navigate().forward();
                        break;
                }
            } catch (WebDriverException wde) {
                takeScreenShotAndExit(wde.getMessage());
            }
        }
    }

    // Default Chrome
    public void open() {
        open(Enums.Browser.CHROME);
    }

    @Override
    public void open(Enums.Browser browser) {
        switch (browser) {
            // Default
            case CHROME:
                chromeConfig();
                break;
            case FIREFOX:
                driver = new FirefoxDriver();
                break;
            case EDGE:
                driver = new EdgeDriver();
                break;
            case SAFARI:
                driver = new SafariDriver();
                break;
            default:
                break;
        }
        log("open()\t", "New Driver : ", browser.name());
        driverConfig();
        waitService = new WaitService(driver);
    }

    @Override
    public void close() {
        log("close() ", "Current Browser", "Use open() to open another browser");
        try {
            driver.close();
        } catch (WebDriverException wde) {
            takeScreenShotAndExit(wde.getMessage());
        }
    }

    @Override
    public void quit() {
        log("quit() ", "All Browser Windows");
        try {
            driver.quit();
        } catch (WebDriverException wde) {
            takeScreenShotAndExit(wde.getMessage());
        }
    }

    ///////////
    // FOCUS //
    ///////////

    @Override
    public void focus(Target target) {

        if (execute) {
            log("focus() ", target.getBy().toString());
            focus(target.getBy());
        }
    }

    @Override
    public void focus(int waitTime) {
        if(execute) {
            log("focus() ", "Set Wait Time : " + waitTime);
            if(waitTime > 0) {
                focusWait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
                focusTimeout = waitTime;
            }
            else {
                throw new CPOException("Invalid wait time : "+waitTime);
            }
        }
    }

    @Override
    public void focus(Target... targets) {
        finder(2, targets);
    }

    @Override
    public void focus(int timeOut, Target... targets) {
        finder(timeOut, targets);
    }

    protected void focus(By by) {
        if (execute) {
            try {
                currentElement = focusWait.until(ExpectedConditions.presenceOfElementLocated(by));
            } catch (TimeoutException toe) {
                throw toe;
            } catch (WebDriverException e) {
                takeScreenShotAndExit(e.getMessage());
            }
            currentElementLocator = by;
        }
    }

    //////////
    // PEEK //
    //////////

    /**
     * Check for presence of element on the DOM by Target.  Does not need to be visible.
     * Peek wait tolerance.  Default : 1 seconds
     * See also
     * peek( int waitTime ) Wait time persists until either overridden by the same method or with reset()
     * peek( Target target, int waitTime ) Timer does not remain in place, it will reset to default after the method has completed.
     * @param target
     * @return true if present, false otherwise
     */
    @Override
    public boolean peek(Target target) {
        boolean found = false;
        if (execute) {
            try {
                currentElement = peekWait.until(ExpectedConditions.presenceOfElementLocated(target.getBy()));
                found = true;
            } catch (TimeoutException toe) {
                // not found
            } catch (WebDriverException e) {
                takeScreenShotAndExit(e.getMessage());
            }
        }
        return found;
    }

    /**
     * Check for presence of element on the DOM.
     * Specify wait time.  Wait time will be reset on return from the method.
     * Element does not need to be visible.
     * Peek wait tolerance.  Default : 1 seconds
     * See also :
     * peek( int waitTime )  Wait time persists until either overridden by the same method or with reset()
     * peek( Target target )
     * @param target
     * @return true if present, false otherwise
     */
    @Override
    public boolean peek(Target target, int waitTime) {
        boolean found = false;
        if (execute) {
            peek(waitTime);
            try {
                currentElement = peekWait.until(ExpectedConditions.presenceOfElementLocated(target.getBy()));
                found = true;
            } catch (TimeoutException toe) {
                // not found
            } catch (WebDriverException e) {
                takeScreenShotAndExit(e.getMessage());
            }
            finally {
                // Reset
                peek(PEEK_ELEMENT_PRESENT_TOLERANCE);
            }
        }
        return found;
    }

    /**
     * Specify wait time. Wait time persists until either overridden by the same method or with reset()
     * Element does not need to be visible.
     * Peek wait tolerance.  Default : 1 seconds
     * See also :
     * peek( Target target, int waitTime ) Timer does not remain in place, it will reset to default after the method has completed.
     * peek( Target target )
     * @param waitTime
     */
    @Override
    public void peek(int waitTime) {
        if(execute) {
            log("peek() ", "waitTime : " + waitTime);
            if(waitTime > 0) {
                peekWait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
            }
            else {
                throw new CPOException("Invalid wait time : "+waitTime);
            }
        }
    }


    private void finder(int timeOut, Target... targets) {
        if (execute) {
            boolean found = false;
            WebDriverWait webDriverWait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeOut));
            for (Target target : targets) {
                try {
                    webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(target.getBy()));
                    focus(target);
                    found = true;
                    break;
                } catch (TimeoutException toe) {
                    // Not present
                }
            }
            if (!found) {
                throw new CPOException("TargetFinder : Target not found from list :" + Arrays.toString(targets));
            }
        }
    }

    ////////////////////////
    // PROBE CONDITIONALS //
    ////////////////////////

    // Default ElementState.VISIBLE
    @Override
    public void probe(Target target) {
        if (execute) {
            log("probe()   ", target.getBy().toString());
            detect(target.getBy(), Enums.ElementState.VISIBLITY);
        }
    }

    @Override
    public void probe(Target target, Enums.ElementState elementState) {
        if (execute) {
            log("probe()   ", target.getBy().toString(), " " + elementState.name());
            detect(target.getBy(), elementState);
        }
    }

    private void detect(By by, Enums.ElementState elementState) {
        if (execute) {
            try {
                switch (elementState) {
                    case VISIBLITY ->
                        // Present and visible.  Throws TIMEOUT otherwise
                            currentElement = probeWait.until(ExpectedConditions.visibilityOfElementLocated(by));
                    case ABSENCE ->
                        // Either invisible or not present. Will return immediately. Throws TIMEOUT otherwise
                            probeWait.until(ExpectedConditions.invisibilityOfElementLocated(by));
                    case PRESENCE ->
                        // Present, not necessarily visible. Throws TIMEOUT otherwise
                            currentElement = probeWait.until(ExpectedConditions.presenceOfElementLocated(by));
                    default -> throw new IllegalStateException("Unexpected Element State: " + elementState);
                }
            } catch (WebDriverException wde) {
                // Gets here with TIMEOUT if :
                // VISIBLITY -> Element is either not present or present but not visible.
                // ABSENCE -> Element is present or present and visible
                // PRESENCE -> Element is not present
                execute = false;
            }
        }
    }

    @Override
    public void probe(int waitTime) {
        if (execute) {
            log("probe() ", "Set Wait Time : " + Integer.toString(waitTime));
            probeWait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
        }
    }

    @Override
    public void end() {
        execute = true;
        probe(PROBE_ELEMENT_PRESENT_TOLERANCE);
    }

    @Override
    public void origin() {
        if (execute) {
            log("origin() ", "Document Root");
            try {
                currentElement = driver.findElement((By.tagName("html")));
            } catch (WebDriverException wde) {
                takeScreenShotAndExit(wde.getMessage());
            }
        }
    }
    private void collect(By by) {
        if (execute) {
            try {
                waitService.waitForStableValue(currentElement, element -> element.findElements(by).size(), collectTimeout);
                currentElements = currentElement.findElements(by);
            } catch (TimeoutException timeout) {
                takeScreenShotAndExit("Element list following collect() not fully loaded after seconds : " + collectTimeout + " with locator : " + by.toString());
            } catch (WebDriverException wde) {
                takeScreenShotAndExit(wde.getMessage());
            }
            if (currentElements.isEmpty()) {
                log("Warn : No elements found following collect() : " + by.toString());
            }
        }
    }

    @Override
    public void collect(int waitTime) {
        collectTimeout = waitTime;
    }

    @Override
    public void collect(Target target) {
        if (execute) {
            log("collect()", target.getBy().toString());
            collect(target.getBy());
            log("collected", Integer.toString(currentElements.size()));
        }
    }

    @Override
    public void collect(List<Target> targets) {
        if (execute) {
            log("collect()", "Targets : ", "" + targets.size());
            WebElement webElement;
            currentElements.clear();
            try {
                for (Target target : targets) {
                    webElement = focusWait.until(ExpectedConditions.presenceOfElementLocated(target.getBy()));
                    currentElements.add(webElement);
                }
            } catch (WebDriverException wde) {
                throw new CPOException(wde.getMessage());
            }
            log("collect()", "Targets found : ", "" + targets.size());
        }
    }

    /**
     * Finds the required target element relative to the original (source) element location in the DOM hierarchy.
     * This method traverses up the DOM from the original element, searching for the required target as a descendant at each level.
     * If multiple matches are found at a level, the first is selected. Throws an exception if not found.
     *
     * @param original the source element to start the search from
     * @param required the target element to find relative to the source
     *
     * <b>Example usage:</b>
     * <pre>
     *     page.cognate(Target.SOURCE, Target.RELATIVE_TARGET);
     * </pre>
     */
    @Override
    public void cognate(Target original, Target required) {
        log("cognate()", "original: " + original.getBy().toString(), "  required: " + required.getBy().toString());
        try {
            WebElement webElement = cognateWait.until(ExpectedConditions.presenceOfElementLocated(original.getBy()));
            cognate(webElement, required, 0);
        } catch (NoSuchElementException e) {
            throw new CPOException("Original element not found by locator: " + original.getBy().toString());
        }
    }

    @Override
    public void cognate(Target required) {
        log("cognate()", "original: " + currentElement, "  required: " + required.getBy().toString());
        try {
            cognate(currentElement, required, 0);
        } catch (NoSuchElementException e) {
            throw new CPOException("Original element not found by locator: " + currentElement.toString());
        }
    }

    @Override
    public void cognate(int waitTime) {
        if (execute) {
            log("cognate() ", "Set Wait Time : " + Integer.toString(waitTime));
            cognateWait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
        }
    }

    /**
     * Helper for cognate: recursively traverses up the DOM from the current element,
     * searching for the required target as a descendant at each level.
     *
     * @param current the current element in the traversal
     * @param required the target to find as a descendant
     * @param level the current level of traversal (root is 0)
     */
    private void cognate(WebElement current, Target required, int level) {
        List<WebElement> targetElements = current.findElements(getRelativeBy(required));

        if (targetElements.isEmpty()) {
            if (current.getTagName().equals("html")) {
                throw new CPOException("Cognate not found");
            } else {
                WebElement parent = current.findElement(By.xpath(".."));
                cognate(parent, required, level + 1);
            }
        } else {
            if (targetElements.size() > 1) {
                log("cognate()", "Warn. Found multiple cognates at " + level + " levels above stem.  Selecting first");
            }
            currentElement = targetElements.getFirst();
            log("cognate()", "Found at/from level : " + level);
        }
    }

    @Override
    public void click() {
        if (execute) {
            log("click() ");
            click(CLICK_TIMEOUT);
        }
    }

    @Override
    public void dblClick() {
        if (execute) {
            log("dblClick()");
            try {
                Actions actions = new Actions(driver);
                actions.doubleClick(currentElement);
            } catch (WebDriverException wde) {
                throw new CPOException(wde.getMessage());
            }
        }
    }

    private void click(int waitTime) {
        if (execute) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
            int clicks = CLICK_ATTEMPTS;
            boolean isRefocused = false;
            while (true) {
                try {
                    if (clicks == CLICK_ATTEMPTS) {
                        wait.until(ExpectedConditions.elementToBeClickable(currentElement)).click();
                    } else {
                        currentElement.click();
                    }
                    break;
                } catch (TimeoutException te) {
                    takeScreenShotAndExit("click() Element not clickable");
                } catch (ElementClickInterceptedException e) {
                    // This is an exception caused by some element "covering" the element trying to be clicked
                    // it won't be detected by elementToBeClickable, so must be dealt with separately - we repeat
                    // clicking #(clickTolerance) times waiting between each attempt to approximately match waitTime
                    pause(1);
                    if (clicks-- == 0) {
                        takeScreenShotAndExit("click() wait time exceeded: " + currentElement.toString());
                    }
                } catch (StaleElementReferenceException e) {
                    // sometimes a focused element's selenium WebElement reference becomes "stale" and interacting
                    // with it throws an error. In this case, we try re-focusing with the given locator only once
                    if (!isRefocused) {
                        log("click()\t", "Stale reference: refocusing");
                        focus(currentElementLocator);
                        isRefocused = true;
                    } else {
                        takeScreenShotAndExit(e.getMessage());
                    }
                } catch (WebDriverException e) {
                    takeScreenShotAndExit(e.getMessage());
                }
            }
        }
    }

    @Override
    public void clear() {
        if (execute) {
            int count = 0;
            try {
                log("clear() ", "Current Element : " + currentElementLocator.toString());
                currentElement.clear();
            } catch (ElementNotInteractableException ENIE) {
                log("Relocating: clear() ", "Current Element : " + currentElementLocator.toString());
                pause(1);
                count++;
                if (count == CLEAR_ATTEMPTS) {
                    takeScreenShotAndExit("Element not clearable");
                }
            } catch (WebDriverException wde) {
                takeScreenShotAndExit(wde.getMessage());
            }
        }
    }

    @Override
    public void choose(int index) {
        if (execute) {
            try {
                log("choose() ", "Index : ", Integer.toString(index));
                currentElement = currentElements.get(index);
            } catch (IndexOutOfBoundsException | WebDriverException exception) {
                takeScreenShotAndExit(exception.getMessage());
            }
        }
    }

    @Override
    public void choose(String text) {
        if (execute) {
            log("choose()", "Text : ", text);
            // Strict check by default
            setCurrentElementFromCurrentElementsByText(text, true);
        }
    }

    @Override
    public void choose(String text, boolean strict) {
        if (execute) {
            setCurrentElementFromCurrentElementsByText(text, strict);
        }
    }

    @Override
    public void choose(Target target) {
        if (execute) {
            log("choose() ", "Type : " + target.getType(), " " + target.getValue());
            String type = target.getValue();
            switch (target.getType()) {
                case VALUE -> setCurrentElementFromCurrentElementsByValue(type);
                case TEXT -> setCurrentElementFromCurrentElementsByText(type, true);
                case PARTIAL_TEXT -> setCurrentElementFromCurrentElementsByText(type, false);
                case CSS -> setCurrentElementFromCurrentElementsByAttributeKeyPair(target.getKey(), target.getValue());
                default -> throw new CPOException("choose(" + type + "). Not supported.  Request if required.");
            }
        }
    }

    private void setCurrentElementFromCurrentElementsByAttributeKeyPair(String key, String value) {
        for (WebElement webElement : currentElements) {
            String attributeValue = webElement.getAttribute(key);
            if (isNotNullOrEmpty(attributeValue) && attributeValue.equals(value)) {
                currentElement = webElement;
                break;
            }
        }
    }

    @Override
    public void choose(Enums.Index index) {
        if (execute) {
            log("choose()", index.name());
            choose(index.ordinal());
        }
    }

    @Override
    public void choose(Enums.ListIndex listIndex) {
        if (execute) {
            log("choose() : ", listIndex.name());
            switch (listIndex) {
                case FIRST:
                    currentElement = currentElements.getFirst();
                    currentListIndex = 0;
                    break;
                case LAST:
                    currentListIndex = currentElements.size() - 1;
                    currentElement = currentElements.get(currentListIndex);
                    break;
                case NEXT:
                    // size() is total, list range is  0 -> size() -1
                    if (++currentListIndex == currentElements.size()) {
                        currentListIndex--;
                        log("Choose()", "NEXT : ", "List pointer already at the end of collection");
                    }
                    currentElement = currentElements.get(currentListIndex);
                    break;
                case PREVIOUS:
                    if (--currentListIndex < 0) {
                        currentListIndex++;
                        log("Choose()", "PREVIOUS : ", "List pointer already at the start of collection");
                    }
                    currentElement = currentElements.get(currentListIndex);
                    break;
            }
        }
    }

    @Override
    public void expand(Target target) {
        if (execute) {
            log("expand() ", target.getBy().toString());
            focus(target);
            try {
                waitService.waitForStableValue(currentElement, WebElement::getSize, focusTimeout);
            } catch (TimeoutException timeout) {
                takeScreenShotAndExit("Target not fully expanded after seconds : " + focusTimeout + " with locator : " + target.getBy().toString());
            } catch (WebDriverException e) {
                takeScreenShotAndExit(e.getMessage());
            }
        }
    }


    @Override
    public void matches(String text) {
        if (execute) {
            try {
                String elementText = currentElement.getText().trim();
                log("matches() ", "Required : " + text, ", Actual : " + elementText);
                if (!elementText.trim().equals(text.trim())) {
                    takeScreenShotAndExit("matches() : Required : " + text + " Actual : " + elementText);
                }
            } catch (WebDriverException wde) {
                takeScreenShotAndExit(wde.getMessage());
            }
        }
    }

    @Override
    public void matches(String text, Enums.Content displayed) {
        if (execute) {
            log("matches() ", displayed.name());
            if (displayed.equals(Enums.Content.PRESENT)) {
                matches(text);
            }
            // ABSENT
            else {
                String elementText = currentElement.getText().trim();
                if (elementText.equals(text.trim())) {
                    takeScreenShotAndExit("matches(ABSENT). Text is present :" + text);
                }
            }
        }
    }

    public void contains(String text) {
        if (execute) {
            try {
                log("contains() ", text);
                String elementText = currentElement.getText();
                if (!elementText.trim().contains(text.trim())) {
                    takeScreenShotAndExit("Text is not present :" + text);
                }
            } catch (WebDriverException wde) {
                takeScreenShotAndExit(wde.getMessage());
            }
        }
    }

    @Override
    public void contains(String text, Enums.Content displayed) {
        if (execute) {
            log("contains() ", displayed.name());
            if (displayed.equals(Enums.Content.PRESENT)) {
                contains(text);
            }
            // ABSENT
            else {
                String elementText = currentElement.getText().trim();
                if (elementText.trim().contains(text.trim())) {
                    takeScreenShotAndExit("contains(ABSENT). Text is present :" + text);
                }
            }
        }
    }

    /////////////////////
    // SESSION STORAGE //
    /////////////////////

    @Override
    public void store(String key, String value) {
        if (isNotNullOrEmpty(key) && isNotNullOrEmpty(value)) {
            sessionMap.put(key, value);
        } else {
            throw new CPOException("store.  Invalid parameters");
        }
    }

    @Override
    public void store(String key, Object value) {

    }

    @Override
    public String retrieve(String key) {
        if (isNotNullOrEmpty(key)) {
            return sessionMap.get(key);
        } else {
            throw new CPOException("store Element.  Invalid parameters");
        }
    }

    @Override
    public Object retrieve(String key, Object o) {
        return null;
    }

    @Override
    public void storeFocused(String key) {
        if (isNotNullOrEmpty(key)) {
            storedElements.put(key, currentElement);
        } else {
            throw new CPOException("store Element.  Invalid parameter");
        }
    }

    @Override
    public void retrieveFocused(String key) {
        if (isNotNullOrEmpty(key)) {
            currentElement = storedElements.get(key);
        } else {
            throw new CPOException("store Element.  Invalid parameter");
        }
    }

    private void depart(By by) {

        try {
            // Element can still be on the DOM but not visible
            stopWatch.start();
            focusWait.until(ExpectedConditions.invisibilityOfElementLocated(by));
            stopWatch.stop();
            log("depart()    Element departs after " + stopWatch.printElapsed());
        } catch (NoSuchElementException nsee) {
            // Element not present - ignore the exception
            log("depart()   Element departed");
        } catch (WebDriverException wde) {
            takeScreenShotAndExit(wde.getMessage());
        }
    }

    @Override
    public void depart(Target target) {
        if (execute) {
            log("depart() ", target.getBy().toString());
            depart(target.getBy());
        }
    }

    @Override
    public void depart(Target target, Enums.Targets targets) {
        if (execute) {
            switch (targets) {
                case SINGLE:
                    depart(target.getBy());
                    break;
                case MULTIPLE:
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(MAX_TIMEOUT));
                    List<WebElement> elementList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(target.getBy()));
                    if (!ListUtils.isNullOrEmpty(elementList)) {
                        wait.until(ExpectedConditions.invisibilityOfAllElements(elementList));
                    }
                default:
                    break;
            }
        }
    }

    @Override
    public void depart(List<Target> targets) {
        if (execute) {
            List<WebElement> webElements = new ArrayList<>();
            // Elements may already have departed the DOM - so short wait
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            for (Target target : targets) {
                try {
                    WebElement webElement = (WebElement) ExpectedConditions.presenceOfElementLocated(target.getBy());
                    webElements.add(webElement);
                } catch (NoSuchElementException nsee) {
                    // Ignore - No longer on DOM or might not have been there in the first place
                } catch (WebDriverException wde) {
                    takeScreenShotAndExit(wde.getMessage());
                }
            }
            if (!ListUtils.isNullOrEmpty(webElements)) {
                wait.until(ExpectedConditions.invisibilityOfAllElements(webElements));
            }
        }
    }

    private void absent(By by) {
        log("absent() ", "By : " + by.toString());
        try {
            // Element can still be on the DOM but not visible
            focusWait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (NoSuchElementException nsee) {
            // Element not present - ignore the exception
        } catch (WebDriverException wde) {
            takeScreenShotAndExit(wde.getMessage());
        }
    }

    @Override
    public void absent(Target target) {
        if (execute) {
            absent(target.getBy());
        }
    }

    @Override
    public void hover() {
        if (execute) {
            try {
                log("hover() " + currentElementLocator.toString());
                Actions actions = new Actions(driver);
                actions.moveToElement(currentElement).perform();
            } catch (WebDriverException wde) {
                takeScreenShotAndExit(wde.getMessage());
            }
        }
    }

    @Override
    public void hover(Target target) {
        focus(target.getBy());
        hover();
    }

    // Drop draggable on dropZone
    @Override
    public void dragDrop(Target draggable, Target dropZone) {
        dragDrop(draggable.getBy(), dropZone.getBy());
    }

    // Drop currently focused element on dropZone
    @Override
    public void drop(Target dropZone) {
        dragDrop(currentElement, focusWait.until(ExpectedConditions.visibilityOfElementLocated(dropZone.getBy())));
    }

    // Drag draggable to currently focused element
    @Override
    public void drag(Target draggable) {
        dragDrop(focusWait.until(ExpectedConditions.visibilityOfElementLocated(draggable.getBy())), currentElement);
    }

    private void dragDrop(By draggable, By dropZone) {
        if (execute) {
            log("dragDrop :draggable : ", draggable.toString());
            log("dragDrop :dropZone : ", dropZone.toString());
            WebElement drag = focusWait.until(ExpectedConditions.visibilityOfElementLocated(draggable));
            WebElement drop = focusWait.until(ExpectedConditions.visibilityOfElementLocated(dropZone));
            dragDrop(drag, drop);
        }
    }

    private void dragDrop(WebElement draggable, WebElement dropZone) {
        try {
            Actions actions = new Actions(driver);
            actions.dragAndDrop(draggable, dropZone).build().perform();
        } catch (WebDriverException wde) {
            takeScreenShotAndExit(wde.getMessage());
        }
    }

    // Driver methodsdepart(
    public void fullScreen() {
        driver.manage().window().fullscreen();
    }

    public void maximise() {
        driver.manage().window().maximize();
    }

    private void position(int x, int y) {
        driver.manage().window().setPosition(new Point(x, y));
    }

    protected static WebDriver getDriver() {
        return driver;
    }

    @Override
    public <T extends Performable> void perform(T runner) {
        runner.run();
    }

    ////////////////////////////
    // COLLECTION SIZE CHECKS //
    ////////////////////////////

    @Override
    public int size() {
        int currentElementsSize = 0;
        if (execute) {
            currentElementsSize = getElementsSize();
            log("size() \t", "Returns : ", Integer.toString(currentElementsSize));
        }
        return currentElementsSize;
    }

    private int getElementsSize() {
        if (!currentElements.isEmpty())
            return currentElements.size();
        else
            return 0;
    }

    ///////////////////////
    // COLLECTION CHECKS //
    ///////////////////////

    /**
     * Checks if the specified text is present in the current collection of elements.
     * If the text is not found, a screenshot is taken and an exception is thrown.
     *
     * @param text the text to check for in the collection
     *
     * <b>Example usage:</b>
     * <pre>
     *     page.present("Success");
     * </pre>
     */
    @Override
    public void present(String text) {
        if (execute) {
            log("present() ", text);
            if (!isTextPresentInElementList(text))
                takeScreenShotAndExit("present(String) : " + text + " Not found in collection");
        }
    }

    /**
     * Checks if all specified texts are present in the current collection of elements.
     * If any text is not found, a screenshot is taken and an exception is thrown.
     *
     * @param textList the list of texts to check for in the collection
     *
     * <b>Example usage:</b>
     * <pre>
     *     page.present(Arrays.asList("Success", "Done"));
     * </pre>
     */
    @Override
    public void present(List<String> textList) {
        if (execute) {
            log("present(List<String>) : ", Arrays.toString(textList.toArray()));
            if (!isStringListPresentInElementList(textList))
                takeScreenShotAndExit("present(List<String> : " + Arrays.toString(textList.toArray()) + " Not found in collection");
        }
    }

    /**
     * Checks if the specified text is absent from the current collection of elements.
     * If the text is found, a screenshot is taken and an exception is thrown.
     *
     * @param text the text to check for absence in the collection
     *
     * <b>Example usage:</b>
     * <pre>
     *     page.absent("Error");
     * </pre>
     */
    @Override
    public void absent(String text) {
        if (execute) {
            log("absent()", text);
            if (isTextPresentInElementList(text))
                takeScreenShotAndExit("absent(String) : " + text + " Found in collection");
        }
    }

    /**
     * Checks if all specified texts are absent from the current collection of elements.
     * If any text is found, a screenshot is taken and an exception is thrown.
     *
     * @param textList the list of texts to check for absence in the collection
     *
     * <b>Example usage:</b>
     * <pre>
     *     page.absent(Arrays.asList("Error", "Failed"));
     * </pre>
     */
    @Override
    public void absent(List<String> textList) {
        if (execute) {
            log("absent(List<String>) : ", Arrays.toString(textList.toArray()));
            if (isStringListPresentInElementList(textList))
                takeScreenShotAndExit("absent(List<String> : " + Arrays.toString(textList.toArray()) + " Found in collection");
        }
    }

    @Override
    public void frame(String idOrName) {
        if (execute) {
            if (execute) {
                log("frame() ", "idOrName  :", idOrName);
                try {
                    driver.switchTo().frame(idOrName);
                    log("Switched to :", "Iframe");
                } catch (NoSuchElementException | TimeoutException exception) {
                    takeScreenShotAndExit("No Iframe Found : idOrName : " + idOrName);
                } catch (WebDriverException wde) {
                    takeScreenShotAndExit(wde.getMessage());
                }
            }
        }
    }

    private void frame(By by) {
        if (execute) {
            log("frame() ", by.toString());
            try {
                WebElement iframe = focusWait.until(ExpectedConditions.presenceOfElementLocated(by));
                driver.switchTo().frame(iframe);
                log("Switched to :", "Iframe");
            } catch (NoSuchElementException | TimeoutException exception) {
                takeScreenShotAndExit("No Iframe Found :" + by);
            } catch (WebDriverException wde) {
                takeScreenShotAndExit(wde.getMessage());
            }
        }
    }

    @Override
    public void frame(Target target) {
        frame(target.getBy());
    }

    @Override
    public void frame() {
        frame(0);
    }

    @Override
    public void frame(int index) {
        if (execute) {
            log("frame() ", "Index : ", Integer.toString(index));
            try {
                driver.switchTo().frame(index);
                log("Switched to :", "Iframe index : ", "" + index);
            } catch (WebDriverException wde) {
                takeScreenShotAndExit(wde.getMessage());
            }
        }
    }

    @Override
    public void deframe() {
        if (execute) {
            log("unframe() ", "");
            try {
                driver.switchTo().defaultContent();
            } catch (WebDriverException wde) {
                takeScreenShotAndExit(wde.getMessage());
            }
        }
    }

    @Override
    public void window(Enums.Window window) {
        if (execute) {
            log("window()\t", Integer.toString(window.getValue()));
            switchToWindow(window.getValue());
        }
    }

    @Override
    public void window(int index) {
        if (execute) {
            log("window()\t", Integer.toString(index));
            switchToWindow(index);
        }
    }

    private void switchToWindow(int index) {
        try {
            Object[] windowHandles = driver.getWindowHandles().toArray();
            driver.switchTo().window((String) windowHandles[index]);
        } catch (WebDriverException wde) {
            takeScreenShotAndExit("window()");
        }
    }

    @Override
    public String get(Enums.ElementTrait elementTrait) {
        String val = null;
        if (execute) {
            log("get()  \t", "Element Attribute : ", elementTrait.name());
            val = getAttribute(elementTrait);
        }
        log("Returning", val);
        return val;
    }

    @Override
    public void get(int waitTime) {
        this.getWaitTime = waitTime;
    }

    @Override
    public void file(String filePath) {
        if (execute) {
            try {
                File file = new File(filePath);
                log("file() \t", "Path : ", filePath);
                driver.findElement(new By.ByCssSelector("[type='file']")).sendKeys(file.getAbsolutePath());

            } catch (WebDriverException wde) {
                takeScreenShotAndExit(wde.getMessage());
            }
        }
    }

    public void file(Target target, String filePath) {
        if (execute) {
            try {
                File file = new File(filePath);
                log("file() ", "Path : ", filePath, "Target : ", target.toString());
                driver.findElement(target.getBy()).sendKeys(file.getAbsolutePath());

            } catch (WebDriverException wde) {
                takeScreenShotAndExit(wde.getMessage());
            }
        }
    }

    @Override
    public String getUrl() {
        String url = null;
        if (execute) {

            try {
                url = driver.getCurrentUrl();
                log("getUrl()", url);
            } catch (WebDriverException wde) {
                takeScreenShotAndExit(wde.getMessage());
            }
        }
        return url;
    }

    @Override
    public String getTitle() {
        String title = null;
        if (execute) {
            try {
                title = driver.getTitle();
                log("getTitle()", title);
            } catch (WebDriverException wde) {
                takeScreenShotAndExit(wde.getMessage());
            }
        }
        return title;
    }

    @Override
    public void refresh() {
        if (execute) {
            try {
                driver.navigate().refresh();
            } catch (WebDriverException wde) {
                takeScreenShotAndExit(wde.getMessage());
            }
        }
    }

    public void log(String... args) {
        logger.log(args);
    }

    public void log(Colours colour, String text) {
        logger.log(colour, text);
    }

    public void warn(String text) {
        logger.warn(text);
    }

    @Override
    public void printFocused() {
        if (execute) {
            log("printFocused()");
            printElement(currentElement);
        }
    }

    @Override
    public void printCollection() {
        if (execute) {
            log("Current Elements : ", Integer.toString(currentElements.size()));
            if (currentElements != null) {
                int index = 1;
                for (WebElement webElement : currentElements) {
                    log("===== " + index + " =====");
                    printElement(webElement);
                    index++;
                }
            } else {
                log("Collection is empty");
            }
        }
    }

    private void printElement(WebElement webElement) {
        if (execute) {
            log("Tag  \t\t", webElement.getTagName());
            log("Text  \t\t", webElement.getText());
            log("Attributes\t", getAttributes());
            Rectangle rectangle = webElement.getRect();
            log("Coordinate x", "" + rectangle.x);
            log("Coordinate y", "" + rectangle.y);
            log("Height", "\t\t" + rectangle.height);
            log("Width", "\t\t" + rectangle.width);
            log("Enabled", "\t\t" + currentElement.isEnabled());
            log("Displayed", "\t" + currentElement.isDisplayed());
            log("Selected", "\t" + currentElement.isSelected());
        }
    }

    @Override
    public void selected() {
        if (execute) {
            log("selected() ");
            if (!currentElement.isSelected()) {
                takeScreenShotAndExit(String.format("Element: %s is not selected", currentElement.getText()));
            }
        }
    }

    @Override
    public void unSelected() {
        if (execute) {
            log("unSelected() ");
            if (currentElement.isSelected()) {
                takeScreenShotAndExit(String.format("Element: %s is selected", currentElement.getText()));
            }
        }
    }

    @Override
    public void enabled() {
        if (execute) {
            log("enabled() ", currentElementLocator.toString());
            if (!currentElement.isEnabled()) {
                takeScreenShotAndExit(String.format("Element: %s is not enabled", currentElement.getText()));
            }
        }
    }

    @Override
    public void disabled() {
        if (execute) {
            log("disabled() ", currentElementLocator.toString());
            if (currentElement.isEnabled()) {
                takeScreenShotAndExit(String.format("Element: %s is not disabled", currentElement.getText()));
            }
        }
    }

    @Override
    public void clickable() {
        if (execute) {
            log("clickable()");
            try {
                shortWait.until(ExpectedConditions.elementToBeClickable(currentElement));
            } catch (TimeoutException te) {
                // Unclickable
                takeScreenShotAndExit(String.format("Element: %s is not clickable", currentElement.getText()));
            } catch (WebDriverException te) {
                takeScreenShotAndExit(String.format(te.getMessage()));
            }
        }
    }

    @Override
    public void unclickable() {
        if (execute) {
            log("unClickable()");
            try {
                shortWait.until(ExpectedConditions.elementToBeClickable(currentElement));
                takeScreenShotAndExit(String.format("Element: %s is clickable", currentElement.getText()));
            } catch (TimeoutException te) {
                // Unclickable - return
            } catch (WebDriverException te) {
                takeScreenShotAndExit(te.getMessage());
            }
        }
    }

    @Override
    public void visible() {
        if (execute) {
            log("visible() ", currentElementLocator.toString());
            shortWait.until(ExpectedConditions.visibilityOf(currentElement));
        }
    }

    @Override
    public void hidden() {
        if (execute) {
            log("hidden() ");
            shortWait.until(ExpectedConditions.invisibilityOf(currentElement));
        }
    }

    @Override
    public void reset() {
        origin();
        currentElements.clear();
        cognateWait = new WebDriverWait(driver, Duration.ofSeconds(COGNATE_ELEMENT_PRESENT_TOLERANCE));
        probeWait = new WebDriverWait(driver, Duration.ofSeconds(PROBE_ELEMENT_PRESENT_TOLERANCE));
        focusWait = new WebDriverWait(driver, Duration.ofSeconds(FOCUS_ELEMENT_PRESENT_TOLERANCE));
        peekWait = new WebDriverWait(driver, Duration.ofSeconds(PEEK_ELEMENT_PRESENT_TOLERANCE));
        focusTimeout = FOCUS_ELEMENT_PRESENT_TOLERANCE;
        collectTimeout = COLLECT_POLL_TIMEOUT;
    }

    @Override
    public void trigger(Enums.Event event) {
        if (execute) {
            log("trigger() ", event.name());
            switch (event) {
                case CLICK -> javascriptExecutor.executeScript("arguments[0].click();", currentElement);
                case FOCUS -> javascriptExecutor.executeScript("arguments[0].focus();", currentElement);
                case BLUR -> javascriptExecutor.executeScript("arguments[0].blur();", currentElement);
                case DBLCLICK -> javascriptExecutor.executeScript("arguments[0].dblclick();", currentElement);
                case CHANGE -> javascriptExecutor.executeScript("arguments[0].change();", currentElement);
                default -> throw new IllegalArgumentException("Not happening.  This an enum");
            }
        }
    }

    @Override
    public String javascript(String script) {
        String jsOutput = null;
        if (execute) {
            log("javascriptExecutor() ", script);
            jsOutput = (String) javascriptExecutor.executeScript(script);
        }
        return jsOutput;
    }

    ///////////////////////////////////////////////////////////
    // NON-INTERFACE INTERNAL, PRIVATE AND PROTECTED METHODS //
    ///////////////////////////////////////////////////////////

    private String getAttributes() {
        return javascriptExecutor.executeScript("var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;", currentElement).toString();
    }

    private void setCurrentElementFromCurrentElementsByText(String text, boolean strict) {
        boolean found = false;
        try {
            for (WebElement webElement : currentElements) {
                String s = webElement.getText();
                if ((webElement.getText().trim().equals(text.trim()) && strict) ||
                        (webElement.getText().trim().contains(text.trim()) && !strict)) {
                    currentElement = webElement;
                    found = true;
                    break;
                }
            }
            if (!found) {
                takeScreenShotAndExit("Text not found in collection : " + text);
            }
        } catch (WebDriverException wde) {
            takeScreenShotAndExit(wde.getMessage());
        }
    }

    private void setCurrentElementFromCurrentElementsByValue(String value) {
        boolean found = false;
        try {
            for (WebElement webElement : currentElements) {
                if (webElement.getAttribute("value").equals(value)) {
                    currentElement = webElement;
                    found = true;
                    break;
                }
            }
            if (!found) {
                takeScreenShotAndExit("Value  not found in collection : " + value);
            }
        } catch (WebDriverException wde) {
            takeScreenShotAndExit(wde.getMessage());
        }
    }

    void takeScreenShotAndExit(String message) {
        if (screenshotOnError) {
            String pathSeparator = File.separator;
            String homeDir = System.getProperty("user.dir");

            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String fileName = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss'.png'").format(new Date());
            String directoryPath = homeDir + pathSeparator + SCREENSHOTS_DIRECTORY;
            File directory = new File(directoryPath);

            if (!directory.exists()) {
                directory.mkdir();
            }

            String fullPath = directoryPath + pathSeparator + fileName;
            try {
                //FileUtils.moveFile(scrFile, new File(fullPath), REPLACE_EXISTING);
            } catch (Exception ioe) {
                throw new CPOException(message + "\nScreenShot: " + Path.of(fullPath).toUri());
            }
        }
        throw new CPOException(message);
    }

    private boolean isTextPresentInElementList(String text) {
        boolean found = false;
        for (WebElement webElement : currentElements) {
            if (webElement.getText().trim().equals(text.trim())) {
                found = true;
                break;
            }
        }
        return found;
    }

    private boolean isStringListPresentInElementList(List<String> stringList) {
        int textPresentCount = 0;
        int elementListSize = stringList.size();
        for (WebElement webElement : currentElements) {
            for (String string : stringList) {
                if (webElement.getText().trim().equals(string.trim())) {
                    textPresentCount++;
                }
            }
        }
        return textPresentCount == elementListSize;
    }

    private String getAttribute(Enums.ElementTrait trait) {
        String val;
        int count = 0;
        while (true) {
            switch (trait) {
                case ATTRIBUTES -> val = getAttributes();
                case TAG -> val = currentElement.getTagName();
                case TEXT -> val = currentElement.getText();
                case SELECTED -> val = String.valueOf(currentElement.isSelected()).toUpperCase();
                case ENABLED -> val = String.valueOf(currentElement.isEnabled()).toUpperCase();
                case DISPLAYED -> val = String.valueOf(currentElement.isDisplayed()).toUpperCase();
                default -> val = currentElement.getAttribute(trait.name().toLowerCase());
            }
            if (!isNullOrEmpty(val))
                break;
            else {
                pause(500L);
                count++;
            }
            if (count > (getWaitTime / 2))
                throw new CPOException("No attribute found");
        }
        return val;
    }

    private By getRelativeBy(Target target) {
        String byString = target.getBy().toString();
        if (byString.contains("By.xpath")) {
            String relativeXPath = byString.replaceAll("By\\.xpath: \\.?//", ".//");
            return By.xpath(relativeXPath);
        } else {
            return target.getBy();
        }
    }

    ///////////////
    // INTERNAL. //
    ///////////////

    ////////////////////
    // BROWSER CONFIG //
    ////////////////////

    private void chromeConfig() {
        ChromeOptions chromeOptions = new ChromeOptions();
        if (headless) {
            chromeOptions.addArguments("--headless=new");
            chromeOptions.addArguments("--window-size=1920,1200");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            chromeOptions.addArguments("--use-fake-device-for-media-stream");
            chromeOptions.addArguments("--use-fake-ui-for-media-stream");
        }
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        driver = new ChromeDriver(chromeOptions);
        devTools = ((HasDevTools) driver).getDevTools();
    }

    private void driverConfig() {
        javascriptExecutor = (JavascriptExecutor) driver;

        int[] windowPosition = headless ? new int[]{0, 0} : WindowUtils.getPositionOfSelectedWindow(browserWindowIndex);
        position(windowPosition[0], windowPosition[1]);

        driver.manage().window().maximize();
        driver.manage().logs();
        cognateWait = new WebDriverWait(driver, Duration.ofSeconds(COGNATE_ELEMENT_PRESENT_TOLERANCE));
        focusWait = new WebDriverWait(driver, Duration.ofSeconds(FOCUS_ELEMENT_PRESENT_TOLERANCE));
        peekWait = new WebDriverWait(driver, Duration.ofSeconds(PEEK_ELEMENT_PRESENT_TOLERANCE));
        probeWait = new WebDriverWait(driver, Duration.ofSeconds(PROBE_ELEMENT_PRESENT_TOLERANCE));
        shortWait = new WebDriverWait(driver, Duration.ofSeconds(SHORT_WAIT));
        focusTimeout = FOCUS_ELEMENT_PRESENT_TOLERANCE;
    }

    // Protected access and for use by JUNIT
    protected String getXpath(WebElement childElement, String current) {
        String childTag = childElement.getTagName();
        if (childTag.equals("html")) {
            return "/html[1]" + current;
        }
        WebElement parentElement = childElement.findElement(By.xpath(".."));
        List<WebElement> childrenElements = parentElement.findElements(By.xpath("*"));
        int count = 0;
        for (WebElement child : childrenElements) {
            String childrenElementTag = child.getTagName();
            if (childTag.equals(childrenElementTag)) {
                count++;
            }
            if (childElement.equals(child)) {
                return getXpath(parentElement, "/" + childTag + "[" + count + "]" + current);
            }
        }
        return null;
    }

    protected void pause(int seconds) {
        pause(seconds * 1000L);
    }

    // Protected access for use by JUNIT tests
    protected void pause(long milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected File takeScreenshot() {
        File scrFile;
        scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        return scrFile;
    }

    public LoopBuilder loop(CommonPageObject commonPageObject) {
        return new LoopBuilder(instance, commonPageObject);
    }

    public LoopBuilder loop(CommonPageObject commonPageObject, int times) {
        return new LoopBuilder(instance, commonPageObject, times);
    }

    public void networkLogging(String type) {
        log("Network Logging() : Looking for failing calls of type: " + type);
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.addListener(responseReceived(), responseReceivedEvent -> {
            String url = responseReceivedEvent.getResponse().getUrl();
            int status = responseReceivedEvent.getResponse().getStatus();
            String requestType = String.valueOf(responseReceivedEvent.getType());
            if (!(status == 200) && requestType.contains(type)) {
                log("Network Logged : " + url + " - TYPE: " + type);
                log("RESPONSE HEADERS: " + responseReceivedEvent.getResponse().getHeaders());
            }
        });
    }

}

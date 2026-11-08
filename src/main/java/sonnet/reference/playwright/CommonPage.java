package sonnet.reference.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.BoundingBox;
import com.microsoft.playwright.options.ElementState;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;
import sonnet.reference.playwright.locators.Target;
import sonnet.reference.playwright.utils.PlaywrightArtifacts;
import sonnet.reference.playwright.utils.VMArgs;
import sonnet.reference.playwright.utils.WindowUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.Assert.assertTrue;
import static sonnet.reference.playwright.locators.TargetFactory.type;
import static sonnet.reference.playwright.utils.Checks.*;
import static sonnet.reference.playwright.utils.StringUtils.isNullOrEmpty;

public class CommonPage implements CommonPageInterface, WindowInterface {
    // COLLECT, HALT, EXPAND
    private final int STABLE_POLL_COUNT = 4;
    private final int POLLING_PAUSE = 4000;
    // Immutable default.  probe timeout, can be temporarily overridden with
    // probe(Target, int) , but will reset to default after each probe.
    private final int PROBE_TIMEOUT = 5000; // Default probe timeout of 5 seconds
    private final int PEEK_TIMEOUT = 2;  // Default peek timeout of 2 seconds
    private final int DEFAULT_ELEMENT_LOCATOR_TIMEOUT = 45000;  // Playwright default
    private final int JUNIT_ELEMENT_LOCATOR_TIMEOUT = 2000;  // Short timeout for JUnit tests with local HTML files

    // Edit this line to set default or JUNIT element locator timeouts.
    // JUNIT tests act on local HTML files so location is almost instantaneous
    private int locatorTimeout = DEFAULT_ELEMENT_LOCATOR_TIMEOUT;
    /// ////////////////////////////////////////////////////

    private final Logging logger = Logging.getInstance();
    private static CommonPage instance = null;
    private Locator currentElement;  // Web Element - current element
    private Playwright playwright;
    private List<Locator> collection = new ArrayList<>(); // Collected child/sibling elements
    private static Page page;  // Driver
    private Browser browser;
    private BrowserContext context;
    private boolean execute = true;
    private int currentListIndex = 0;
    private FrameLocator frameLocator;  // Active iframe context (null = main frame)
    private int waitForTimeout = 250;
    private boolean slowMotion = false;
    private boolean boxed = false;
    private Locator boxRoot = null;
    private Locator siblingLocator;

    // Session storage — simple String K/V (RNQA-1974)
    private static final java.util.Map<String, String> sessionMap = new java.util.HashMap<>();

    // No instances allowed.  Use CommonPage.getInstance() to get the singleton instance.
    private CommonPage() {
        super();
    }

    protected static synchronized CommonPage getInstance() {
        if (instance == null) {
            instance = new CommonPage();
        }
        return instance;
    }

    ///////////////////////////////
    // INTERFACE IMPLEMENTATIONS //
    /// ////////////////////////////

    @Override
    public void open() {
        log("open()");
        try {
            playwright = Playwright.create();
            BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                    .setHeadless(VMArgs.isHeadless())
                    .setSlowMo(VMArgs.getSlowMo());
            String chromiumPath = System.getenv("CHROMIUM_EXECUTABLE_PATH");
            if (chromiumPath != null && !chromiumPath.isBlank()) {
                launchOptions.setExecutablePath(Paths.get(chromiumPath));
            }
            browser = playwright.chromium().launch(launchOptions);
            context = PlaywrightArtifacts.createContext(browser);
            PlaywrightArtifacts.startTracing(context);
            page = context.newPage();
            // For JUNIT testing with local HTML files set default.timeout=3000 in properties.
            double d = VMArgs.getDefaultTimeout();
            page.setDefaultTimeout(VMArgs.getDefaultTimeout());
            WindowUtils.applyWindowState(page);
        } catch (PlaywrightException playwrightException) {
            System.out.println(playwrightException.getMessage());
        }
    }

    @Override
    public void open(Enums.Browser browser) {
        // Currently only Chromium is supported.  Future versions may support Firefox and Webkit.
        open();
    }

    @Override
    public void setScreenSize(int width, int height) {
        if (page == null) return;
        throwOnZeroOrMinus(width, height);
        log("setScreenSize()", String.valueOf(width), String.valueOf(height));
        page.setViewportSize(width, height);
    }

    @Override
    public void setScreenSize(Enums.ScreenSize screenSize) {
        if (page == null) return;
        throwOnNull(screenSize, "ScreenSize");
        log("setScreenSize()", screenSize.name());
        if (screenSize == Enums.ScreenSize.FULL && !VMArgs.isHeadless()) {
            WindowUtils.maximise(page);
        } else {
            page.setViewportSize(screenSize.getWidth(), screenSize.getHeight());
        }
    }

    @Override
    public void go(String url) {
        throwOnNullOrEmpty(url, "URL");
        log("go()\t", url);
        frameLocator = null;
        boolean isExternal = url.startsWith("http");
        if (isExternal) {
            page.setDefaultTimeout(DEFAULT_ELEMENT_LOCATOR_TIMEOUT);
        }
        try {
            page.navigate(url);
        } finally {
            if (isExternal) {
                page.setDefaultTimeout(locatorTimeout);
            }
        }
        origin();
    }

    @Override
    public void refresh() {
        log("refresh()");
        page.reload();
        resetState();
    }

    /**
     * Wait for the current page to reach the {@code load} lifecycle state.
     */
    @Override
    public void ready() {
        log("ready()");
        page.waitForLoadState(com.microsoft.playwright.options.LoadState.LOAD);
    }

    @Override
    public void go(Enums.Direction direction) {
        throwOnNull(direction, "Direction");
        log("go()\t", direction.name() );
        switch (direction) {
            case BACK:
                if (page.goBack() == null) {
                    log("go(BACK)", "no previous page in history");
                }
                break;
            case FORWARD:
                if (page.goForward() == null) {
                    log("go(FORWARD)", "no next page in history");
                }
                break;
            case REFRESH:
                refresh();
                return;
        }
        resetState();
    }

    @Override
    public void focus(Target target) {
        if (execute) {
            currentElement = decode(target);
        }
    }

    @Override
    public void focus(Target... targets) {
        if (execute) {
            targetFinder(targets);
        }
    }

    @Override
    public void probe(Target target) {
        if (!execute) return;
        throwOnNull(target, "Target");
        log("probe() ", target.getType().name(), target.getValue());
        // Default visible  and timeout of 5 seconds
        try {
            currentElement = decode(target, PROBE_TIMEOUT);
            currentElement.waitFor(new Locator.WaitForOptions().setTimeout(PROBE_TIMEOUT));
        } catch (TimeoutError exception) {
            execute = false;
        }
    }

    @Override
    public void probe(Target target, int waitTime) {
        if (!execute) return;
        log("Probe()" + target.getType(), target.getValue(), "WaitTime: " + waitTime + "s");
        probeForElement(target, waitTime);
    }


    @Override
    public void origin() {
        if (execute) {
            log("origin() ", frameLocator != null ? "Frame Root" : (boxed ? "Boxed Root" : "Document Root"));
            if (boxed) {
                currentElement = boxRoot;
            } else {
                currentElement = locateElement("html");
            }
        }
    }

    /**
     * Locks the traversal scope to the current element, making it the root for
     * all later locator operations until unbox() is called.
     * <p>Has no effect when {@code execute} is {@code false} (deferred execution mode).</p>
     *
     * @throws IllegalArgumentException if no element is currently in focus
     *                                  (i.e. {@code currentElement} is {@code null})
     */
    @Override
    public void box() {
        if (execute) {
            throwOnNull(currentElement, "No Current Element");
            log("box()\t", "Current Element");
            boxRoot = currentElement;
            boxed = true;
        }
    }

    /**
     * Executes the boxing operation on the specified target element.
     *
     * @param target The target element to be boxed.
     */
    @Override
    public void box(Target target) {
        if (execute) {
            throwOnNull(target, "Target");
            log("box()\t",  target.getType().name() + " ", target.getValue());
            focus(target);
            boxRoot = currentElement;
            boxed = true;
        }
    }

    /**
     * Unboxes the current element if it is boxed.
     */
    @Override
    public void unbox() {
        if (execute) {
            log("unbox()");
            boxRoot = null;
            boxed = false;
        }
    }

    // ── DESCEND ──────

    @Override
    public void descend() {
        if (execute) {
            log("descend() ", "To Child");
            Locator child = currentElement.locator("xpath=./*").first();
            child.waitFor();
            currentElement = child;
        }
    }

    @Override
    public void descend(Target target) {
        if (execute) {
            throwOnNull(target, "Target");
            log("descend()", target.getType().name() + " " + target.getValue());
            Locator child = scopedLocator(currentElement, target);
            child = firstIfMany(child, "descend(): '" + describe(target) + "'");
            child.waitFor();
            currentElement = child;
        }
    }

    // ── ASCEND ──────

    @Override
    public void ascend() {
        if (execute) {
            log("ascend()", "To Parent");
            ascendBy(1);
        }
    }

    @Override
    public void ascend(int levels) {
        if (execute) {
            throwOnZeroOrMinus(levels, "levels");
            log("ascend()", "Levels: ",levels+"");
            ascendBy(levels);
        }
    }

    @Override
    public void ascend(Enums.Index index) {
        if (execute) {
            throwOnNull(index, "Index");
            int levels = index.ordinal() + 1;
            log("ascend()",index.name());
            ascendBy(levels);
        }
    }

    @Override
    public void ascend(Enums.Tag tag) {
        if (execute) {
            throwOnNull(tag, "Tag");
            log("ascend()", "Tag: ",tag.name());
            try {
                Locator ancestor = currentElement.locator("xpath=ancestor::" + tag.name().toLowerCase());
                if (ancestor.count() == 0) {
                    throw new IllegalArgumentException(
                            "Ancestor tag '" + tag.name() + "' not found above current element");
                }
                currentElement = ancestor.last();
            } catch (PlaywrightException e) {
                throw new IllegalArgumentException(
                        "Ancestor tag '" + tag.name() + "' not found: " + e.getMessage());
            }
        }
    }


    @Override
    public void cognate(Target origin, Target target) {
        throwOnNull(origin, "Origin target");
        throwOnNull(target, "Target");
        if (execute) {
            log("cognate()", "From:",origin.getType().name(),origin.getValue());
            log("cognate()", "To:\t ",target.getType().name(),target.getValue());
            try {
                focus(origin);
            } catch (TimeoutError e) {
                throw new CPOException("Element Not Found: " + origin.getType().name() + " " + origin.getValue());
            }
            cognateSearch(currentElement, target, 0);
        }
    }

    @Override
    public void cognate(Target target) {
        throwOnNull(target, "Target");
        if (execute) {
            log("cognate()",target.getType().name(),target.getValue());
            cognateSearch(currentElement, target, 0);
        }
    }


    @Override
    public void end() {
        log("end()");
        execute = true;
    }

    @Override
    public boolean peek(Target target) {

        return peek(target, PEEK_TIMEOUT);
    }

    @Override
    public boolean peek(Target target, int waitTime) {
        if (!execute) return false;
        throwOnMinus(waitTime, "waitTime");
        int timeoutMs = waitTime * 1000;
        log("peek()\t", target.getType().name(), target.getValue(),"Wait: " + timeoutMs + "ms (" + waitTime + "s)");
        try {
            Locator element = decode(target, timeoutMs);
            element.waitFor(new Locator.WaitForOptions().setTimeout(timeoutMs));
            currentElement = element;
            return true;
        } catch (TimeoutError e) {
            log("peek()\t", "Target not found within ",waitTime + "s (" + timeoutMs + "ms)");
            return false;
        }
    }

    @Override
    public void collect(Target target) {
        if (!execute) return;
        throwOnNull(target, "Target");
        log("collect()", target.getType().name() + " " + target.getValue());
        try {
            Locator baseLocator = scopedLocator(currentElement, target);
            if (target.isConjunction()) {
                log("AND: ", target.getSecondaryTarget().getType() + " ", target.getSecondaryTarget().getValue());
                baseLocator = baseLocator.and(scopedLocator(currentElement, target.getSecondaryTarget()));
            }
            log("collect() ", target.getType().name() + " ", target.getValue());

            int previousSize = -1;
            int stableCount = 0;
            long startTime = System.currentTimeMillis();

            while (System.currentTimeMillis() - startTime < POLLING_PAUSE) {
                collection = baseLocator.all();
                int currentSize = collection.size();

                if (currentSize > 0 && currentSize == previousSize) {
                    stableCount++;
                    if (stableCount >= STABLE_POLL_COUNT) {
                        break;
                    }
                } else {
                    stableCount = 0;
                }
                previousSize = currentSize;
                page.waitForTimeout(waitForTimeout);
            }
            if (collection.isEmpty()) {
                log("collect()","No elements found",target.getType().name(), target.getValue());
            } else {
                log("collect()", "Found " + collection.size() + " elements");
            }

        } catch (TimeoutError Exception) {
            execute = false;
        }
    }

    @Override
    public void choose(int index) {
        if (!execute) return;
        log("choose()", index+" ");
        if (collection == null || collection.isEmpty() || index >= collection.size() || index < 0) {
            throw new IllegalArgumentException("Warn: Invalid index " + index + " or collection is empty");
        }
        currentElement = collection.get(index);
    }

    @Override
    public void choose(String option) {
        boolean found = false;
        if (!execute) return;
        throwOnNullOrEmpty(option, "Text");
        log("choose()", option);
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException("choose() No collection present");
        }
        for (Locator locator : collection) {
            if (locator.textContent().trim().equals(option)) {
                currentElement = locator;
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("choose(" + option + ") not found in collection");
        }
    }


    @Override
    public void choose(Target target) {
        if (!execute) {
            return;
        }
        throwOnNull(target, "Target");
        if (target.isConjunction()) {
            throw new IllegalStateException("choose(target) does not process conjunction.");
        }
        log("choose()", target.getType().name(), target.getValue());
        String value = target.getValue();
        switch (target.getType()) {
            case TEXT -> setCurrentElementFromCollectionByText(value, false);
            case PARTIAL_TEXT -> setCurrentElementFromCollectionByText(value, true);
            case HTML_TAG -> setCurrentElementFromCollectionByTag(Enums.Tag.valueOf(value));
            case VALUE, TITLE, TYPE, CLASS, NAME, PLACEHOLDER, HREF, ALT, ATTRIBUTE_VALUE,
                 DATA_ENDPOINT -> setCurrentElementFromCollectionByAttributeKeyPair(target.getKey(), value);
            default -> throw new CPOException("choose(" + value + "). Not supported.  Request if required.");
        }
    }

    @Override
    public void choose(Enums.Index index) {
        if (!execute) return;
        throwOnNull(index, "Index");
        log("choose(): ", index.name());
        if (collection == null || collection.isEmpty() || index.ordinal() >= collection.size() || index.ordinal() < 0) {
            log("Warn: Invalid index " + index.name() + " or collection is empty");
            return;
        }
        currentElement = collection.get(index.ordinal());
    }

    @Override
    public void choose(Enums.ListIndex listIndex) {
        if (execute) {
            throwOnNull(listIndex, "ListIndex");
            log("choose(): ", listIndex.name());
            switch (listIndex) {
                case FIRST:
                    currentElement = collection.getFirst();
                    currentListIndex = 0;
                    break;
                case LAST:
                    currentListIndex = collection.size() - 1;
                    currentElement = collection.get(currentListIndex);
                    break;
                case NEXT:
                    if (++currentListIndex == collection.size()) {
                        currentListIndex--;
                        log("choose()", "NEXT : ", "List pointer already at the end of collection");
                    }
                    currentElement = collection.get(currentListIndex);
                    break;
                case PREVIOUS:
                    if (--currentListIndex < 0) {
                        currentListIndex++;
                        log("choose()", "PREVIOUS : ", "List pointer already at the beginning of collection");
                    }
                    currentElement = collection.get(currentListIndex);
                    break;
            }
        }

    }

    @Override
    public void secure(Target target) {
        if (!execute) return;
        throwOnNull(target, "Target");
        log("secure()", target.getType().name(), target.getValue());
        focus(target);
        secure();
    }

    /**
     * Wait for the current element to reach a stable position on the page.
     */
    @Override
    public void secure() {
        if (!execute) return;
        boolean stable = false;
        double prevWidth = -1;
        double prevHeight = -1;
        double prevX = Double.NaN;
        double prevY = Double.NaN;
        int stableCount = 0;
        long startTime = System.currentTimeMillis();

        log("secure()");

        while (((System.currentTimeMillis() - startTime) < POLLING_PAUSE) && !stable) {
            BoundingBox box = currentElement.boundingBox();
            if (box != null) {
                if ((box.x == prevX && box.y == prevY) &&
                        (box.width == prevWidth && box.height == prevHeight)) {
                    stableCount++;
                    if (stableCount >= STABLE_POLL_COUNT) {
                        stable = true;
                    }
                } else {
                    stableCount = 0;
                }
                prevX = box.x;
                prevY = box.y;
                prevWidth = box.width;
                prevHeight = box.height;
            }
            page.waitForTimeout(waitForTimeout);
        }
        //log("Final location: \nx= " + prevX + "\ny= " + prevY + "\nwidth= " + prevWidth + "\nheight= " + prevHeight);
        if (prevWidth == -1 && prevHeight == -1) {
            throw new ElementNotVisibleException("Element has zero width and height. Verify locator is correct.");
        }
        if (!stable) {
            throw new ElementNotStableException("Element identified by did not reach a stable position within : " + (POLLING_PAUSE * STABLE_POLL_COUNT) + "ms");
        }
    }

    public int size() {
        int size = collection.size();
        log("size()", "\t"+size);
        return size;
    }

    @Override
    public void click() {
        if (!execute) return;
        log("click()");
        currentElement.click();
    }

    @Override
    public void click(Target target) {
        if (!execute) return;
        throwOnNull(target, "Target must not be null");
        log("click()", target.getType().name() + " ", target.getValue());
        focus(target);
        currentElement.click();
    }

    @Override
    public void check() {
        if (!execute) return;
        log("check()");
        currentElement.check();
    }

    @Override
    public void check(Target target) {
        if (!execute) return;
        throwOnNull(target, "Target must not be null");
        log("check()\t", target.getType().name() + " ", target.getValue());
        focus(target);
        currentElement.check();
    }

    @Override
    public void uncheck() {
        if (!execute) return;
        log("uncheck()");
        currentElement.uncheck();
    }

    @Override
    public void uncheck(Target target) {
        if (!execute) return;
        throwOnNull(target, "Target must not be null");
        log("uncheck()", target.getType().name() + " ", target.getValue());
        focus(target);
        currentElement.uncheck();
    }

    @Override
    public void dblclick() {
        if (!execute) return;
        log("dblclick()");
        currentElement.dblclick();
    }

    @Override
    public void dblclick(Target target) {
        if (!execute) return;
        throwOnNull(target, "Target must not be null");
        log("dblclick()", target.getType().name() + " ", target.getValue());
        focus(target);
        currentElement.dblclick();
    }

    @Override
    public void dragdrop(Target source, Target destination) {
        if (!execute) {
            return;
        }
        throwOnNull(source, "dragdrop source Target");
        throwOnNull(destination, "dragdrop destination Target");
        log("dragdrop()", "Source: ", source.getSelector(), "Destination: ", destination.getSelector());
        Locator sourceLocator = decode(source);
        Locator destLocator = decode(destination);
        sourceLocator.dragTo(destLocator);
        // Some HTML5 drag implementations update the source DOM inside a
        // `dragend` handler that fires after dragTo's `drop` resolves. Wait
        // for two animation frames so any post-drop DOM mutations are
        // committed before the caller asserts on the page.
        page.evaluate("() => new Promise(r => requestAnimationFrame(() => requestAnimationFrame(r)))");
    }

    // ── SCROLL ──────

    @Override
    public void scroll() {
        if (!execute) return;
        log("scroll()");
        currentElement.scrollIntoViewIfNeeded();
    }

    @Override
    public void scroll(Target target) {
        if (!execute) return;
        throwOnNull(target, "scroll Target");
        log("scroll()", target.getType().name(), target.getValue());
        currentElement = decode(target);
        currentElement.scrollIntoViewIfNeeded();
    }

    @Override
    public void scroll(Enums.Scroll direction) {
        if (!execute) return;
        throwOnNull(direction, "scroll direction");
        log("scroll()", direction.name());
        String js = switch (direction) {
            case UP -> "window.scrollBy(0, -window.innerHeight)";
            case DOWN -> "window.scrollBy(0, window.innerHeight)";
            case HOME -> "window.scrollTo(0, 0)";
            case END -> "window.scrollTo(0, document.body.scrollHeight)";
        };
        page.evaluate(js);
    }

    @Override
    public void scroll(Target container, Enums.Scroll direction) {
        if (!execute) return;
        throwOnNull(container, "scroll container Target");
        throwOnNull(direction, "scroll direction");
        log("scroll()", container.getType().name() + " " + container.getValue(),direction.name());
        Locator c = decode(container);
        String js = switch (direction) {
            case UP -> "el => el.scrollBy(0, -el.clientHeight)";
            case DOWN -> "el => el.scrollBy(0, el.clientHeight)";
            case HOME -> "el => el.scrollTo(0, 0)";
            case END -> "el => el.scrollTo(0, el.scrollHeight)";
        };
        c.evaluate(js);
    }

    @Override
    public void scrollUntil(Target container, Target visible) {
        if (!execute) return;
        throwOnNull(container, "scrollUntil container Target");
        throwOnNull(visible, "scrollUntil visible Target");
        log("scrollUntil()", "container=" + container.getValue() + " visible=" + visible.getValue());
        Locator c = decode(container);
        Locator v = buildSingleLocator(visible);
        final int maxScrolls = 10;  // Arbitrary limit to prevent infinite scrolling
        for (int i = 0; i < maxScrolls; i++) {
            if (v.count() > 0 && v.first().isVisible()) {
                v.first().scrollIntoViewIfNeeded();
                return;
            }
            c.evaluate("el => el.scrollBy(0, el.clientHeight)");
        }
        throw new CPOException("scrollUntil(): '" + describe(visible)
                + "' not visible after " + maxScrolls + " scrolls of '" + describe(container) + "'");
    }

    // ── FRAME & DEFRAME ──────

    @Override
    public void frame(Target target) {
        if (!execute) return;
        throwOnNull(target, "frame Target");
        log("frame()", target.getType().name() + " ", target.getValue());
        Locator iframeLocator = buildSingleLocator(target);
        iframeLocator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.ATTACHED)
                .setTimeout(locatorTimeout));
        frameLocator = iframeLocator.contentFrame();
        origin();
    }

    @Override
    public void frame(int index) {
        if (!execute) return;
        throwOnMinus(index);
        log("frame()", "Index: ", String.valueOf(index));
        frameLocator = page.frameLocator("iframe >> nth=" + index);
        origin();
    }

    @Override
    public void frame(Enums.Index index) {
        if (!execute) return;
        throwOnNull(index, "frame Index");
        log("frame()", "Index: ", index.ordinal()+"");
        frame(index.ordinal());
    }

    @Override
    public void frame() {
        if (!execute) return;
        log("frame()", "First iframe");
        frameLocator = page.frameLocator("iframe >> nth=0");
        origin();
    }

    @Override
    public void frame(String value) {
        if (!execute) return;
        log("frame()", "ID or Name",value);
        frameLocator = page.frameLocator(value);
        origin();
    }

    @Override
    public void deframe() {
        if (!execute) return;
        log("deframe()");
        frameLocator = null;
        origin();
    }

    @Override
    public void hover() {
        if (!execute) return;
        log("hover()");
        currentElement.hover();
    }

    @Override
    public void hover(Target target) {
        if (!execute) return;
        throwOnNull(target, "Target");
        log("hover()\t", target.getType().name() + " ", target.getValue());
        focus(target);
        currentElement.hover();
    }


    @Override
    public void depart(Target target) {
        if (!execute) return;
        throwOnNull(target, "Target");
        log("depart()", target.getType().name() + " ", target.getValue());
        Locator l = buildLocator(target);
        int matches;
        try {
            matches = l.count();
        } catch (PlaywrightException e) {
            matches = 1;
        }
        if (matches > 1) {
            logger.warn("depart(): '" + describe(target) + "' matched " + matches
                    + " elements; waiting for all to be hidden.");
            for (Locator one : l.all()) {
                one.waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.HIDDEN)
                        .setTimeout(locatorTimeout));
            }
            return;
        }
        l.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.HIDDEN)
                .setTimeout(locatorTimeout));
    }

    @Override
    public void depart(Target target, Enums.Targets targets) {
        if (!execute) return;
        throwOnNull(target, "Target");
        if (targets == null) {
            throw new IllegalArgumentException("Target detection criteria is null: should be SINGLE or MULTIPLE");
        }
        log("depart(), Mode: ", targets.name() + " Target: " + target.getType().name() + " ", target.getValue());
        switch (targets) {
            case SINGLE -> depart(target);
            case MULTIPLE -> {
                List<ElementHandle> snapshot = buildLocator(target).elementHandles();
                if (snapshot.isEmpty()) {
                    log("depart(MULTIPLE) ", "No matching elements present — already departed");
                    return;
                }
                log("depart(MULTIPLE), count: ", String.valueOf(snapshot.size()));
                for (ElementHandle handle : snapshot) {
                    handle.waitForElementState(ElementState.HIDDEN,
                            new ElementHandle.WaitForElementStateOptions().setTimeout(locatorTimeout));
                }
            }
        }
    }

    @Override
    public void depart(List<Target> targets) {
        if (!execute) return;
        throwOnNullOrEmpty(targets, "Target list");
        log("depart(List), size: ", String.valueOf(targets.size()));
        for (Target t : targets) {
            depart(t);
        }
    }

    @Override
    public void depart(Target... targets) {
        if (!execute) return;
        throwOnNullOrEmpty(targets, "Targets varargs");
        log("depart(varargs), count: ", String.valueOf(targets.length));
        for (Target t : targets) {
            depart(t);
        }
    }

    @Override
    public void trigger(Enums.Event event) {
        if (!execute) return;
        throwOnNull(event, "Event");
        log("trigger()", event.name());
        currentElement.dispatchEvent(event.getValue());
    }

    @Override
    public void javascript(String script) {
        if (!execute) return;
        throwOnNull(script, "Script");
        log("javascript()", script);
        page.evaluate(script);
    }

    protected void compose(String content) {
        if (!execute) return;
        throwOnNull(content, "Content");
        log("compose()", isPasswordField() ? "*".repeat(content.length()) : content);
        if (isPasswordField()) {
            fillPasswordSecurely(content);
        } else {
            currentElement.fill(content);
        }
    }

    /**
     * Clears the contents of the currently focused {@code <input>} or
     * {@code <textarea>} element. Makes no assumption about the element's
     * {@code type} attribute (text, textarea, or unset).
     *
     * <p>Throws {@link IllegalStateException} (with a diagnostic log entry)
     * when invoked against an element that cannot be cleared:</p>
     * <ul>
     *   <li>Element is read-only</li>
     *   <li>Element is disabled</li>
     *   <li>Element is not interactable / writable (e.g. a {@code <label>})</li>
     * </ul>
     *
     * @throws IllegalStateException if the focused element is not editable
     */
    @Override
    public void clear() {
        if (!execute) return;
        log("clear()");
        if (currentElement == null) {
            log("clear()", "failed — no element in focus");
            throw new IllegalStateException("clear() failed: no element in focus");
        }
        try {
            if (!currentElement.isEditable()) {
                log("clear()", "failed — element is not editable");
                throw new IllegalStateException("clear() failed: element is not editable (read-only, disabled, or non-input)");
            }
            currentElement.fill("");
        } catch (PlaywrightException e) {
            String reason = e.getMessage() == null ? "unknown" : e.getMessage().split("\n")[0];
            log("clear()", "failed — " + reason);
            throw new IllegalStateException("clear() failed: " + reason, e);
        }
    }

    @Override
    public String get() {
        return get(Enums.ElementTrait.TEXT);
    }

    @Override
    public String get(Enums.ElementTrait elementTrait) {
        if (!execute) return "";
        throwOnNull(elementTrait, "ElementTrait");
        log("get()\t", elementTrait.name());
        String value =  getTraitValue(elementTrait);
        log("get()\t","Returns:", value);
        return value;
    }


    public void elementPrint() {
        if (!execute) return;
        if (currentElement == null) {
            log("elementPrint(): no element in focus");
            return;
        }
        log(">>>>> Current Element <<<<<<");
        printElement(currentElement);
    }

    public void collectionPrint() {
        if (!execute) return;
        log(">>>>> Current Collection <<<<<<");
        if (collection == null || collection.isEmpty()) {
            log("Collection is empty");
            return;
        }
        log("Size : " + collection.size());
        for (int i = 0; i < collection.size(); i++) {
            log(">>>>> Element " + (i + 1) + " <<<<<<");
            printElement(collection.get(i));
        }
    }

    @Override
    public String getUrl() {
        log("getUrl()");
        return page.url();
    }

    public void clickable() {
        if (this.execute) {
            log("clickable()");
            try {
                assertThat(currentElement).isEnabled();
            } catch (PlaywrightException | AssertionError e) {
                throw new IllegalStateException("The element " + currentElement + " is not clickable", e);
            }
        }
    }

    @Override
    public void editable() {
        if (this.execute) {
            log("editable()");
            try {
                assertThat(currentElement).isEditable();
            } catch (PlaywrightException | AssertionError e) {
                throw new IllegalStateException("The element " + currentElement + " is not editable", e);
            }
        }
    }

    @Override
    public void enabled() {
        if (this.execute) {
            log("enabled()");
            try {
                assertThat(currentElement).isEnabled();
            } catch (PlaywrightException | AssertionError e) {
                throw new IllegalStateException("The element " + currentElement + " is not enabled", e);
            }
        }
    }

    @Override
    public void disabled() {
        if (this.execute) {
            log("disabled()");
            try {
                assertThat(currentElement).isDisabled();
            } catch (PlaywrightException | AssertionError e) {
                throw new IllegalStateException("The element " + currentElement + " is not disabled", e);
            }
        }
    }

    @Override
    public void visible() {
        if (this.execute) {
            log("visible()");
            try {
                assertThat(currentElement).isVisible();
            } catch (PlaywrightException | AssertionError e) {
                throw new IllegalStateException("The element " + currentElement + " is not visible", e);

            }
        }
    }

    @Override
    public void hidden() {
        if (this.execute) {
            log("hidden()");
            try {
                assertThat(currentElement).isHidden();
            } catch (PlaywrightException | AssertionError e) {
                throw new IllegalStateException("The element " + currentElement + " is not hidden", e);
            }
        }
    }

    @Override
    public void unclickable() {
        if (this.execute) {
            log("unclickable()");
            try {
                assertThat(currentElement).isDisabled();
            } catch (PlaywrightException | AssertionError e) {
                throw new IllegalStateException("The element " + currentElement + " is not unclickable", e);
            }
        }
    }

    @Override
    public void checked() {
        if (this.execute) {
            log("checked()");
            try {
                assertThat(currentElement).isChecked();
            } catch (PlaywrightException | AssertionError e) {
                throw new IllegalStateException("The element " + currentElement + " is not selected", e);
            }
        }
    }

    @Override
    public void unchecked() {
        if (this.execute) {
            log("unchecked()");
            try {
                assertThat(currentElement).not().isChecked();
            } catch (PlaywrightException | AssertionError e) {
                throw new IllegalStateException("The element " + currentElement + " was expected to be unselected", e);
            }
        }
    }

    @Override
    public void matches(String text) {
        if (!execute) return;
        throwOnNull(text, "Text");
        assertTrue(currentElement.textContent().equals(text));
    }

    @Override
    public void contains(String partialText) {
        if (!execute) return;
        throwOnNull(partialText, "Partial text");
        log("contains()", partialText);
        assertTrue(currentElement.textContent().contains(partialText));
    }

    @Override
    public void log(String... args) {
        logger.log(args);
    }
    @Override
    public void log(Enums.LogStatus logStatus) {
        logger.log(logStatus);
    }
    @Override
    public void log(Enums.LogStatus logStatus, String reason) {
        logger.log(logStatus, reason);
    }

    /////////////////////
    // SESSION STORAGE //
    /// //////////////////

    @Override
    public void store(String key, String value) {
        throwOnNullOrEmpty(key, "store. Key");
        throwOnNullOrEmpty(value, "store. Value");
        log("store()", "Key: " + key,"Value: " + value);
        sessionMap.put(key, value);
    }

    @Override
    public String retrieve(String key) {
        throwOnNullOrEmpty(key, "retrieve. Key");
        log("retrieve()", "Key: " + key);
        String value = sessionMap.get(key);
        if(isNullOrEmpty(value)) {
            logger.warn("retrieve. Key '" + key + "' not found in session storage");
        }
        return value;
    }

    @Override
    public File takeScreenshot(boolean fullPage) {
        if (page == null) {
            throw new IllegalStateException("Page is not initialized");
        }
        try {
            File file = File.createTempFile("screenshot-", ".png");
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(fullPage));
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(screenshot);
            }
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File takeScreenshot(Target target) {
        if (page == null) {
            throw new IllegalStateException("Page is not initialized");
        }
        throwOnNull(target, "takeScreenshot. Target");
        try {
            File file = File.createTempFile("screenshot-", ".png");
            byte[] screenshot = decode(target).screenshot();
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(screenshot);
            }
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void resetState() {
        log("resetState()");
        frameLocator = null;
        origin();
        collection = new ArrayList<>();
        execute = true;
        currentListIndex = 0;
        slowMotion = false;
        boxed = false;
        boxRoot = null;
    }


    @Override
    public <T extends sonnet.reference.playwright.Performable> void perform(T runner) {
        if (!execute) {
            return;
        }
        throwOnNull(runner, "Performable runner");
        log("Perform()",runner.getClass().getSimpleName(),runner.description());
        runner.run();
    }


    @Override
    public void quit() {
        log("quit()");
        try {
            if (!VMArgs.isCloseOnExit()) {
                // Browser stays open for inspection, but close the page so Playwright
                // flushes the video file to disk (otherwise it remains unwritten).
                if (page != null) {
                    try {
                        page.close();
                    } catch (PlaywrightException ignored) {
                    }
                }
                return;
            }

            if (context != null) context.close();
            if (browser != null) browser.close();
            if (playwright != null) playwright.close();
        } catch (PlaywrightException playwrightException) {
            System.out.println(playwrightException.getMessage());
        } finally {
            if (VMArgs.isCloseOnExit()) {
                page = null;
                context = null;
                browser = null;
                playwright = null;
                frameLocator = null;
                synchronized (CommonPage.class) {
                    instance = null;
                }
            }
        }
    }

    //-- ================= TRAVERSE ================= --//

    // 1. Default -> to next sibling
    @Override
    public void traverse() {
        if (!execute) return;
        log("traverse() ", "Following Sibling");
        selectFollowingSibling(1);
    }

    // 2. By index -> to nth sibling
    @Override
    public void traverse(int index) {
        if (!execute) return;
        throwOnZeroOrMinus(index, "Index");
        log("traverse() ", Integer.toString(index));
        selectFollowingSibling(index);
    }

    // 3. By enum -> to sibling by node enum
    @Override
    public void traverse(Enums.NodeEnum index) {
        if (!execute) return;
        throwOnNull(index, "NodeEnum");
        log("traverse() ",index.name());
        selectFollowingSibling(index.ordinal() + 1);
    }


    //-- ================= REVERSE ================= --//

    // 1. Default -> to previous sibling
    @Override
    public void reverse() {
        if (!execute) return;
        log("reverse() ", "Previous Sibling");
        selectPreviousSibling(1);
    }

    @Override
    // 2. By index -> to nth previous sibling
    public void reverse(int index) {
        if (!execute) return;
        throwOnZeroOrMinus(index, "Index");
        log("reverse() ", Integer.toString(index));
        selectPreviousSibling(index);
    }

    @Override
    // 3. By enum -> to previous sibling by node enum
    public void reverse(Enums.NodeEnum index) {
        if (!execute) return;
        throwOnNull(index, "NodeEnum");
        log("reverse() ",index.name());
        selectPreviousSibling(index.ordinal() + 1);
    }

    @Override
    public void window() {
        if (!execute) {
            return;
        }
        switchToPage(1);
        log("window() ", "Switching to new window");
    }

    @Override
    public void window(int index) {
        if (!execute) {
            return;
        }
        throwOnMinus(index);
        log("window()", Integer.toString(index));
        switchToPage(index);

    }

    @Override
    public void window(Enums.Window window) {
        if (!execute) {
            return;
        }
        throwOnMinus(window.getValue());
        log("window()", window.name());
        switchToPage(window.getValue());
    }

    @Override
    public void tab() {
        if (!execute) return;
        log("tab()", "Switching to new tab");
        switchToPage(1);

    }

    @Override
    public void tab(int index) {
        if (!execute) return;
        throwOnMinus(index);
        log("tab()", Integer.toString(index));
        switchToPage(index);
    }

    @Override
    public void tab(Enums.Window window) {

        throwOnMinus(window.getValue());
        log("tab()", window.name());
        switchToPage(window.getValue());

    }

    @Override
    public void close() {
        if (!execute) return;
        log("close()");
        List<Page> pages = context.pages();
        Page mainPage = pages.getFirst();
        if (pages.size() <= 1) {
            logger.warn("Warn: close() - only one window open, nothing to close.");
            return;
        }
        // A page that is still being opened (e.g. a click that triggers
        // window.open) may not appear in context.pages() until shortly after
        // the click returns. Loop until either only the main page remains or
        // the locator timeout elapses, so close() is robust against in-flight
        // tab/window creations regardless of which page currently has focus.
        long deadline = System.currentTimeMillis() + DEFAULT_ELEMENT_LOCATOR_TIMEOUT;
        while (System.currentTimeMillis() < deadline) {
            List<Page> extras = context.pages().stream().filter(p -> p != mainPage).toList();
            if (extras.isEmpty()) break;
            for (Page extra : extras) {
                int idx = context.pages().indexOf(extra);
                extra.close();
                log("Closed tab/window at index: " + idx);
            }
            // Yield briefly so any in-flight popups have a chance to materialise
            // before we re-check the page list. Use mainPage explicitly because
            // the framework's active 'page' reference may be one of the extras
            // we just closed (TargetClosedError otherwise).
            mainPage.waitForTimeout(100);
        }
        List<Page> remainingPages = context.pages();
        if (remainingPages.size() == 1 && remainingPages.contains(mainPage)) {
            switchToPage(remainingPages.indexOf(mainPage));
        } else {
            throw new CPOException("close(): expected only main window to remain, but found " + remainingPages.size() + " window(s)");
        }
    }

    // Page-scan single-file upload — see CommonPageInterface for the contract.
    @Override
    public void upload(String path) {
        if (!execute) return;
        throwOnNullOrEmpty(path, "path");
        log("upload()", "Path:",path);
        scanForFirstFileInput("upload()");
        performUpload("upload()", List.of(path));
    }

    // Page-scan multi-file upload — see CommonPageInterface for the contract.
    @Override
    public void upload(List<String> paths) {
        if (!execute) return;
        throwOnNullOrEmpty(paths, "paths");
        log("upload()", "Paths:" + paths);
        scanForFirstFileInput("upload(List)");
        performUpload("upload(List)", paths);
    }

    // Targeted single-file upload — see CommonPageInterface for the contract.
    @Override
    public void upload(Target target, String path) {
        if (!execute) return;
        throwOnNull(target, "Target");
        throwOnNullOrEmpty(path, "path");
        log("upload()", target.getType().name() + " " + target.getValue(), "Path: " + path);
        focus(target);
        performUpload("upload(Target)", List.of(path));
    }

    // Targeted multi-file upload — see CommonPageInterface for the contract.
    @Override
    public void upload(Target target, List<String> paths) {
        if (!execute) return;
        throwOnNull(target, "Target");
        throwOnNullOrEmpty(paths, "paths");
        log("upload()", target.getType().name() + " " + target.getValue(), "Paths: " + paths);
        focus(target);
        performUpload("upload(Target, List)", paths);
    }

    ////////////////////////////////////////////////////////////////////////////
    // PROTECTED METHODS.  CAN BE ACCESSED THROUGH THE PERFORMABLE CLASS ONLY //
    /// /////////////////////////////////////////////////////////////////////////

    protected Page getPage() {
        return page;
    }

    protected List<Page> getPages() {
        return getContext().pages();
    }

    protected BrowserContext getContext() {
        return context;
    }

    protected String getTitle() {
        return page.title();
    }

    protected List<String> getCollectionAsText() {
        if (collection == null || collection.isEmpty()) {
            return List.of();
        }
        List<String> textValues = new ArrayList<>();
        for (Locator locator : collection) {
            textValues.add(locator.textContent().trim());
        }
        return textValues;
    }

    //////////////////////////////
    // PRIVATE INTERNAL METHODS //
    //////////////////////////////

    ////////////////////
    // TARGET DECODER //
    ////////////////////

    private Locator decode(Target target, int timeout) {
        log("focus() ", target.getType().name() + " ", target.getValue());
        boolean mainPage = frameLocator == null ? true : false;
        Locator locator;
        locator = switch (target.getType()) {
            // The following single cases are CORE Playwright methods and, as such,
            // * Do not support conjunctions
            // * Playwright methods are called directly
            case LABEL ->
                    boxed ? boxRoot.getByLabel(target.getValue(), new Locator.GetByLabelOptions().setExact(true)) : mainPage
                            ? page.getByLabel(target.getValue(), new Page.GetByLabelOptions().setExact(true))
                            : frameLocator.getByLabel(target.getValue(), new FrameLocator.GetByLabelOptions().setExact(true));
            case ROLE_AND_LABEL ->
                    boxed ? boxRoot.getByRole(target.getAriaRole(), new Locator.GetByRoleOptions().setName(target.getValue()).setExact(true)) : mainPage
                            ? page.getByRole(target.getAriaRole(), new Page.GetByRoleOptions().setName(target.getValue()).setExact(true))
                            : frameLocator.getByRole(target.getAriaRole(), new FrameLocator.GetByRoleOptions().setName(target.getValue()).setExact(true));
            case ROLE -> boxed ? boxRoot.getByRole(target.getAriaRole()) : mainPage
                    ? page.getByRole(target.getAriaRole())
                    : frameLocator.getByRole(target.getAriaRole());
            case ALT, ATTRIBUTE, ATTRIBUTE_VALUE, ATTRIBUTE_VALUE_CONTAINS,
                 CHILDREN, CLASS, CLASSES, CSS, DATA, HREF,
                 HTML_ATTRIBUTE, HTML_ATTRIBUTE_VALUE, HTML_ATTRIBUTE_VALUE_CONTAINS,
                 HTML_TAG, HTML_TAG_CONTAINS_TEXT, HTML_TAG_WITH_TEXT, ID, LEAF,
                 LINK_TEXT, NAME, PARTIAL_ATTRIBUTE, PARTIAL_LINK_TEXT, PLACEHOLDER, ROLE_LITERAL,
                 ROOT, SIBLINGS, SPLICE, SRC, STYLE,
                 TAG, TAG_CONTAINS_TEXT, TAG_WITH_TEXT, TEST_ID, TEXT, TITLE, TYPE, VALUE, XPATH -> {
                if (target.isConjunction()) {
                    yield firstIfMany(locateElement(target.getSelector()).and(locateElement(target.getSecondaryTarget().getSelector())), "");
                } else {
                    yield locateElement(target.getSelector());
                }
            }
            default ->
                // LEGACY: Deprecated
                    locateElement(target.getSelector());
        };
        try {
            locator.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.ATTACHED)
                    .setTimeout(timeout));
        } catch (PlaywrightException e) {
            if (e.getMessage() == null || !e.getMessage().contains("strict mode violation")) throw e;
        }
        return firstIfMany(locator, "focus(): '" + target.getSelector() + "'");
    }

    /**
     * If the given locator matches more than one element, log a warning and return
     * {@code locator.first()}; otherwise return the locator unchanged.
     * <p>
     * Centralised guard against Playwright strict-mode violations for any operation
     * (focus, descend, conjunctions, etc.) where matching a single element is the
     * intended semantic but DOM duplicates can legitimately occur.
     *
     * @param locator the locator under inspection
     * @param context label used in the warn log line — typically the calling method and selector
     * @return {@code locator.first()} when {@link Locator#count()} > 1, otherwise {@code locator}
     */

    private Locator firstIfMany(Locator locator, String context) {
        int matches;
        try {
            matches = locator.count();
        } catch (PlaywrightException e) {
            return locator;
        }
        if (matches > 1) {
            log(context + " matched " + matches + " elements; using first.");
            return locator.first();
        }
        return locator;
    }


    private void scanForFirstFileInput(String operation) {
        origin();
        collect(type(Enums.Type.FILE));

        if (collection == null || collection.isEmpty()) {
            log(operation, "No file loaders found");
            throw new IllegalStateException(operation + " failed: no <input type='file'> elements found");
        }
        if (collection.size() > 1) {
            log(operation, "warning — " + collection.size()
                    + " <input type='file'> elements found, using first");
        }
        choose(0);
    }

    private void performUpload(String operation, List<String> paths) {
        String tag = String.valueOf(currentElement.evaluate("el => el.tagName.toLowerCase()"));
        String type = currentElement.getAttribute("type");
        if (!"input".equals(tag) || type == null || !"file".equalsIgnoreCase(type)) {
            log(operation, "failed — element is not <input type='file'> (tag=" + tag + ", type=" + type + ")");
            throw new IllegalStateException(
                    operation + " failed: element is not <input type='file'> (tag=" + tag + ", type=" + type + ")");
        }
        if (!currentElement.isEditable()) {
            log(operation, "failed — element is disabled / not editable");
            throw new IllegalStateException(operation + " failed: element is disabled / not editable");
        }
        Path[] filePaths = new Path[paths.size()];
        for (int i = 0; i < paths.size(); i++) {
            String path = paths.get(i);
            throwOnNullOrEmpty(path, "paths[" + i + "]");
            Path filePath;
            try {
                filePath = Path.of(path);
            } catch (InvalidPathException e) {
                log(operation, "failed — invalid path: " + path);
                throw new IllegalStateException(operation + " failed: invalid path: " + path, e);
            }
            if (!Files.exists(filePath)) {
                log(operation, "failed — file does not exist: " + path);
                throw new IllegalStateException(operation + " failed: file does not exist: " + path);
            }
            if (!Files.isRegularFile(filePath)) {
                log(operation, "failed — path is not a regular file: " + path);
                throw new IllegalStateException(operation + " failed: path is not a regular file: " + path);
            }
            if (!Files.isReadable(filePath)) {
                log(operation, "failed — file is not readable: " + path);
                throw new IllegalStateException(operation + " failed: file is not readable: " + path);
            }
            filePaths[i] = filePath;
        }
        try {
            currentElement.setInputFiles(filePaths);
        } catch (PlaywrightException e) {
            log(operation, "failed — unable to upload file(s): " + paths);
            throw new IllegalStateException(operation + " failed: unable to upload file(s): " + paths, e);
        }
        log(operation, "uploaded: " + paths);
    }

    private void selectPreviousSibling(int index) {
        try {
            siblingLocator = currentElement.locator("xpath=preceding-sibling::*[" + index + "]");
            if (siblingLocator.count() == 0) {
                throw new IllegalArgumentException("No preceding/previous sibling found at index " + index);
            }
            if (boxed) {
                boolean isOutsideBox = (boolean) siblingLocator.first().evaluate("(el, boxRoot) => !boxRoot.contains(el) && el !== boxRoot", boxRoot.elementHandle());
                if (isOutsideBox) {
                    throw new IllegalArgumentException("No preceding/previous sibling found at index in the box boundary " + index);
                }
            }
            log("reversed", "To : ", currentElement.evaluate("el => el.tagName").toString());
            currentElement = siblingLocator;
        } catch (PlaywrightException e) {
            throw new CPOException(e.getMessage());
        }
    }

    private void targetFinder(Target... targets) {
        boolean found = false;
        if (!execute) {
            return;
        }
        throwOnNullOrEmpty(targets, "TargetFinder targets");

        final int totalTimeoutSeconds = DEFAULT_ELEMENT_LOCATOR_TIMEOUT / 1000;
        final int perTargetSeconds = 1;
        final int maxPasses = Math.max(1, totalTimeoutSeconds / targets.length);

        exitLoop:
        for (int pass = 0; pass < maxPasses; pass++) {
            for (Target target : targets) {
                if (peek(target, perTargetSeconds)) {
                    log("TargetFinder : Target found: " + target);
                    found = true;
                    break exitLoop;
                }
            }
        }
        if (!found) {
            throw new CPOException("TargetFinder : Target not found from list :" + Arrays.toString(targets));
        }
    }

    private void switchToPage(int index) {
        throwOnMinus(index);

        List<Page> pages = context.pages();
        long windowTimeout = System.currentTimeMillis() + DEFAULT_ELEMENT_LOCATOR_TIMEOUT;
        while (pages.size() <= index && System.currentTimeMillis() < windowTimeout) {
            page.waitForTimeout(500);
            pages = context.pages();
        }
        if (index >= pages.size()) {
            throw new CPOException("Page index " + index + " not found. Open windows: " + pages.size());
        }
        page = pages.get(index);
        page.bringToFront();
        page.setDefaultTimeout(locatorTimeout);

        log("Switched to window [" + index + "]: " + page.url());
        // focus Back to Origin
        origin();
    }

    private void selectFollowingSibling(int index) {
        try {
            siblingLocator = currentElement.locator("xpath=following-sibling::*[" + index + "]");
            if (siblingLocator.count() == 0) {
                throw new IllegalArgumentException("No following sibling found at index " + index);
            }
            if (boxed) {
                boolean isOutsideBox = (boolean) siblingLocator.first().evaluate(
                        "(el, boxRoot) => !boxRoot.contains(el) && el !== boxRoot",
                        boxRoot.elementHandle());
                if (isOutsideBox) {
                    throw new IllegalArgumentException(
                            "No preceding/previous sibling found at index in the box boundary " + index);
                }
            }
            log("traversed", "To : ", currentElement.evaluate("el => el.tagName").toString());
            currentElement = siblingLocator;
        } catch (PlaywrightException e) {
            throw new CPOException(e.getMessage());
        }
    }

    private Locator decode(Target target) {
        throwOnNull(target, "Target");
        return decode(target, VMArgs.getDefaultTimeout());
    }

    private Locator locateElement(String selector) {
        if (boxed) {
            return boxRoot.locator(selector);
        }
        return frameLocator != null ? frameLocator.locator(selector) : page.locator(selector);
    }

    private void probeForElement(Target target, int waitTime) {
        if (!execute) return;
        throwOnNull(target, "Target");
        throwOnMinus(waitTime, "waitTime");
        // Default visible  and timeout of 5 seconds
        try {
            currentElement = decode(target, waitTime);
            currentElement.waitFor(new Locator.WaitForOptions().setTimeout(waitTime));
        } catch (TimeoutError exception) {
            execute = false;
        }
    }

    private void setCurrentElementFromCollectionByAttributeKeyPair(String key, String value) {
        if (collection == null || collection.isEmpty()) {
            log("Warn: collection is empty");
            return;
        }
        boolean found = false;
        for (Locator locator : collection) {
            String currentAttributeValue = locator.getAttribute(key);
            if (currentAttributeValue != null && currentAttributeValue.equals(value)) {
                currentElement = locator;
                log("Info: Found element with " + key + "='" + value + "'");
                found = true;
                break;
            }

        }
        if (!found) {
            log("Warn: No element found with " + key + "='" + value + "'");
        }
    }

    private void setCurrentElementFromCollectionByText(String text, boolean partial) {
        if (collection == null || collection.isEmpty()) {
            log("Warn: collection is empty");
            return;
        }
        boolean found = false;
        for (Locator locator : collection) {
            String rawContent = locator.textContent();
            if (rawContent != null) {
                String content = rawContent.trim();
                boolean matches = partial ? content.contains(text) : content.equals(text);
                if (matches) {
                    currentElement = locator;
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            log("Warn: No element found with text: " + text);
        }
    }

    private void setCurrentElementFromCollectionByTag(Enums.Tag tag) {
        if (collection == null || collection.isEmpty()) {
            log("Warn: collection is empty");
            return;
        }
        boolean found = false;
        for (Locator locator : collection) {
            String tagName = locator.evaluate("el => el.tagName.toLowerCase()").toString();
            if (tagName != null && tagName.equals(tag.name().toLowerCase())) {
                currentElement = locator;
                found = true;
                break;
            }
        }
        if (!found) {
            log("Warn: No element found with tag: '" + tag.toString().toLowerCase() + "'");
        }
    }

    private void ascendBy(int levels) {
        try {
            StringBuilder xpath = new StringBuilder("xpath=");
            for (int i = 0; i < levels; i++) {
                if (i > 0) xpath.append("/");
                xpath.append("..");
            }
            Locator ancestor = currentElement.locator(xpath.toString());
            if (ancestor.count() == 0) {
                throw new IllegalArgumentException(
                        "Cannot ascend " + levels + " level(s) — exceeds DOM depth");
            }
            if (boxed) {
                Locator boxedAncestor = ancestor.first();
                boolean outsideBox = (boolean) boxedAncestor.evaluate("(el, boxRoot) => !boxRoot.contains(el) && el !== boxRoot", boxRoot.elementHandle());
                if (outsideBox) {
                    throw new IllegalArgumentException(
                            "Cannot ascend " + levels + " level(s) — exceeds the box boundary");
                }
            }
            currentElement = ancestor;
            log("ascended", "Levels: " + levels);
        } catch (PlaywrightException e) {
            throw new IllegalArgumentException(
                    "Cannot ascend " + levels + " level(s): " + e.getMessage());
        }
    }

    private void cognateSearch(Locator current, Target required, int level) {
        Locator matches = decodeScoped(current, required);
        int matchCount = matches.count();

        if (matchCount > 0) {
            if (matchCount > 1) {
                log("cognate()", "Warn. Found multiple cognates at " + level +
                        " levels above origin. Selecting first");
            }
            currentElement = matches.first();
            // 4 levels above the origin is considered a reasonable limit for a cognate search.
            if(level > 4) {
                log("cognate()", "Warning: Found at level: " + level + " — Is this the intended element?");
            }
            else {
                log("cognate()", "Found at level: ", Integer.toString(level));
            }
        } else {
            if (current.count() == 0) {
                throw new CPOException("Element Not Found");
            }
            String tagName = (String) current.first().evaluate("e => e.tagName.toLowerCase()");
            if ("html".equals(tagName)) {
                throw new CPOException("Element Not Found");
            }
            if (boxed) {
                boolean currentIsBoxed = (boolean) current.first().evaluate("(el, boxRoot) => boxRoot.contains(el) && el === boxRoot", boxRoot.elementHandle());
                if (currentIsBoxed) {
                    throw new CPOException("— exceeds the box boundary");
                }
            }
            Locator parent = current.locator("xpath=..");
            cognateSearch(parent, required, level + 1);
        }
    }

    private Locator decodeScoped(Locator origin, Target target) {
        if (target.isConjunction()) {
            return firstIfMany(
                    scopedLocator(origin, target).and(scopedLocator(origin, target.getSecondaryTarget())),
                    "scoped(AND): '" + describe(target) + "' & '" + describe(target.getSecondaryTarget()) + "'");
        }
        return scopedLocator(origin, target);
    }

    /**
     * Human-readable description of a Target for log/exception messages —
     * falls back to type + value when the xpath selector is null (ROLE / LABEL).
     */
    private String describe(Target target) {
        String selector = target.getSelector();
        if (selector != null) return selector;
        String value = target.getValue();
        return target.getType().name() + (value == null ? "" : "[" + value + "]");
    }

    /**
     * Builds a {@link Locator} for the supplied target without forcing an
     * attached-state wait. Used by {@code depart()} which intentionally
     * tolerates an already-detached element.
     * <p>
     * Type-aware: ROLE / ROLE_AND_LABEL / LABEL targets are resolved via the
     * Playwright core methods (getByRole / getByLabel) because they have no
     * xpath/CSS selector set. All other target types resolve via the shared
     * {@link #locateElement(String)} path. Mirrors the routing performed by
     * {@link #decode(Target, int)} so that {@code depart()} does not fail with
     * "selector: expected string, got undefined" when handed a ROLE target.
     */
    private Locator buildLocator(Target target) {
        if (target.isConjunction()) {
            return buildSingleLocator(target)
                    .and(buildSingleLocator(target.getSecondaryTarget()));
        }
        return buildSingleLocator(target);
    }

    private Locator buildSingleLocator(Target target) {
        boolean mainPage = frameLocator == null;
        return switch (target.getType()) {
            case ROLE -> boxed ? boxRoot.getByRole(target.getAriaRole()) : mainPage
                    ? page.getByRole(target.getAriaRole())
                    : frameLocator.getByRole(target.getAriaRole());
            case ROLE_AND_LABEL -> boxed ? boxRoot.getByRole(target.getAriaRole(),
                    new Locator.GetByRoleOptions().setName(target.getValue()).setExact(true)) : mainPage
                    ? page.getByRole(target.getAriaRole(),
                    new Page.GetByRoleOptions().setName(target.getValue()).setExact(true))
                    : frameLocator.getByRole(target.getAriaRole(),
                    new FrameLocator.GetByRoleOptions().setName(target.getValue()).setExact(true));
            case LABEL ->
                    boxed ? boxRoot.getByLabel(target.getValue(), new Locator.GetByLabelOptions().setExact(true)) : mainPage
                            ? page.getByLabel(target.getValue(), new Page.GetByLabelOptions().setExact(true))
                            : frameLocator.getByLabel(target.getValue(), new FrameLocator.GetByLabelOptions().setExact(true));
            default -> locateElement(target.getSelector());
        };
    }

    /**
     * Scoped counterpart to {@link #buildSingleLocator(Target)} — resolves a
     * child locator underneath an existing origin {@link Locator} in a
     * type-aware manner so ROLE / ROLE_AND_LABEL / LABEL targets are honoured
     * inside {@code descend()}, {@code collect()}, {@code decodeScoped()}, etc.
     */
    private Locator scopedLocator(Locator origin, Target target) {
        return switch (target.getType()) {
            case ROLE -> origin.getByRole(target.getAriaRole());
            case ROLE_AND_LABEL -> origin.getByRole(target.getAriaRole(),
                    new Locator.GetByRoleOptions().setName(target.getValue()).setExact(true));
            case LABEL -> origin.getByLabel(target.getValue(),
                    new Locator.GetByLabelOptions().setExact(true));
            default -> origin.locator(target.getSelector());
        };
    }

    /**
     * Fills a password field via JavaScript so the real value never appears
     * in Playwright trace action metadata, DOM snapshots, or screenshots.
     * Dispatches 'input' and 'change' events to satisfy framework bindings.
     */
    private void fillPasswordSecurely(String password) {
        throwOnNull(password, "Password");
        currentElement.fill("");
        currentElement.evaluate("(el, pwd) => {"
                + "  const nativeSetter = Object.getOwnPropertyDescriptor("
                + "    window.HTMLInputElement.prototype, 'value').set;"
                + "  nativeSetter.call(el, pwd);"
                + "  el.dispatchEvent(new Event('input', { bubbles: true }));"
                + "  el.dispatchEvent(new Event('change', { bubbles: true }));"
                + "}", password);
    }

    private boolean isPasswordField() {
        String type = currentElement.getAttribute("type");
        return type != null && !type.isEmpty() && type.equals("password");
    }

    private String getTraitValue(Enums.ElementTrait elementTrait) {
        try {
            return switch (elementTrait) {
                case ALT, CLASS, CONTENT, HREF, ID, LANG,
                     NAME, PLACEHOLDER, STYLE, TITLE, TYPE ->
                        Objects.toString(currentElement.getAttribute(elementTrait.name().toLowerCase()), "");
                case TEXT -> Objects.toString(currentElement.textContent(), "");
                case TEXT_TRIMMED -> Objects.toString(currentElement.textContent(), "").trim();
                case DISPLAYED -> String.valueOf(currentElement.isVisible());
                case ENABLED -> String.valueOf(currentElement.isEnabled());
                case SELECTED -> String.valueOf(currentElement.isChecked());
                case REQUIRED -> currentElement.getAttribute("required") != null ? "true" : "";
                case TAG -> getTagName();
                case ATTRIBUTES -> getAllAttributes();
                case DIMX -> {
                    BoundingBox b = currentElement.boundingBox();
                    yield b != null ? String.valueOf(b.width) : "";
                }
                case DIMY -> {
                    BoundingBox b = currentElement.boundingBox();
                    yield b != null ? String.valueOf(b.height) : "";
                }
                case POSX -> {
                    BoundingBox b = currentElement.boundingBox();
                    yield b != null ? String.valueOf(b.x) : "";
                }
                case POSY -> {
                    BoundingBox b = currentElement.boundingBox();
                    yield b != null ? String.valueOf(b.y) : "";
                }
                case VALUE -> {
                    String tag = getTagName();
                    if (tag.equals("input") || tag.equals("textarea") || tag.equals("select")) {
                        // <select>, <input> and <textarea> all expose their current
                        // value via the IDL `.value` property — not the HTML `value`
                        // attribute (which doesn't exist on <select> at all).
                        yield Objects.toString(currentElement.inputValue(), "");
                    } else {
                        yield Objects.toString(currentElement.getAttribute("value"), "");
                    }
                }
            };
        } catch (PlaywrightException e) {
            throw new RuntimeException("Failed to get '" + elementTrait.name() + "' from element: " + e.getMessage(), e);
        }
    }

    private String getTagName() {
        return (String) currentElement.evaluate("el => el.tagName.toLowerCase()");
    }

    private String getAllAttributes() {
        Object result = currentElement.evaluate(
                "el => JSON.stringify(Array.from(el.attributes).reduce((o, a) => { o[a.name] = a.value; return o; }, {}))");
        return result != null ? result.toString() : "";
    }

    private void printTrait(String label, Enums.ElementTrait trait) {
        String value;
        try {
            value = getTraitValue(trait);
        } catch (RuntimeException e) {
            String message = e.getMessage();
            log(label + " : <error" + (message != null && !message.isEmpty() ? ": " + message : "") + ">");
            return;
        }
        if (value != null && !value.isEmpty()) {
            log(label + " : " + value);
        }
    }

    private void printElement(Locator element) {
        Locator previous = currentElement;
        try {
            currentElement = element;
            printTrait("Tag", Enums.ElementTrait.TAG);
            printTrait("ID", Enums.ElementTrait.ID);
            printTrait("Name", Enums.ElementTrait.NAME);
            printTrait("Type", Enums.ElementTrait.TYPE);
            printTrait("Value", Enums.ElementTrait.VALUE);
            printTrait("Class", Enums.ElementTrait.CLASS);
            printTrait("Text", Enums.ElementTrait.TEXT);
            BoundingBox box;
            try {
                box = element.boundingBox();
            } catch (PlaywrightException e) {
                box = null;
            }
            if (box != null) {
                log("Width  : " + box.width);
                log("Height : " + box.height);
                log("Pos X  : " + box.x);
                log("Pos Y  : " + box.y);
            }
        } finally {
            currentElement = previous;
        }
    }
    @Override
    public void select(Target T, String... Options) {
        if (!execute) {
            return;
        }
        throwOnNull(T, "target");
        throwOnNullOrEmpty(Options, "Options");
        log("select()", "Target: " + T.getType().name() + " " + T.getValue(), "Options: " + Arrays.toString(Options));
        focus(T);
        currentElement.selectOption(Options);
    }

    @Override
    public void select(String label, String... Options) {
        if (!execute) {
            return;
        }
        throwOnNull(label, "label");
        throwOnNullOrEmpty(Options, "Options");
        log("select()", "Label: " + label, "Options: " + Arrays.toString(Options));
        page.getByLabel(label, new Page.GetByLabelOptions().setExact(true)).selectOption(Options);

    }

    @Override
    public void select(Target T, int... indices) {
        if (!execute) {
            return;
        }
        throwOnNull(T, "target");
        for (int index : indices) {
            throwOnMinus(index, "indices");
        }
        log("select(Target, indices)", T.getType().name() + " " + T.getValue(),
                "indices: " + Arrays.toString(indices));
        focus(T);
        SelectOption[] options = new SelectOption[indices.length];
        for (int i = 0; i < indices.length; i++ ) {
            options[i] = new SelectOption().setIndex(indices[i]);
        }
        currentElement.selectOption(options);
    }

    @Override
    public void select(String label, Enums.Index... indices) {
        if (!execute) {
            return;
        }
        throwOnNull(label, "label");
        throwOnNullOrEmpty(indices, "indices");
        SelectOption[] Options = new SelectOption[indices.length];
        for (int i = 0; i < indices.length; i++) {
            Options[i] = new SelectOption().setIndex(indices[i].ordinal());
        }
        log("select()", "Label: " + label, "Options: " + Arrays.toString(Options));
        page.getByLabel(label, new Page.GetByLabelOptions().setExact(true)).selectOption(Options);

    }

    @Override
    public void select(Target target, Enums.Selector S, String... Options) {
        if (!execute) {
            return;
        }
        throwOnNull(target, "target");
        throwOnNull(S, "S");
        throwOnNullOrEmpty(Options, "Options");
        log("select()", "Target: " + target.getType().name() + " " + target.getValue(), "Selector: " + S.name(), "Options: " + Arrays.toString(Options));
        focus(target);
        SelectOption[] selectOptions = new SelectOption[Options.length];
        for (int i = 0; i < Options.length; i++) {
            selectOptions[i] = S == Enums.Selector.LABEL ?
                    new SelectOption().setLabel(Options[i]):
                    new SelectOption().setValue(Options[i]);
        }
       currentElement.selectOption(selectOptions);

    }

    @Override
    public void select(String label, Enums.Selector S, String... Options) {
        if (!execute) {
            return;
        }
        throwOnNull(label, "label");
        throwOnNull(S, "S");
        throwOnNullOrEmpty(Options, "Options");
        log("select()", "Label: " + label, "Selector: " + S.name(), "Options: " + Arrays.toString(Options));
        SelectOption[] selectOptions = new SelectOption[Options.length];
        for (int i = 0; i < Options.length; i++) {
            selectOptions[i] = S == Enums.Selector.LABEL ?
                    new SelectOption().setLabel(Options[i]):
                    new SelectOption().setValue(Options[i]);
        }
        page.getByLabel(label, new Page.GetByLabelOptions().setExact(true)).selectOption(selectOptions);
    }


}

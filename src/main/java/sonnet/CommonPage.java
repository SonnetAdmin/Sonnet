package sonnet;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.BoundingBox;
import sonnet.utils.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.Assert.assertEquals;
import static sonnet.utils.Checks.throwOnNull;
import static sonnet.utils.Checks.throwOnZeroOrMinus;
import static sonnet.utils.Time.pause;

public class CommonPage extends TargetFactory implements CommonPageInterface {

    private static final Map<String, String> sessionMap = new HashMap<>();
    static CommonPage commonPage = null;
    private final Logger logger = new Logger(); // This is the logger from sonnet.utils
    private final int DEFAULT_TIMEOUT = 30;
    private final int DEFAULT_PROBE_TIMEOUT = 5;
    private String result = "";
    private Page page;
    private Locator currentElement;
    private List<Locator> currentElements;
    private int currentListIndex;
    private Boolean execute = true;
    private Boolean boxed = false;

    public static CommonPage getInstance() {
        if (commonPage == null) {
            commonPage = new CommonPage();
        }
        return commonPage;
    }

    @Override
    public void ascend() {
        try {
            logger.log("ascend() ", "first ancestor");
            currentElement = currentElement.locator("xpath=./ancestor::*[1]");
            currentElement.waitFor(new Locator.WaitForOptions().setTimeout(1000));
        } catch (PlaywrightException pe) {
            throw new SonnetException("ascend to first ancestor failed");
        }
    }

    @Override
    public void ascend(int level) {
        try {
            logger.log("ascend() ", "ancestor ", ""+level);
            currentElement = currentElement.locator("xpath=./ancestor::*["+level+"]");
            currentElement.waitFor(new Locator.WaitForOptions().setTimeout(1000));
        } catch (PlaywrightException pe) {
            throw new SonnetException("ascend to ancestor "+level+" failed");
        }
    }

    @Override
    public void ascend(Enum.Index level) {
        try {
            int levelIndex = level.ordinal()+1;
            logger.log("ascend() ", "ancestor ", ""+level);
            currentElement = currentElement.locator("xpath=./ancestor::*["+levelIndex+"]");
            currentElement.waitFor(new Locator.WaitForOptions().setTimeout(1000));
        } catch (PlaywrightException pe) {
            throw new SonnetException("ascend to "+level+" ancestor failed");
        }
    }

    @Override
    public void ascend(Enum.HTMLTag tag) {
        logger.log("ascend() ", "first: ", ""+tag);
        try {
            currentElement = currentElement.locator("xpath=./ancestor::"+tag.name().toLowerCase()+"[1]");
            currentElement.waitFor(new Locator.WaitForOptions().setTimeout(1000));
        } catch (PlaywrightException pe){
            throw new SonnetException("ascend to "+tag+" ancestor failed");
        }
    }

    @Override
    public void ascend(Target target) {
        logger.log("ascend() ", "first: ", ""+target);
        String targetString = target.toString();
        int i = targetString.indexOf(':');
        String attribute = "@"+targetString.substring(0, i).toLowerCase();
        String value = targetString.substring(i+2);
        try {
            currentElement = currentElement.locator("xpath=./ancestor::*["+attribute+"='"+value+"'][1]");
            currentElement.waitFor(new Locator.WaitForOptions().setTimeout(1000));
        } catch (PlaywrightException pe){
            throw new SonnetException("ascend to "+target+" ancestor failed");
        }
    }

    //TODO Box methods need to be implemented. They are currently just placeholders.
    @Override
    public void box(Target target) {
        if (!execute) return;
        logger.log("box()", target.getType().toString(), target.getValue());
        boxed = true;
    }

    @Override
    public void box() {
        if (!execute) return;
        logger.log("box()");
        boxed = true;
    }

    @Override
    public void unbox() {
        if (!execute) return;
        logger.log("unbox()");
        boxed = false;
    }

    @Override
    public void click() {
        if (!execute) return;
        logger.log("click()");
        currentElement.click();
    }

    @Override
    public void choose(int index) {
        if (!execute) return;
        throwOnZeroOrMinus(index);
        logger.log("choose()", "Int ", String.valueOf(index - 1));
        currentElement = currentElements.get(index - 1);
    }

    @Override
    public void choose(Enum.Index index) {
        if (!execute) return;
        logger.log("choose()", "Index ", index.name());
        currentElement = currentElements.get(index.ordinal());
    }

    @Override
    public void choose(Enum.ListIndex index) {
        if (!execute) return;
        logger.log("choose()", "ListIndex ", index.name());
        switch (index) {
            case NOSE:
                currentElement = currentElements.getFirst();
                currentListIndex = 0;
                break;

            case TAIL:
                currentElement = currentElements.getLast();
                currentListIndex = currentElements.size() - 1;
                break;

            case NEXT:
                if (++currentListIndex == currentElements.size()) {
                    logger.log("choose()", "NEXT : ", "List pointer already at the end of collection");
                    currentElement = currentElements.getLast();
                    currentListIndex--;
                } else {
                    currentElement = currentElements.get(currentListIndex);
                }
                break;

            case PREVIOUS:
                if (--currentListIndex < 0) {
                    logger.log("choose()", "PREVIOUS : ", "List pointer already at the beginning of collection");
                    currentElement = currentElements.getFirst();
                    currentListIndex++;
                } else {
                    currentElement = currentElements.get(currentListIndex);
                }
                break;

            default:
                throw new IllegalArgumentException("Invalid list index: " + index);
        }
    }

    @Override
    public void choose(String name) {
        if (!execute) return;
        logger.log("choose()", "String ", name);
        for (Locator listElement : currentElements) {
            String elementText = listElement.textContent();
            if (elementText.contains(name)) {
                currentElement = listElement;
                break;
            }
        }

    }

    @Override
    public void cognate(Target destination) {
        if (!execute) return;
        logger.log("cognate()", destination.getType().toString(), destination.getValue());
        boolean found = false;
        while (!found) {
            currentElements = currentElement.locator(destination.getSelector()).all();
            if (currentElements.isEmpty()) {
                if (currentElement.evaluate("el => el.tagName").toString().equalsIgnoreCase("html")) {
                    throw new IllegalArgumentException("Failed to cognate to destination: " + destination);
                }
                currentElement = currentElement.locator("..");
            } else {
                found = true;
                currentElement = currentElements.getFirst();
            }
        }
    }

    @Override
    public void cognate(Target origin, Target destination) {
        if (!execute) return;
        logger.log("cognate()", origin.getType().toString(), origin.getValue(), destination.getType().toString(), destination.getValue());
        decode(origin, true);
        cognate(destination);
    }

    @Override
    public void assemble(Target target) {
        if (!execute) return;
        logger.log("assemble()", target.getType().toString(), target.getValue());
        final int ITERATIONS = 5;
        final int STABLE_COUNT = 3;
        int stableCount = 0;
        int count = 0;
        int currentSize = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            currentElements = currentElement.locator(target.getSelector()).all();
            count = currentElements.size();
            if (currentSize == count) {
                if (++stableCount == STABLE_COUNT) break;
            } else {
                stableCount = 0;
                currentSize = count;
            }
            pause(1);
        }
        logger.log("Found() ", currentSize + " elements for target: ", target.toString());
        if (stableCount < STABLE_COUNT)
            throw new IllegalStateException("Failed to assemble elements after " + ITERATIONS + " iterations");

    }

    @Override
    public void compose(Enum.Action action) {
        if (!execute) return;
        logger.log("compose()", "Action ", action.toString());
        currentElement.press("Enter");

    }

    @Override
    public void compose(String text) {
        if (!execute) return;
        if (currentElement.getByRole(AriaRole.COMBOBOX) != null) {
            logger.log("compose()", "ComboBox", text);      //TODO LET JOHN CHECK LATER
        } else if (currentElement.getAttribute("type").equals("password")) {
            logger.log("compose()", "Password", text.replaceAll(".", "*"));
        } else {
            logger.log("compose()", "Text", text);
        }
        currentElement.fill(text);

    }

    @Override
    public void descend() {
        try {
            logger.log("descend() ", "first descendant");
            currentElement = currentElement.locator("xpath=./descendant::*[1]");
            currentElement.waitFor(new Locator.WaitForOptions().setTimeout(1000));
        } catch (PlaywrightException pe) {
            throw new SonnetException("descend to first child failed");
        }
    }

    @Override
    public void descend(int level) {
        try {
            logger.log("descend() ", "descendant ", ""+level);
            currentElement = currentElement.locator("xpath=./descendant::*["+level+"]");
            currentElement.waitFor(new Locator.WaitForOptions().setTimeout(1000));
        } catch (PlaywrightException pe) {
            throw new SonnetException("descend to descendant "+level+" failed");
        }
    }

    @Override
    public void descend(Enum.Index level) {
        int levelIndex = level.ordinal()+1;
        try {
            logger.log("descend() ", "descendant ", ""+level);
            currentElement = currentElement.locator("xpath=./descendant::*["+levelIndex+"]");
            currentElement.waitFor(new Locator.WaitForOptions().setTimeout(1000));
        } catch (PlaywrightException pe) {
            throw new SonnetException("descend to descendant "+level+" failed");
        }
    }

    @Override
    public void descend(Enum.HTMLTag tag) {
        try {
            logger.log("descend() ", "first: ", ""+tag);
            currentElement = currentElement.locator(tag.name().toLowerCase()).first();
            currentElement.waitFor(new Locator.WaitForOptions().setTimeout(1000));
        } catch (PlaywrightException pe) {
            throw new SonnetException("descend to first "+tag+" descendant failed");
        }
    }

    @Override
    public void descend(Target target) {
        if (!execute) return;
        logger.log("descend()", target.getType().toString(), target.getValue());
        try{
            decode(target, false);
        } catch (NoSuchElementException nsee) {
            throw new SonnetException("descend to first "+target+" descendant failed");
        } catch (PlaywrightException pe){

        }
    }

    @Override
    public void check() {
        currentElement.check();
        logger.log("check()");
    }

    @Override
    public void uncheck() {
        currentElement.uncheck();
        logger.log("uncheck()");
    }

    @Override
    public void checked() {
        if (!execute) return;
        try {
            assertThat(currentElement).isChecked();
            logger.log("checked()");
        } catch (PlaywrightException | AssertionError e) {
            throw new IllegalStateException("The element " + currentElement + " is not selected", e);
        }
    }

    @Override
    public void unchecked() {
        if (!execute) return;
        try {
            assertThat(currentElement).not().isChecked();
            logger.log("unchecked()");
        } catch (PlaywrightException | AssertionError e) {
            throw new IllegalStateException("The element " + currentElement + " was expected to be unselected", e);
        }
    }

    @Override
    public void end() {
        logger.log("end()");
        execute = true;

    }

    @Override
    public void focus(Target target) {
        if (!execute) return;
        logger.log("focus()", target.getType().toString(), target.getValue());
        decode(target, true);

    }

    @Override
    public String get(Enum.Trait trait) {
        try {
            return result = switch (trait) {
                case ALT, CLASS, CONTENT, HREF, ID, LANG,
                     NAME, PLACEHOLDER, STYLE, TYPE -> currentElement.getAttribute(trait.name().toLowerCase());
//              case ATTRIBUTES -> getAllAttributes();
                case DISPLAYED -> String.valueOf(currentElement.isVisible());
                case ENABLED -> String.valueOf(currentElement.isEnabled());
                case HEIGHT -> String.valueOf(currentElement.boundingBox().height);
                case REQUIRED -> currentElement.getAttribute("required") != null ? "true" : "";
                case SELECTED -> String.valueOf(currentElement.isChecked());
                case TAG -> currentElement.evaluate("el => el.tagName").toString();
                case TEXT -> currentElement.textContent();
                case TEXT_TRIMMED -> currentElement.textContent().trim();
                case TITLE -> page.title();
                case URL -> page.url();
                case VALUE -> currentElement.getAttribute("value");
                case VISIBLE -> String.valueOf(currentElement.isVisible());
                case WIDTH -> String.valueOf(currentElement.boundingBox().width);
                case XPOS -> String.valueOf(currentElement.boundingBox().x);
                case YPOS -> String.valueOf(currentElement.boundingBox().y);
            };
        } finally {
            logger.log("get()", trait.name()+"", "result: ", result);
        }


    }

    @Override
    public void go(String url) {
        logger.log("go()", url);
        page.navigate(url);
        if (url.contains("http")){
            this.page.waitForURL(url);
            logger.log("Loaded()", url);
        }
    }

    @Override
    public void go(Enum.Direction direction) {
        throwOnNull(direction, "Direction");
        logger.log("go()", direction.name());
        switch (direction){
            case BACK:
                if(page.goBack() == null){
                    logger.log("go(BACK)", "no previous page found");
                }
                break;
            case FORWARD:
                if(page.goForward() == null){
                    logger.log("go(FORWARD)", "no next page found");
                }
                break;
            case REFRESH:
                logger.log("refresh()", "refreshing page");
                page.reload();
        }
    }

    @Override
    public void open() {
        logger.log("open()", "Chrome");
        try {
            Playwright playwright = Playwright.create();
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            page = browser.newPage();
        } catch (PlaywrightException pe) {
            logger.log("Playwright Exception" + pe.getMessage());

        }

    }

    @Override
    public void probe(Target target) {
        logger.log("probe()", target.getType().toString(), target.getValue());
        setTimeout(DEFAULT_PROBE_TIMEOUT);
        if (!decode(target, true)) execute = false;
        setTimeout(DEFAULT_TIMEOUT);

    }

    @Override
    public void reverse(int index) {
        selectPreviousSibling(index);
    }

    @Override
    public void reverse(Enum.NodeEnum index) {
        selectPreviousSibling(index.getValue());
    }

    @Override
    public int assembled() {
        logger.log("assembled()");
        return currentElements.size();
    }

    // TODO review
    @Override
    public void submit() {
        if (!execute) return;
        logger.log("submit()");

    }

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
        logger.log("scroll()", target.getType().name(), target.getValue());
        focus(target);
        currentElement.scrollIntoViewIfNeeded();
    }


    @Override
    public void scroll(Enum.Scroll direction) {
        if (!execute) return;
        throwOnNull(direction, "scroll direction");
        logger.log("scroll()", direction.name());
        switch (direction) {
            case UP -> page.mouse().wheel(0, -1000);
            case DOWN -> page.mouse().wheel(0, 1000);
            case HOME -> page.mouse().wheel(0, -1000000000);
            case END -> page.mouse().wheel(0, 1000000000);
        };
    }

    @Override
    public void traverse(int index) {
        selectFollowingSibling(index);
    }

    @Override
    public void traverse(Enum.NodeEnum index) {
        selectFollowingSibling(index.getValue());
    }

    @Override
    public void log(String message) {
        logger.log(message);
    }

    @Override
    public void leaf() {
        if(!execute) return;
        logger.log("leaf()");
        while (true){
            try{
                currentElement = currentElement.locator("css=:first-child");
            } catch (NoSuchElementException nsee){
                break;
            } catch (PlaywrightException pe){
                logger.log("leaf() failed to find child");
            }
        }
    }

    //////////////////////////
    /// DIAGNOSTIC METHODS ///
    //////////////////////////

    public void printElements() {
        logger.log("Print Elements");
        for (Locator listElement : currentElements) {
            logger.log(listElement.textContent());
        }

    }

    public void printElement() {
        logger.log("Print Element");
        BoundingBox box = currentElement.boundingBox();
        logger.log("Tag: " + currentElement.evaluate("el => el.tagName").toString());
        logger.log("Text: " + currentElement.textContent());
        logger.log("Enabled: " + currentElement.isEnabled());
        logger.log("Visible: " + currentElement.isVisible());
//        logger.log("Location:  " + box.x + " " + box.y);
//        logger.log("Dimensions: " + box.width + " " + box.height);

    }

    @Override
    public <T extends Performable> void perform(T runner) {
        if (!execute) {
            return;
        }
        throwOnNull(runner, "Performable runner");
        logger.log("Perform()", runner.getDescription());
        runner.run();
    }

    @Override
    public void store(String key, String value) {
        sessionMap.put(key, value);
    }

    @Override
    public String retrieve(String key) {
        return sessionMap.get(key);
    }

    protected Page getPage() {
        return page;
    }

    private boolean decode(Target target, boolean fromRoot) {
        boolean found = true;
        try {
            switch (target.getType()) {
                case PLACEHOLDER:
                    currentElement = fromRoot ? page.getByPlaceholder(target.getSelector()) : currentElement.getByPlaceholder(target.getSelector());
                    break;

                case LABEL:
                    currentElement = fromRoot ? page.getByLabel(target.getValue(), new Page.GetByLabelOptions().setExact(true)) : currentElement.getByLabel(target.getValue(), new Locator.GetByLabelOptions().setExact(true));
                    break;

                case ALT:
                    currentElement = fromRoot ? page.getByAltText(target.getSelector()) : currentElement.getByAltText(target.getSelector());
                    break;

                case TITLE:
                    currentElement = fromRoot ? page.getByTitle(target.getSelector()) : currentElement.getByTitle(target.getSelector());
                    break;

                case ROLE:
                    currentElement = fromRoot ? page.getByRole(target.getAriaRole()) : currentElement.getByRole(target.getAriaRole());
                    break;

                case ROLE_AND_LABEL:
                    currentElement = fromRoot ? page.getByRole(target.getAriaRole(), new Page.GetByRoleOptions().setName(target.getValue())) : currentElement.getByRole(target.getAriaRole(), new Locator.GetByRoleOptions().setName(target.getValue()));
                    break;

                case ATTRIBUTE, ATTRIBUTE_VALUE, CLASS, CLASSES, CSS, DATA, HREF, HTML_ATTRIBUTE_VALUE, ID, LEAF,
                     LINK_TEXT, NAME,
                     PARTIAL_LINK_TEXT, PARTIAL_TEXT, ROLE_LITERAL, ROOT, SPLICE, SRC, STYLE, TAG, TAG_CONTAINS_TEXT,
                     TAG_WITH_TEXT, TEST_ID, TEXT, TYPE, VALUE, XPATH:
                    List<Locator> elements = fromRoot ? page.locator(target.getSelector()).all() : currentElement.locator(target.getSelector()).all();
                    currentElement = elements.getFirst();
                    break;

                default: {
                    logger.log("decode() failed for target");
                }
            }
        } catch (PlaywrightException pe) {
            logger.log("decode() failed for target: " + target);
            found = false;
        }
        return found;
    }

    private void selectFollowingSibling(int index) {
        try {
            logger.log("traverse() ", "Following Sibling - Index: " +index);
            currentElement = currentElement.locator("xpath=./following-sibling::*[" + index + "]");
        } catch (PlaywrightException pe) {
            logger.log("traverse to following sibling failed");
        }
    }

    private void selectPreviousSibling(int index) {
        try {
            logger.log("reverse() ", "Previous Sibling - Index: " +index);
            currentElement = currentElement.locator("xpath=./preceding-sibling::*[" + index + "]");
        } catch (PlaywrightException pe) {
            logger.log("reverse to preceding sibling failed");
        }
    }

    private void setTimeout(int seconds) {
        page.setDefaultTimeout(seconds * 1000);
    }
}
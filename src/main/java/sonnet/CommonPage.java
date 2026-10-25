package sonnet;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.BoundingBox;
import sonnet.utils.Logger;

import java.util.List;

import static sonnet.utils.Checks.throwOnZeroOrMinus;
import static sonnet.utils.Time.pause;

public class CommonPage extends TargetFactory implements CommonPageInterface {

    static CommonPage commonPage = null;
    private final Logger logger = new Logger(); // This is the logger from sonnet.utils

    private final int DEFAULT_TIMEOUT = 30;
    private final int DEFAULT_PROBE_TIMEOUT = 5;

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
        logger.log("box()\t");
        boxed = true;
    }

    @Override
    public void unbox() {
        if (!execute) return;
        logger.log("unbox()\t");
        boxed = false;
    }

    @Override
    public void click() {
        if (!execute) return;
        logger.log("click()\t");
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
        decode(origin);
        cognate(destination);
    }

    @Override
    public void collect(Target target) {
        if (!execute) return;
        logger.log("collect()", target.getType().toString(), target.getValue());
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
        logger.log("collect()", "Found: " + currentSize + " elements for target: ", target.toString());
        if (stableCount < STABLE_COUNT)
            throw new IllegalStateException("Failed to collect elements after " + ITERATIONS + " iterations");

    }

    @Override
    public void compose(Action action) {
        if (!execute) return;
        logger.log("compose()", "Action ", action.toString());
        currentElement.fill(action.toString());

    }

    @Override
    public void compose(String text) {
        if (!execute) return;
        if (currentElement.getAttribute("type").equals("password")) {
            logger.log("compose()", "Password", text.replaceAll(".", "*"));
        } else {
            logger.log("compose()", "Text", text);
        }
        currentElement.fill(text);

    }

    @Override
    public void end() {
        logger.log("end()\t");
        execute = true;

    }

    @Override
    public void focus(Target target) {
        if (!execute) return;
        logger.log("focus()\t", target.getType().toString(), target.getValue());
        decode(target);

    }

    @Override
    public String get(Trait trait) {
        logger.log("get()\t", trait.name());
        return switch (trait) {
            case ALT, CLASS, CONTENT, HREF, ID, LANG,
                 NAME, PLACEHOLDER, STYLE, TITLE, TYPE -> currentElement.getAttribute(trait.name().toLowerCase());
//            case ATTRIBUTES -> getAllAttributes();
            case DISPLAYED -> String.valueOf(currentElement.isVisible());
            case ENABLED -> String.valueOf(currentElement.isEnabled());
            case HEIGHT -> String.valueOf(currentElement.boundingBox().height);
            case REQUIRED -> currentElement.getAttribute("required") != null ? "true" : "";
            case SELECTED -> String.valueOf(currentElement.isChecked());
            case TAG -> currentElement.evaluate("el => el.tagName").toString();
            case TEXT -> currentElement.textContent();
            case TEXT_TRIMMED -> currentElement.textContent().trim();
            case VALUE -> currentElement.getAttribute("value");
            case VISIBLE -> String.valueOf(currentElement.isVisible());
            case WIDTH -> String.valueOf(currentElement.boundingBox().width);
            case XPOS -> String.valueOf(currentElement.boundingBox().x);
            case YPOS -> String.valueOf(currentElement.boundingBox().y);
        };
    }


    @Override
    public void go(String url) {
        logger.log("go()\t", url);
        page.navigate(url);

    }

    @Override
    public void open() {
        logger.log("open()\t", "Chrome");
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
        logger.log("probe()\t", target.getType().toString(), target.getValue());
        setTimeout(DEFAULT_PROBE_TIMEOUT);
        if (!decode(target)) execute = false;
        setTimeout(DEFAULT_TIMEOUT);

    }

    @Override
    public int size() {
        logger.log("size()\t");
        return currentElements.size();
    }

    // TODO review
    @Override
    public void submit() {
        if (!execute) return;
        logger.log("submit()\t");

    }

    @Override
    public void log(String message) {
        logger.log(message);
    }

    /// ///////////////////////
    /// DIAGNOSTIC METHODS ///
    /// ///////////////////////

    public void printElements() {
        logger.log("print elements");
        for (Locator listElement : currentElements) {
            logger.log(listElement.textContent());
        }

    }

    public void printElement() {
        logger.log("print element");
        BoundingBox box = currentElement.boundingBox();
        logger.log("Tag: \t\t" + currentElement.evaluate("el => el.tagName").toString());
        logger.log("Text: \t\t" + currentElement.textContent());
        logger.log("Enabled: \t" + currentElement.isEnabled());
        logger.log("Visible: \t" + currentElement.isVisible());
//        logger.log("Location: \t " + box.x + " " + box.y);
//        logger.log("Dimensions: \t" + box.width + " " + box.height);

    }

    private boolean decode(Target target) {
        boolean found = true;
        try {
            switch (target.getType()) {
                case PLACEHOLDER:
                    currentElement = page.getByPlaceholder(target.getSelector());
                    break;

                case LABEL:
                    currentElement = page.getByLabel(target.getValue(), new Page.GetByLabelOptions().setExact(true));
                    break;

                case ALT:
                    currentElement = page.getByAltText(target.getSelector());
                    break;

                case TITLE:
                    currentElement = page.getByTitle(target.getSelector());
                    break;

                case ROLE:
                    currentElement = page.getByRole(target.getAriaRole());
                    break;

                case ROLE_AND_LABEL:
                    currentElement = page.getByRole(target.getAriaRole(), new Page.GetByRoleOptions().setName(target.getValue()));
                    break;

                case ATTRIBUTE, ATTRIBUTE_VALUE, CLASS, CLASSES, CSS, DATA, HREF, ID, LEAF, LINK_TEXT, NAME,
                     PARTIAL_LINK_TEXT, PARTIAL_TEXT, ROLE_LITERAL, ROOT, SPLICE, SRC, STYLE, TAG, TAG_CONTAINS_TEXT,
                     TAG_WITH_TEXT, TEST_ID, TEXT, TYPE, VALUE, XPATH:
                    List<Locator> elements = page.locator(target.getSelector()).all();
                    currentElement = elements.getFirst();

                default: {

                }
            }
        } catch (PlaywrightException pe) {
            logger.log("decode() failed for target: " + target);
            found = false;
        }
        return found;
    }

    private void setTimeout(int seconds) {
        page.setDefaultTimeout(seconds * 1000);
    }


}
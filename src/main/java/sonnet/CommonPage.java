package sonnet;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.BoundingBox;
import sonnet.CommonPageInterface;
import sonnet.utils.Logger;

import java.util.List;

import static org.sonnet.utils.Print.*;
import static sonnet.utils.Time.pause;

public class CommonPage extends TargetFactory implements CommonPageInterface {

    private Page page;
    private Locator currentElement;
    private List<Locator> currentElements;
    private final Logger logger = new Logger(); // This is the logger from sonnet.utils
    private Boolean execute = true;

    //////////
    //TIMERS//
    //////////

    private final int DEFAULT_TIMEOUT = 30;
    private final int DEFAULT_PROBE_TIMEOUT = 5;

    @Override
    public CommonPage click() {
        if (!execute) return this;
        logger.log("click()\t");
        currentElement.click();
        return this;
    }

    @Override
    public CommonPage choose(int index) {
        if (!execute) return this;
        logger.log("choose()", "Int ", String.valueOf(index));
        currentElement = currentElements.get(index);
        return this;
    }

    @Override
    public CommonPage choose(String name) {
        if (!execute) return this;
        logger.log("choose()", "String ", name);
        for (Locator listElement : currentElements) {
            if (listElement.textContent().contains(name)) {
                currentElement = listElement;
                break;
            }
        }
        return this;
    }

    @Override
    public CommonPage collect(String locator) {
        if (!execute) return this;
        logger.log("collect()", locator);
        currentElements = currentElement.locator(locator).all();
        return this;
        //TODO FIX LATER
    }

    @Override
    public CommonPage collect(Target target) {
        if (!execute) return this;
        logger.log("collect()", target.getType().toString(), target.getValue());
        final int ITERATIONS = 5;
        final int STABLE_COUNT = 3;
        int stableCount = 0;
        int count = 0;
        int currentCount = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            currentElements = currentElement.locator(target.getSelector()).all();
            currentCount = currentElements.size();
            if (currentCount == count) {
                stableCount++;
            }
            else {
                stableCount = 0;
            }
            count = currentCount;
            pause(1);
        }

        return this;
    }

    @Override
    public CommonPage compose(Action action) {
        if (!execute) return this;
        logger.log("compose()", "Action ", action.toString());
        currentElement.fill(action.toString());
        return this;
    }

    @Override
    public CommonPage compose(String text) {
        if (!execute) return this;
        logger.log("compose()", "", text);
        currentElement.fill(text);
        return this;
    }

    @Override
    public CommonPage end() {
        logger.log("end()\t");
        execute = true;
        return this;
    }

    @Override
    public CommonPage focus(Target target) {
        if (!execute) return this;
        logger.log("focus()\t",target.getType().toString(), target.getValue());
        decode(target);
        return this;
    }

    @Override
    public String get(Trait trait) {
        logger.log("get()\t", trait.name());
        return switch (trait) {
            case TEXT -> currentElement.textContent();
            case TAG -> currentElement.evaluate("el => el.tagName").toString();
            case XPOS -> String.valueOf(currentElement.boundingBox().x);
            case YPOS -> String.valueOf(currentElement.boundingBox().y);
            case WIDTH -> String.valueOf(currentElement.boundingBox().width);
            case HEIGHT -> String.valueOf(currentElement.boundingBox().height);
            case ENABLED -> String.valueOf(currentElement.isEnabled());
            case VISIBLE -> String.valueOf(currentElement.isVisible());
            case SELECTED -> String.valueOf(currentElement.isChecked());

        };
    }

    @Override
    public CommonPage go(String url) {
        logger.log("go()\t", url);
        page.navigate(url);
        return this;
    }

    @Override
    public CommonPage open() {
        logger.log("open()\t", "Chrome");
        try {
            Playwright playwright = Playwright.create();
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            page = browser.newPage();
        } catch (PlaywrightException pe) {
            print("Playwright Exception" + pe.getMessage());

        }
        return this;
    }

    @Override
    public CommonPage probe(Target target) {
        logger.log("probe()\t",target.getType().toString(), target.getValue());
        setTimeout(DEFAULT_PROBE_TIMEOUT);
        if (!decode(target)) execute = false;
        setTimeout(DEFAULT_TIMEOUT);
        return this;
    }

    @Override
    public int size() {
        logger.log("size()\t");
        return currentElements.size();
    }

    // TODO review
    @Override
    public CommonPage submit() {
        if (!execute) return this;
        logger.log("submit()\t");
        return this;
    }

    //////////////////////////
    /// DIAGNOSTIC METHODS ///
    //////////////////////////

    public CommonPage printElements() {
        logger.log("print elements");
        printPartition();
        for (Locator listElement : currentElements) {
            print(listElement.textContent());
        }
        return this;
    }

    public CommonPage printElement() {
        logger.log("print element");
        BoundingBox box = currentElement.boundingBox();
        print("Tag: \t\t" + currentElement.evaluate("el => el.tagName").toString());
        print("Text: \t\t" + currentElement.textContent());
        print("Enabled: \t" + currentElement.isEnabled());
        print("Visible: \t" + currentElement.isVisible());
        print("Location: \t " + box.x + " " + box.y);
        print("Dimensions: \t" + box.width + " " + box.height);
        return this;
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

                default: {
                    List<Locator> elements = page.locator(target.getSelector()).all();
                    currentElement = elements.getFirst();
                }
            }
        }
        catch (PlaywrightException pe) {
            logger.log("decode() failed for target: " + target.toString());
            found = false;
        }
        return found;
    }

    private void setTimeout(int seconds) {
        page.setDefaultTimeout(seconds * 1000);
    }
}
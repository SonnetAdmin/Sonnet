package sonnet;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.BoundingBox;
import sonnet.CommonPageInterface;
import sonnet.utils.Logger;

import java.util.List;

import static org.sonnet.utils.Print.*;

public class CommonPage extends TargetFactory implements CommonPageInterface {

    private Page page;
    private Locator currentElement;
    private List<Locator> currentElements;
    private final Logger logger = new Logger();  // This is the logger from sonnet.utils

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
    public CommonPage go(String url) {
        logger.log("go()\t", url);
        page.navigate(url);
        return this;
    }

    @Override
    public CommonPage focus(Target target) {
        logger.log("focus()\t", target.toString());
        List<Locator> elements = page.locator(target.getSelector()).all();
        if (elements.isEmpty()) {
            throw new IllegalArgumentException("There is no element with locator " + target.getSelector());
        }
        if (elements.size() > 1) {
            logger.warn("There are multiple. Focusing on the first one.");
        }
        currentElement = elements.getFirst();
        return this;
    }

    @Override
    public CommonPage sleep(int milliseconds) {
        logger.log("sleep()\t", "Milliseconds ",String.valueOf(milliseconds));
        try {
            Thread.sleep(milliseconds * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public CommonPage click() {
        logger.log("click()\t");
        currentElement.click();
        return this;
    }

    @Override
    public CommonPage choose(String name) {
        logger.log("choose()", "String ", name);
        for (Locator listElement : currentElements) {
            if (listElement.textContent().contains(name)) {
                currentElement = listElement;
                print("found");
                break;
            }
        }
        return this;
    }

    @Override
    public CommonPage choose(int index) {
        logger.log("choose()", "Int ", String.valueOf(index));
        currentElement = currentElements.get(index);
        return this;
    }

    @Override
    public CommonPage compose(String text) {
        logger.log("compose()\t", "Text ", text);
        currentElement.fill(text);
        return this;
    }

    @Override
    public CommonPage compose(Action action) {
        logger.log("compose()\t", "Action ", action.toString());
        currentElement.fill(action.toString());
        return this;
    }

    // TODO review
    @Override
    public CommonPage submit() {
        logger.log("submit()\t");
        return this;
    }

    @Override
    public CommonPage collect(String locator) {
        logger.log("collect()", locator);
        currentElements = currentElement.locator(locator).all();
        return this;
        //TODO FIX LATER
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
}

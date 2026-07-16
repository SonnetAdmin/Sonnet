package sonnet;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.BoundingBox;
import sonnet.CommonPageInterface;

import java.util.List;

import static org.sonnet.utils.Print.*;

public class CommonPage implements CommonPageInterface {

    private Page page;
    private Locator currentElement;
    private List<Locator> currentElements;

    @Override
    public CommonPage open() {
        print("open");
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
        print("go");
        page.navigate(url);
        return this;
    }

    @Override
    public CommonPage focus(String locator) {
        print("find");
        List<Locator> elements = page.locator(locator).all();
        if (elements.isEmpty()) {
            throw new IllegalArgumentException("There is no element with locator " + locator);
        }
        currentElement = elements.getFirst();
        return this;
    }

    @Override
    public CommonPage expect(String locator) {
        print("expect");
        int attempts = 0;
        while (attempts++ < 5) {
            try {
                focus(locator);
                break;
            } catch (IllegalArgumentException e) {
                sleep(2);
            }
        }
        return this;
    }

    @Override
    public CommonPage sleep(int milliseconds) {
        print("sleep");
        try {
            Thread.sleep(milliseconds * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public CommonPage click() {
        print("click");
        currentElement.click();
        return this;
    }

    @Override
    public CommonPage choose(String name) {
        print("choose name");
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
        print("choose index");
        currentElement = currentElements.get(index);
        return this;
    }

    @Override
    public CommonPage compose(String text) {
        print("compose");
        currentElement.fill(text);
        return this;
    }

    @Override
    public CommonPage compose(Action action) {
        print("compose");
        currentElement.fill(action.toString());
        return this;
    }

    @Override
    public CommonPage printElements() {
        print("print elements");
        printPartition();
        for (Locator listElement : currentElements) {
            print(listElement.textContent());
        }
        return this;
    }

    @Override
    public CommonPage printElement() {
        print("print element");
        BoundingBox box = currentElement.boundingBox();
        print("Tag: \t\t" + currentElement.evaluate("el => el.tagName").toString());
        print("Text: \t\t" + currentElement.textContent());
        print("Enabled: \t" + currentElement.isEnabled());
        print("Visible: \t" + currentElement.isVisible());
        print("Location: \t " + box.x + " " + box.y);
        print("Dimensions: \t" + box.width + " " + box.height);
        return this;
    }


    // TODO review
    @Override
    public CommonPage submit() {
        print("submit");
        return this;
    }

    @Override
    public CommonPage collect(String locator) {
        print("collect");
        currentElements = currentElement.locator(locator).all();
        return this;
    }

    @Override
    public String get(Trait trait) {
        print("get " + trait);
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

}

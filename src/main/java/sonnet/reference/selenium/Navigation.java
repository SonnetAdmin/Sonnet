package sonnet.reference.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import sonnet.reference.selenium.locators.Target;

public class Navigation extends CommonPage implements NavigationInterface {
    private static Navigation instance = null;
    public static synchronized Navigation getInstance() {
        if(instance == null) {
            instance = new Navigation();
        }
        return instance;
    }

    @Override
    public void leaf() {
        if (execute) {
            log("leaf()", "To leaf");
            while (true) {
                try {
                    WebElement nextElement = currentElement.findElement(By.cssSelector(":first-child"));
                    currentElement = nextElement;
                } catch (NoSuchElementException e) {
                    break;
                } catch (WebDriverException wde) {
                    takeScreenShotAndExit(wde.getMessage());
                }
            }
        }
    }

    ///////////////
    // SCROLLERS //
    ///////////////

    @Override
    public void scroll(Target target) {
        if (execute) {
            log("scroll() ", target.getBy().toString());
            focus(target.getBy());
            scrollIntoView();
        }
    }

    @Override
    public void scroll() {
        if (execute) {
            log("scroll() ", "To currently focused element");
            scrollIntoView();
        }
    }

    private void scrollIntoView() {
        try {
            new Actions(driver)
                    .scrollToElement(currentElement)
                    .perform();
        } catch (WebDriverException wde) {
            takeScreenShotAndExit("scrollIntoView");
        }
    }

    /////////////
    // DESCEND //
    /////////////

    @Override
    public void descend() {
        // To child
        if (execute) {
            log("descend() ", "To Child");
            try {
                currentElement = currentElement.findElement(By.cssSelector(":first-child"));
            } catch (WebDriverException wde) {
                takeScreenShotAndExit(wde.getMessage());
            }
        }
    }

    @Override
    public void descend(Target target) {
        if (execute) {
            log("descend()", target.getBy().toString());
            descend(target.getBy());
        }
        currentElementLocator = target.getBy();
    }

    private void descend(By by) {
        if (execute) {
            try {
                currentElement = focusWait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(currentElement, by));
            } catch (WebDriverException wde) {
                takeScreenShotAndExit(wde.getMessage());
            }
            currentElementLocator = by;
        }
    }


    ////////////
    // ASCEND //
    ////////////

    @Override
    public void ascend() {
        // To parent
        if (execute) {
            log("ascend()", "To Parent");
            ascendBy(1);
        }
    }

    @Override
    public void ascend(int index) {
        // To ancestor by index
        if (execute) {
            log("ascend() ", "Levels : " + index);
            // Prevent 0
            ascendBy(index == 0 ? 1 : index);
        }
    }

    @Override
    public void ascend(Enums.Index index) {
        // To ancestor by index ordinal + 1
        if (execute) {
            log("ascend() ", "Levels : " + index.ordinal() + 1);
            ascendBy((index.ordinal() + 1));
        }
    }


    private void ascendBy(int index) {
        // To ancestor by index
        if (execute) {
            log("ascend() ", "Levels : " + index);
            try {
                currentElement = currentElement.findElement(By.xpath("ancestor::*[" + index + "]"));
                log("ascended", "To : ", currentElement.getTagName());
            } catch (WebDriverException wde) {
                takeScreenShotAndExit(wde.getMessage());
            }
        }
    }


    public void ascend(Enums.Tag tag) {
        if (execute) {
            log("ascend()", "Tag: " + tag.name());
            try {
                ascendToTag(currentElement, tag, 0);
            } catch (NoSuchElementException e) {
                throw new CPOException("Original element not found by locator: " + tag.name());
            }
        }
    }

    private void ascendToTag(WebElement current, Enums.Tag tag, int level) {
        if (current.getTagName().equalsIgnoreCase(tag.name())) {
            currentElement = current;
            log("ascend()", "Found tag '" + tag.name() + "' at level: " + level);
        } else if (current.getTagName().equalsIgnoreCase("html")) {
            throw new CPOException("Ancestor tag '" + tag.name() + "' not found");
        } else {
            WebElement parent = current.findElement(By.xpath(".."));
            ascendToTag(parent, tag, level += 1);
        }
    }



    //////////////
    // TRAVERSE //
    //////////////

    @Override
    public void traverse() {
        // To next sibling
        if (execute) {
            log("traverse() ", "Following Sibling");
            selectFollowingSibling(1);
        }
    }

    @Override
    public void traverse(int index) {
        // To sibling by index
        if (execute) {
            log("traverse() ", Integer.toString(index));
            selectFollowingSibling(index);
        }
    }

    @Override
    public void traverse(Enums.NodeEnum index) {
        if (execute) {
            log("traverse() ", index.name());
            selectFollowingSibling(index.getValue());
        }
    }

    private void selectFollowingSibling(int index) {
        try {
            currentElement = currentElement.findElement(By.xpath("following-sibling::*[" + index + "]"));
            log("traversed", "To : ", currentElement.getTagName());
        } catch (WebDriverException wde) {
            takeScreenShotAndExit(wde.getMessage());
        }
    }


    //////////////
    // REVERSE //
    //////////////

    @Override
    public void reverse() {
        if (execute) {
            log("reverse() ", "Previous Sibling");
            selectPreviousSibling(1);
        }
    }

    public void reverse(int index) {
        if (execute) {
            log("reverse() ", Integer.toString(index));
            selectPreviousSibling(index);
        }
    }

    public void reverse(Enums.NodeEnum index) {
        if (execute) {
            log("reverse() ", index.name());
            selectPreviousSibling(index.getValue());
        }
    }

    private void selectPreviousSibling(int index) {
        try {
            currentElement = currentElement.findElement(By.xpath("preceding-sibling::*[" + index + "]"));
            log("reversed", "To : ", currentElement.getTagName());
        } catch (WebDriverException wde) {
            takeScreenShotAndExit(wde.getMessage());
        }
    }
}

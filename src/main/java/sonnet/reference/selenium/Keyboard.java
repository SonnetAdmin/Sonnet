package sonnet.reference.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import sonnet.reference.selenium.locators.Target;

import java.awt.*;

import static sonnet.reference.selenium.locators.Target.AlertAction.ACCEPT;
import static sonnet.reference.selenium.locators.Target.AlertAction.DISMISS;


public class Keyboard extends CommonPage implements KeyInterface {
    private static Keyboard instance = null;
    public static synchronized Keyboard getInstance() {
        if(instance == null) {
            instance = new Keyboard();
        }
        return instance;
    }

    @Override
    public void compose(String keysToSend) {
        if (execute) {
            String attribute = currentElement.getAttribute("type");
            if (attribute == null) {
                throw new CPOException("Element has no type attribute or is not editable");
            }
            if (!attribute.equals("password")) {
                log("compose() ", "Text : " + keysToSend);
            } else {
                int stringSize = keysToSend.length();
                log("compose() ", "Text : " + "*".repeat(stringSize));
            }
            try {
                click();
                currentElement.sendKeys(keysToSend);
            } catch (StaleElementReferenceException sere) {
                warn("compose() : StaleElementReferenceException : Relocating : By : " + currentElementLocator.toString());
                focus(By.xpath(getXpath(currentElement, "")));
                currentElement.sendKeys(keysToSend);
            } catch (IllegalArgumentException iae) {
                takeScreenShotAndExit("compose() parameters are NULL");
            }
        }
    }

    @Override
    public void compose(org.openqa.selenium.Keys keyToSend) {
        if (execute) {
            log("compose() ", "Text : " + keyToSend.name());
            currentElement.sendKeys(keyToSend);
        }
    }

    @Override
    public void compose(org.openqa.selenium.Keys keyToSend, int repeat) {
        if (execute) {
            log("compose(Keys, repeat ) ", "Text : " + keyToSend.name() + " Repeat : " + repeat);
            for (int i = 0; i < repeat; i++) {
                compose(keyToSend);
            }
        }
    }
    @Override
    public void hold(String keysToHold){
        if (execute) {
            log("keyDown ", "Key : " + keysToHold);
            new Actions(driver)
                    .keyDown(keysToHold)
                    .perform();
        }
    }
    @Override
    public void hold(org.openqa.selenium.Keys keysToHold){
        if (execute) {
            log("keyDown ", "Key : " + keysToHold.name());
            new Actions(driver)
                    .keyDown(keysToHold)
                    .perform();
        }
    }
    @Override
    public void release(String keysToRelease){
        if (execute) {
            log("keyUp ", "Key : " + keysToRelease);
            new Actions(driver)
                    .keyUp(keysToRelease)
                    .perform();
        }
    }
    @Override
    public void release(org.openqa.selenium.Keys keysToRelease){
        if (execute) {
            log("keyUp ", "Key : " + keysToRelease.name());
            new Actions(driver)
                    .keyUp(keysToRelease)
                    .perform();
        }
    }

    @Override
    public void submit() {
        if (execute) {
            log("submit()");
            try {
                currentElement.submit();
            } catch (WebDriverException wde) {
                throw new CPOException(wde.getMessage());
            }
        }
    }

    @Override
    public void alert(sonnet.reference.selenium.locators.Target.AlertAction action) {
        if (execute) {
            log("alert()", action.name());
            try {
                Alert alert = driver.switchTo().alert();
                switch (action) {
                    case ACCEPT -> alert.accept();
                    case DISMISS -> alert.dismiss();
                }
            } catch (WebDriverException wde) {
                takeScreenShotAndExit(wde.getMessage());
            }
        }
    }

    @Override
    public void alert(int keys) {
        if (execute) {
            Actions action = new Actions(driver);
            log("alert()");
            try {
                Robot robot;
                try {
                    robot = new Robot();
                } catch (AWTException e) {
                    throw new RuntimeException(e);
                }
                robot.keyPress(keys);
            } catch (WebDriverException wde) {
                takeScreenShotAndExit(wde.getMessage());
            }
        }
    }

    @Override
    public void alert(String keysToSend) {
        if (execute) {
            Alert alert = driver.switchTo().alert();
            log("alert() ", keysToSend);
            try {
                alert.sendKeys(keysToSend);
            } catch (WebDriverException wde) {
                takeScreenShotAndExit(wde.getMessage());
            }
        }
    }
}

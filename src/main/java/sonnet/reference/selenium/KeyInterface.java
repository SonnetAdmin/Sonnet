package sonnet.reference.selenium;

import org.openqa.selenium.Keys;
import sonnet.reference.selenium.locators.Target;

interface KeyInterface {

    // Compose

    void compose(String content);

    void compose(Keys keyToSend);

    void compose(Keys keyToSend, int repeat);

    // Hold and release

    void hold(Keys keyToHold);

    void hold(String keyToHold);

    void release(Keys keyToRelease);

    void release(String keyToRelease);

    // Alerts

    void alert(String keysToSend);

    void alert(Target.AlertAction action);

    void alert(int keys);

    // Other

    void submit();
}

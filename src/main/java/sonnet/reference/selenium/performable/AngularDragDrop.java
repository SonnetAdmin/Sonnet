package sonnet.reference.selenium.performable;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import sonnet.reference.selenium.CPOException;
import sonnet.reference.selenium.locators.Target;

import java.time.Duration;

/**
 * Performable action for simulating Angular drag-and-drop using Selenium Actions.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     Performable dragDrop = new AngularDragDrop(Target.SOURCE, Target.DEST);
 *     dragDrop.run();
 * </pre>
 */
public class AngularDragDrop extends Performable {

    Target draggable;
    Target dropZone;

    /**
     * Angular Drag Drop.   Implemented as V2 class to include a Selenium Actions class offset method.
     * @param draggable
     * @param dropZone
     * @Throws
     */
    public AngularDragDrop(Target draggable, Target dropZone) {
        this.draggable = draggable;
        this.dropZone = dropZone;
    }

    @Override
    public void run() {
        try {
            // Default Wait time pending Properties implementation
            WebDriverWait webDriverWait = new WebDriverWait(getDriver(), Duration.ofSeconds(60));
            log("perform()", "Angular Drag Drop");
            log("dragDrop :draggable : ", draggable.toString());
            log("dragDrop :dropZone  : ", dropZone.toString());
            WebElement drag = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(draggable.getBy()));
            WebElement drop = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(dropZone.getBy()));
            Actions actions = new Actions(getDriver());
            actions.clickAndHold(drag);
            actions.moveByOffset(20,20);  // Angular fix. Offset is from the current element X/Y
            actions.moveToElement(drop);
            actions.release();
            actions.perform();
        } catch (WebDriverException wde) {
            throw new CPOException(wde.getMessage());
        }

    }

    @Override
    public String description() {
        return "";
    }
}

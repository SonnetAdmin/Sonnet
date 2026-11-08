package sonnet.reference.selenium.performable;


import sonnet.reference.selenium.CPOException;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

/**
 * Performable action for uploading a file using the system clipboard and keyboard events.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     Performable uploader = new FileUploader("C:/path/to/file.txt");
 *     uploader.run();
 * </pre>
 */
public class FileUploader extends Performable {

    private String path;

    public FileUploader(String path) {
        this.path = path;
    }

    @Override

    public void run()  {
        Robot robot;
        try {
            robot = new Robot();
        } catch (Exception e) {
            throw new sonnet.reference.selenium.CPOException("Robot not supported");
        }
        StringSelection stringSelection = new StringSelection(path);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    @Override
    public String description() {
        return "FileUploader : " + path;
    }
}

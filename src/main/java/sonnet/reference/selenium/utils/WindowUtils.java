package sonnet.reference.selenium.utils;

import java.awt.*;

/**
 * Utility class for window-related operations, such as retrieving the position of a selected screen.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     int[] pos = WindowUtils.getPositionOfSelectedWindow(0);
 *     System.out.println("X: " + pos[0] + ", Y: " + pos[1]);
 * </pre>
 */
public class WindowUtils {

    public static int[] getPositionOfSelectedWindow(int screenIndex) {

        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screenDevices = graphicsEnvironment.getScreenDevices();

        GraphicsDevice selectedScreen;

        if (screenIndex < 0 || screenIndex >= screenDevices.length) {
            selectedScreen = graphicsEnvironment.getDefaultScreenDevice();
        } else {
            selectedScreen = screenDevices[screenIndex];
        }

        Rectangle selectedScreenBounds = selectedScreen.getDefaultConfiguration().getBounds();

        return new int[]{selectedScreenBounds.x, selectedScreenBounds.y};
    }
}

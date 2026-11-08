package sonnet.reference.playwright.utils;

import com.google.gson.JsonObject;
import com.microsoft.playwright.CDPSession;
import com.microsoft.playwright.Page;

import java.awt.*;

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

    public static void applyWindowState(Page page) {
        if (!VMArgs.isHeadless()) {
            setPosition(page, VMArgs.getBrowserWindow());
        }
        if (VMArgs.isMinimised()) {
            minimise(page);
        }
    }

    public static void setPosition(Page page, int screenIndex) {
        try {
            int[] position = getPositionOfSelectedWindow(screenIndex);
            CDPSession cdp = page.context().newCDPSession(page);
            JsonObject window = cdp.send("Browser.getWindowForTarget");
            int windowId = window.get("windowId").getAsInt();
            JsonObject bounds = new JsonObject();
            bounds.addProperty("left", position[0]);
            bounds.addProperty("top", position[1]);
            bounds.addProperty("windowState", "normal");
            JsonObject params = new JsonObject();
            params.addProperty("windowId", windowId);
            params.add("bounds", bounds);
            cdp.send("Browser.setWindowBounds", params);
        } catch (Exception e) {
            System.out.println("WindowUtils: CDP window position change skipped — " + e.getMessage());
        }
    }

    public static void minimise(Page page) {
        setWindowState(page, "minimized");
    }

    public static void maximise(Page page) {
        if (VMArgs.isMinimised()) return;   // honour user preference to keep window minimised
        setWindowState(page, "maximized");
    }

    private static void setWindowState(Page page, String state) {
        if (VMArgs.isHeadless()) return;
        try {
            CDPSession cdp = page.context().newCDPSession(page);
            JsonObject window = cdp.send("Browser.getWindowForTarget");
            int windowId = window.get("windowId").getAsInt();
            JsonObject bounds = new JsonObject();
            bounds.addProperty("windowState", state);
            JsonObject params = new JsonObject();
            params.addProperty("windowId", windowId);
            params.add("bounds", bounds);
            cdp.send("Browser.setWindowBounds", params);
        } catch (Exception e) {
            System.out.println("WindowUtils: CDP window state change skipped — " + e.getMessage());
        }
    }
}

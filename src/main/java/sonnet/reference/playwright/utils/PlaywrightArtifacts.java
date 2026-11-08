package sonnet.reference.playwright.utils;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.ScreenshotAnimations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Manages Playwright artifact capture: traces, screenshots and video recordings.
 * All artifacts are saved to the configured output directory.
 */
public class PlaywrightArtifacts {

    private static final String ARTIFACTS_DIR = "target/playwright-artifacts";

    /**
     * Creates a BrowserContext configured with video recording.
     */
    public static BrowserContext createContext(Browser browser) {
        return browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080)
                .setRecordVideoDir(Paths.get(ARTIFACTS_DIR, "videos"))
                .setRecordVideoSize(1920, 1080)
        );
    }

    /**
     * Starts tracing if enabled via {@code -Dtrace=true}.
     */
    public static void startTracing(BrowserContext context) {
        if (!VMArgs.isTraced()) return;
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
    }

    /**
     * Stops tracing (if enabled), flushing the trace file to disk.
     *
     * @param scenarioName used to name the trace file.
     * @return the path to the saved trace zip, or {@code null} if tracing was not active.
     */
    public static Path stopTracing(BrowserContext context, String scenarioName) {
        if (context == null || !VMArgs.isTraced()) return null;
        String safeName = scenarioName.replaceAll("[^a-zA-Z0-9_-]", "_");
        Path tracePath = Paths.get(ARTIFACTS_DIR, "traces", safeName + ".zip");
        try {
            Files.createDirectories(tracePath.getParent());
            context.tracing().stop(new Tracing.StopOptions().setPath(tracePath));
        } catch (PlaywrightException | IOException e) {
            System.out.println("Failed to stop tracing: " + e.getMessage());
            return null;
        }
        return tracePath;
    }

    /**
     * Takes a full-page screenshot and returns the raw PNG bytes.
     *
     * @return screenshot bytes, or {@code null} if the page is unavailable.
     */
    public static byte[] takeScreenshot(Page page) {
        if (page == null) return null;
        try {
            return page.screenshot(new Page.ScreenshotOptions()
                    .setFullPage(true)
                    .setTimeout(5000)
                    .setAnimations(ScreenshotAnimations.DISABLED));
        } catch (PlaywrightException e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }

    /**
     * Returns the path to the video file recorded for the given page,
     * or {@code null} if video recording is not active.
     * <p>Must be called <b>before</b> closing the BrowserContext.</p>
     */
    public static Path getVideoPath(Page page) {
        if (page == null || page.video() == null) return null;
        try {
            return page.video().path();
        } catch (PlaywrightException e) {
            System.out.println("Failed to get video path: " + e.getMessage());
            return null;
        }
    }

    /**
     * Deletes an artifact file if it exists.
     */
    public static void deleteArtifact(Path path) {
        if (path == null) return;
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            System.out.println("Failed to delete artifact: " + e.getMessage());
        }
    }
}

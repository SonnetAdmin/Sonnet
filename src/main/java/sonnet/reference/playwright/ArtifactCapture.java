package sonnet.reference.playwright;

import sonnet.reference.playwright.utils.PlaywrightArtifacts;

import java.nio.file.Path;

/**
 * Provides public artifact capture methods for infrastructure code (e.g. Hooks)
 * that needs screenshots and video paths without direct access to the raw Playwright Page.
 * <p>
 * Resides in the {@code playwright} package so it can access
 * {@code CommonPage.getPage()} via same-package protected visibility.
 */
public class ArtifactCapture {

    private ArtifactCapture() {
    }

    public static byte[] screenshotBytes() {
        return PlaywrightArtifacts.takeScreenshot(CommonPage.getInstance().getPage());
    }

    public static Path videoPath() {
        return PlaywrightArtifacts.getVideoPath(CommonPage.getInstance().getPage());
    }
}

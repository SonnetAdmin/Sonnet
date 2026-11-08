package sonnet.reference.playwright.visualregression;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public interface VisualServiceInterface {
    ////////////////////////
    // Visual Regression  //
    ////////////////////////

    void setThreshold(double threshold);

    void captureScreenshot(String name, BufferedImage image) throws IOException;

    void saveBaseline(String name, BufferedImage image) throws IOException;

    double compareImages(BufferedImage image1, BufferedImage image2) throws IOException;

    double compareImagesWithTolerance(BufferedImage image1, BufferedImage image2, int tolerance) throws IOException;

    BufferedImage generateDiffImage(BufferedImage image1, BufferedImage image2) throws IOException;

    void saveDiffImage(String name, BufferedImage diffImage) throws IOException;

    VisualTestResult performVisualTest(String testName, BufferedImage actualImage, double threshold, int colorTolerance) throws IOException;

    VisualTestResult performVisualTest(String testName, BufferedImage actualImage) throws IOException;

    void generateHTMLReport(List<VisualTestResult> results, String reportPath) throws IOException;
}

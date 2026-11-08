package sonnet.reference.playwright.visualregression;

import sonnet.reference.playwright.Logging;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static sonnet.reference.playwright.utils.Checks.*;

public class VisualServiceImpl implements VisualServiceInterface {

    private Logging logger = Logging.getInstance();

    // Visual Regression
    private static final String VISUAL_REGRESSION_BASE_DIR = System.getProperty("visual.regression.baseDir", "src/test/resources/");
    private static final String BASELINE_DIR = VISUAL_REGRESSION_BASE_DIR + "baseline/";
    private static final String ACTUAL_DIR = System.getProperty("visual.regression.actualDir", "target/visual-regression/actual/");
    private static final String DIFF_DIR = System.getProperty("visual.regression.diffDir", "target/visual-regression/diff/");
    private static double DEFAULT_THRESHOLD = 0.1; // Default threshold for image comparison

    // Singleton instance
    private static VisualServiceImpl instance;

    // Private constructor to prevent instantiation
    private VisualServiceImpl() {
    }

    // Method to get the singleton instance
    public static VisualServiceImpl getInstance() {
        if (instance == null) {
            instance = new VisualServiceImpl();
        }
        return instance;
    }

    // Visual Regression Methods
    // ------------------------
    @Override
    public void setThreshold(double threshold) {
        throwOnMinus(threshold, "threshold");
        DEFAULT_THRESHOLD = threshold;
        logger.log("setThreshold()", String.valueOf(threshold));
    }

    @Override
    public void captureScreenshot(String filename, BufferedImage image) throws IOException {
        throwOnNullOrEmpty(filename, "Filename");
        throwOnNull(image, "Image");
        Path actualPath = Paths.get(ACTUAL_DIR);
        if (!actualPath.toFile().exists()) {
            actualPath.toFile().mkdirs();
        }
        File outputFile = new File(ACTUAL_DIR + filename);
        ImageIO.write(image, "png", outputFile);
        logger.log("Screenshot captured: " + outputFile.getAbsolutePath());
    }

    @Override
    public void saveBaseline(String filename, BufferedImage image) throws IOException {
        throwOnNullOrEmpty(filename, "Filename");
        throwOnNull(image, "Image");
        Path baselinePath = Paths.get(BASELINE_DIR);
        if (!baselinePath.toFile().exists()) {
            baselinePath.toFile().mkdirs();
        }
        File outputFile = new File(BASELINE_DIR + filename);
        ImageIO.write(image, "png", outputFile);
        logger.log("Baseline saved: " + outputFile.getAbsolutePath());
    }

    @Override
    public double compareImages(BufferedImage baseline, BufferedImage actual) {
        throwOnNull(baseline, "Baseline image");
        throwOnNull(actual, "Actual image");
        if (baseline.getWidth() != actual.getWidth() || baseline.getHeight() != actual.getHeight()) {
            throw new IllegalArgumentException("Images must be of the same dimensions for comparison."+
                    " Baseline: " + baseline.getWidth() + "x" + baseline.getHeight() +
                    ", Actual: " + actual.getWidth() + "x" + actual.getHeight());
        }
        int width = baseline.getWidth();
        int height = baseline.getHeight();
        long diffPixelCount = 0;
        long totalPixels = width * height;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (baseline.getRGB(x, y) != actual.getRGB(x, y)) {
                    diffPixelCount++;
                }
            }
        }
        logger.log("compareImages()", "Total Pixels: " + totalPixels, "Different Pixels: " + diffPixelCount);
        return (double) diffPixelCount / totalPixels;
    }

    @Override
    public double compareImagesWithTolerance(BufferedImage baseline, BufferedImage actual, int colorTolerance) {
        throwOnNull(baseline, "Baseline image");
        throwOnNull(actual, "Actual image");
        if (baseline.getWidth() != actual.getWidth() || baseline.getHeight() != actual.getHeight()) {
            throw new IllegalArgumentException("Images must be of the same dimensions for comparison."+
                    " Baseline: " + baseline.getWidth() + "x" + baseline.getHeight() +
                    ", Actual: " + actual.getWidth() + "x" + actual.getHeight());
        }
        throwOnMinus(colorTolerance);
        if (colorTolerance > 255) {
            throw new IllegalArgumentException("colorTolerance must be between 0 and 255 inclusive. Provided: " + colorTolerance);
        }
        int width = baseline.getWidth();
        int height = baseline.getHeight();
        long diffPixelCount = 0;
        long totalPixels = width * height;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color baselineColor = new Color(baseline.getRGB(x, y));
                Color actualColor = new Color(actual.getRGB(x, y));

                int rDiff = Math.abs(baselineColor.getRed() - actualColor.getRed());
                int gDiff = Math.abs(baselineColor.getGreen() - actualColor.getGreen());
                int bDiff = Math.abs(baselineColor.getBlue() - actualColor.getBlue());

                if(rDiff > colorTolerance || gDiff > colorTolerance || bDiff > colorTolerance) {
                    diffPixelCount++;
                }
            }
        }

        return (double) diffPixelCount / totalPixels;
    }

    @Override
    public BufferedImage generateDiffImage(BufferedImage baseline, BufferedImage actual) {
        throwOnNull(baseline, "Baseline image");
        throwOnNull(actual, "Actual image");
        if (baseline.getWidth() != actual.getWidth() || baseline.getHeight() != actual.getHeight()) {
            throw new IllegalArgumentException("Images must be of the same dimensions for diff generation." +
                    " Baseline: " + baseline.getWidth() + "x" + baseline.getHeight() +
                    ", Actual: " + actual.getWidth() + "x" + actual.getHeight());
        }
        int width = baseline.getWidth();
        int height = baseline.getHeight();
        BufferedImage diffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int baselineRGB = baseline.getRGB(x, y);
                int actualRGB = actual.getRGB(x, y);

                if(baselineRGB != actualRGB) {
                    diffImage.setRGB(x, y, Color.ORANGE.getRGB());
                } else {
                    Color originalColor = new Color(baselineRGB);
                    Color grayed = new Color(
                            originalColor.getRed() /2
                            ,originalColor.getGreen() /2
                            ,originalColor.getBlue() /2
                    );
                    diffImage.setRGB(x, y, grayed.getRGB());
                }
            }
        }
        return diffImage;
    }

    @Override
    public void saveDiffImage(String filename, BufferedImage diffImage) throws IOException {
        throwOnNullOrEmpty(filename, "Filename");
        throwOnNull(diffImage, "Diff image");
        Path diffPath = Paths.get(DIFF_DIR);
        if (!diffPath.toFile().exists()) {
            diffPath.toFile().mkdirs();
        }
        File outputFile = new File(DIFF_DIR + filename);
        ImageIO.write(diffImage, "png", outputFile);
        logger.log("Diff image saved: " + outputFile.getAbsolutePath());
    }

    @Override
    public VisualTestResult performVisualTest(String testname, BufferedImage actualImage, double threshold, int colorTolerance) throws IOException {
        String filename = testname + ".png";
        File baselineFile = new File(BASELINE_DIR + filename);

        captureScreenshot(filename, actualImage);

        if (!baselineFile.exists()) {
            saveBaseline(filename, actualImage);
            return new VisualTestResult(testname, true, 0.0, "Baseline image created.")
                    .withImagePaths(BASELINE_DIR + filename, ACTUAL_DIR + filename, null);
        }
        BufferedImage baselineImage = ImageIO.read(baselineFile);
        double difference = colorTolerance > 0 ?
                compareImagesWithTolerance(baselineImage, actualImage, colorTolerance) :
                compareImages(baselineImage, actualImage);
        boolean passed = difference <= threshold;

        String diffPath = null;
        if (!passed) {
            BufferedImage diffImage = generateDiffImage(baselineImage, actualImage);
            saveDiffImage(filename, diffImage);
            diffPath = DIFF_DIR + filename;
        }
        return new VisualTestResult(testname, passed, difference, passed ? "Visual test passed." : "Visual test failed. Difference: " + (difference * 100) + "%")
                .withImagePaths(BASELINE_DIR + filename, ACTUAL_DIR + filename, diffPath);
    }

    @Override
    public VisualTestResult performVisualTest(String testname, BufferedImage actualImage) throws IOException {
        return performVisualTest(testname, actualImage, DEFAULT_THRESHOLD, 0);
    }

    @Override
    public void generateHTMLReport(List<VisualTestResult> results, String outputPath) throws IOException {
        throwOnNullOrEmpty(outputPath, "outputPath");
        throwOnNull(results, "results");

        String baseName = outputPath.endsWith(".html")
                ? outputPath.substring(0, outputPath.length() - 5)
                : outputPath;

        String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss_SSS")
                .format(new java.util.Date());
        String timedFilename = baseName + "_" + timestamp + ".html";

        Path reportDir = Paths.get("target/visual-regression/report/");
        if (!reportDir.toFile().exists()) {
            reportDir.toFile().mkdirs();
        }

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html><html><head><title>Visual Test Report</title>")
                .append("<style>")
                .append("body{font-family:Arial,sans-serif;margin:20px;background:#f5f5f5;}")
                .append("h1{color:#333;}p{color:#666;}")
                .append(".card{background:#fff;border-radius:6px;box-shadow:0 2px 6px rgba(0,0,0,.15);margin-bottom:28px;overflow:hidden;}")
                .append(".card-header{display:flex;align-items:center;gap:16px;padding:14px 18px;border-bottom:1px solid #eee;}")
                .append(".card-header h2{margin:0;font-size:1rem;flex:1;}")
                .append(".badge{padding:4px 12px;border-radius:12px;font-size:.85rem;font-weight:bold;color:#fff;}")
                .append(".pass-badge{background:#4CAF50;} .fail-badge{background:#e53935;}")
                .append(".diff-pct{font-size:.9rem;color:#555;}")
                .append(".message{padding:8px 18px;font-size:.85rem;color:#777;border-bottom:1px solid #eee;}")
                .append(".images{display:flex;gap:0;flex-wrap:wrap;}")
                .append(".img-block{flex:1;min-width:200px;padding:12px 18px;box-sizing:border-box;}")
                .append(".img-block h3{margin:0 0 8px;font-size:.8rem;text-transform:uppercase;color:#888;letter-spacing:.05em;}")
                .append(".img-block img{max-width:100%;border:1px solid #ddd;border-radius:4px;display:block;}")
                .append(".no-image{color:#bbb;font-size:.85rem;font-style:italic;}")
                .append("</style></head><body>")
                .append("<h1>Visual Test Report</h1>")
                .append("<p>Generated: ").append(timestamp).append(" &nbsp;|&nbsp; Tests: ").append(results.size()).append("</p>");

        for (VisualTestResult result : results) {
            String badgeClass = result.isPassed() ? "pass-badge" : "fail-badge";
            String status     = result.isPassed() ? "PASS" : "FAIL";

            html.append("<div class='card'>")
                    .append("<div class='card-header'>")
                    .append("<h2>").append(escapeHtml(result.getTestName())).append("</h2>")
                    .append("<span class='badge ").append(badgeClass).append("'>").append(status).append("</span>")
                    .append("<span class='diff-pct'>").append(String.format("%.2f%%", result.getDifferentPercentage() * 100)).append(" different</span>")
                    .append("</div>")
                    .append("<div class='message'>").append(escapeHtml(result.getMessage())).append("</div>")
                    .append("<div class='images'>");

            html.append(imageBlock("Baseline", result.getBaselineImagePath()));
            html.append(imageBlock("Actual",   result.getActualImagePath()));
            html.append(imageBlock("Diff",     result.getDiffImagePath()));

            html.append("</div></div>");
        }

        html.append("</body></html>");

        Path outputFile = reportDir.resolve(timedFilename);
        Files.write(outputFile, html.toString().getBytes());
        logger.log("HTML report generated: " + outputFile.toAbsolutePath());
    }

    /** Returns an HTML image block with the image encoded as a base64 data URI, or a placeholder if the file is absent. */
    private String imageBlock(String label, String imagePath) {
        StringBuilder block = new StringBuilder("<div class='img-block'><h3>").append(label).append("</h3>");
        if (imagePath != null) {
            File file = new File(imagePath);
            if (file.exists()) {
                try {
                    byte[] imageBytes = Files.readAllBytes(file.toPath());
                    String base64 = java.util.Base64.getEncoder().encodeToString(imageBytes);
                    block.append("<img src='data:image/png;base64,").append(base64).append("' alt='").append(label).append("'/>");
                } catch (IOException e) {
                    block.append("<span class='no-image'>Could not load image</span>");
                }
            } else {
                block.append("<span class='no-image'>Image not found</span>");
            }
        } else {
            block.append("<span class='no-image'>N/A</span>");
        }
        return block.append("</div>").toString();
    }

    private static String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
    // ------------------------
}

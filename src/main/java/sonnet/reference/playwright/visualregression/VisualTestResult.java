package sonnet.reference.playwright.visualregression;

public class VisualTestResult {
    private String testName;
    private boolean passed;
    private double differentPercentage;
    private String message;
    private String baselineImagePath;
    private String actualImagePath;
    private String diffImagePath;

    public VisualTestResult(String testName, boolean passed, double differentPercentage, String message) {
        this.testName = testName;
        this.passed = passed;
        this.differentPercentage = differentPercentage;
        this.message = message;
    }

    public String getTestName() {
        return testName;
    }

    public boolean isPassed() {
        return passed;
    }

    public double getDifferentPercentage() {
        return differentPercentage;
    }

    public String getMessage() {
        return message;
    }

    public String getBaselineImagePath() { return baselineImagePath; }
    public String getActualImagePath()   { return actualImagePath; }
    public String getDiffImagePath()     { return diffImagePath; }

    public VisualTestResult withImagePaths(String baselineImagePath, String actualImagePath, String diffImagePath) {
        this.baselineImagePath = baselineImagePath;
        this.actualImagePath   = actualImagePath;
        this.diffImagePath     = diffImagePath;
        return this;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s%% different. %s", passed ? "PASS" : "FAIL", testName, differentPercentage, message);
    }
}

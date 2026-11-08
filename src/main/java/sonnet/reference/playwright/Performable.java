package sonnet.reference.playwright;

/**
 * Abstract base class for defining custom actions (performables) in Playwright automation.
 * Extend this class and implement {@link #run()} and {@link #description()} for custom behavior.
 * <p>
 * Inside {@code run()}, you have access to:
 * <ul>
 *     <li>All fluent framework methods inherited from {@link CommonPageObject} (focus, click, compose, etc.)</li>
 *     <li>The raw Playwright {@link com.microsoft.playwright.Page} via {@link #getPage()}</li>
 * </ul>
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     public class MyAction extends Performable {
 *         public void run() {
 *             focus(id("username")).compose("admin");
 *             getPage().locator("#submit").click();
 *         }
 *         public String description() {
 *             return "Login as admin";
 *         }
 *     }
 * </pre>
 *
 * @see CommonPageObject#perform(Performable)
 */
public abstract class Performable extends CommonPageObject {

    public Performable() {
    }

    /**
     * Returns the raw Playwright {@link com.microsoft.playwright.Page}.
     * <p>
     * Widens the package-private {@link CommonPageObject#getPage()} to {@code protected},
     * making it available to Performable subclasses in any package while keeping it
     * hidden from direct CommonPageObject subclasses outside the {@code playwright} package.
     *
     * @return the Playwright Page instance
     */
    @Override
    protected com.microsoft.playwright.Page getPage() {
        return super.getPage();
    }

    @Override
    protected com.microsoft.playwright.BrowserContext getContext() {
        return super.getContext();
    }

    /** Run the Performable code. */
    public abstract void run();

    /** Provide a description for logging. */
    public abstract String description();
}

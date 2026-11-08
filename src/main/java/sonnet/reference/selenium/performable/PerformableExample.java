package sonnet.reference.selenium.performable;

/**
 * Example implementation of a custom Performable action for UI automation.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 *     Performable action = new PerformableExample();
 *     action.run();
 *     System.out.println(action.description());
 * </pre>
 */
public class PerformableExample extends Performable {

    @Override
    public void run() {
        getDriver();
    }

    @Override
    public String description() {
        return "PerformableExample Description";
    }
}

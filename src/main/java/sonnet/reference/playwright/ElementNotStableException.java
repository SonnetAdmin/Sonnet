package sonnet.reference.playwright;

/**
 * Thrown when an element does not reach a stable state (size or position)
 * within the configured timeout.
 */
public class ElementNotStableException extends CPOException {

    public ElementNotStableException(String message) {
        super(message);
    }
}

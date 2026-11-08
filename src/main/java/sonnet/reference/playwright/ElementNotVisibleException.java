package sonnet.reference.playwright;

/**
 * Thrown when an element reaches a stable state (width : 0, height: 0) but is not visible
 * within the configured timeout.
 */
public class ElementNotVisibleException extends CPOException {

    public ElementNotVisibleException(String message) {
        super(message);
    }
}

package sonnet.reference.playwright.locators;

import com.microsoft.playwright.options.AriaRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Target {
    private Target secondaryTarget;
    private String selector;

    private String value;
    private String key;
    private Type type;
    private AriaRole ariaRole;

    public enum Type {
        ALT,
        ATTRIBUTE,
        ATTRIBUTE_VALUE,
        ATTRIBUTE_VALUE_CONTAINS,
        CHILDREN,
        CLASS,
        CLASSES,
        CSS,
        DATA,
        HREF,
        HTML_ATTRIBUTE,
        HTML_ATTRIBUTE_VALUE,
        HTML_ATTRIBUTE_VALUE_CONTAINS,
        HTML_TAG,
        HTML_TAG_CONTAINS_TEXT,
        HTML_TAG_WITH_TEXT,
        ID,
        LABEL,
        LEAF,
        LINK_TEXT,
        NAME,
        PARTIAL_ATTRIBUTE,
        PARTIAL_LINK_TEXT,
        PARTIAL_TEXT,
        PLACEHOLDER,
        ROLE,
        ROLE_AND_LABEL,
        ROLE_LITERAL,
        ROOT,
        SIBLINGS,
        SPLICE,
        SRC,
        STYLE,
        TAG,
        TAG_CONTAINS_TEXT,
        TAG_WITH_TEXT,
        TEST_ID,
        TEXT,
        TITLE,
        TYPE,
        VALUE,
        XPATH,

        /////////////////////////////
        // HTML 5 TEST ATTRIBUTES  //
        /////////////////////////////

        // TODO Only include standard HTML 5 data attribute.
        // The Angular specific ones should be moved.
        DATA_CI_ID, // Angular for sure.
        DATA_TEST_ID_CONTAINS,  // Angular ?
        DATA_ENDPOINT,
        DATA_FIELD,
        DATA_TEST_ID,
        DATA_ICON_NAME,  // Angular ?
        DATA_TEST,

        /////////////////////////////////////////////////
        // TO BE WITHDRAWN. REPLACED WITH CONJUNCTORS //
        ////////////////////////////////////////////////

        TAG_WITH_ID,
        TAG_WITH_CLASS,
        TAG_WITH_TITLE,
        TAG_WITH_NAME,
        TAG_WITH_VALUE, DATA_TEST_ID_SUFFIX, DATA_TEST_ATTRIBUTE,
        ///////////////
    }

    public String getSelector() {
        return selector;
    }

    public Target getSecondaryTarget() {
        return secondaryTarget;
    }

    public String getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public boolean isConjunction() {
        return secondaryTarget != null;
    }

    /**
     * Conjunction method — combines this Target with another using AND logic.
     * <p>Example: {@code tag(SPAN).and(text("hello"))} produces a selector
     * matching a SPAN element that also contains the exact text "hello".</p>
     */
    public Target and(Target other) {
        if (this.isConjunction()) {
            throw new IllegalStateException("Cannot combine more than two targets in a conjunction.");
        }
        switch(this.getType()) {
            case SPLICE -> throw new IllegalArgumentException("Cannot combine a SPLICE target.");
            case ROLE_AND_LABEL, ROLE, LABEL ->
                    throw new IllegalArgumentException("Playwright protected Targets. Cannot combine a " + this.getType() + " target in a conjunction.");
            default -> {
                // No action needed for other types
            }
        }
        switch(other.getType()) {
            case SPLICE -> throw new IllegalArgumentException("Cannot combine a SPLICE target.");
            case ROLE_AND_LABEL, ROLE, LABEL ->
                    throw new IllegalArgumentException("Cannot combine Playwright core Targets.");
            default -> {
                // No action needed for other types
            }
        }
        secondaryTarget = other;
        return this;
    }

    // e.g. Target : className("MyClass"));
    public Target(String selector, Type type) {
        this.selector = selector;
        this.value = selector;
        this.type = type;
    }

    // With selector — primary constructor for TargetFactory
    public Target(String selector, String value, Type type) {
        this.selector = selector;
        this.value = value;
        this.type = type;
    }

    // With selector + key/value pair
    public Target(String selector, String key, String value, Type type) {
        this.selector = selector;
        this.key = key;
        this.value = value;
        this.type = type;
    }

    public Target(Type type) {
        // CHILDREN
        // SIBLINGS
        // ROOT
        // LEAF
        // TAG
        this.type = type;
    }

    // ROLE_AND_LABEL: stores the ARIA role enum and its accessible-name label.
    // Selector is not used — decode() in CommonPage routes this to page.getByRole(role, name).
    public Target(AriaRole ariaRole, String label, Type type) {
        this.ariaRole = ariaRole;
        this.key = "role";
        this.value = label;
        this.type = type;
    }

    // ARIA ROLE only
    public Target(AriaRole ariaRole, Type type) {
        this.ariaRole = ariaRole;
        this.type = type;
    }
}


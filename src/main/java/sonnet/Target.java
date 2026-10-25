package sonnet;

import com.microsoft.playwright.options.AriaRole;

public class Target {
    private String selector;
    private Enum.Type type;
    private String value;
    private AriaRole ariaRole;

    public Target(String selector, Enum.Type type, String value) {
        this.selector = selector;
        this.type = type;
        this.value = value;
    }

    public Target(AriaRole ariaRole, Enum.Type type) {
        this.ariaRole = ariaRole;
        this.type = type;
        this.value = ariaRole.toString();
    }

    public Target(AriaRole ariaRole, String label, Enum.Type type) {
        this.ariaRole = ariaRole;
        this.value = label;
        this.type = type;
    }

        public Target(String selector, String prefix, String suffix, Enum.Type type) {
        this.selector = selector;
        this.type = type;
        this.value = "(Prefix: "+prefix +") (Suffix: " + suffix+")";
    }

    public Enum.Type getType() {
        return type;
    }

    public String getSelector() {
        return selector;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return type.name() + ":" + selector;
    }

    public AriaRole getAriaRole() {
        return ariaRole;
    }
}

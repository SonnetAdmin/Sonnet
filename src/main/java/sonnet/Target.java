package sonnet;

public class Target {
    private String selector;
    private Enum.Type type;
    private String value;

    public Target(String selector, Enum.Type type, String value) {
        this.selector = selector;
        this.type = type;
        this.value = value;
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
}

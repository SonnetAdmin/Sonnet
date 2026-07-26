package sonnet;

public class Target {
    private String selector;
    private Enum.Type type;

    public Target(String selector, Enum.Type type) {
        this.selector = selector;
        this.type = type;
    }

    public Enum.Type getType() {
        return type;
    }

    public String getSelector() {
        return selector;
    }

    public String toString() {
        return type.name() + ":" + selector;
    }
}

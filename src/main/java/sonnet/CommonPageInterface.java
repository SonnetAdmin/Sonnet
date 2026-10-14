package sonnet;

public interface CommonPageInterface {
    enum Action{
        BACKSPACE,
        DOWN,
        ENTER,
        HOME,
        LEFT,
        RIGHT,
        SPACE,
        TAB,
        UP;
    }

    enum Trait{
        ENABLED,
        HEIGHT,
        SELECTED,
        TAG,
        TEXT,
        VISIBLE,
        WIDTH,
        XPOS,
        YPOS;
    }

    void box(Target target);
    void box();
    void unbox();
    void click();
    void choose(int index);
    void choose(String name);
    void cognate(Target destination);
    void cognate(Target origin,Target destination);
    void collect(String locator);
    void collect(Target target);
    void compose(Action action);
    void compose(String text);
    void compose(int number);
    void end();
    void focus(Target target);
    String get(Trait trait);
    void go(String url);
    void open();
    void probe(Target target);
    int size();
    void submit();
    void log(String message);

}

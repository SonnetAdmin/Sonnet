package sonnet;

public interface CommonPageInterface {
    enum Action {
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

    enum Trait {
        ALT,
//        ATTRIBUTES,
        CLASS,
        CONTENT,
        DISPLAYED,
        ENABLED,
        HEIGHT,
        HREF,
        ID,
        LANG,
        NAME,
        PLACEHOLDER,
        REQUIRED,
        SELECTED,
        STYLE,
        TAG,
        TEXT,
        TEXT_TRIMMED,
        TITLE,
        TYPE,
        VALUE,
        VISIBLE,
        WIDTH,
        XPOS,
        YPOS;
    }

    void box(Target target);
    void box();
    void choose(int index);
    void choose(String name);
    void click();
    void cognate(Target destination);
    void cognate(Target origin, Target destination);
    void collect(String locator);
    void collect(Target target);
    void compose(Action action);
    void compose(String text);
    void end();
    void focus(Target target);
    String get(Trait trait);
    void go(String url);
    void log(String message);
    void open();
    void probe(Target target);
    int size();
    void submit();
    void unbox();
}

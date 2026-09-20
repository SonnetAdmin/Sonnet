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

    CommonPage click();
    CommonPage choose(int index);
    CommonPage choose(String name);
    CommonPage collect(String locator);
    CommonPage collect(Target target);
    CommonPage compose(Action action);
    CommonPage compose(String text);
    CommonPage end();
    CommonPage focus(Target target);
    String get(Trait trait);
    CommonPage go(String url);
    CommonPage open();
    CommonPage probe(Target target);
    int size();
    CommonPage submit();

}

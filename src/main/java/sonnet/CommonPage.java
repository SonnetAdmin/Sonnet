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
    enum DialogAction{
        OK,
        CANCEL;
    }

    CommonPage click();
    CommonPage choose(String name);
    CommonPage choose(int index);
    CommonPage collect(String locator);
    CommonPage compose(String text);
    CommonPage compose(Action action);
    CommonPage focus(Target target);

    CommonPage collect(Target target);

    String get(Trait trait);
    CommonPage go(String url);
    CommonPage open();
    CommonPage sleep(int milliseconds);
    CommonPage submit();
    String getTitle();
    CommonPage upload(Target target, String filePath);
    CommonPage dialog(DialogAction action);
    void store(String key, String value);
    String retrieve(String key);

}

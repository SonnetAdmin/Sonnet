package org.sonnet;

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
    CommonPage choose(String name);
    CommonPage choose(int index);
    CommonPage collect(String locator);
    CommonPage compose(String text);
    CommonPage compose(Action action);
    CommonPage expect(String locator);
    CommonPage focus(String locator);
    String get(Trait trait);
    CommonPage go(String url);
    CommonPage open();
    CommonPage printElement();
    CommonPage printElements();
    CommonPage sleep(int milliseconds);
    CommonPage submit();

}

package sonnet.reference.playwright;

import sonnet.reference.playwright.locators.Target;

import java.io.File;
import java.util.List;

interface CommonPageInterface {

    void open();

    void open(Enums.Browser browser);

    void close();

    void quit();

    void go(String url);

    void refresh();

    void ready();

    void go(Enums.Direction direction);

    void focus(Target target);

    void probe(Target target, int waitTime);

    void focus(Target... targets);

    void depart(Target target);

    void depart(Target target, Enums.Targets targets);

    void depart(List<Target> targets);

    void depart(Target... targets);

    void origin();

    void probe(Target target);

    void end();

    boolean peek(Target target);

    boolean peek(Target target, int waitTime);

    void collect(Target target);

    int size();

    void cognate(Target origin, Target target);

    void cognate(Target target);

    void choose(int index);

    void choose(String text);

    void choose(Target target);

    void choose(Enums.Index index);

    void choose(Enums.ListIndex listIndex);

    void secure(Target target);

    void secure();

    void click();

    void click(Target target);

    void check();

    void check(Target target);

    void uncheck();

    void uncheck(Target target);

    void dblclick();

    void dblclick(Target target);

    void clear();

    void hover();

    void hover(Target target);


    void dragdrop(Target source, Target destination);

    String get();

    String get(Enums.ElementTrait elementTrait);

    void upload(String path);

    void upload(List<String> paths);

    void upload(Target target, String path);

    void upload(Target target, List<String> paths);

    void descend();

    void descend(Target target);

    void ascend();

    void ascend(int levels);

    void ascend(Enums.Index index);

    void ascend(Enums.Tag tag);

    void traverse();

    void traverse(int index);

    void traverse(Enums.NodeEnum index);

    void reverse();

    void reverse(int index);

    void reverse(Enums.NodeEnum index);

    void scroll();

    void scroll(Target target);

    void scroll(Enums.Scroll direction);

    void scroll(Target container, Enums.Scroll direction);

    void scrollUntil(Target container, Target visible);

    void frame(Target target);

    void frame(int index);

    void frame(Enums.Index index);

    void frame();

    void frame(String value);
    void deframe();


    void window();

    void window(Enums.Window window);

    void window(int index);

    String getUrl();

    void tab(Enums.Window window);

    void tab(int index);

    void tab();

    void matches(String text);

    void contains(String partialText);

    File takeScreenshot(boolean fullPage);

    File takeScreenshot(Target target);

    void checked();

    void unchecked();

    void enabled();

    void editable();

    void disabled();

    void clickable();

    void unclickable();

    void visible();

    void hidden();


    void select(Target target, String... option);

    void select(Target target, int... index);

    void select(String label, String... option);

    void select(String label, Enums.Index... index);

    void select(Target target, Enums.Selector S, String... option);

    void select(String label, Enums.Selector S, String... option);

    void box();

    void box(Target target);

    void unbox();

    void store(String key, String value);

    String retrieve(String key);

    void log(String... args);
    void log(Enums.LogStatus logStatus);
    void log(Enums.LogStatus logStatus, String reason);

    void trigger(Enums.Event event);

    void javascript(String script);

    <T extends sonnet.reference.playwright.Performable> void perform(T runner);
}

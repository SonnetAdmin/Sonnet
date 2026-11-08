package sonnet.reference;

import sonnet.Enum;
import sonnet.Target;

import java.io.File;
import java.util.List;

interface InterfaceReference {

    void open();

    //void open(sonnet.Enum.Browser browser);

    void close();

    void quit();

    void go(String url);

    void refresh();

    void ready();

   // void go(sonnet.Enum.Direction direction);

    void focus(Target target);

    void probe(Target target, int waitTime);

    void focus(Target... targets);

    void depart(Target target);
    
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

    //void choose(sonnet.sonnet.Enum.Index index);

   // void choose(sonnet.sonnet.Enum.ListIndex listIndex);

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

    String get(sonnet.Enum.Trait elementTrait);

    void upload(String path);

    void upload(List<String> paths);

    void upload(Target target, String path);

    void upload(Target target, List<String> paths);

    void descend();

    void descend(Target target);

    void ascend();

    void ascend(int levels);

    void ascend(sonnet.Enum.Index index);

    void ascend(Enum.HTMLTag tag);

    void traverse();

    void traverse(int index);

    void traverse(Enum.Index index);

    void reverse();

    void reverse(int index);

    void reverse(Enum.Index index);

    void scroll();

    void scroll(Target target);

    //void scroll(sonnet.Enum.Scroll direction);

    //void scroll(Target container, sonnet.Enum.Scroll direction);

    void scrollUntil(Target container, Target visible);

    void frame(Target target);

    void frame(int index);

    void frame(sonnet.Enum.Index index);

    void frame();

    void frame(String value);
   // void deframe();
    //void deframe();

    void window();

    //void window(sonnet.Enum.Window window);

    void window(int index);

    String getUrl();

    //void tab(sonnet.Enum.Window window);

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

    void select(String label, sonnet.Enum.Index... index);

    //void select(Target target, sonnet.Enum.Selector S, String... option);

    //void select(String label, sonnet.Enum.Selector S, String... option);

    void box();

    void box(Target target);

    void unbox();

    void store(String key, String value);

    String retrieve(String key);

    void log(String... args);
    //void log(sonnet.Enum.LogStatus logStatus);
    //void log(sonnet.Enum.LogStatus logStatus, String reason);

   // void trigger(sonnet.Enum.Event event);

    void javascript(String script);

   // <T extends com.risknarrative.qa.automation.playwright.Performable> void perform(T runner);
}

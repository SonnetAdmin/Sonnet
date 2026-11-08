package sonnet.reference.playwright;

import sonnet.reference.playwright.locators.Target;
import sonnet.reference.playwright.locators.TargetFactory;
import sonnet.reference.playwright.utils.ListUtils;
import sonnet.reference.playwright.visualregression.VisualServiceImpl;
import sonnet.reference.playwright.visualregression.VisualTestResult;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static sonnet.reference.playwright.Enums.ElementTrait.TAG;
import static sonnet.reference.playwright.Enums.ElementTrait.TEXT;
import static sonnet.reference.playwright.utils.Checks.*;


public class CommonPageObject extends TargetFactory implements DOMInterface {

    // Singletons
    private final CommonPage commonPage = CommonPage.getInstance();
    private final Keyboard keysInteraction = Keyboard.getInstance();
    private final VisualServiceImpl commonVisual = VisualServiceImpl.getInstance();

    public CommonPageObject open() {
        commonPage.open();
        return this;
    }

    public CommonPageObject loop() {
        return this;
    }

    public CommonPageObject resetState() {
        commonPage.resetState();
        return this;
    }

    public CommonPageObject setScreenSize(int width, int height) {
        commonPage.setScreenSize(width, height);
        return this;
    }

    public CommonPageObject setScreenSize(Enums.ScreenSize screenSize) {
        commonPage.setScreenSize(screenSize);
        return this;
    }

    public void quit() {
        commonPage.quit();
    }

    public void close() {
        commonPage.close();
    }

    public File takeScreenshot(boolean fullpage) {
        return commonPage.takeScreenshot(fullpage);
    }

    public File takeScreenshot(Target target) {
        return commonPage.takeScreenshot(target);
    }

    public CommonPageObject probe(Target target) {
        commonPage.probe(target);
        return this;
    }

    public CommonPageObject end() {
        commonPage.end();
        return this;
    }

    public CommonPageObject choose(Enums.Index index) {
        commonPage.choose(index);
        return this;
    }

    public CommonPageObject choose(Enums.ListIndex index) {
        commonPage.choose(index);
        return this;
    }

    public CommonPageObject log(String... args) {
        commonPage.log(args);
        return this;
    }

    public CommonPageObject log(Enums.LogStatus logStatus) {
        commonPage.log(logStatus);
        return this;
    }

    public CommonPageObject log(Enums.LogStatus logStatus, String reason) {
        commonPage.log(logStatus, reason);
        return this;
    }

    public CommonPageObject store(String key, String value) {
        commonPage.store(key, value);
        return this;
    }

    public String retrieve(String key) {
        return commonPage.retrieve(key);
    }

    public CommonPageObject origin() {
        commonPage.origin();
        return this;
    }

    public CommonPageObject ascend() {
        commonPage.ascend();
        return this;
    }

    public CommonPageObject ascend(int levels) {
        commonPage.ascend(levels);
        return this;
    }

    public CommonPageObject ascend(Enums.Index index) {
        commonPage.ascend(index);
        return this;
    }

    public CommonPageObject ascend(Enums.Tag tag) {
        commonPage.ascend(tag);
        return this;
    }

    public CommonPageObject descend() {
        commonPage.descend();
        return this;
    }

    public CommonPageObject descend(Target target) {
        commonPage.descend(target);
        return this;
    }

    public CommonPageObject cognate(Target origin, Target target) {
        commonPage.cognate(origin, target);
        return this;
    }

    public CommonPageObject cognate(Target target) {
        commonPage.cognate(target);
        return this;
    }

    public CommonPageObject go(String url) {
        commonPage.go(url);
        return this;
    }

    public CommonPageObject refresh() {
        commonPage.refresh();
        return this;
    }

    public CommonPageObject go(Enums.Direction direction) {
        commonPage.go(direction);
        return this;
    }

    public CommonPageObject focus(Target target) {
        commonPage.focus(target);
        return this;
    }

    public CommonPageObject focus(Target... targets) {
        commonPage.focus(targets);
        return this;
    }

    public CommonPageObject clickable() {
        commonPage.clickable();
        return this;
    }

    public CommonPageObject editable() {
        commonPage.editable();
        return this;
    }

    public CommonPageObject enabled() {
        commonPage.enabled();
        return this;
    }

    public CommonPageObject disabled() {
        commonPage.disabled();
        return this;
    }

    public CommonPageObject visible() {
        commonPage.visible();
        return this;
    }

    public CommonPageObject hidden() {
        commonPage.hidden();
        return this;
    }

    public CommonPageObject unclickable() {
        commonPage.unclickable();
        return this;
    }

    public CommonPageObject checked() {
        commonPage.checked();
        return this;
    }

    public CommonPageObject unchecked() {
        commonPage.unchecked();
        return this;
    }

    public CommonPageObject secure(Target target) {
        commonPage.secure(target);
        return this;
    }

    public CommonPageObject secure() {
        commonPage.secure();
        return this;
    }

    public CommonPageObject collect(Target target) {
        commonPage.collect(target);
        return this;
    }

    public CommonPageObject click() {
        commonPage.click();
        return this;
    }

    public CommonPageObject click(Target target) {
        commonPage.click(target);
        return this;
    }

    public CommonPageObject check() {
        commonPage.check();
        return this;
    }

    public CommonPageObject check(Target target) {
        commonPage.check(target);
        return this;
    }

    public CommonPageObject uncheck() {
        commonPage.uncheck();
        return this;
    }

    public CommonPageObject uncheck(Target target) {
        commonPage.uncheck(target);
        return this;
    }

    public CommonPageObject dblclick() {
        commonPage.dblclick();
        return this;
    }

    public CommonPageObject dblclick(Target target) {
        commonPage.dblclick(target);
        return this;
    }

    public CommonPageObject dragdrop(Target source, Target destination) {
        commonPage.dragdrop(source, destination);
        return this;
    }

    public CommonPageObject scroll() {
        commonPage.scroll();
        return this;
    }

    public CommonPageObject scroll(Target target) {
        commonPage.scroll(target);
        return this;
    }

    public CommonPageObject scroll(Enums.Scroll direction) {
        commonPage.scroll(direction);
        return this;
    }

    public CommonPageObject scroll(Target container, Enums.Scroll direction) {
        commonPage.scroll(container, direction);
        return this;
    }

    public CommonPageObject scrollUntil(Target container, Target visible) {
        commonPage.scrollUntil(container, visible);
        return this;
    }

    public CommonPageObject frame(Target target) {
        commonPage.frame(target);
        return this;
    }

    public CommonPageObject frame(String value) {
        commonPage.frame(value);
        return this;
    }

    public CommonPageObject frame(int index) {
        commonPage.frame(index);
        return this;
    }

    public CommonPageObject frame(Enums.Index index) {
        commonPage.frame(index);
        return this;
    }

    public CommonPageObject frame() {
        commonPage.frame();
        return this;
    }

    public CommonPageObject deframe() {
        commonPage.deframe();
        return this;
    }

    public CommonPageObject choose(int index) {
        commonPage.choose(index);
        return this;
    }

    public CommonPageObject choose(String text) {
        commonPage.choose(text);
        return this;
    }

    public CommonPageObject choose(Target target) {
        commonPage.choose(target);
        return this;
    }

    public CommonPageObject compose(String keysToSend) {
        keysInteraction.compose(keysToSend);
        return this;
    }

    public CommonPageObject compose(String key, int repeat) {
        keysInteraction.compose(key, repeat);
        return this;
    }

    public CommonPageObject compose(Keyboard.Key key) {
        keysInteraction.compose(key);
        return this;
    }

    public CommonPageObject compose(Keyboard.Key key, int repeat) {
        keysInteraction.compose(key, repeat);
        return this;
    }

    public CommonPageObject clear() {
        commonPage.clear();
        return this;
    }

    public CommonPageObject hold(String keyToHold) {
        keysInteraction.hold(keyToHold);
        return this;
    }

    public CommonPageObject release(String keyToRelease) {
        keysInteraction.release(keyToRelease);
        return this;
    }

    public CommonPageObject alert(Keyboard.AlertAction action) {
        keysInteraction.alert(action);
        return this;
    }

    public CommonPageObject alert(String keysToSend) {
        keysInteraction.alert(keysToSend);
        return this;
    }

    public CommonPageObject press(String keys) {
        keysInteraction.press(keys);
        return this;
    }

    public CommonPageObject submit() {
        keysInteraction.submit();
        return this;
    }

    public CommonPageObject copy() {
        String mod = Keyboard.modifier();
        keysInteraction.press(mod + "+a");
        keysInteraction.press(mod + "+c");
        return this;
    }

    public CommonPageObject paste() {
        String mod = Keyboard.modifier();
        keysInteraction.press(mod + "+v");
        return this;
    }

    public CommonPageObject matches(String text) {
        commonPage.matches(text);
        return this;
    }

    public CommonPageObject contains(String text) {
        commonPage.contains(text);
        return this;
    }

    public boolean peek(Target target) {
        return commonPage.peek(target);
    }

    public boolean peek(Target target, int waitTime) {
        return commonPage.peek(target, waitTime);
    }

    public CommonPageObject trigger(Enums.Event event) {
        commonPage.trigger(event);
        return this;
    }

    public CommonPageObject ready() {
        commonPage.ready();
        return this;
    }

    public CommonPageObject javascript(String script) {
        commonPage.javascript(script);
        return this;
    }

    public CommonPageObject hover() {
        commonPage.hover();
        return this;
    }

    public CommonPageObject hover(Target target) {
        commonPage.hover(target);
        return this;
    }

    public CommonPageObject tab() {
        commonPage.tab();
        return this;
    }

    public CommonPageObject tab(int index) {
        commonPage.tab(index);
        return this;
    }

    public CommonPageObject tab(Enums.Window window) {
        commonPage.tab(window);
        return this;
    }

    public CommonPageObject window(int index) {
        commonPage.window(index);
        return this;
    }

    public CommonPageObject window() {
        commonPage.window();
        return this;
    }

    public CommonPageObject window(Enums.Window window) {
        commonPage.window(window);
        return this;
    }

    public CommonPageObject depart(Target target) {
        commonPage.depart(target);
        return this;
    }

    public CommonPageObject depart(Target target, Enums.Targets targets) {
        commonPage.depart(target, targets);
        return this;
    }

    public CommonPageObject depart(List<Target> targets) {
        commonPage.depart(targets);
        return this;
    }

    public CommonPageObject depart(Target... targets) {
        commonPage.depart(targets);
        return this;
    }

    public String get() {
        return commonPage.get();
    }

    public String get(Enums.ElementTrait trait) {
        return commonPage.get(trait);
    }

    public CommonPageObject traverse() {
        commonPage.traverse();
        return this;
    }

    public CommonPageObject traverse(int index) {
        commonPage.traverse(index);
        return this;
    }

    public CommonPageObject traverse(Enums.NodeEnum index) {
        commonPage.traverse(index);
        return this;
    }

    public CommonPageObject reverse() {
        commonPage.reverse();
        return this;
    }

    public CommonPageObject reverse(int index) {
        commonPage.reverse(index);
        return this;
    }

    public CommonPageObject reverse(Enums.NodeEnum index) {
        commonPage.reverse(index);
        return this;
    }

    @Override
    public int size() {
        return commonPage.size();
    }
    
    ///////////////
    // DOM UTILS //
    ///////////////

    @Override
    public CommonPageObject urlEquals(String url) {
        String actual = commonPage.getUrl();
        log("urlEquals()", "Expected: " + url);
        log("urlEquals()", "Actual:   " + actual);
        assertTrue(actual.equals(url));
        return this;
    }

    @Override
    public CommonPageObject urlContains(String url) {
        String actual = commonPage.getUrl();
        log("urlContains()", "Expected:",url);
        log("urlContains()", "Actual:  ",actual);
        assertTrue(actual.contains(url));
        return this;
    }

    @Override
    public CommonPageObject urlStartsWith(String url) {
        String actual = commonPage.getUrl();
        log("urlStartsWith()", "Expected: ",url);
        log("urlStartsWith()", "Actual:   ",actual);
        assertTrue(actual.startsWith(url));
        return this;
    }

    @Override
    public CommonPageObject urlEndsWith(String url) {
        String actual = commonPage.getUrl();
        log("urlEndsWith()", "Expected:",url);
        log("urlEndsWith()", "Actual:  ",actual);
        assertTrue(actual.endsWith(url));
        return this;
    }

    @Override
    public CommonPageObject titleEquals(String title) {
        String actual = commonPage.getTitle();
        log("titleEquals()", "Expected:",title);
        log("titleEquals()", "Actual:   ",actual);
        assertTrue(actual.equals(title));
        return this;
    }

    @Override
    public CommonPageObject titleContains(String title) {
        String actual = commonPage.getTitle();
        log("titleContains()", "Expected: ",title);
        log("titleContains()", "Actual:   ",actual);
        assertTrue(actual.contains(title));
        return this;
    }

    @Override
    public CommonPageObject titleStartsWith(String title) {
        String actual = commonPage.getTitle();
        log("titleStartsWith()", "Expected: ",title);
        log("titleStartsWith()", "Actual:   ",actual);
        assertTrue(actual.startsWith(title));
        return this;
    }

    @Override
    public CommonPageObject titleEndsWith(String title) {
        String actual = commonPage.getTitle();
        log("titleEndsWith()", "Expected: ",title);
        log("titleEndsWith()", "Actual:   ",actual);
        assertTrue(actual.endsWith(title));
        return this;
    }

    @Override
    public CommonPageObject textEquals(String text) {
        String actual = commonPage.get(TEXT);
        log("textEquals()","Expected: ",text);
        log("textEquals()","Actual:   ",actual);
        assertTrue(actual.equals(text));
        return this;
    }

    @Override
    public CommonPageObject textDiffers(String text) {
        String actual = commonPage.get(TEXT);
        log("textDiffers()", "Expected: ",text);
        log("textDiffers()", "Actual:   ",actual);
        assertFalse(actual.equals(text));
        return this;
    }

    @Override
    public CommonPageObject textContains(String text) {
        String actual = commonPage.get(TEXT);
        log("textContains()", "Expected: ",text);
        log("textContains()", "Actual:   ",actual);
        assertTrue(actual.contains(text));
        return this;
    }

    @Override
    public CommonPageObject textStartsWith(String text) {
        String actual = commonPage.get(TEXT);
        log("textStartsWith()","Expected: ",text);
        log("textStartsWith()","Actual:   ",actual);
        assertTrue(actual.startsWith(text));
        return this;
    }

    @Override
    public CommonPageObject textEndsWith(String text) {
        String actual = commonPage.get(TEXT);
        log("textEndsWith()","Expected: ",text);
        log("textEndsWith()","Actual:   ",actual);
        assertTrue(actual.endsWith(text));
        return this;
    }

    @Override
    public CommonPageObject textPresent() {
        boolean actual = !commonPage.get(TEXT).isEmpty();
        log("textPresent()");
        assertTrue(actual);
        return this;
    }

    @Override
    public CommonPageObject textPresent(String text) {
        String actual = commonPage.get(TEXT);
        log("textPresent()","Expected: ",text);
        log("textPresent()","Actual:   ",actual);
        assertTrue(actual.equals(text));
        return this;
    }

    @Override
    public CommonPageObject textNotPresent() {
        boolean actual = commonPage.get(TEXT).isEmpty();
        log("textNotPresent()");
        assertTrue(actual);
        return this;
    }

    @Override
    public CommonPageObject textNotPresent(String text) {
        String actual = commonPage.get(TEXT);
        log("textNotPresent()",text);
        assertTrue(!actual.contains(text));
        return this;
    }

    @Override
    public CommonPageObject textHasLength(int length) {
        int actual = commonPage.get(TEXT).length();
        log("textHasLength()",length+"");
        assertTrue(actual == length);
        return this;
    }

    @Override
    public CommonPageObject attributeEquals(Enums.ElementTrait attribute, String value) {
        String actual = commonPage.get(attribute);
        log("attributeEquals()",attribute.name(),value);
        assertTrue(actual != null && !actual.isEmpty());
        assertTrue(actual.equals(value));
        return this;
    }

    @Override
    public CommonPageObject attributeContains(Enums.ElementTrait attribute, String value) {
        String actual = commonPage.get(attribute);
        log("attributeContains()",attribute.name(),value);
        assertTrue(actual != null && !actual.isEmpty());
        assertTrue(actual.contains(value));
        return this;
    }

    @Override
    public CommonPageObject attributeStartsWith(Enums.ElementTrait attribute, String value) {
        String actual = commonPage.get(attribute);
        log("attributeStartsWith()",attribute.name(),value);
        assertTrue(actual != null && !actual.isEmpty());
        assertTrue(actual.startsWith(value));
        return this;
    }

    @Override
    public CommonPageObject attributeEndsWith(Enums.ElementTrait attribute, String value) {
        String actual = commonPage.get(attribute);
        log("attributeEndsWith()", "Expected: " + value, "Actual: " + actual);
        assertTrue(actual != null && !actual.isEmpty());
        assertTrue(actual.endsWith(value));
        return this;
    }

    @Override
    public CommonPageObject elementPresent(Target target) {
        boolean actual = commonPage.peek(target);
        log("elementPresent()", target.getType().name(), target.getValue());
        assertTrue(actual);
        return this;
    }

    @Override
    public CommonPageObject elementNotPresent(Target target) {
        boolean actual = !commonPage.peek(target);
        log("elementPresent()", target.getType().name(), target.getValue());
        assertTrue(actual);
        return this;
    }

    @Override
    public CommonPageObject collectionPresent() {
        log("collectionPresent()");
        assertTrue("Collection not present", commonPage.size() > 0);
        return this;
    }

    @Override
    public CommonPageObject collectionNotPresent() {
        log("collectionNotPresent()");
        assertTrue("Collection present", commonPage.size() == 0);
        return this;
    }

    @Override
    public CommonPageObject collectionHasSize(int size) {
        int actual = commonPage.size();
        log("collectionHasSize()", "Expected : " + size);
        log("collectionHasSize()", "Actual   : " + actual);
        assertEquals(size, actual);
        return this;
    }

    @Override
    public CommonPageObject collectionLargerThan(int size) {
        int actual = commonPage.size();
        log("collectionLargerThan()",size+"");
        assertTrue(actual > size);
        return this;
    }

    @Override
    public CommonPageObject collectionSmallerThan(int size) {
        int actual = commonPage.size();
        log("collectionSmallerThan()",size+"");
        assertTrue(actual < size);
        return this;
    }

    @Override
    public CommonPageObject collectionIs(List<String> expected) {
        return collectionIs(expected, true);
    }

    @Override
    public CommonPageObject collectionIs(List<String> expected, boolean strict) {
        throwOnNull(expected, "expected");
        List<String> actual = commonPage.getCollectionAsText();
        boolean result = strict ? ListUtils.identical(expected, actual) : ListUtils.comparable(expected, actual);
        String mode = strict ? "STRICT" : "LENIENT";
        log("collectionIs(" + mode + "):: Expected: " + expected + " Actual: " + actual);
        assertTrue("collectionIs(" + mode + ") failed. Expected: " + expected + " Actual: " + actual, result);
        return this;
    }

    @Override
    public CommonPageObject collectionContains(String item) {
        throwOnNullOrEmpty(item, "item");
        List<String> actual = commonPage.getCollectionAsText();
        boolean result = ListUtils.presentInList(item, actual);
        log("collectionContains:: Item: [" + item + "] Present: " + result + " Collection: " + actual);
        assertTrue("Expected collection to contain [" + item + "] but collection was: " + actual, result);
        return this;
    }

    @Override
    public CommonPageObject collectionContains(List<String> items) {
        throwOnNullOrEmpty(items, "items");
        List<String> actual = commonPage.getCollectionAsText();
        for (String item : items) {
            boolean result = ListUtils.presentInList(item, actual);
            log("collectionContains:: Item: [" + item + "] Present: " + result + " Collection: " + actual);
            assertTrue("Expected collection to contain [" + item + "] but collection was: " + actual, result);
        }
        return this;
    }

    @Override
    public CommonPageObject collectionDoesNotContain(String item) {
        throwOnNullOrEmpty(item, "item");
        List<String> actual = commonPage.getCollectionAsText();
        boolean result = ListUtils.presentInList(item, actual);
        log("collectionDoesNotContain:: Item: [" + item + "] Absent: " + result + " Collection: " + actual);
        assertFalse("Expected collection NOT to contain [" + item + "] but collection was: " + actual, result);
        return this;
    }

    @Override
    public CommonPageObject collectionDoesNotContain(List<String> items) {
        throwOnNullOrEmpty(items, "items");
        List<String> actual = commonPage.getCollectionAsText();
        for (String item : items) {
            boolean result = ListUtils.presentInList(item, actual);
            log("collectionDoesNotContain:: Item: [" + item + "] Absent: " + result + " Collection: " + actual);
            assertFalse("Expected collection NOT to contain [" + item + "] but collection was: " + actual, result);
        }
        return this;
    }

    @Override
    public CommonPageObject tagIs(Enums.Tag tag) {
        String actual = commonPage.get(TAG);
        log("tagIs()\t",tag.name());
        assertTrue(actual.equalsIgnoreCase(tag.name()));
        return this;
    }

    @Override
    public CommonPageObject tagIsNot(Enums.Tag tag) {
        String actual = commonPage.get(TAG);
        log("tagIsNot()",tag.name());
        assertFalse(actual.equalsIgnoreCase(tag.name()));
        return this;
    }

    ///////////////////
    // END DOM UTILS //
    ///////////////////

    @Override
    public CommonPageObject elementPrint() {
        commonPage.elementPrint();
        return this;
    }

    @Override
    public CommonPageObject collectionPrint() {
        commonPage.collectionPrint();
        return this;
    }

    public CommonPageObject setThreshold(double threshold) {
        commonVisual.setThreshold(threshold);
        return this;
    }

    public CommonPageObject captureScreenshot(String filename, BufferedImage image) throws IOException { commonVisual.captureScreenshot(filename, image); return this; }

    public CommonPageObject saveBaseline(String name, BufferedImage image) throws IOException { commonVisual.saveBaseline(name, image); return this; }

    public double compareImages(BufferedImage baseline, BufferedImage actual) { return commonVisual.compareImages(baseline, actual); }

    public double compareImagesWithTolerance(BufferedImage baseline, BufferedImage actual, int tolerance) { return commonVisual.compareImagesWithTolerance(baseline, actual, tolerance); }

    public BufferedImage generateDiffImage(BufferedImage image1, BufferedImage image2) throws IOException { return commonVisual.generateDiffImage(image1, image2); }

    public void saveDiffImage(String filename, BufferedImage diffImage) throws IOException { commonVisual.saveDiffImage(filename, diffImage); }

    public VisualTestResult performVisualTest(String testName, BufferedImage actualImage, double threshold, int colorTolerance) throws IOException { return commonVisual.performVisualTest(testName, actualImage, threshold, colorTolerance); }

    public VisualTestResult performVisualTest(String testName, BufferedImage actualImage) throws IOException { return commonVisual.performVisualTest(testName, actualImage); }

    public void generateHTMLReport(List<VisualTestResult> results, String reportPath) throws IOException { commonVisual.generateHTMLReport(results, reportPath); }

    public CommonPageObject reload() { commonPage.getPage().reload(); return this; }

    public CommonPageObject select(Target target, String... options) { commonPage.select(target, options); return this; }

    public CommonPageObject select(String label, String... options) { commonPage.select(label, options); return this; }

    public CommonPageObject select(String label, Enums.Selector S, String... options) { commonPage.select(label, S, options); return this; }

    public CommonPageObject select(Target target, Enums.Selector S, String... options) { commonPage.select(target, S, options); return this; }

    public CommonPageObject select(Target target, int... index) { commonPage.select(target, index); return this; }

    public CommonPageObject select(String label, Enums.Index... index) { commonPage.select(label, index); return this; }

    public <T extends sonnet.reference.playwright.Performable> CommonPageObject perform(T runner) { commonPage.perform(runner); return this; }

    public CommonPageObject upload(String path) { commonPage.upload(path); return this; }

    public CommonPageObject upload(List<String> paths) { commonPage.upload(paths); return this; }

    public CommonPageObject upload(Target target, String path) { commonPage.upload(target, path); return this; }

    public CommonPageObject upload(Target target, List<String> paths) { commonPage.upload(target, paths); return this; }

    public CommonPageObject box() {
        commonPage.box();
        return this;
    }

    public CommonPageObject box(Target target) {
        commonPage.box(target);
        return this;
    }

    public CommonPageObject unbox() {
        commonPage.unbox();
        return this;
    }

    public String getUrl() {
        return commonPage.getUrl();
    }

    //////////////////////////////////////////////
    // PROTECTED.  ACCESSIBLE FROM PERFORMABLES //
    //////////////////////////////////////////////

    protected com.microsoft.playwright.Page getPage() {
        return commonPage.getPage();
    }

    protected com.microsoft.playwright.BrowserContext getContext() {
        return commonPage.getContext();
    }

    protected List<com.microsoft.playwright.Page> getPages() {
        return commonPage.getPages();
    }

    protected List<String> getCollectionAsText() {
        return commonPage.getCollectionAsText();
    }


}

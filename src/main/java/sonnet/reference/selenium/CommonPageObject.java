package sonnet.reference.selenium;

import org.openqa.selenium.WebDriver;
import sonnet.reference.selenium.locators.Target;
import sonnet.reference.selenium.locators.TargetFactory;
import sonnet.reference.selenium.performable.Performable;
import sonnet.reference.selenium.utils.Colours;

import java.awt.*;
import java.io.File;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static sonnet.reference.selenium.Enums.ElementTrait.TAG;
import static sonnet.reference.selenium.Enums.ElementTrait.TEXT;

public class CommonPageObject extends TargetFactory {

    // Singletons
    private final CommonPage commonPage = CommonPage.getInstance();
    private final Keyboard keysInteraction = Keyboard.getInstance();
    private final Navigation navigation = Navigation.getInstance();

    public LoopBuilder loop() {
        return commonPage.loop(this);
    }

    public LoopBuilder loop(int times) {
        return commonPage.loop(this, times);
    }

    /**
     * This will open the browser, to begin the test.
     * <br>
     * <b style="color:blue;">Default set to Chrome browser.</b>
     * <br>
     * @return CommonPageObject
     */
    public CommonPageObject open() {
        commonPage.open();
        return this;
    }

    /**
     * This will be utilised to output log information to the console.
     * <br>
     * <b style="color:blue;">log("Equals:: Expected value: " + value + " Actual value: " + actual);</b>
     * <br>
     * @param args - The arguments to be logged.
     * @return
     */
    public CommonPageObject log(String... args) {
        commonPage.log(args);
        return this;
    }

    public CommonPageObject log(Colours colour, String text) {
        commonPage.log(colour, text);
        return this;
    }

    /**
     * This will be utilised to output warning information to the console.
     * <br>
     * <b style="color:blue;">warn("Activated default in switch statement due to unmatched value passed.");</b>
     * <br>
     * @param text - The warning text to be logged.
     * @return
     */
    public CommonPageObject warn(String text) {
        commonPage.warn(text);
        return this;
    }

    /**
     * This will be utilised for navigation to a specific URL.
     * <br>
     * <b style="color:blue;">go("https://qa.trunarrative.cloud/");</b>
     * <br>
     * @param url - The URL to navigate to.
     * @return
     */
    public CommonPageObject go(String url) {
        commonPage.go(url);
        origin();
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

    //////////
    // PEEK //
    //////////


    /**
     * @deprecated
     * peek() replaces isPresent()
     * Use instead :
     * peek(Target target)
     * peek(Target target, int waitTime)
     * peek( int waitTime );
     */
    @Deprecated
    public boolean isPresent(Target target) {
        return commonPage.peek(target);
    }

    public boolean peek(Target target) {
        return commonPage.peek(target);
    }

    public boolean peek(Target target, int waitTime) {
        return commonPage.peek(target, waitTime);
    }

    public CommonPageObject peek(int waitTime) {
        commonPage.peek(waitTime);
        return this;
    }

    /**
     * This will locate multiple targets with a default timeout of 2 seconds.
     * <br>
     * <br>
     * <b style="color:blue;">focus(id("checkbox1"), id("checkbox2"),id("checkbox3")).click()</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;input id="checkbox1" &gt;
     * &lt;input id="checkbox2" &gt;
     * &lt;input id="checkbox3" &gt;
     * </div>
     * @param targets
     * @return
     */
    public CommonPageObject focus(Target... targets ) {
        commonPage.focus(targets);
        return this;
    }

    /**
     * This will locate multiple targets with a custom timeout.
     * <br>
     * <br>
     * <b style="color:blue;">focus(5, id("checkbox1"), id("checkbox2"),id("checkbox3")).click()</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;input type="checkbox" id="checkbox1" &gt;
     * &lt;input type="checkbox" id="checkbox2" &gt;
     * &lt;input type="checkbox" id="checkbox3" &gt;
     * </div>
     * @param targets
     * @return
     */
    public CommonPageObject focus(Integer waitTime, Target... targets) {
        commonPage.focus(waitTime, targets);
        return this;
    }

    public CommonPageObject focus(Integer waitTime) {
        commonPage.focus(waitTime);
        return this;
    }

    /**
     * This will proceed to apply a wait time at the given stage applied.
     * <br>
     * <b style="color:blue;">probe(5);</b>
     * <br>
     * @param waitTime - The volume of wait time to be applied, in seconds.
     * @return
     */
    public CommonPageObject probe(int waitTime) {
        commonPage.probe(waitTime);
        return this;
    }

    public CommonPageObject probe(Target target, Enums.ElementState elementState) {
        commonPage.probe(target, elementState);
        return this;
    }

    public CommonPageObject probe(Target target) {
        commonPage.probe(target);
        return this;
    }

    /**
     * The end() statement concludes the conditional probe() statement
     * <br>
     * <b style="color:blue;">probe(ACCEPT_COOKIES_BUTTON).click().end()</b>
     * <br>
     * @return
     */
    public CommonPageObject end() {
        commonPage.end();
        return this;
    }

    public CommonPageObject collect(Target target) {
        commonPage.collect(target);
        return this;
    }

    public CommonPageObject collect(List<Target> targets) {
        commonPage.collect(targets);
        return this;
    }

    public CommonPageObject collect(int waitTime) {
        commonPage.collect(waitTime);
        return this;
    }

    /**
     * This will locate the nearest element with the given origin.
     * <br>
     * <br>
     * <b style="color:blue;">Target origin = id("parent")</b>
     * <br>
     * <b style="color:blue;">Target nearestRelative = tagWithText(DIV,"Mary's child 1")</b>
     * <br>
     * <b style="color:blue;">cognate(origin, nearestRelative)</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;div id="parent" &gt; Mary
     * <br>
     * &lt;div id="children-1" &gt; Mary's child 1 &lt;/div&gt;
     * <br>
     * &lt;div id="children-2" &gt; Mary's child 2 &lt;/div&gt;
     * <br>
     *  &lt;/div&gt;
     * </div>
     * @param  origin
     * @param  destination
     */
    public CommonPageObject cognate(Target origin, Target destination) {
        commonPage.cognate(origin, destination);
        return this;
    }

    /**
     * Finds the required target element relative to the currently focused element.
     * This method traverses up the DOM from the current element, searching for the required target as a descendant at each level.
     * <br>
     * <br>
     * <b style="color:blue;">Target origin = id("parent")</b>
     * <br>
     * <b style="color:blue;">Target nearestRelative = tagWithText(DIV,"Mary's child 1")</b>
     * <br>
     * <b style="color:blue;">focus(origin)</b>
     * <br>
     * <b style="color:blue;">cognate(nearestRelative)</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;div id="parent" &gt; Mary
     * <br>
     * &lt;div id="children-1" &gt; Mary's child 1 &lt;/div&gt;
     * <br>
     * &lt;div id="children-2" &gt; Mary's child 2 &lt;/div&gt;
     * <br>
     *  &lt;/div&gt;
     * </div>
     * <br>
     * <b>Note:</b> An element must be focused before calling this method. Use {@code focus(Target)} first.
     *
     * @param destination
     */


    public CommonPageObject cognate(Target destination) {
        commonPage.cognate(destination);
        return this;
    }

    /**
     * This will proceed to apply a wait time for cognate operation at the given stage applied.
     * <br>
     * <b style="color:blue;">cognate(5);</b>
     * <br>
     * @param waitTime - The volume of wait time to be applied, in seconds.
     * @return
     */

    public CommonPageObject cognate(int waitTime) {
        commonPage.cognate(waitTime);
        return this;
    }

    /**
     * This will set the current element to the document root element of html.
     * <br>
     * <b style="color:blue;">origin();</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;html lang="en" &gt;
     * <br>
     * </div>
     * @return
     */
    public CommonPageObject origin() {
        commonPage.origin();
        return this;
    }

    /**
     * This will find the first child element of the current element.
     * <br>
     * <b style="color:blue;">Target origin = id("parent")</b>
     * <br>
     * <b style="color:blue;">focus(origin).leaf()</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;div id="parent" &gt; Mary
     * <br>
     * &lt;div id="children-1" &gt; Mary's child 1 &lt;/div&gt;
     *  &lt;/div&gt;
     * </div>
     * @return
     */
    public CommonPageObject leaf() {
        navigation.leaf();
        return this;
    }

    /**
     * This will click the current element that is in focus.
     * <br>
     * <b style="color:blue;">Target assignBtn = id("assign-button")</b>
     * <br>
     * <b style="color:blue;">focus(assignBtn).click()</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;button id="assign-button" &gt;
     * <br>
     *  &lt;/div&gt;
     * </div>
     * @return
     */
    public CommonPageObject click() {
        commonPage.click();
        return this;
    }

    /**
     * This will clear the textual value of the current element.
     * <br>
     * <b style="color:blue;">Target pre-filled-TxtBox = id("pre-filled-TxtBox")</b>
     * <br>
     * <b style="color:blue;">focus(pre-filled-TxtBox).clear()</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;input id="pre-filled-TxtBox" value="This is prefilled needs clearing." &gt;
     * <br>
     *  &lt;/div&gt;
     * </div>
     * @return
     */
    public CommonPageObject clear() {
        commonPage.clear();
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

    public CommonPageObject choose(String text, boolean strict) {
        commonPage.choose(text, strict);
        return this;
    }

    public CommonPageObject choose(Enums.Index index) {
        commonPage.choose(index);
        return this;
    }

    public CommonPageObject choose(Enums.ListIndex listIndex) {
        commonPage.choose(listIndex);
        return this;
    }

    public CommonPageObject choose(Target target) {
        commonPage.choose(target);
        return this;
    }

    public CommonPageObject expand(Target target) {
        commonPage.expand(target);
        return this;
    }

    /**
     * This will check if the textual value passed is present in the current elements list.
     * <br>
     * <b style="color:blue;">Target txt1 = id("form")</b>
     * <br>
     * <b style="color:blue;">Target txt2 = id("form")</b>
     * <br>
     * <b style="color:blue;">collect(txt1)</b>
     * <br>
     * <b style="color:blue;">collect(txt2)</b>
     * <br>
     * <b style="color:blue;">present("Text Present")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;input id="txt-1" value="" &gt;
     * <br>
     * &lt;input id="txt-2" value="Text Present" &gt;
     * <br>
     *  &lt;/div&gt;
     * </div>
     * @param text - The text to be checked.
     */
    public CommonPageObject present(String text) {
        commonPage.present(text);
        return this;
    }

    public CommonPageObject present(List<String> textList) {
        commonPage.present(textList);
        return this;
    }


    public CommonPageObject absent(String text) {
        commonPage.absent(text);
        return this;
    }


    public CommonPageObject absent(List<String> textList) {
        commonPage.absent(textList);
        return this;
    }

    public CommonPageObject compose(String keysToSend) {
        keysInteraction.compose(keysToSend);
        return this;
    }

    public CommonPageObject compose(org.openqa.selenium.Keys key) {
        keysInteraction.compose(key);
        return this;
    }

    public CommonPageObject compose(org.openqa.selenium.Keys keysToSend, int repeat) {
        keysInteraction.compose(keysToSend, repeat);
        return this;
    }

    /**
     * This can be used to hold down a key on the keyboard.
     * <br>
     * <br>
     * <b style="color:blue;">hold(Keys.CONTROL).hold("a")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;input value="att-eval-value" &gt;
     * CTRL + a = highlight all text of "att-eval-value".
     * </div>
     * @param keyToHold - The key to hold down from selenium keys enumeration.
     */
    public CommonPageObject hold(org.openqa.selenium.Keys keyToHold) {
        keysInteraction.hold(keyToHold);
        return this;
    }
    /**
     * This can be used to hold down a key on the keyboard.
     * <br>
     * <br>
     * <b style="color:blue;">hold(Keys.CONTROL).hold("a")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;input value="att-eval-value" &gt;
     * CTRL + a = highlight all text of "att-eval-value".
     * </div>
     * @param keyToHold - The key to hold down from textual input e.g. "a" for the letter a.
     */
    public CommonPageObject hold(String keyToHold) {
        keysInteraction.hold(keyToHold);
        return this;
    }

    /**
     *  This can be used to release a key on the keyboard.
     *  This is used in conjunction with the hold method.
     *  <br>
     *  <br>
     *  <b style="color:blue;">release(Keys.CONTROL).release("a")</b>
     *  <br>
     * @param keyToRelease - The key to release down from selenium keys enumeration.
     */
    public CommonPageObject release(org.openqa.selenium.Keys keyToRelease) {
        keysInteraction.release(keyToRelease);
        return this;
    }

    /**
     *  This can be used to release a key on the keyboard.
     *  This is used in conjunction with the hold method.
     *  <br>
     *  <br>
     *  <b style="color:blue;">release(Keys.CONTROL).release("a")</b>
     *  <br>
     * @param keyToRelease - The key to release down from textual input e.g. "a" for the letter a.
     */
    public CommonPageObject release(String keyToRelease) {
        keysInteraction.release(keyToRelease);
        return this;
    }

    public CommonPageObject traverse() {
        navigation.traverse();
        return this;
    }

    public CommonPageObject traverse(int index) {
        navigation.traverse(index);
        return this;
    }

    public CommonPageObject traverse(Enums.NodeEnum index) {
        navigation.traverse(index);
        return this;
    }

    public CommonPageObject reverse() {
        navigation.reverse();
        return this;
    }

    public CommonPageObject reverse(int index) {
        navigation.reverse(index);
        return this;
    }

    public CommonPageObject reverse(Enums.NodeEnum index) {
        navigation.reverse(index);
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

    public CommonPageObject descend() {
        navigation.descend();
        return this;
    }

    public CommonPageObject descend(Target target) {
        navigation.descend(target);
        return this;
    }

    public CommonPageObject ascend() {
        navigation.ascend();
        return this;
    }

    public CommonPageObject ascend(int levels) {
        navigation.ascend(levels);
        return this;
    }

    public CommonPageObject ascend(Enums.Level level) {
        navigation.ascend(level.getValue());
        return this;
    }

    /**
     * This will locate the nearest matching tag starting from the current element and traversing upwards through its parents.
     * <br>
     * <br>
     * <b style="color:blue;">Target origin = id("current")</b>
     * <br>
     * <b style="color:blue;">focus(target)</b>
     * <br>
     * <b style="color:blue;">ascend(SECTION)</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;section id="outer2" &gt; Outer2
     *     &lt;div id="outer1" &gt; Outer1
     *       &lt;div id="current" &gt; Current element &lt;/div&gt;
     *     &lt;/div&gt;
     * &lt;/section&gt;
     * </div>
     * @param tag
     * @return
     */
    public CommonPageObject ascend(Enums.Tag tag){
        navigation.ascend(tag);
        return this;
    }


    public CommonPageObject fullScreen() {
        commonPage.fullScreen();
        return this;
    }

    public CommonPageObject quit() {
        commonPage.quit();
        return this;
    }

    public CommonPageObject close() {
        commonPage.close();
        return this;
    }

    public CommonPageObject scroll(Target target) {
        navigation.scroll(target);
        return this;
    }

    public CommonPageObject scroll() {
        navigation.scroll();
        return this;
    }

    public CommonPageObject maximise() {
        commonPage.maximise();
        return this;
    }

    WebDriver getDriver() {
        return commonPage.getDriver();
    }

    public <T extends Performable> CommonPageObject perform(T runner) {
        commonPage.perform(runner);
        return this;
    }

    public CommonPageObject store(String key, String val) {
        commonPage.store(key, val);
        return this;
    }

    public CommonPageObject storeFocused(String key) {
        commonPage.storeFocused(key);
        return this;
    }

    public CommonPageObject retrieveFocused(String key) {
        commonPage.retrieveFocused(key);
        return this;
    }

    public String retrieve(String key) {
        return commonPage.retrieve(key);
    }

    public CommonPageObject depart(Target target) {
        commonPage.depart(target);
        return this;
    }

    public CommonPageObject depart(Target target, Enums.Targets targets ) {
        commonPage.depart( target, targets);
        return this;
    }

    public CommonPageObject absent(Target target) {
        commonPage.absent(target);
        return this;
    }

    public CommonPageObject dragDrop(Target draggable, Target dropZone) {
        commonPage.dragDrop(draggable, dropZone);
        return this;
    }

    // Drag draggable to currently focused element
    public CommonPageObject drag(Target draggable) {
        commonPage.drag(draggable);
        return this;
    }

    // Drop currently focused element on dropZone
    public CommonPageObject drop(Target dropZone) {
        commonPage.drop(dropZone);
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

    public CommonPageObject frame(Target target) {
        commonPage.frame(target);
        return this;
    }

    public CommonPageObject frame(String nameOrId) {
        commonPage.frame(nameOrId);
        return this;
    }

    public CommonPageObject frame() {
        commonPage.frame();
        return this;
    }
    public CommonPageObject frame(int index) {
        commonPage.frame(index);
        return this;
    }

    public CommonPageObject deframe() {
        commonPage.deframe();
        return this;
    }

    public CommonPageObject window(Enums.Window window) {
        commonPage.window(window);
        return this;
    }

    public CommonPageObject window(int index) {
        commonPage.window(index);
        return this;
    }

    public String get(Enums.ElementTrait elementTrait) {
        return commonPage.get(elementTrait);
    }

    public CommonPageObject get(int getWaitTime) {
        commonPage.get(getWaitTime);
        return this;
    }

    public CommonPageObject printFocused() {
        commonPage.printFocused();
        return this;
    }

    public CommonPageObject printCollection() {
        commonPage.printCollection();
        return this;
    }

    public CommonPageObject refresh() {
        commonPage.refresh();
        return this;
    }

    public String getUrl() {
        return commonPage.getUrl();
    }

    public String getTitle() {
        return commonPage.getTitle();
    }

    public void file(String filePath) {
        commonPage.file(filePath);
    }

    public void file(Target target, String filePath) {
        commonPage.file(target, filePath);
    }

    public int size() {
        return commonPage.size();
    }

    public CommonPageObject selected() {
        commonPage.selected();
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

    public CommonPageObject clickable() {
        commonPage.clickable();
        return this;
    }

    public CommonPageObject visible() {
        commonPage.visible();
        return this;
    }

    public CommonPageObject reset() {
        commonPage.reset();
        return this;
    }

    
    public CommonPageObject alert(Target.AlertAction action){
        keysInteraction.alert(action);
        return this;
    }

    public CommonPageObject alert(int keys) throws AWTException {
        keysInteraction.alert(keys);
        return this;
    }


    protected File takeScreenshot() {
        return commonPage.takeScreenshot();
    }

    /**
     * Execute JavaScript
     * <br>
     * <br>
     * <b style="color:blue;">jsExecutor("return document.getElementById('att-eval-id').value")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;input value="att-eval-value" &gt;
     * </div>
     * @param script
     * @return
     */
    public String javascript(String script) {return commonPage.javascript(script);}

    /**
     * Trigger Javascript event.
     * @param event from :
     *         BLUR
     *         CHANGE
     *         CLICK
     *         DBLCLICK
     *         FOCUS
     *  example :
     *  trigger(
     * @return
     */
    public CommonPageObject trigger(Enums.Event event) {
        commonPage.trigger(event);
        return this;
    }

    ///////////////
    // DOM UTILS //
    ///////////////

    // These utilise, but are not, interface methods

    /////////
    // URL //
    /////////

    public CommonPageObject urlEquals(String url) {
        String actual = commonPage.getUrl();
        log("Equals:: Expected value: " + url + " Actual value: " + actual);
        assertTrue(actual.equals(url));
        return this;
    }

    public CommonPageObject urlContains(String url) {
        String actual = commonPage.getUrl();
        log("Contains:: Expected value: " + url + " Actual value: " + actual);
        assertTrue(actual.contains(url));
        return this;
    }

    public CommonPageObject urlStartsWith(String url) {
        String actual = commonPage.getUrl();
        log("StartsWith:: Expected value: " + url + " Actual value: " + actual);
        assertTrue(actual.startsWith(url));
        return this;
    }

    public CommonPageObject urlEndsWith(String url) {
        String actual = commonPage.getUrl();
        log("EndsWith:: Expected value: " + url + " Actual value: " + actual);
        assertTrue(actual.startsWith(url));
        return this;
    }

    ///////////
    // TITLE //
    ///////////

    public CommonPageObject titleEquals(String title) {
        String actual = commonPage.getTitle();
        log("Equals:: Expected value: " + title + " Actual value: " + actual);
        assertTrue(actual.equals(title));
        return this;
    }

    public CommonPageObject titleContains(String title) {
        String actual = commonPage.getTitle();
        log("Contains:: Expected value: " + title + " Actual value: " + actual);
        assertTrue(actual.contains(title));
        return this;
    }
    public CommonPageObject titleStartsWith(String title) {
        String actual = commonPage.getTitle();
        log("StartsWith:: Expected value: " + title + " Actual value: " + actual);
        assertTrue(actual.startsWith(title));
        return this;
    }

    public CommonPageObject titleEndsWith(String title) {
        String actual = commonPage.getTitle();
        log("EndsWith:: Expected value: " + title + " Actual value: " + actual);
        assertTrue(actual.endsWith(title));
        return this;
    }

    //////////////////
    // ELEMENT TEXT //
    //////////////////

    public CommonPageObject textEquals(String text) {
        String actual = commonPage.get(TEXT);
        log("Equals:: Expected value: " + text + " Actual value: " + actual);
        assertTrue(actual.equals(text));
        return this;
    }

    public CommonPageObject textDiffers(String text) {
        String actual = commonPage.get(TEXT);
        log("Differs:: Expected value: " + text + " Actual value: " + actual);
        assertFalse(actual.equals(text));
        return this;
    }

    public CommonPageObject textContains(String text) {
        String actual = commonPage.get(TEXT);
        log("Contains:: Expected value: " + text + " Actual value: " + actual);
        assertTrue(actual.contains(text));
        return this;
    }
    public CommonPageObject textStartsWith(String text) {
        String actual = commonPage.get(TEXT);
        log("StartsWith:: Expected value: " + text + " Actual value: " + actual);
        assertTrue(actual.startsWith(text));
        return this;
    }

    public CommonPageObject textEndsWith(String text) {
        String actual = commonPage.get(TEXT);
        log("EndsWith:: Expected value: " + text + " Actual value: " + actual);
        assertTrue(actual.endsWith(text));
        return this;
    }

    public CommonPageObject textPresent() {
        boolean actual = !commonPage.get(TEXT).isEmpty();
        log("Present:: Expected value: true " + "Actual value: " + actual);
        assertTrue(actual);
        return this;
    }

    public CommonPageObject textPresent(String text) {
        String actual = commonPage.get(TEXT);
        log("Present:: Expected value: " + text + " Actual value: " + actual);
        assertTrue(actual.equals(text));
        return this;
    }

    public CommonPageObject textNotPresent() {
        boolean actual = commonPage.get(TEXT).isEmpty();
        log("NotPresent:: Expected value: false " + "Actual value: " + actual);
        assertTrue(actual);
        return this;
    }

    public CommonPageObject textNotPresent(String text) {
        String actual = commonPage.get(TEXT);
        log("NotPresent:: Expected value: " + text + " Actual value: " + actual);
        assertTrue(!actual.contains(text));
        return this;
    }

    public CommonPageObject textHasLength(int length) {
        int actual = commonPage.get(TEXT).length();
        log("TxtLength:: Expected value: " + length + " Actual value: " + actual);
        assertTrue(actual == length);
        return this;
    }

    ////////////////
    // ATTRIBUTES //
    ////////////////

    public CommonPageObject attributeEquals(Enums.ElementTrait attribute, String value) {
        String actual = commonPage.get(attribute);
        log("Equals:: Expected value: " + value + " Actual value: " + actual);
        assertTrue(actual.equals(value));
        return this;
    }

    public CommonPageObject attributeContains(Enums.ElementTrait attribute, String value) {
        String actual = commonPage.get(attribute);
        log("Contains:: Expected value: " + value + " Actual value: " + actual);
        assertTrue(actual.contains(value));
        return this;
    }

    public CommonPageObject attributeStartsWith(Enums.ElementTrait attribute, String value) {
        String actual = commonPage.get(attribute);
        log("StartsWith:: Expected value: " + value + " Actual value: " + actual);
        assertTrue(actual.startsWith(value));
        return this;
    }

    public CommonPageObject attributeEndsWith(Enums.ElementTrait attribute, String value) {
        String actual = commonPage.get(attribute);
        log("Contains:: Expected value: " + value + " Actual value: " + actual);
        assertTrue(actual.endsWith(value));
        return this;
    }

    //////////////
    // ELEMENTS //
    //////////////

    public CommonPageObject elementPresent(Target target) {
        boolean actual = commonPage.peek(target);
        log("Present:: Expected value: true " + "Actual value: " + actual);
        assertTrue(actual);
        return this;
    }

    public CommonPageObject elementNotPresent(Target target) {
        boolean actual = !commonPage.peek(target);
        log("NotPresent:: Expected value: false " + "Actual value: " + actual);
        assertTrue(actual);
        return this;
    }

    /////////////////
    // COLLECTIONS //
    /////////////////

    public CommonPageObject collectionPresent() {
        boolean actual = commonPage.size() > 0;
        log("Present:: Expected value: true " + "Actual value: " + actual);
        assertTrue(actual);
        return this;
    }

    public CommonPageObject collectionNotPresent() {
        boolean actual = commonPage.size() == 0;
        log("NotPresent:: Expected value: true " + "Actual value: " + actual);
        assertTrue(actual);
        return this;
    }

    public CommonPageObject collectionHasSize(int size) {
        int actual = commonPage.size();
        log("HasSize:: Expected value: " + size + " Actual value: " + actual);
        assertTrue(actual == size);
        return this;
    }

    public CommonPageObject collectionLargerThan(int size) {
        int actual = commonPage.size();
        log("LargerThan:: Expected value: " + size + " Actual value: " + actual);
        assertTrue(actual > size );
        return this;
    }

    public CommonPageObject collectionSmallerThan(int size) {
        int actual = commonPage.size();
        log("SmallerThan:: Expected value: " + size + " Actual value: " + actual);
        assertTrue(actual < size );
        return this;
    }

    /////////
    // TAG //
    /////////

    public CommonPageObject tagIs(Enums.Tag tag) {
        String actual = commonPage.get(TAG);
        log("TagIs:: Expected value: " + tag.name() + " Actual value: " + actual);
        assertTrue(actual.equalsIgnoreCase(tag.name()));
        return this;
    }

    public CommonPageObject tagIsNot(Enums.Tag tag) {
        String actual = commonPage.get(TAG);
        log("TagIsNot:: Expected value: " + tag.name() + " Actual value: " + actual);
        assertFalse(actual.equalsIgnoreCase(tag.name()) );
        return this;
    }
    
    public CommonPageObject networkLogging(String type) {
        commonPage.networkLogging(type);
        return this;
    }

    ////////////////////
    // HOLD & RELEASE //
    ////////////////////

    public CommonPageObject copy() {
        keysInteraction.hold(org.openqa.selenium.Keys.CONTROL);
        keysInteraction.hold("a");
        keysInteraction.release("a");
        keysInteraction.hold("c");
        keysInteraction.release("c");
        keysInteraction.release(org.openqa.selenium.Keys.CONTROL);
        return this;
    }

    public CommonPageObject paste() {
        keysInteraction.hold(org.openqa.selenium.Keys.CONTROL);
        keysInteraction.hold("v");
        keysInteraction.release("v");
        keysInteraction.release(org.openqa.selenium.Keys.CONTROL);
        return this;
    }
}

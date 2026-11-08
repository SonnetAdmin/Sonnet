package sonnet.reference.selenium.locators;


import org.openqa.selenium.By;
import sonnet.reference.selenium.Enums;

import java.util.StringTokenizer;

import static sonnet.reference.selenium.locators.Target.Type.*;

/**
 * <p>The Targets class provides utility methods for <code>focus() descend() expect() depart() collect() choose() and contains()</code> methods
 * @since June 2024
 **/
public class TargetFactory {

    public TargetFactory(){

    }

    /////////////
    // TARGETS //
    /////////////

//    css(String attribute, String value)
//    className(String className )
//    classNames(String classNames )
//    id(String value)
//    name(String value)
//    value(String value)
//    text(String value)
//    partialText(String value)
//    tagWithText(Enums.Tag tag, String text)
//    tag(Enums.Tag tag)
//    linkText(String linkText)
//    partialLinkText(String partialLinkText)
//    xpath(String xpath)
//    root()
//    children()
//    siblings()

    /**
     * This will locate an element with the given attribute and value eg.
     * <br>
     * <br>
     * <b style="color:blue;">attribute("data_testid", "data_testid-example")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;span data_testid="data_testid-example" &gt; &lt;/span&gt;
     * </div>
     * @param attribute
     * @param value
     * @return
     */
    protected static Target attribute(String attribute, String value) {
        return  new Target(By.cssSelector("*["+attribute+"=\"" + value + "\"]"), attribute, value, CSS );  }
    /**
     * This will locate an element with a given standard HTML5 attribute and value eg.
     * <br>
     * <br>
     * <b style="color:blue;">attribute(TYPE, "password")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;input type="password" &gt; &lt;/input&gt;
     * </div>
     * @param attribute
     * @param value
     * @return
     */
    protected static Target attribute(Enums.Attribute attribute, String value) {
        return  new Target(By.cssSelector("*["+attribute.getValue()+"=\"" + value + "\"]"), attribute.getValue(), value, CSS );  }

    /**
     * This will locate an element with the given attribute and partial value eg.
     * <br>
     * <br>
     * <b style="color:blue;">attribute("data_testid", "example")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;span data_testid="data_testid-example" &gt; &lt;/span&gt;
     * </div>
     * @param attribute
     * @param value
     * @return
     */
    protected static Target partialAttribute(String attribute, String value) {
        return  new Target(By.xpath("//*[contains(@"+attribute+", '"+value+"')]"), attribute, value, CSS );  }
    /**
     * This will locate an element with a given standard HTML5 attribute and partial value eg.
     * <br>
     * <br>
     * <b style="color:blue;">attribute(TYPE, "pass")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;input type="password" &gt; &lt;/input&gt;
     * </div>
     * @param attribute
     * @param value
     * @return
     */
    protected static Target partialAttribute(Enums.Attribute attribute, String value) {
        return  new Target(By.xpath("//*[contains(@"+attribute.getValue()+", '"+value+"')]"), attribute.getValue(), value, CSS );  }

    /**
     * This will locate an element with the given attribute eg.
     * <br>
     * <br>
     * <b style="color:blue;">attribute("enabled")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;div enabled &gt; &lt;/div&gt;
     * </div>
     * @param attribute
     * @return
     */
    protected static Target attribute(String attribute) {
        return  new Target(By.cssSelector("["+attribute+"]"), attribute, ATTRIBUTE );  }

    /**
     * This will locate an element with the given class name eg.
     * <br>
     * <br>
     * <b style="color:blue;">className("abc")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;input class="abc" &gt;
     * </div>
     * @param className
     * @return
     */
    public static Target className(String className ) {
        return  new Target(By.className(className), className, CLASS);
    }

    /**
     * This will locate an element with the given class names eg.
     * <br>
     * <br>
     * <b style="color:blue;">classNames("abc def")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;div class="abc def" &gt; &lt;/div&gt;
     * </div>
     * @param classNames
     * @return
     */
    protected static Target classNames(String classNames )  {

        StringTokenizer st = new StringTokenizer(classNames);
        StringBuffer sb = new StringBuffer();

        while(st.hasMoreTokens()) {
            sb.append(".");
            sb.append(st.nextToken());
        }

        return  new Target(By.cssSelector(sb.toString()), classNames, CLASSES);
    }

    /**
     * This will locate an element with the given id eg.
     * <br>
     * <br>
     * <b style="color:blue;">id("test")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;div id="test" &gt; &lt;/div&gt;
     * </div>
     * @param value
     * @return
     */
    public static Target id(String value) {
        return new Target(By.id(value), value, ID );
    }

    /**
     * This will locate an element with the given name eg.
     * <br>
     * <br>
     * <b style="color:blue;">name("test")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;div name="test" &gt; &lt;/div&gt;
     * </div>
     * @param value
     * @return
     */
    public static Target name(String value) {

        return new Target(By.name(value), value, NAME );
    }

    protected static Target css(String value) {
        return new Target(By.cssSelector(value), value, ID );
    }

    /**
     * This will locate an element with the given placeholder eg.
     * <br>
     * <br>
     * <b style="color:blue;">placeholder("please type here")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;input placeholder="please type here" &gt;
     * </div>
     * @param value
     * @return
     */
    protected static Target placeholder(String value) {
        return new Target((By.xpath("//input[@placeholder='" + value + "'] | //textarea[@placeholder='" + value + "']")), value, PLACEHOLDER );
    }

    /**
     * This will locate an element with the given title eg.
     * <br>
     * <br>
     * <b style="color:blue;">title("this is title")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;input title="this is title" &gt;
     * </div>
     * @param value
     * @return
     */

    protected static Target title(String value) {
        return new Target((By.xpath("//*[@title='" + value + "']")), value, TITLE );
    }


    /**
     * This will locate an element with the given role eg.
     * <br>
     * <br>
     * <b style="color:blue;">role("applicant")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;div role="applicant" &gt; &lt;/div&gt;
     * </div>
     * @param value
     * @return
     */
    protected static Target role(String value) {
        return new Target((By.xpath("//*[@role='" + value + "']")), value, ROLE );
    }

    /**
     * This will locate an element with the given data_testid eg.
     * <br>
     * <br>
     * <b style="color:blue;">data_testid("sidemenu-button")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;button data_testid="sidemenu-button" &gt; &lt;/button&gt;
     * </div>
     * @param value
     * @return
     */
    protected static Target data_testid(String value) {
        return  new Target((By.xpath("//*[@data-testid='" + value +"']")), value, value, DATA_TEST_ID );
    }

    /**
     * This will locate an element with the given href eg.
     * <br>
     * <br>
     * <b style="color:blue;">href("https://www.google.co.uk/")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;a href="https://www.google.co.uk/" &gt; &lt;/a&gt;
     * </div>
     * @param value
     * @return
     */
    protected static Target href(String value) {
        return new Target((By.xpath("//*[@href='" + value + "']")), value, HREF );
    }

    /**
     * This will locate an element with the given src eg.
     * <br>
     * <br>
     * <b style="color:blue;">src("example.jpg")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;img src="example.jpg" &gt;
     * </div>
     * @param value
     * @return
     */
    protected static Target src(String value) {
        return new Target((By.xpath("//*[@src='" + value + "']")), value, SRC );
    }

    /**
     * This will locate an element with the given style eg.
     * <br>
     * <br>
     * <b style="color:blue;">style("color:red")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;p style="color:red" &gt; &lt;/p&gt;
     * </div>
     * @param value
     * @return
     */

    protected static Target style(String value) {
        return new Target((By.xpath("//*[@style='" + value + "']")), value, STYLE );
    }

    /**
     * This will locate an element with the given alt eg.
     * <br>
     * <br>
     * <b style="color:blue;">alt("company logo")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;img alt="company logo" &gt;
     * </div>
     * @param value
     * @return
     */
    protected static Target alt(String value) {
        return new Target((By.xpath("//*[@alt='" + value + "']")), value, ALT );
    }

    /**
     * This will locate an element with the given exact text eg.
     * <br>
     * <br>
     * <b style="color:blue;">text(" This is an example text ")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;p &gt; This is an example text &lt;/p&gt;
     * </div>
     * @param value
     * @return
     */
    protected static Target text(String value) {
        String valueTrimmed = value.trim();
        return new Target((By.xpath("//*[(normalize-space(text())='"+value+"' )]")), valueTrimmed, TEXT );
    }

    /**
     * This will locate an element with the given partial text eg.
     * <br>
     * <br>
     * <b style="color:blue;">partialText("partial text")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;p &gt; This is an example partial text &lt;/p&gt;
     * </div>
     * @param value
     * @return
     */
    protected static Target partialText(String value) {
        String valueTrimmed = value.trim();
        return new Target((By.xpath("//*[contains(text(), '"+valueTrimmed+"' )]")), value, PARTIAL_TEXT );
    }

    /**
     * This will locate an element with the given HTML5 tag and exact text eg.
     * <br>
     * <br>
     * <b style="color:blue;">tagWithText(P, " This is an example text ")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;p id="text-example" &gt; This is an example text &lt;/p&gt;
     * </div>
     * @param tag
     * @param text
     * @return
     */
    public static Target tagWithText(Enums.Tag tag, String text) {
        String textTrimmed = text.trim();
        return new Target((By.xpath("//" + tag.name().toLowerCase() + "[contains(normalize-space(text()), '" + textTrimmed + "')]")), text, TAG_WITH_TEXT );
    }

    /**
     * This will locate an element with the given HTML5 tag and partial text eg.
     * <br>
     * <br>
     * <b style="color:blue;">tagContainsText(P,"an example")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;p id="text-example" &gt; This is an example partial text &lt;/p&gt;
     * </div>
     * @param tag
     * @param text
     * @return
     */
    protected static Target tagContainsText(Enums.Tag tag, String text) {
        String textTrimmed = text.trim();
        return new Target((By.xpath("//" + tag.name().toLowerCase() + "[contains(normalize-space(text()), '" + textTrimmed + "')]")), text, TAG_CONTAINS_TEXT );
    }

    /**
     * This will locate an element with the given bespoke tag and text eg.
     * <br>
     * <br>
     * <b style="color:blue;">tagWithName("mytag", "example")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;mytag name="example" &gt; &lt;/mytag&gt;
     * </div>
     * @param tag
     * @param text
     * @return
     */
    // Bespoke tags
    protected static Target tagWithText(String tag, String text) {
        String textTrimmed = text.trim();
        return new Target((By.xpath("//" + tag.toLowerCase() + "[contains(normalize-space(text()), '" + textTrimmed + "')]")), text, TAG_WITH_TEXT );
    }

    /**
     * This will locate an element with the given tag and id eg.
     * <br>
     * <br>
     * <b style="color:blue;">tagWithId(DIV, "example")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;div id="example" &gt; &lt;/div&gt;
     * </div>
     * @param tag
     * @param id
     * @return
     */
    // Identifiers should be unique in the DOM but can use tag(tag).and(id(id)) instead
    protected static Target tagWithId(Enums.Tag tag, String id) {
        return new Target((By.xpath("//" + tag.name() + "[@id='" + id + "']")), tag.name(), id, TAG_WITH_ID );
    }

    /**
     * This will locate an element with the given tag and class eg.
     * <br>
     * <br>
     * <b style="color:blue;">tagWithClass(P, "example")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;p class="example" &gt; &lt;/p&gt;
     * </div>
     * @param tag
     * @param className
     * @return
     */
    // Can be replaced with conjunctions e.g. tag(tag).and(className(class)) instead
    protected static Target tagWithClass(Enums.Tag tag, String  className){
        return new Target((By.xpath("//" + tag.name() + "[@class='" + className + "']")), tag.name(), className, TAG_WITH_CLASS );
    }

    /**
     * This will locate an element with the given tag and title eg.
     * <br>
     * <br>
     * <b style="color:blue;">tagWithTitle(P, "example")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;p title="example" &gt; &lt;/p&gt;
     * </div>
     * @param tag
     * @param title
     * @return
     */

    /**
     * This will locate an element with the given tag and name eg.
     * <br>
     * <br>
     * <b style="color:blue;">tagWithName(P, "example")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;p name="example" &gt; &lt;/p&gt;
     * </div>
     * @param tag
     * @param name
     * @return
     */
    // Can be replaced with conjunctions e.g. tag(tag).and(name(name)) instead
    protected static Target tagWithName(Enums.Tag tag, String name) {
        return new Target((By.xpath("//" + tag.name() + "[@name='" + name + "']")), tag.name(), name, TAG_WITH_NAME );
    }



    /**
     * This will locate an element with the given tag eg.
     * <br>
     * <br>
     * <b style="color:blue;">tag(P)</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;p id="tag-example" &gt; &lt;/p&gt;
     * </div>
     * @param tag
     * @return
     */
    protected static Target tag(Enums.Tag tag) {
        return  new Target(By.tagName(tag.name().toLowerCase()), tag.name(), TAG);  }

    /**
     * This will locate an element with the given tag eg.
     * <br>
     * <br>
     * <b style="color:blue;">tag("mytag")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;mytag id="tag-example" &gt; &lt;/mytag&gt;
     * </div>
     * @param tag
     * @return
     */
    protected static Target tag(String tag) {
        return  new Target(By.tagName(tag.toLowerCase()), tag, TAG);  }

    /**
     * This will locate an element with the given link text eg.
     * <br>
     * <br>
     * <b style="color:blue;">linkText("Google")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;a href="https://www.google.co.uk/" &gt; Google &lt;/a&gt;
     * </div>
     * @param text
     * @return
     */
    protected static Target linkText(String text) {
        String textTrimmed = text.trim();

        return  new Target(By.linkText(textTrimmed),textTrimmed, LINK_TEXT);
    }

    /**
     * This will locate an element with the given partial link text eg.
     * <br>
     * <br>
     * <b style="color:blue;">partialLinkText("oog")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;a href="https://www.google.co.uk/" &gt; Google &lt;/a&gt;
     * </div>
     * @param text
     * @return
     */
    protected static Target partialLinkText(String text) {
        String textTrimmed = text.trim();
        return new Target(By.partialLinkText(textTrimmed), textTrimmed, PARTIAL_LINK_TEXT );
    }

    /**
     * This will locate an element with the given value eg.
     * <br>
     * <br>
     * <b style="color:blue;">value("IN")</b>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;option value="IN" &gt; India &lt;/option&gt;
     * </div>
     * @param value
     * @return
     */
    protected static Target value(String value) {
        return new Target(By.cssSelector("*[value='"+value+"']"), value, VALUE );  }

    @Deprecated
    protected static Target xpath(String xpath) {
        return new Target(By.xpath(xpath), xpath, XPATH );  }

    @Deprecated
    protected static Target root() {
        return new Target(By.tagName("html"), "Root", ROOT ) ; }

    /**
     * This will locate the children of the given element eg.
     * <br>
     * <br>
     * <b style="color:blue;">focus(id("parent"));</b>
     * <br>
     * <b style="color:blue;">click();</b>
     * <br>
     * <b style="color:blue;">collect(children());</b>
     * <br>
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
     * @return
     */
    protected static Target children() {
        return new Target( By.xpath("./child::*" ), "Direct Children", CHILDREN ) ; }

    /**
     * This will locate the siblings of the given element eg.
     * <br>
     * <br>
     * <b style="color:blue;">focus(id("example"));</b>
     * <br>
     * <b style="color:blue;">click();</b>
     * <br>
     * <b style="color:blue;">collect(siblings());</b>
     * <br>
     * <br>
     * <div style="color:green;font-weight:bold;">
     * &lt;div id="example" &gt; Tom &lt;/div&gt;
     * <br>
     * &lt;div id="siblings-1" &gt; Tom's sibling 1 &lt;/div&gt;
     * <br>
     * &lt;div id="siblings-2" &gt; Tom's sibling 2 &lt;/div&gt;
     * <br>
     * </div>
     * @return
     */
    protected static Target siblings() {
        return new Target( By.xpath("following-sibling::* | preceding-sibling::*" ), "Siblings", SIBLINGS ) ; }

    public static Target splice(String prefix, String suffix) {
        // This will match on a prefix and text /  suffix which can occur anywhere in the element text
        // ends-with() is an XPath 2 method and Selenium supports only XPath 1 so ends-with(text() not possible
        return new Target(By.xpath(".//*[starts-with(text(), '" + prefix + "') and contains(text(), '" + suffix + "' )]"), prefix, suffix, XPATH);
    }



    @Deprecated
    protected static Target dataEndpoint(String value) {
        return new Target((By.xpath("//*[@data-endpoint='" + value + "']")), value, DATA_ENDPOINT ); }

    @Deprecated
    protected static Target dataField(String value) {
        return new Target((By.xpath("//*[@data-field='" + value + "']")), value, DATA_FIELD); }
}

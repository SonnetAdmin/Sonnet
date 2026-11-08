package sonnet.reference.playwright.locators;

import com.microsoft.playwright.options.AriaRole;
import sonnet.reference.playwright.Enums;
import sonnet.reference.playwright.Logging;

import java.util.StringTokenizer;

import static sonnet.reference.playwright.locators.Target.Type.*;
import static sonnet.reference.playwright.locators.Target.Type.ATTRIBUTE;

/**
 * <p>The Targets class provides utility methods for <code>focus() descend() expect() depart() collect() choose() and contains()</code> methods
 *
 * @since February 2026
 **/
public class TargetFactory {
    private static Logging logger = Logging.getInstance();
    private static final String DATA_ATTRIBUTE_PREFIX = "data-";
    private static String dataAttribute = "";          // e.g. data-ci-id
    private static String dataAttributeSuffix = "";    // "ci-name" resolves to "data-ci-name"

    public static String getDataAttributePrefix() {
        return DATA_ATTRIBUTE_PREFIX;
    }

    public static String getDataAttribute() {

        if(!dataAttribute.isEmpty()) {
            //logger.log("Getting data attribute.  Current value : " + dataAttribute);
            return dataAttribute;
        }
        if(!dataAttributeSuffix.isEmpty()) {
                logger.log("Data attribute is not explicitly set. Returning default and suffix : " + DATA_ATTRIBUTE_PREFIX + dataAttributeSuffix);
                return DATA_ATTRIBUTE_PREFIX + dataAttributeSuffix;
            }
        logger.log("Data attribute is not set. \nPlease set either the full data attribute with setDataAttribute(String dataAttribute) \nor the data attribute suffix with setDataAttributeSuffix(String dataAttributeSuffix).");
        logger.log("TargetFactory.setDataAttribute('data-test'");
        logger.log("TargetFactory.setDataAttributeSuffix('test'");
        throw new IllegalStateException();

        }

    public static void setDataAttribute(String dataAttribute) {
        TargetFactory.dataAttribute = dataAttribute;
    }

    public static String getDataAttributeSuffix() {
        return dataAttributeSuffix;
    }

    public static void setDataAttributeSuffix(String dataAttributeSuffix) {
        TargetFactory.dataAttributeSuffix = dataAttributeSuffix;
    }

    public TargetFactory() {
    }

    ////////////////
    // ATTRIBUTES //
    ////////////////

    // HTML ATTRIBUTE
    protected static Target attribute(Enums.Attribute attribute) {
        return new Target("[" + attribute.getValue() + "]", attribute.getValue(), HTML_ATTRIBUTE);
    }

    // HTML ATTRIBUTE WITH VALUE
    protected static Target attribute(Enums.Attribute attribute, String value) {
        return new Target("[" + attribute.getValue() + "='" + value + "']", attribute.getValue(), value, HTML_ATTRIBUTE_VALUE);
    }

    //  HTML ATTRIBUTE WITH PARTIAL VALUE
    protected static Target attributeContains(Enums.Attribute attribute, String value) {
        return new Target("//*[contains(@" + attribute.getValue() + ", '" + value + "')]", attribute.getValue(), value, HTML_ATTRIBUTE_VALUE_CONTAINS);
    }

    // NON-HTML ATTRIBUTE
    protected static Target attribute(String attribute) {
        return new Target("[" + attribute + "]", attribute, ATTRIBUTE);
    }

    // NON-HTML ATTRIBUTE WITH VALUE
    protected static Target attribute(String attribute, String value) {
        return new Target("[" + attribute + "='" + value + "']", attribute, value, ATTRIBUTE_VALUE);
    }
    // NON-HTML ATTRIBUTE WITH PARTIAL VALUE
    protected static Target attributeContains(String attribute, String value) {
        return new Target("//*[contains(@" + attribute + ", '" + value + "')]", attribute, value, ATTRIBUTE_VALUE_CONTAINS);
    }

    //  HTML ATTRIBUTE WITH PARTIAL VALUE
    protected static Target partialAttribute(Enums.Attribute attribute, String value) {
        return new Target("//*[contains(@" + attribute.getValue() + ", '" + value + "')]", attribute.getValue(), value, PARTIAL_ATTRIBUTE);
    }

    // NON-HTML ATTRIBUTE WITH PARTIAL VALUE
    protected static Target partialAttribute(String attribute, String value) {
        return new Target("//*[contains(@" + attribute + ", '" + value + "')]", attribute, value, PARTIAL_ATTRIBUTE);
    }

    /////////////
    // CLASSES //
    /////////////

    // Target : className("MyClass")
    protected static Target className(String className) {
        return new Target("." + className, "class", className, CLASS);
    }

    // Target : classNames("MyClass1 MyClass2")
    protected static Target classNames(String classNames) {

        StringTokenizer st = new StringTokenizer(classNames);
        StringBuffer sb = new StringBuffer();

        while (st.hasMoreTokens()) {
            sb.append(".");
            sb.append(st.nextToken());
        }

        return new Target(sb.toString(), classNames, CLASSES);
    }

    // Target : id("MyId") = "#MyId"
    protected static Target id(String value) {
        return new Target("#" + value, "id", value, ID);
    }

    // Target : name("MyName")
    protected static Target name(String value) {
        return new Target("[name='" + value + "']", "name", value, NAME);
    }

    // Target : css("css selector")
    protected static Target css(String value) {
        return new Target(value, value, CSS);
    }

    // Target : placeholder("Enter your name")
    protected static Target placeholder(String value) {
        return new Target("//input[@placeholder='" + value + "'] | //textarea[@placeholder='" + value + "']", "placeholder", value, PLACEHOLDER);
    }

    // Target : label("Enter your name").  Will locate associated <input> control.
    protected static Target label(String label) {
        return new Target(label, LABEL);
    }

    // Target : title("My Title")
    protected static Target title(String value) {
        return new Target("//*[@title='" + value + "']", "title", value, TITLE);
    }

    // Target : role(AriaRole.BUTTON)
    // Carries the AriaRole only (no selector). Resolved at decode-time via
    // Playwright's page.getByRole(role) which walks the accessibility tree.
    // role(AriaRole) cannot be used in a conjunction.
    protected static Target role(AriaRole ariaRole) {
        return new Target(ariaRole, ROLE);
    }

    // Target : role("button")
    // <a role = "button" >.
    // role(String) can be used in a conjunction.
    protected static Target role(String role) {
        return new Target("//*[@role='" + role + "']", "title", role, ROLE_LITERAL);
    }

    // Target : role(AriaRole.CHECKBOX, "Subscribe")
    // Resolves at decode-time via Playwright's page.getByRole(role, name) so it cannot be used
    // in a conjunction — Target.and() throws IllegalArgumentException for ROLE_AND_LABEL.
    protected static Target role(AriaRole role, String label) {
        return new Target(role, label, ROLE_AND_LABEL);
    }

    ////////////////////////////
    // HTML 5 DATA ATTRIBUTES //
    ////////////////////////////

    // W3 standard data attributes are in the format data-*
    // Suffix is provided unless previously set
    // setDataAttributeSuffix(String suffix) method
    // setDataAttributeSuffix("ci-id")
    // OR
    // setDataAttribute("data-ci-id")

    // Commonly used data attribute - Target : dataTestId("test-id") = data-testId="test-id"
    protected static Target dataTestId(String value) {
        return new Target("[data-test-id='" + value + "']", "data-test-id", value, DATA_TEST_ID);
    }

    // Commonly used data attribute - Target : dataTest("data-test") = data-test="data-test"
    protected static Target dataTest(String value) {
        return new Target("[data-test='" + value + "']", "data-test", value, DATA_TEST);
    }

    // e.g. Target : data("ci-id", "test-id") = data-ci-id="test-id"
    protected static Target data(String suffix, String value) {
        return new Target("[" + DATA_ATTRIBUTE_PREFIX + suffix + "='" + value + "']", value, DATA_TEST_ID_SUFFIX);
    }

    // Assumes the data attribute suffix has been set with e.g. setDataAttributeSuffix("ci-id") resolving to "data-ci-id""
    // or the whole data attribute has been set with setDataAttribute("data-ci-id")
    // Selector is resolved lazily — see getData() — because setDataAttribute() may not have been called yet
    protected static Target data(String value) {
        return new Target("[" + getDataAttribute() + "='" + value + "']", value, DATA);
    }

    // Hyperlink.
    // <a href="https://www.example.com">Example</a>
    // Target : href("https://www.example.com")
    protected static Target href(String value) {
        return new Target("[href='" + value + "']", "href", value, HREF);
    }

    // Image source.
    // <img src="https://www.example.com/image.jpg" alt="Example Image">
    protected static Target src(String value) {
        return new Target("//*[@src='" + value + "']", value, SRC);
    }

    // CSS style attribute.
     // <div style="row styled">Styled Text
    // Target : style("row styled")
    protected static Target style(String value) {
        return new Target("//*[@style='" + value + "']", value, STYLE);
    }

    // Alternative text for images.
    // <img src="https://www.example.com/image.jpg" alt="Example Image">
    // Target : alt("Example Image")
    protected static Target alt(String value) {
        return new Target("[alt='" + value + "']", "alt",value, ALT);
    }

    // This will locate an element with the given exact text regardless of containing tag
    // <p> This is an example text </p>
    // text("This is an example text")
    protected static Target text(String value) {
        String v = value.trim();
        return new Target("//*[(normalize-space(text())=" + xpathLiteral(v) + ")]", v, TEXT);
    }

    // This will locate an element with the given partial text regardless of containing tag
    // <p> This is an example partial text </p>
    // partialText("example partial")
    protected static Target partialText(String text) {
        String v = text.trim();
        return new Target("//*[contains(text(), " + xpathLiteral(v) + ")]", v, PARTIAL_TEXT);
    }

    // This will locate an element with the given HTML5 tag and exact text
    // <p> This is an example text </p>
    // tagWithText(P, "This is an example text")
    protected static Target tagWithText(Enums.Tag tag, String text) {
        String t = text.trim();
        return new Target("//" + tag.name().toLowerCase() + "[contains(normalize-space(text()), " + xpathLiteral(t) + ")]", tag.name().toLowerCase(), t, HTML_TAG_WITH_TEXT);
    }

    // This will locate an element with the given HTML5 tag and partial text
    // <p> This is an example partial text </p>
    protected static Target tagContainsText(Enums.Tag tag, String text) {
        String t = text.trim();
        return new Target("//" + tag.name().toLowerCase() + "[contains(normalize-space(text()), " + xpathLiteral(t) + ")]", tag.name().toLowerCase(), t, HTML_TAG_CONTAINS_TEXT);
    }

    // This will locate an element with the given non-html tag and exact text eg.
    // <mytag> This is an example text </mytag>
    // tagWithText("mytag", "This is an example text")
    protected static Target tagWithText(String tag, String text) {
        String t = text.trim();
        return new Target("//" + tag.toLowerCase() + "[contains(normalize-space(text()), " + xpathLiteral(t) + ")]", tag.toLowerCase(), t, TAG_WITH_TEXT);
    }

    // This will locate an element with the given non-html tag and exact text e.g.
    // <mytag> This is an example text </mytag>
    // tagContainsText("mytag", "This is an example text")
    protected static Target tagContainsText(String tag, String text) {
        String t = text.trim();
        return new Target("//" + tag.toLowerCase() + "[contains(normalize-space(text()), " + xpathLiteral(t) + ")]", tag.toLowerCase(), t, TAG_CONTAINS_TEXT);
    }

    /**
     * Encodes a Java string as an XPath 1.0 string literal that is safe to embed
     * directly inside a predicate (e.g. contains(text(), …)).
     *
     * <p>XPath 1.0 has no escape sequence inside a string literal — a single quote
     * terminates a single-quoted literal and vice versa. Strings that contain
     * apostrophes (e.g. "Driver's license") must be assembled with
     * {@code concat('Driver', "'", 's license')}.</p>
     *
     * <ul>
     *   <li>No apostrophe → {@code 'value'}</li>
     *   <li>No double quote → {@code "value"}</li>
     *   <li>Both → {@code concat('part', "'", 'part', '"', …)}</li>
     * </ul>
     */
    protected static String xpathLiteral(String value) {
        if (value == null) return "''";
        if (!value.contains("'")) return "'" + value + "'";
        if (!value.contains("\"")) return "\"" + value + "\"";
        StringBuilder sb = new StringBuilder("concat(");
        boolean first = true;
        int i = 0;
        while (i < value.length()) {
            int next = value.indexOf('\'', i);
            String chunk = next == -1 ? value.substring(i) : value.substring(i, next);
            if (!chunk.isEmpty()) {
                if (!first) sb.append(", ");
                sb.append('\'').append(chunk).append('\'');
                first = false;
            }
            if (next == -1) break;
            if (!first) sb.append(", ");
            sb.append("\"'\"");
            first = false;
            i = next + 1;
        }
        sb.append(')');
        return sb.toString();
    }


    // Locate an element with the given HTML5 tag eg.
    // <p> This is an example text </p>
    // tag(P)
    protected static Target tag(Enums.Tag tag) {
        return new Target("//"+tag.name().toLowerCase(), tag.name(), HTML_TAG);
    }

    // Locate an element with the given non-HTML5 tag e.g.
    // <myTag> This is an example text </myTag>
    // tag("myTag")
    protected static Target tag(String tag) {

        return new Target("//"+tag.toLowerCase(), tag, HTML_TAG);
        }
    // This will locate a hyperlink with the given exact text eg.
    // <a href="https://www.example.com">Example</a>
    // linkText("Example")
    protected static Target linkText(String text) {
        String t = text.trim();
        return new Target("a:text('" + t + "')", t, LINK_TEXT);
    }

    // This will locate a hyperlink with the given partial text eg.
    // <a href="https://www.example.com">This is an example partial text</
    // Target : partialLinkText("example partial")
    protected static Target partialLinkText(String partialText) {
        return new Target("a:text-matches('" + partialText + "')", partialText, PARTIAL_LINK_TEXT);
    }

    // Value attribute of an element e.g.
    // <input type="text" value="Example Value">
    // value("Example Value")
    protected static Target value(String value) {
        return new Target("[value='" + value + "']", "value", value, VALUE);
    }

    // HTML ROOT ELEMENT.  See also Core method : origin()
    protected static Target root() {
        return new Target("html", "Root", ROOT);
    }

    // Use with collect() to collect list of children of an element.
    protected static Target children() {
        return new Target("> *", "Direct Children", CHILDREN);
    }

    // Use with collect() to collect list of siblings of an element.
    protected static Target siblings() {
        return new Target("xpath=following-sibling::* | preceding-sibling::*", "Siblings", SIBLINGS);
    }

    // This will match on a prefix and text /  suffix which can occur anywhere in the element text
    // ends-with() is an XPath 2 method and Selenium supports only XPath 1 so ends-with(text() not possible
    // Playwright auto-detects XPath only when the selector starts with "//" or "xpath="; a leading
    // ".//" is otherwise treated as CSS and fails to parse, so we prefix explicitly.
    public static Target splice(String prefix, String suffix) {
        return new Target("xpath=.//*[starts-with(text(), '" + prefix + "') and contains(text(), '" + suffix + "')]", prefix, suffix, SPLICE);
    }

    // Innermost element from current focus.  See also Core method : leaf()
    public static Target leaf() {
        return new Target(LEAF);
    }

    // HTML type attribute e.g.
    // <input type="text">
    // type(TEXT)
    public static Target type(Enums.Type type) {
        return new Target("//*[@type='" + type.getValue() + "']", "type", type.getValue(), TYPE);
    }

    //////////////////////////////////
    // TO BE MOVED TO ANGULAR CLASS //
    //////////////////////////////////
    // TODO.  Check V2 usage

    @Deprecated
    protected static Target dataEndpoint(String dataEndpoint) {
        return new Target("//*[@data-endpoint='" + dataEndpoint + "']", "data-endpoint", dataEndpoint, DATA_ENDPOINT);
    }

    @Deprecated
    protected static Target dataField(String dataField) {
        return new Target("//*[@data-field='" + dataField + "']", dataField, DATA_FIELD);
    }

    /////////////////////////////////////////////////////////////////////////
    // DEPRECATED - Use conjunctions e.g. tag(tag).and(id("MyId")) instead //
    /////////////////////////////////////////////////////////////////////////

    @Deprecated
    protected static Target tagWithId(Enums.Tag tag, String id) {
        return new Target("//" + tag.name() + "[@id='" + id + "']", tag.name(), id, TAG_WITH_ID);
    }

    @Deprecated
    protected static Target tagWithTitle(Enums.Tag tag, String title) {
        return new Target("//" + tag.name() + "[@title='" + title + "']", tag.name(), title, TAG_WITH_TITLE);
    }

    @Deprecated
    protected static Target tagWithName(Enums.Tag tag, String name) {
        return new Target("//" + tag.name() + "[@name='" + name + "']", tag.name(), name, TAG_WITH_NAME);
    }

    @Deprecated
    // XPATH
    protected static Target xpath(String xpath) {
        return new Target(xpath, xpath, XPATH);
    }
}

package sonnet.reference.playwright;

import sonnet.reference.playwright.locators.Target;

import java.util.List;

/**
 * DOM assertion interface — defines utility methods for verifying
 * URL, title, text content, attributes, element presence, collections,
 * and tag types against the current page and focused element.
 *
 * <p>Implemented by {@link CommonPageObject}. All methods return
 * {@code CommonPageObject} for fluent chaining and throw
 * {@link AssertionError} when the assertion fails.</p>
 *
 * @see CommonPageObject
 */
public interface DOMInterface {

    // ── URL ─────────────────────────────────────────────────────────

    /**
     * Asserts the current page URL equals the expected value.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.urlEquals("https://example.com/dashboard");
     * }</pre>
     *
     * @param url the expected URL
     * @return this instance for chaining
     */
    CommonPageObject urlEquals(String url);

    /**
     * Asserts the current page URL contains the expected substring.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.urlContains("/dashboard");
     * }</pre>
     *
     * @param url the substring to look for
     * @return this instance for chaining
     */
    CommonPageObject urlContains(String url);

    /**
     * Asserts the current page URL starts with the expected prefix.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.urlStartsWith("https://example.com");
     * }</pre>
     *
     * @param url the expected prefix
     * @return this instance for chaining
     */
    CommonPageObject urlStartsWith(String url);

    /**
     * Asserts the current page URL ends with the expected suffix.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.urlEndsWith("/dashboard");
     * }</pre>
     *
     * @param url the expected suffix
     * @return this instance for chaining
     */
    CommonPageObject urlEndsWith(String url);

    // ── TITLE ───────────────────────────────────────────────────────

    /**
     * Asserts the current page title equals the expected value.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.titleEquals("Dashboard — My App");
     * }</pre>
     *
     * @param title the expected title
     * @return this instance for chaining
     */
    CommonPageObject titleEquals(String title);

    /**
     * Asserts the current page title contains the expected substring.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.titleContains("Dashboard");
     * }</pre>
     *
     * @param title the substring to look for
     * @return this instance for chaining
     */
    CommonPageObject titleContains(String title);

    /**
     * Asserts the current page title starts with the expected prefix.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.titleStartsWith("Dashboard");
     * }</pre>
     *
     * @param title the expected prefix
     * @return this instance for chaining
     */
    CommonPageObject titleStartsWith(String title);

    /**
     * Asserts the current page title ends with the expected suffix.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.titleEndsWith("My App");
     * }</pre>
     *
     * @param title the expected suffix
     * @return this instance for chaining
     */
    CommonPageObject titleEndsWith(String title);

    // ── TEXT ─────────────────────────────────────────────────────────

    /**
     * Asserts the focused element's text content equals the expected value.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.focus(id("greeting")).textEquals("Hello World");
     * }</pre>
     *
     * @param text the expected text
     * @return this instance for chaining
     */
    CommonPageObject textEquals(String text);

    /**
     * Asserts the focused element's text content does <em>not</em> equal the given value.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.focus(id("greeting")).textDiffers("Goodbye");
     * }</pre>
     *
     * @param text the value the text must not equal
     * @return this instance for chaining
     */
    CommonPageObject textDiffers(String text);

    /**
     * Asserts the focused element's text content contains the expected substring.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.focus(id("greeting")).textContains("Hello");
     * }</pre>
     *
     * @param text the substring to look for
     * @return this instance for chaining
     */
    CommonPageObject textContains(String text);

    /**
     * Asserts the focused element's text content starts with the expected prefix.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.focus(id("greeting")).textStartsWith("Hello");
     * }</pre>
     *
     * @param text the expected prefix
     * @return this instance for chaining
     */
    CommonPageObject textStartsWith(String text);

    /**
     * Asserts the focused element's text content ends with the expected suffix.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.focus(id("greeting")).textEndsWith("World");
     * }</pre>
     *
     * @param text the expected suffix
     * @return this instance for chaining
     */
    CommonPageObject textEndsWith(String text);

    /**
     * Asserts the focused element has non-empty text content.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.focus(id("greeting")).textPresent();
     * }</pre>
     *
     * @return this instance for chaining
     */
    CommonPageObject textPresent();

    /**
     * Asserts the focused element's text content equals the given value.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.focus(id("greeting")).textPresent("Hello World");
     * }</pre>
     *
     * @param text the expected text
     * @return this instance for chaining
     */
    CommonPageObject textPresent(String text);

    /**
     * Asserts the focused element has empty text content.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.focus(id("empty-div")).textNotPresent();
     * }</pre>
     *
     * @return this instance for chaining
     */
    CommonPageObject textNotPresent();

    /**
     * Asserts the focused element's text content does not contain the given value.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.focus(id("greeting")).textNotPresent("Goodbye");
     * }</pre>
     *
     * @param text the value that must not appear
     * @return this instance for chaining
     */
    CommonPageObject textNotPresent(String text);

    /**
     * Asserts the focused element's text content has exactly the given length.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.focus(id("code")).textHasLength(6);
     * }</pre>
     *
     * @param length the expected character count
     * @return this instance for chaining
     */
    CommonPageObject textHasLength(int length);

    // ── ATTRIBUTE ───────────────────────────────────────────────────

    /**
     * Asserts the focused element's attribute equals the expected value.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.focus(id("logo")).attributeEquals(Enums.ElementTrait.ALT, "Company Logo");
     * }</pre>
     *
     * @param attribute the attribute to inspect
     * @param value     the expected value
     * @return this instance for chaining
     */
    CommonPageObject attributeEquals(Enums.ElementTrait attribute, String value);

    /**
     * Asserts the focused element's attribute contains the expected substring.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.focus(id("logo")).attributeContains(Enums.ElementTrait.CLASS, "active");
     * }</pre>
     *
     * @param attribute the attribute to inspect
     * @param value     the substring to look for
     * @return this instance for chaining
     */
    CommonPageObject attributeContains(Enums.ElementTrait attribute, String value);

    /**
     * Asserts the focused element's attribute starts with the expected prefix.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.focus(id("link")).attributeStartsWith(Enums.ElementTrait.HREF, "https://");
     * }</pre>
     *
     * @param attribute the attribute to inspect
     * @param value     the expected prefix
     * @return this instance for chaining
     */
    CommonPageObject attributeStartsWith(Enums.ElementTrait attribute, String value);

    /**
     * Asserts the focused element's attribute ends with the expected suffix.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.focus(id("link")).attributeEndsWith(Enums.ElementTrait.HREF, ".html");
     * }</pre>
     *
     * @param attribute the attribute to inspect
     * @param value     the expected suffix
     * @return this instance for chaining
     */
    CommonPageObject attributeEndsWith(Enums.ElementTrait attribute, String value);

    // ── ELEMENT PRESENCE ────────────────────────────────────────────

    /**
     * Asserts the specified Target is present in the DOM.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.elementPresent(id("sidebar"));
     * }</pre>
     *
     * @param target the Target to check
     * @return this instance for chaining
     */
    CommonPageObject elementPresent(Target target);

    /**
     * Asserts the specified Target is <em>not</em> present in the DOM.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.elementNotPresent(id("modal-overlay"));
     * }</pre>
     *
     * @param target the Target to check
     * @return this instance for chaining
     */
    CommonPageObject elementNotPresent(Target target);

    // ── COLLECTION ──────────────────────────────────────────────────

    /**
     * Asserts the current collection is not empty.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.collect(className("row")).collectionPresent();
     * }</pre>
     *
     * @return this instance for chaining
     */
    CommonPageObject collectionPresent();

    /**
     * Asserts the current collection is empty.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.collect(className("error")).collectionNotPresent();
     * }</pre>
     *
     * @return this instance for chaining
     */
    CommonPageObject collectionNotPresent();

    /**
     * Asserts the current collection has exactly the expected size.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.collect(className("row")).collectionHasSize(5);
     * }</pre>
     *
     * @param size the expected count
     * @return this instance for chaining
     */
    CommonPageObject collectionHasSize(int size);

    /**
     * Returns the size of the current collection.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.collect(className("row"));
     *   int n = cpo.size();
     * }</pre>
     *
     * @return the number of elements in the current collection, or 0 if no collection is set
     */
    int size();

    /**
     * Asserts the current collection has more than the given number of elements.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.collect(className("row")).collectionLargerThan(2);
     * }</pre>
     *
     * @param size the lower bound (exclusive)
     * @return this instance for chaining
     */
    CommonPageObject collectionLargerThan(int size);

    /**
     * Asserts the current collection has fewer than the given number of elements.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.collect(className("row")).collectionSmallerThan(10);
     * }</pre>
     *
     * @param size the upper bound (exclusive)
     * @return this instance for chaining
     */
    CommonPageObject collectionSmallerThan(int size);

    // ── COLLECTION CONTENT CHECKS ───────────────────────────────────

    /**
     * Asserts the current collection's text content matches the expected list exactly
     * (same elements, same order, no extras). Equivalent to {@code collectionIs(expected, true)}.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.collect(tag(LI)).collectionIs(List.of("Apple", "Banana", "Cherry"));
     * }</pre>
     *
     * @param expected the expected list of text values in order
     * @return this instance for chaining
     */
    CommonPageObject collectionIs(List<String> expected);

    /**
     * Asserts the current collection's text content matches the expected list.
     * When {@code strict} is true, order must also match. When false (lenient),
     * all elements must be present and no extras, but in any order.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.collect(tag(LI)).collectionIs(List.of("Cherry", "Apple", "Banana"), false);
     * }</pre>
     *
     * @param expected the expected list of text values
     * @param strict   true for ordered match, false for unordered match
     * @return this instance for chaining
     */
    CommonPageObject collectionIs(List<String> expected, boolean strict);

    /**
     * Asserts the current collection contains an element with the given text.
     * Other elements may also be present.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.collect(tag(LI)).collectionContains("Banana");
     * }</pre>
     *
     * @param item the text value that must be present
     * @return this instance for chaining
     */
    CommonPageObject collectionContains(String item);

    /**
     * Asserts the current collection contains all elements from the given list.
     * Other elements may also be present.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.collect(tag(LI)).collectionContains(List.of("Apple", "Cherry"));
     * }</pre>
     *
     * @param items the text values that must all be present
     * @return this instance for chaining
     */
    CommonPageObject collectionContains(List<String> items);

    /**
     * Asserts the current collection does not contain an element with the given text.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.collect(tag(LI)).collectionDoesNotContain("Mango");
     * }</pre>
     *
     * @param item the text value that must not be present
     * @return this instance for chaining
     */
    CommonPageObject collectionDoesNotContain(String item);

    /**
     * Asserts none of the elements from the given list are present in the collection.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.collect(tag(LI)).collectionDoesNotContain(List.of("Mango", "Papaya"));
     * }</pre>
     *
     * @param items the text values that must all be absent
     * @return this instance for chaining
     */
    CommonPageObject collectionDoesNotContain(List<String> items);

    // ── TAG ─────────────────────────────────────────────────────────

    /**
     * Asserts the focused element's tag name matches the expected tag.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.focus(id("title")).tagIs(Enums.Tag.H1);
     * }</pre>
     *
     * @param tag the expected tag
     * @return this instance for chaining
     */
    CommonPageObject tagIs(Enums.Tag tag);

    /**
     * Asserts the focused element's tag name does <em>not</em> match the given tag.
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.focus(id("title")).tagIsNot(Enums.Tag.DIV);
     * }</pre>
     *
     * @param tag the tag that must not match
     * @return this instance for chaining
     */
    CommonPageObject tagIsNot(Enums.Tag tag);

    // ── PRINT (visual debug) ────────────────────────────────────────

    /**
     * Logs a structured snapshot of the currently focused element to the
     * configured logger. HTML attributes that are absent or empty are
     * omitted from the output.
     *
     * <p>Visual debug aid only — has no effect on focus state.</p>
     *
     * <p><b>Example output:</b></p>
     * <pre>{@code
     *   >>>>> Current Element <<<<<<
     *   Tag   : input
     *   ID    : email-field
     *   Name  : email
     *   Type  : email
     *   Class : form-control active
     *   Width  : 220.0
     *   Height : 32.0
     *   Pos X  : 480.0
     *   Pos Y  : 312.0
     * }</pre>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.focus(id("email-field")).elementPrint();
     * }</pre>
     *
     * @return this instance for chaining
     */
    CommonPageObject elementPrint();

    /**
     * Logs a structured snapshot of every element in the current collection
     * to the configured logger. Calls the same per-element block as
     * {@link #elementPrint()} for each item, prefixed with an element index.
     *
     * <p>Does not mutate the currently focused element.</p>
     *
     * <p><b>Example:</b></p>
     * <pre>{@code
     *   cpo.collect(className("row")).collectionPrint();
     * }</pre>
     *
     * @return this instance for chaining
     */
    CommonPageObject collectionPrint();
}

package sonnet.junit.dom;

import org.junit.jupiter.api.*;
import org.opentest4j.AssertionFailedError;
import sonnet.CommonPageObject;

import static org.junit.Assert.assertThrows;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class Text extends CommonPageObject {
    String home = System.getProperty("user.dir");
    final String URL = home + "\\src\\test\\java\\sonnet\\junit\\html\\dom\\Text.html";

    @BeforeAll
    public void setUpAll() {
        open();
        go(URL);
    }

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("text")
    @DisplayName("Equals Text Test")
    public void t1() {
        focus(id("texttest"));
        //true
        textEquals("Hello. This is a text field. Please test me!");
        //false
        assertThrows(AssertionFailedError.class, () ->
                textEquals("Text field 101"));
    }

    @Test
    @Tag("text")
    @DisplayName("Differs Text Test")
    public void t2() {
        focus(id("texttest"));
        //true
        textDiffers("Goodbye. This isn't a text field. Please go away!");
        //false
        assertThrows(AssertionFailedError.class, () ->
                textDiffers("Hello. This is a text field. Please test me!"));
    }

    @Test
    @Tag("text")
    @DisplayName("Contains Text Test")
    public void t3() {
        focus(id("texttest"));
        //true
        textContains("This is a text field.");
        //false
        assertThrows(AssertionFailedError.class, () ->
                textContains("Goodbye."));
    }

    @Test
    @Tag("text")
    @DisplayName("StartsWith Text Test")
    public void t4() {
        focus(id("texttest"));
        //true
        textStartsWith("Hello");
        //false
        assertThrows(AssertionFailedError.class, () ->
                textStartsWith("me!"));
    }

    @Test
    @Tag("text")
    @DisplayName("EndsWith Text Test")
    public void t5() {
        focus(id("texttest"));
        //true
        textEndsWith("me!");
        //false
        assertThrows(AssertionFailedError.class, () ->
                textEndsWith("Hello"));
    }

    @Test
    @Tag("text")
    @DisplayName("Present Text Test")
    public void t6() {
        focus(id("texttest"));
        //true
        textPresent();
        //----------------//
        focus(id("emptytest"));
        //false
        assertThrows(AssertionFailedError.class, () ->
                textPresent());
    }

    @Test
    @Tag("text")
    @DisplayName("Present Text Test")
    public void t7() {
        focus(id("emptytest"));
        //true
        textNotPresent();
        //----------------//
        focus(id("texttest"));
        //false
        assertThrows(AssertionFailedError.class, () ->
                textNotPresent());
    }

    @Test
    @Tag("text")
    @DisplayName("Present Text Test")
    public void t8() {
        focus(id("lengthtest"));
        //true
        textHasLength(10);
        //false
        assertThrows(AssertionFailedError.class, () ->
                textHasLength(12));
    }

    @Test
    @Tag("text")
    @DisplayName("Chained Text Test")
    public void t9() {
        focus(id("texttest"))
                .textEquals("Hello. This is a text field. Please test me!")
                .textDiffers("Goodbye. This isn't a text field. Please go away!")
                .textContains("This is a text field.")
                .textStartsWith("Hello")
                .textEndsWith("me!")
                .textPresent()
        .focus(id("lengthtest"))
                .textHasLength(10);
    }
}
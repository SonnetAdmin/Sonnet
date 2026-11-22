package sonnet.junit.dom;

import org.junit.jupiter.api.*;
import org.opentest4j.AssertionFailedError;
import sonnet.CommonPageObject;

import static org.junit.Assert.assertThrows;
import static sonnet.Enum.HTMLTag.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class Assembly extends CommonPageObject {
    String home = System.getProperty("user.dir");
    final String URL = home + "\\src\\test\\java\\sonnet\\junit\\html\\dom\\Assembly.html";

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
    @Tag("assembly")
    @DisplayName("Assembly Size Test")
    public void t1() {
        //true
        focus(id("days"));
        assemble(tag(P));
        assemblySize(7);
        //false
        assertThrows(AssertionFailedError.class, () ->
                assemblySize(25));
    }

    @Test
    @Tag("assembly")
    @DisplayName("Not Present Assembly Test")
    public void t3() {
        focus(id("texttest"));
        //true
        textContains("This is a text field.");
        //false
        assertThrows(AssertionFailedError.class, () ->
                textContains("Goodbye."));
    }

    @Test
    @Tag("assembly")
    @DisplayName("StartsWith Assembly Test")
    public void t4() {
        focus(id("texttest"));
        //true
        textStartsWith("Hello");
        //false
        assertThrows(AssertionFailedError.class, () ->
                textStartsWith("me!"));
    }

    @Test
    @Tag("assembly")
    @DisplayName("EndsWith Assembly Test")
    public void t5() {
        focus(id("texttest"));
        //true
        textEndsWith("me!");
        //false
        assertThrows(AssertionFailedError.class, () ->
                textEndsWith("Hello"));
    }

    @Test
    @Tag("assembly")
    @DisplayName("Present Assembly Test")
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
    @Tag("Assembly")
    @DisplayName("Chained Assembly Test")
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
package sonnet.junit.dom;

import org.junit.jupiter.api.*;
import org.opentest4j.AssertionFailedError;
import sonnet.CommonPageObject;

import static org.junit.Assert.assertThrows;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class URL extends CommonPageObject {
    String home = System.getProperty("user.dir");
    final String URL = home + "\\src\\test\\java\\sonnet\\junit\\html\\dom\\Url.html";
    final String URL_FORMAT = "file:///"+URL.replace("\\", "/");

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
    @Tag("url")
    @DisplayName("Equals Url Test")
    public void t1() {
        //true
        urlEquals(URL_FORMAT); // file:///+home+/src/test/java/sonnet/junit/html/dom/Url.html
        //false
        assertThrows(AssertionFailedError.class, () ->
                urlEquals(URL_FORMAT.replace("Url", "Alt"))); // file:///+home+/src/test/java/sonnet/junit/html/dom/Alt.html
    }

    @Test
    @Tag("url")
    @DisplayName("Contains Url Test")
    public void t2() {
        //true
        urlContains("Url.html");
        //false
        assertThrows(AssertionFailedError.class, () ->
                urlContains("Alt.html"));
    }

    @Test
    @Tag("url")
    @DisplayName("StartsWith Url Test")
    public void t3() {
        //true
        urlStartsWith("file:///");
        //false
        assertThrows(AssertionFailedError.class, () ->
                urlStartsWith("page:"));
    }

    @Test
    @Tag("url")
    @DisplayName("EndsWith Url Test")
    public void t4() {
        //true
        urlEndsWith("html");
        //false
        assertThrows(AssertionFailedError.class, () ->
                urlEndsWith("lmth"));
    }

    @Test
    @Tag("url")
    @DisplayName("Chained Url Test")
    public void t5() {
        urlEquals(URL_FORMAT)
                .urlContains("Url.html")
                .urlStartsWith("file:///")
                .urlEndsWith("html");
    }

    @Test
    @Tag("url")
    @DisplayName("Url Sonnet Test")
    public void t6() {
        go("https://sonnetsoft.com/");
        urlEquals("https://sonnetsoft.com/");
    }


}
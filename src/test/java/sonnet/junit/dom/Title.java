package sonnet.junit.dom;

import org.junit.jupiter.api.*;
import org.opentest4j.AssertionFailedError;
import sonnet.CommonPageObject;

import static org.junit.Assert.assertThrows;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class Title extends CommonPageObject {
    String home = System.getProperty("user.dir");
    final String URL = home + "\\src\\test\\java\\sonnet\\junit\\html\\dom\\Title.html";

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
    @Tag("title")
    @DisplayName("Equals Title Test")
    public void t1() {
        //true
        titleEquals("Title Test");
        //false
        assertThrows(AssertionFailedError.class, () ->
                titleEquals("URL Test"));
    }

    @Test
    @Tag("title")
    @DisplayName("Contains Title Test")
    public void t2() {
        //true
        titleContains("le Te");
        //false
        assertThrows(AssertionFailedError.class, () ->
                titleContains("Banana"));
    }

    @Test
    @Tag("title")
    @DisplayName("StartsWith Title Test")
    public void t3() {
        //true
        titleStartsWith("T");
        //false
        assertThrows(AssertionFailedError.class, () ->
                titleStartsWith("X"));
    }

    @Test
    @Tag("title")
    @DisplayName("EndsWith Title Test")
    public void t4() {
        //true
        titleEndsWith("t");
        //false
        assertThrows(AssertionFailedError.class, () ->
                titleEndsWith("m"));
    }

    @Test
    @Tag("title")
    @DisplayName("Chained Title Test")
    public void t5() {
        titleEquals("Title Test")
                .titleContains("le Te")
                .titleStartsWith("T")
                .titleEndsWith("t");
    }
}
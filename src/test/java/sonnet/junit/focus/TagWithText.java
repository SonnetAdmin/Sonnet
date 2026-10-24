package sonnet.junit.focus;

import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;
import sonnet.Target;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static sonnet.CommonPageInterface.Trait.*;
import static sonnet.Enum.HTMLTag.*;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class TagWithText extends CommonPageObject {

    @BeforeAll
    public void setUpAll() {
        String home = System.getProperty("user.dir");
        open().go(home + "\\src\\test\\java\\sonnet\\junit\\html\\focus\\TagWithText.html");
    }

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("tagwithtext")
    @DisplayName("h1")
    public void t1() {
        Target target = tagWithText(H1,"TagWithText Test");
        focus(target);
        assertTrue(get(TEXT).equals("TagWithText Test"));
        assertFalse(get(TEXT).equals("LabelWithText"));
    }

    @Test
    @Tag("tagwithtext")
    @DisplayName("label")
    public void t2() {
        Target target = tagWithText(LABEL,"LabelWithText");
        focus(target);
        assertTrue(get(TEXT).equals("LabelWithText"));
        assertFalse(get(TEXT).equals("TagWithText Test"));
    }

    @Test
    @Tag("tagwithtext")
    @DisplayName("p")
    public void t3() {
        Target target = tagWithText(P,"Hello");
        focus(target).click();
        assertTrue(get(TEXT).equals("Hello"));
        assertEquals("Hello", get(TEXT));
    }

    @Test
    @Tag("tagwithtext")
    @DisplayName("button")
    public void t4() {
        Target target = tagWithText(BUTTON,"Button");
        focus(target).click();
        assertTrue(get(ENABLED).equals("true"));
        assertTrue(get(TEXT).equals("Button"));
    }

    @Test
    @Tag("tagwithtext")
    @DisplayName("nested text")
    public void t5() {
        focus(tag(H2));
        String text = get(TEXT);
        assertTrue(text.contains("Div"));
        assertFalse(text.contains("Nested Element"));
        assertFalse(text.contains("Child Label"));
    }

    @Test
    @Tag("tagwithtext")
    @DisplayName("nested tag with text")
    public void t6() {
        focus(tagWithText(H2,"Div"));
        assertTrue(get(ID).equals("top"));
        assertFalse(get(TEXT).equals("Nested Element"));
        assertThrows(NoSuchElementException.class, () -> focus(tagWithText(H2,"Nested Element")));

    }
}


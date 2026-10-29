package sonnet.junit;

import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;
import sonnet.Target;

import static org.junit.jupiter.api.Assertions.*;
import static sonnet.CommonPageInterface.Trait.ENABLED;
import static sonnet.CommonPageInterface.Trait.TEXT;
import static sonnet.Enum.HTMLTag.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JTagWithText extends CommonPageObject {

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
    @Tag("add")
    @DisplayName("h1")
    public void t1() {
        Target target = tagWithText(H1,"TagWithText Test Exact");
        focus(target);
        assertTrue(get(TEXT).equals("TagWithText Test Exact"));
        assertFalse(get(TEXT).equals("LabelWithText Exact"));
    }

    @Test
    @Tag("add")
    @DisplayName("label")
    public void t2() {
        Target target = tagWithText(LABEL,"LabelWithText Exact");
        focus(target);
        assertTrue(get(TEXT).equals("LabelWithText Exact"));
        assertFalse(get(TEXT).equals("TagWithText Test Exact"));
    }

    @Test
    @Tag("add")
    @DisplayName("p")
    public void t3() {
        Target target = tagWithText(P,"Hello Exact");
        focus(target).click();
        assertTrue(get(TEXT).equals("Hello Exact"));
        assertEquals("Hello Exact", get(TEXT));
    }

    @Test
    @Tag("add")
    @DisplayName("button")
    public void t4() {
        Target target = tagWithText(BUTTON,"Button Exact");
        focus(target).click();
        assertTrue(get(ENABLED).equals("true"));
        assertTrue(get(TEXT).equals("Button Exact"));
    }
}


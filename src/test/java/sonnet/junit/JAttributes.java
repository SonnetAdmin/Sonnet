package sonnet.junit;


import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;
import sonnet.Html;
import sonnet.Target;

import static org.junit.jupiter.api.Assertions.*;
import static sonnet.CommonPageInterface.Trait.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JAttributes extends CommonPageObject {

    @BeforeAll
    public void setUpAll() {
        String home = System.getProperty("user.dir");
        open().go(home + "\\src\\test\\java\\sonnet\\junit\\html\\focus\\Attributes.html");
    }

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("add")
    @DisplayName("Max test")
    public void t1() {
        Target target = attribute(Html.Attribute.MAX, "12");
        focus(target);
        assertEquals("3", get(VALUE));
    }

    @Test
    @Tag("add")
    @DisplayName("Cols test")
    public void t2() {
        Target target = attribute(Html.Attribute.COLS, "40");
        focus(target);
        assertEquals("A short message", get(TEXT));
    }

    @Test
    @Tag("add")
    @DisplayName("Contenteditable test")
    public void t3() {
        Target target = attribute(Html.Attribute.CONTENTEDITABLE, "true");
        focus(target);
        assertEquals("Drag or edit this notice.", get(TEXT));
    }

    @Test
    @Tag("add")
    @DisplayName("High test")
    public void t4() {
        Target target = attribute(Html.Attribute.HIGH, "80");
        focus(target);
        assertEquals("72", get(VALUE));
    }

    @Test
    @Tag("add")
    @DisplayName("ID Attribute test")
    public void t5() {
        Target target = attribute(Html.Attribute.ID, "email");
        focus(target);
        assertEquals("reader@example.com", get(VALUE));
    }

    @Test
    @Tag("add")
    @DisplayName("ID Target test")
    public void t6() {
        Target target = id("email");
        focus(target);
        assertEquals("reader@example.com", get(VALUE));
    }
}


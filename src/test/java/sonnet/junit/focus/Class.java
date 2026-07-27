package sonnet.junit.focus;

import org.junit.jupiter.api.*;
import sonnet.CommonPage;
import sonnet.Target;

import static org.junit.jupiter.api.Assertions.*;
import static sonnet.CommonPageInterface.Trait.*;
import static sonnet.Enum.HTMLTag.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class Class extends CommonPage {

    @BeforeAll
    public void setUpAll() {
        String home = System.getProperty("user.dir");
        open().go(home + "\\src\\test\\java\\sonnet\\junit\\html\\focus\\Class.html");
    }

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}


    @Test
    @Tag("add")
    @DisplayName("Text Input test")
    public void t1() {
        Target target = classname("text_input");
        focus(target);
        assertTrue(get(TAG).equals("INPUT"));
        assertEquals("INPUT", get(TAG));

    }

    @Test
    @Tag("add")
    @DisplayName("Text Area test")
    public void t2() {
        Target target = classname("text_area");
        focus(target);
        assertTrue(get(TAG).equals("TEXTAREA"));
        assertEquals("TEXTAREA", get(TAG));

    }

    @Test
    @Tag("add")
    @DisplayName("Radio Button test")
    public void t3() {
        Target target = classname("radio1");
        focus(target);
        assertTrue(get(TAG).equals("INPUT"));
        assertEquals("INPUT", get(TAG));

    }

    @Test
    @Tag("add")
    @DisplayName("Checkbox test")
    public void t4() {
        Target target = classname("checkbox");
        focus(target);
        assertTrue(get(TAG).equals("INPUT"));
        assertEquals("INPUT", get(TAG));

    }

    @Test
    @Tag("add")
    @DisplayName("Button test")
    public void t5() {
        Target target = classname("button");
        focus(target);
        assertTrue(get(TAG).equals("BUTTON"));
        assertEquals("BUTTON", get(TAG));

    }
}


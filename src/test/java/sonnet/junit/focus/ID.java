package sonnet.junit.focus;

import org.junit.jupiter.api.*;
import sonnet.CommonPage;
import sonnet.CommonPageInterface;
import sonnet.Target;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class ID extends CommonPage {

    @BeforeAll
    public void setUpAll() {
        String home = System.getProperty("user.dir");
        open().go(home + "\\src\\test\\java\\sonnet\\junit\\html\\focus\\ID.html");
    }

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("add")
    @DisplayName("Text Input test")
    public void t1() {
        Target target = id("text_input");
        focus(target);
        assertTrue(get(CommonPageInterface.Trait.TAG).equals("INPUT"));
        assertEquals("INPUT", get(CommonPageInterface.Trait.TAG));

    }

    @Test
    @Tag("add")
    @DisplayName("Text Area test")
    public void t2() {
        Target target = id("text_area");
        focus(target);
        assertTrue(get(CommonPageInterface.Trait.TAG).equals("TEXTAREA"));
        assertEquals("TEXTAREA", get(CommonPageInterface.Trait.TAG));

    }

    @Test
    @Tag("add")
    @DisplayName("Radio Button test")
    public void t3() {
        Target target = id("radio1");
        focus(target);
        assertTrue(get(CommonPageInterface.Trait.TAG).equals("INPUT"));
        assertEquals("INPUT", get(CommonPageInterface.Trait.TAG));

    }

    @Test
    @Tag("add")
    @DisplayName("Checkbox test")
    public void t4() {
        Target target = id("checkbox");
        focus(target);
        assertTrue(get(CommonPageInterface.Trait.TAG).equals("INPUT"));
        assertEquals("INPUT", get(CommonPageInterface.Trait.TAG));

    }

    @Test
    @Tag("add")
    @DisplayName("Button test")
    public void t5() {
        Target target = id("button");
        focus(target);
        assertTrue(get(CommonPageInterface.Trait.TAG).equals("BUTTON"));
        assertEquals("BUTTON", get(CommonPageInterface.Trait.TAG));

    }
}


package sonnet.junit;

import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;
import sonnet.Target;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static sonnet.CommonPageInterface.Trait.TEXT;
import static sonnet.Enum.HTMLTag.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JCognate extends CommonPageObject {

    @BeforeAll
    public void setUpAll() {
        String home = System.getProperty("user.dir");
        open().go(home + "\\src\\test\\java\\sonnet\\junit\\html\\focus\\Cognate.html");
    }

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("add")
    @DisplayName("Cognate test")
    public void t0() {
        Target target = id("button1");
        focus(target);
        cognate(tag(BUTTON));
        assertEquals("Find me", get(TEXT));
    }

    @Test
    @Tag("add")
    @DisplayName("Cognate test 1")
    public void t1() {
        Target target = id("start");
        focus(target);
        cognate(tag(BUTTON));
        assertEquals("Find me", get(TEXT));
    }

    @Test
    @Tag("add")
    @DisplayName("Cognate test 2")
    public void t2() {
        Target target = id("start2");
        focus(target);
        cognate(tag(BUTTON));
        assertEquals("Find me 2", get(TEXT));
    }

    @Test
    @Tag("add")
    @DisplayName("Cognate test 3")
    public void t3() {
        Target target = id("start2");
        focus(target);
        cognate(tag(BUTTON));
        try {
            assertEquals("Find me", get(TEXT));
        }
        catch (AssertionError ignored) {}
    }

    @Test
    @Tag("add")
    @DisplayName("Cognate test 4")
    public void t4() {
        Target target = id("start4");
        focus(target);
        cognate(tag(BUTTON));
        assertEquals("Find me", get(TEXT));
    }

    @Test
    @Tag("add")
    @DisplayName("Cognate test 5")
    public void t5() {
        Target target = id("start4");
        focus(target);
        cognate(tag(BUTTON));
        try {
            assertEquals("You cant find me", get(TEXT));
        }
        catch (AssertionError ignored) {}
    }

    @Test
    @Tag("add")
    @DisplayName("Cognate test 6")
    public void t6() {
        Target target = id("start4");
        focus(target);
        assertThrows(IllegalArgumentException.class,
                () -> cognate(tag(INPUT)));
    }

    @Test
    @Tag("add")
    @DisplayName("Cognate test 7")
    public void t7() {
        Target target = tag(HTML);
        focus(target);
        cognate(tag(BUTTON));
        assertEquals("Find me", get(TEXT));
    }

    @Test
    @Tag("add")
    @DisplayName("Cognate test 8")
    public void t8() {
        Target target = tag(HTML);
        focus(target);
        assertThrows(IllegalArgumentException.class,
                () -> cognate(tag(INPUT)));
    }
}


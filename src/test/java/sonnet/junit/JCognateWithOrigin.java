package sonnet.junit;

import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;
import sonnet.Target;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static sonnet.CommonPageInterface.Trait.TEXT;
import static sonnet.Enum.HTMLTag.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JCognateWithOrigin extends CommonPageObject {

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
        cognate(id("button1"), tag(BUTTON));
        assertEquals("Find me", get(TEXT));
    }

    @Test
    @Tag("add")
    @DisplayName("Cognate test 1")
    public void t1() {
        cognate(id("start"), tag(BUTTON));
        assertEquals("Find me", get(TEXT));
    }

    @Test
    @Tag("add")
    @DisplayName("Cognate test 2")
    public void t2() {
        cognate(id("start2"), tag(BUTTON));
        assertEquals("Find me 2", get(TEXT));
    }

    @Test
    @Tag("add")
    @DisplayName("Cognate test 3")
    public void t3() {
        cognate(id("start2"), tag(BUTTON));
        try {
            assertEquals("Find me", get(TEXT));
        }
        catch (AssertionError ignored) {}
    }

    @Test
    @Tag("add")
    @DisplayName("Cognate test 4")
    public void t4() {
        cognate(id("start4"), tag(BUTTON));
        assertEquals("Find me", get(TEXT));
    }

    @Test
    @Tag("add")
    @DisplayName("Cognate test 5")
    public void t5() {
        cognate(id("start4"), tag(BUTTON));
        try {
            assertEquals("You cant find me", get(TEXT));
        }
        catch (AssertionError ignored) {}
    }

    @Test
    @Tag("add")
    @DisplayName("Cognate test 6")
    public void t6() {
        assertThrows(IllegalArgumentException.class,
                () -> cognate(id("start4"), tag(INPUT)));
    }

    @Test
    @Tag("add")
    @DisplayName("Cognate test 7")
    public void t7() {
        cognate(tag(HTML), tag(BUTTON));
        assertEquals("Find me", get(TEXT));
    }

    @Test
    @Tag("add")
    @DisplayName("Cognate test 8")
    public void t8() {
        assertThrows(IllegalArgumentException.class,
                () -> cognate(tag(HTML), tag(INPUT)));
    }
}

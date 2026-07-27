package sonnet.junit.focus;

import org.junit.jupiter.api.*;
import sonnet.CommonPage;
import sonnet.Target;

import static org.junit.jupiter.api.Assertions.*;
import static sonnet.CommonPageInterface.Trait.*;
import static sonnet.Enum.HTMLTag.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class Placeholder extends CommonPage {

    @BeforeAll
    public void setUpAll() {
        String home = System.getProperty("user.dir");
        open().go(home + "\\src\\test\\java\\sonnet\\junit\\html\\focus\\Placeholder.html");
    }

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("placeholder")
    @DisplayName("input")
    public void t1() {
        Target target = placeholder("Input");
        focus(target);
        assertTrue(get(ENABLED).equals("true"));
    }

    @Test
    @Tag("placeholder")
    @DisplayName("textarea")
    public void t2() {
        Target target = placeholder("TextArea");
        focus(target);
        assertTrue(get(ENABLED).equals("true"));
    }
}


package sonnet.junit.focus;

import org.junit.jupiter.api.*;
import org.w3c.dom.Text;
import sonnet.CommonPage;
import sonnet.Target;

import static org.junit.jupiter.api.Assertions.*;
import static sonnet.CommonPageInterface.Trait.*;
import static sonnet.Enum.HTMLTag.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class Classnames extends CommonPage {

    @BeforeAll
    public void setUpAll() {
        String home = System.getProperty("user.dir");
        open().go(home + "\\src\\test\\java\\sonnet\\junit\\html\\focus\\Classes.html");
    }

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("add")
    @DisplayName("Class test")
    public void t1() {
        Target target = classnames("A", "B", "C", "D");
        focus(target);
        assertTrue(get(TEXT).equals("ABCD"));
        assertEquals("ABCD", get(TEXT));

    }

    @Test
    @Tag("add")
    @DisplayName("Class test")
    public void t2() {
        Target target = classnames("D", "C", "B", "A");
        focus(target);
        assertTrue(get(TEXT).equals("ABCD"));
        assertEquals("ABCD", get(TEXT));

    }

}


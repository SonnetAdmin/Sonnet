package sonnet.junit;

import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static sonnet.CommonPageInterface.Trait.TEXT;
import static sonnet.Enum.HTMLTag.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JCognateChain extends CommonPageObject {

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
    public void t1() {
        cognate(tag(HTML), id("start")).cognate(tag(BUTTON));
        assertEquals("Find me", get(TEXT));
    }

    @Test
    @Tag("add")
    @DisplayName("Cognate test 2")
    public void t2() {
        assertThrows(IllegalArgumentException.class,
                () -> cognate(tag(HTML), id("start2")).cognate(tag(INPUT)));
    }
}

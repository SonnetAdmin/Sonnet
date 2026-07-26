package sonnet.junit.focus;

import org.junit.jupiter.api.*;
import sonnet.CommonPage;
import sonnet.Target;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sonnet.CommonPageInterface.Trait.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class Name extends CommonPage {

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
    @DisplayName("Checkbox Selected")
    public void t1() {
        Target target = name("checkbox");
        focus(target).click();
        assertTrue(get(SELECTED).equals("true"));

    }

    @Test
    @Tag("add")
    @DisplayName("Text Input test")
    public void t2() {
        Target target = name("button");
        focus(target);
        assertTrue(get(ENABLED).equals("true"));

    }

}


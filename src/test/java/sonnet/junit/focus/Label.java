package sonnet.junit.focus;

import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;
import sonnet.Target;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sonnet.CommonPageInterface.Trait.TAG;
import static sonnet.CommonPageInterface.Trait.TEXT;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class Label extends CommonPageObject {

    @BeforeAll
    public void setUpAll() {
        String home = System.getProperty("user.dir");
        open().go(home + "\\src\\test\\java\\sonnet\\junit\\html\\focus\\Label.html");
    }

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("add")
    @DisplayName("ARIA test")
    public void t1() {
        Target target = label("Name");
        focus(target).compose("My Name");
        assertEquals("INPUT", get(TAG));

    }

    @Test
    @Tag("add")
    @DisplayName("Child test")
    public void t2() {
        Target target = label("Surname");
        focus(target).compose("My Surname");
        assertEquals("INPUT", get(TAG));

    }
}


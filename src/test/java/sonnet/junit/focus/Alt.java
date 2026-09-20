package sonnet.junit.focus;

import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;
import sonnet.Target;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sonnet.CommonPageInterface.Trait.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class Alt extends CommonPageObject {

    @BeforeAll
    public void setUpAll() {
        String home = System.getProperty("user.dir");
        open().go(home + "\\src\\test\\java\\sonnet\\junit\\html\\focus\\Alt.html");
    }

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("add")
    @DisplayName("Alt test with image")
    public void t1() {
        Target target = alt("This is actually a picture of a panda");
        focus(target);
        assertEquals("375.0", get(WIDTH));
        assertEquals("500.0", get(HEIGHT));

    }
    @Test
    @Tag("add")
    @DisplayName("Alt test without image")
    public void t2() {
        Target target = alt("This is a picture of a panda");
        focus(target);
        assertEquals("IMG", get(TAG));

    }
}


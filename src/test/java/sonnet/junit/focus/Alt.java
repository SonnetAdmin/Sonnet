package sonnet.junit.focus;

import org.junit.jupiter.api.*;
import sonnet.CommonPage;
import sonnet.Target;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sonnet.CommonPageInterface.Trait.TAG;
import static sonnet.CommonPageInterface.Trait.TEXT;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class Alt extends CommonPage {

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
    @DisplayName("Alt test")
    public void t1() {
        Target target = alt("This is a picture of a panda");
        focus(target);
        assertEquals("IMG", get(TAG));

    }
}


package sonnet.junit.focus;

import org.junit.jupiter.api.*;
import sonnet.CommonPage;
import sonnet.Target;

import static org.junit.jupiter.api.Assertions.*;
import static sonnet.CommonPageInterface.Trait.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class Title extends CommonPage {

    @BeforeAll
    public void setUpAll() {
        String home = System.getProperty("user.dir");
        open().go(home + "\\src\\test\\java\\sonnet\\junit\\html\\focus\\Title.html");
    }

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("add")
    @DisplayName("Title test")
    public void t1() {
        Target target = title("This is a picture of a Raccoon");
        focus(target);
        assertEquals("IMG", get(TAG));

    }

    @Test
    @Tag("add")
    @DisplayName("Title test")
    public void t2() {
        Target target = title("This is a picture of a Raccoon");
        focus(target);
        assertEquals("387.0", get(WIDTH));
        assertEquals("516.0", get(HEIGHT));
    }
}


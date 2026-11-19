package sonnet.junit;

import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;
import sonnet.Target;

import static org.junit.jupiter.api.Assertions.*;
import static sonnet.Enum.HTMLTag.*;
import static sonnet.Enum.NodeEnum.*;
import static sonnet.Enum.Scroll.*;
import static sonnet.Enum.Trait.*;
import static sonnet.utils.Time.pause;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JTraverse extends CommonPageObject {

    @BeforeAll
    public void setUpAll() {
        open().go("https://practice.expandtesting.com/");
    }


    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("add")
    @DisplayName("Traverse by int index")
    public void t1() {
        Target target = id("menuleft");
        focus(target);
        traverse(1);
        assertEquals("core", get(ID));
    }

    @Test
    @Tag("add")
    @DisplayName("Traverse by enum index")
    public void t2() {
        Target target = id("menuleft");
        focus(target);
        traverse(FIRST);
        assertEquals("core", get(ID));
    }
}


package sonnet.junit.navigate;

import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;
import sonnet.Target;

import static org.junit.jupiter.api.Assertions.*;
import static sonnet.Enum.NodeEnum.*;
import static sonnet.Enum.Trait.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JTraverseAndReverse extends CommonPageObject {

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

    @Test
    @Tag("add")
    @DisplayName("Reverse by int index")
    public void t3() {
        Target target = id("core");
        focus(target);
        reverse(1);
        assertEquals("menuleft", get(ID));
    }

    @Test
    @Tag("add")
    @DisplayName("Reverse by enum index")
    public void t4() {
        Target target = id("core");
        focus(target);
        reverse(FIRST);
        assertEquals("menuleft", get(ID));
    }
}


package sonnet.junit;


import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;
import sonnet.Target;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static sonnet.CommonPageInterface.Trait.*;
import static sonnet.Enum.HTMLTag.*;
import static sonnet.Enum.Index.*;
import static sonnet.Enum.ListIndex.*;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JCollect extends CommonPageObject {

    @BeforeAll
    public void setUpAll() {
        String home = System.getProperty("user.dir");
        open().go(home + "\\src\\test\\java\\sonnet\\junit\\html\\focus\\Collect.html");
    }

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("add")
    @DisplayName("Choose String test")
    public void t1() {
        Target target = id("seasons");
        focus(target).click();
        collect(tag(OPTION));
        choose("Spring");
        assertEquals("Spring", get(TEXT));
    }

    @Test
    @Tag("add")
    @DisplayName("Choose Int test")
    public void t2() {
        focus(tag(SECTION));
        collect(name("layer"));
        assertThrows(IllegalArgumentException.class, () -> choose(0));
        choose(1);
        assertEquals("layer 1", get(TEXT));
        assertThrows(IndexOutOfBoundsException.class, () -> choose(10));
    }

    @Test
    @Tag("add")
    @DisplayName("Choose Index test")
    public void t3() {
        focus(tag(SECTION));
        collect(name("layer"));
        assertThrows(IllegalArgumentException.class, () -> choose(0));
        choose(FIRST);
        assertEquals("layer 1", get(TEXT));
        assertThrows(IndexOutOfBoundsException.class, () -> choose(TENTH));
    }

    @Test
    @Tag("add")
    @DisplayName("Choose List test")
    public void t4() {
        focus(tag(OL));
        collect(tag(LI));
        assertEquals(4, size());
        choose(NOSE);
        assertEquals("Red", get(TEXT));
        choose(TAIL);
        assertEquals("Yellow", get(TEXT));
    }

    @Test
    @Tag("add")
    @DisplayName("Choose next / previous test")
    public void t5() {
        focus(tag(OL));
        collect(tag(LI));
        choose(NEXT);
        assertEquals("Blue", get(TEXT));
        choose(PREVIOUS);
        assertEquals("Red", get(TEXT));
    }

    @Test
    @Tag("add")
    @DisplayName("Choose next / previous after nose test")
    public void t6() {
        focus(tag(OL));
        collect(tag(LI));
        choose(NOSE);
        assertEquals("Red", get(TEXT));
        choose(NEXT);
        assertEquals("Blue", get(TEXT));
        choose(PREVIOUS);
        assertEquals("Red", get(TEXT));
    }

    @Test
    @Tag("add")
    @DisplayName("Choose next / previous after tail test")
    public void t7() {
        focus(tag(OL));
        collect(tag(LI));
        choose(TAIL);
        assertEquals("Yellow", get(TEXT));
        choose(NEXT);
        assertEquals("Yellow", get(TEXT));
        choose(PREVIOUS);
        assertEquals("Green", get(TEXT));
    }



}


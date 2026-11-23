package sonnet.junit;

import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;
import sonnet.SonnetException;
import sonnet.Target;


import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static sonnet.Enum.HTMLTag.*;
import static sonnet.Enum.Index.*;
import static sonnet.Enum.Trait.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JAscendDescend extends CommonPageObject {

    @BeforeAll
    public void setUpAll() {
        String home = System.getProperty("user.dir");
        open().go(home + "\\src\\test\\java\\sonnet\\junit\\html\\focus\\AscendAndDescend.html");
    }

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("Descend")
    @DisplayName("Direct Descend")
    public void t1() {
        Target target = id("parent");
        focus(target);
        get(ID);
        descend();
        assertNotEquals("grandchild", get(ID));
        assertEquals("child", get(ID));
        descend();
        assertNotEquals("child", get(ID));
        assertEquals("grandchild", get(ID));
    }

    @Test
    @Tag("Descend")
    @DisplayName("Direct Descendent Not Present")
    public void t2() {
        Target target = id("grandchild");
        focus(target);
        assertEquals("grandchild", get(ID));
        assertThrows(SonnetException.class, () -> descend());
    }

    @Test
    @Tag("Descend")
    @DisplayName("Index Descend")
    public void t3() {
        Target target = id("parent");
        focus(target);
        descend(2);
        assertNotEquals("child", get(ID));
        assertNotEquals("parent", get(ID));
        assertEquals("grandchild", get(ID));
    }

    @Test
    @Tag("Descend")
    @DisplayName("Index Descendant Not Present")
    public void t4() {
        Target target = id("grandchild");
        focus(target);
        assertThrows(SonnetException.class, () -> descend(5));
    }

    @Test
    @Tag("Descend")
    @DisplayName("Enumerated Index Descend")
    public void t5() {
        Target target = id("parent");
        focus(target);
        descend(SECOND);
        assertNotEquals("child", get(ID));
        assertNotEquals("parent", get(ID));
        assertEquals("grandchild", get(ID));
    }

    @Test
    @Tag("Descend")
    @DisplayName("Enumerated Index Descendant Not Present")
    public void t6() {
        Target target = id("grandchild");
        focus(target);
        assertThrows(SonnetException.class, () -> descend(FIFTH));
    }

    @Test
    @Tag("Descend")
    @DisplayName("Descend To Tag")
    public void t7() {
        Target target = id("parent");
        focus(target);
        descend(DIV);
        assertNotEquals("grandchild", get(ID));
        assertEquals("child", get(ID));
        descend(H1);
        assertNotEquals("child", get(ID));
        assertNotEquals("grandchild", get(ID));
        assertEquals("greatgrandchild", get(ID));
    }

    @Test
    @Tag("Descend")
    @DisplayName("Descend To Tag Not Present")
    public void t8() {
        Target target = id("parent");
        focus(target);
        descend(P);
        assertNotEquals("child", get(ID));
        assertEquals("grandchild", get(ID));
        assertThrows(SonnetException.class, () -> descend(H1));
    }

    @Test
    @Tag("Descend")
    @DisplayName("Descend To Target")
    public void t9() {
        Target target = id("parent");
        Target descendTarget = id("grandchild");
        focus(target);
        descend(descendTarget);
        assertNotEquals("child", get(ID));
        assertEquals("grandchild", get(ID));

    }
    @Test
    @Tag("Descend")
    @DisplayName("Descend To Target Not Present")
    public void t10() {
        Target target = id("grandchild");
        Target descendTarget = id("fake");
        focus(target);
        assertThrows(SonnetException.class, () -> descend(descendTarget));
    }

    @Test
    @Tag("Ascend")
    @DisplayName("Direct Ascend")
    public void t11() {
        Target target = id("grandchild");
        focus(target);
        ascend();
        assertNotEquals("parent", get(ID));
        assertNotEquals("grandchild", get(ID));
        assertEquals("child", get(ID));
        ascend();
        assertNotEquals("child", get(ID));
        assertNotEquals("grandchild", get(ID));
        assertEquals("parent", get(ID));
    }

    @Test
    @Tag("Ascend")
    @DisplayName("Direct Ancestor Not Present")
    public void t12() {
        Target target = tag(HTML);
        focus(target);
        assertEquals("HTML", get(TAG));
        assertThrows(SonnetException.class, () -> ascend());
    }

    @Test
    @Tag("Ascend")
    @DisplayName("Index Ascend")
    public void t13() {
        Target target = id("grandchild");
        focus(target);
        ascend(2);
        assertNotEquals("child", get(ID));
        assertNotEquals("grandchild", get(ID));
        assertEquals("parent", get(ID));
    }

    @Test
    @Tag("Ascend")
    @DisplayName("Index Ancestor Not Present")
    public void t14() {
        Target target = id("parent");
        focus(target);
        assertThrows(SonnetException.class, () -> ascend(5));
    }

    @Test
    @Tag("Ascend")
    @DisplayName("Enumerated Index Ascend")
    public void t15() {
        Target target = id("grandchild");
        focus(target);
        ascend(SECOND);
        assertNotEquals("child", get(ID));
        assertNotEquals("grandchild", get(ID));
        assertEquals("parent", get(ID));
    }

    @Test
    @Tag("Ascend")
    @DisplayName("Enumerated Index Ancestor Not Present")
    public void t16() {
        Target target = id("parent");
        focus(target);
        assertThrows(SonnetException.class, () -> ascend(FIFTH));
    }

    @Test
    @Tag("Ascend")
    @DisplayName("Ascend To Tag")
    public void t17() {
        Target target = id("grandchild");
        focus(target);
        ascend(DIV);
        assertNotEquals("grandchild", get(ID));
        assertNotEquals("parent", get(ID));
        assertEquals("child", get(ID));;
    }

    @Test
    @Tag("Ascend")
    @DisplayName("Ascend To Tag Not Present")
    public void t18() {
        Target target = id("parent");
        focus(target);
        assertThrows(SonnetException.class, () -> ascend(H1));
    }

    @Test
    @Tag("Ascend")
    @DisplayName("Ascend To Target")
    public void t19() {
        Target target = id("grandchild");
        Target ascendTarget = id("parent");
        focus(target);
        ascend(ascendTarget);
        assertNotEquals("grandchild", get(ID));
        assertNotEquals("child", get(ID));
        assertEquals("parent", get(ID));;
    }

    @Test
    @Tag("Ascend")
    @DisplayName("Ascend To Target Not Present")
    public void t20() {
        Target target = id("parent");
        Target ascendTarget = id("fake");
        focus(target);
        assertThrows(SonnetException.class, () -> ascend(ascendTarget));
    }

}


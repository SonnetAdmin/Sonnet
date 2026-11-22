package sonnet.junit;

import com.microsoft.playwright.PlaywrightException;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;
import sonnet.SonnetException;
import sonnet.Target;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static sonnet.Enum.HTMLTag.*;
import static sonnet.Enum.Index.FIRST;
import static sonnet.Enum.Trait.ID;
import static sonnet.Enum.Trait.TEXT;


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
        assertThrows(SonnetException.class, () -> {
            descend();
        });
    }

    @Test
    @Tag("Descend")
    @DisplayName("Descend To Tag")
    public void t3() {
        Target target = id("parent");
        focus(target);
        descend(DIV);
        assertNotEquals("grandchild", get(ID));
        assertEquals("child", get(ID));
        descend(H1);
        assertNotEquals("child", get(ID));
        assertNotEquals("grandchild", get(ID));
        assertEquals("grandestchild", get(ID));
    }

    @Test
    @Tag("Descend")
    @DisplayName("Descend To Tag Not Present")
    public void t4() {
        Target target = id("parent");
        focus(target);
        descend(P);
        assertNotEquals("child", get(ID));
        assertEquals("grandchild", get(ID));
        assertThrows(SonnetException.class, () -> {
            descend(H1);
        });
    }

    @Test
    @Tag("Descend")
    @DisplayName("Descend To Target")
    public void t5() {
        Target target = id("parent");
        Target descendTarget = id("grandchild");
        focus(target);
        descend(descendTarget);
        assertNotEquals("child", get(ID));
        assertEquals("grandchild", get(ID));

    }

//    @Test
//    @Tag("Descend")
//    @DisplayName("Descend By Index")
//    public void t42() {
//        Target target = id("parent");
//        focus(target);
//        descend(2);
//        assertNotEquals("child", get(ID));
//        assertEquals("grandchild", get(ID));
//    }
//
//    @Test
//    @Tag("Descend")
//    @DisplayName("Descend By Enumerated Index")
//    public void t44() {
//        Target target = id("parent");
//        focus(target);
//        descend(FIRST);
//        assertNotEquals("grandchild", get(ID));
//        assertEquals("child", get(ID));
//    }






}


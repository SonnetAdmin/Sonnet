package sonnet.junit;

import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;
import sonnet.Target;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sonnet.CommonPageInterface.Trait.ID;
import static sonnet.CommonPageInterface.Trait.TEXT;
import static sonnet.Enum.HTMLTag.OPTION;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JRole extends CommonPageObject {

    @BeforeAll
    public void setUpAll() {
        String home = System.getProperty("user.dir");
        open().go(home + "\\src\\test\\java\\sonnet\\junit\\html\\focus\\Role.html");
    }

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("add")
    @DisplayName("Role searchbox test")
    public void t1() {
        Target target = ariaRole(AriaRole.SEARCHBOX);
        focus(target);
        assertEquals(get(ID).equalsIgnoreCase("search"), true);
    }

    @Test
    @Tag("add")
    @DisplayName("Role combobox test")
    public void t2() {
        Target target = ariaRole(AriaRole.COMBOBOX);
        focus(target);
        assertEquals(get(ID).equalsIgnoreCase("combo"), true);
    }

    @Test
    @Tag("add")
    @DisplayName("Role menu test")
    public void t3() {
        Target target = ariaRole(AriaRole.MENU);
        focus(target).focus(ariaRole(AriaRole.MENUITEMRADIO));
        assertEquals(get(ID).equalsIgnoreCase("menuradio"), true);
    }
}


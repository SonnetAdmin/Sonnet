package sonnet.junit;

import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;
import sonnet.Target;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static sonnet.CommonPageInterface.Trait.*;
import static sonnet.Enum.HTMLTag.OPTION;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JSelect extends CommonPageObject {

    @BeforeAll
    public void setUpAll() {
        String home = System.getProperty("user.dir");
        open().go(home + "\\src\\test\\java\\sonnet\\junit\\html\\focus\\Select.html");
    }

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("add")
    @DisplayName("Select test")
    public void t1() {
        Target target = id("seasons");
        focus(target).click();
        collect(tag(OPTION));
        choose("Spring").click();
        log("");
        assertEquals("Spring", get(TEXT));

    }
}


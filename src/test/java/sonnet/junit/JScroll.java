package sonnet.junit;

import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;
import sonnet.Target;

import static sonnet.Enum.HTMLTag.*;
import static sonnet.Enum.Scroll.*;
import static sonnet.utils.Time.pause;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JScroll extends CommonPageObject {

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
    @DisplayName("Scrolling Up and down")
    public void t1() {
        scroll(DOWN);
        pause(1);
        scroll(UP);
    }

    @Test
    @Tag("add")
    @DisplayName("Scrolling End and Home")
    public void t2() {
        scroll(END);
        pause(1);
        scroll(HOME);
    }

    @Test
    @Tag("add")
    @DisplayName("Scrolling to target")
    public void t3() {
        Target target = id("version");
        scroll(target);
        pause(1);
    }

    @Test
    @Tag("add")
    @DisplayName("Scrolling End and Home")
    public void t4() {
        Target target = tagWithText(H4,"Practice Test Automation WebSite for Web UI and Rest API");
        focus(target);
        scroll();
        pause(1);
    }
}


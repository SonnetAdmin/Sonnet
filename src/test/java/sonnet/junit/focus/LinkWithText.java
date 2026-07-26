package sonnet.junit.focus;

import org.junit.jupiter.api.*;
import sonnet.CommonPage;
import sonnet.Target;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class LinkWithText extends CommonPage {

    @BeforeAll
    public void setUpAll() {
        String home = System.getProperty("user.dir");
        open().go(home + "\\src\\test\\java\\sonnet\\junit\\html\\focus\\TagWithText.html");
    }

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("tagwithtext")
    @DisplayName("h1")
    public void t1() {
        Target target = tagWithText(sonnet.Enum.HTMLTag.H1,"H1WithText");
        focus(target);
        assertTrue(get(Trait.TEXT).equals("H1WithText"));
        assertFalse(get(Trait.TEXT).equals("LabelWithText"));
    }

    @Test
    @Tag("tagwithtext")
    @DisplayName("label")
    public void t2() {
        Target target = tagWithText(sonnet.Enum.HTMLTag.LABEL,"LabelWithText");
        focus(target);
        assertTrue(get(Trait.TEXT).equals("LabelWithText"));
        assertFalse(get(Trait.TEXT).equals("H1WithText"));
    }
}


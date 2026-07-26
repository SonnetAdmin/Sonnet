package sonnet.junit.focus;

import org.junit.jupiter.api.*;
import sonnet.CommonPage;
import sonnet.Target;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sonnet.CommonPageInterface.Trait.TEXT;
import static sonnet.Enum.HTMLTag.H1;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class LinkWithText extends CommonPage {

    @BeforeAll
    public void setUpAll() {
        String home = System.getProperty("user.dir");
        open().go(home + "\\src\\test\\java\\sonnet\\junit\\html\\focus\\LinkWithText.html");
    }

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("linkwithtext")
    @DisplayName("h1")
    public void t1() {
        Target target = linkText("LinkWithText");
        Target targetID = tagWithText(H1, "ID Test");
        focus(target).click();

        focus(targetID);
        assertTrue(get(TEXT).equals("ID Test"));

    }


}


package sonnet.junit;

import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;
import sonnet.Target;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static sonnet.Enum.HTMLTag.H1;
import static sonnet.Enum.Trait.TEXT;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JLinkContainsText extends CommonPageObject {

    @BeforeAll
    public void setUpAll() {
        String home = System.getProperty("user.dir");
        open().go(home + "\\src\\test\\java\\sonnet\\junit\\html\\focus\\LinkContainsText.html");
    }

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("linkcontainstext")
    @DisplayName("h1")
    public void t1() {
        Target target = linkContainsText("this text");
        Target targetID = tagContainsText(H1, "ID Test");
        focus(target).click();
        focus(targetID);
        assertEquals("ID Test", get(TEXT));
    }


}


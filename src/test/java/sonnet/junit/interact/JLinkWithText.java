package sonnet.junit.interact;

import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;
import sonnet.Target;

import static org.junit.jupiter.api.Assertions.*;
import static sonnet.Enum.Trait.*;
import static sonnet.Enum.HTMLTag.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JLinkWithText extends CommonPageObject {

    @BeforeAll
    public void setUpAll() {
        String home = System.getProperty("user.dir");
        open().go(home + "\\src\\test\\java\\sonnet\\junit\\html\\interact\\LinkWithText.html");
    }

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("linkwithtext")
    @DisplayName("h1")
    public void t1() {
        Target target = linkWithText("with");
        Target targetID = tagContainsText(H1, "ID Test");
        focus(target).click();
        focus(targetID);
        assertEquals("ID Test", get(TEXT));
    }


}


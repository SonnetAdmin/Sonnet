package sonnet.junit;

import org.junit.jupiter.api.*;
import sonnet.CommonPage;
import sonnet.CommonPageInterface;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class Focus {

    CommonPage commonPage;

    @BeforeAll
    public void setUpAll() {
        commonPage = new CommonPage();
        String home = System.getProperty("user.dir");
        commonPage.open().go(home + "\\src\\test\\java\\sonnet\\junit\\html\\Focus.html");
    }

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("add")
    @DisplayName("Text Input test")
    public void textInputTest() {
        commonPage.focus("#text_input");
        assertTrue(commonPage.get(CommonPageInterface.Trait.TAG).equals("INPUT"));
        assertEquals("INPUT", commonPage.get(CommonPageInterface.Trait.TAG));

    }

    @Test
    @Tag("add")
    @DisplayName("Text Area test")
    public void textAreaTest() {
        commonPage.focus("#text_area");
        assertTrue(commonPage.get(CommonPageInterface.Trait.TAG).equals("TEXTAREA"));
        assertEquals("TEXTAREA", commonPage.get(CommonPageInterface.Trait.TAG));

    }

    @Test
    @Tag("add")
    @DisplayName("Radio Button test")
    public void radioButtonTest() {
        commonPage.focus("#radio1");
        assertTrue(commonPage.get(CommonPageInterface.Trait.TAG).equals("INPUT"));
        assertEquals("INPUT", commonPage.get(CommonPageInterface.Trait.TAG));

    }

    @Test
    @Tag("add")
    @DisplayName("Checkbox test")
    public void checkboxTest() {
        commonPage.focus("#checkbox");
        assertTrue(commonPage.get(CommonPageInterface.Trait.TAG).equals("INPUT"));
        assertEquals("INPUT", commonPage.get(CommonPageInterface.Trait.TAG));

    }

    @Test
    @Tag("add")
    @DisplayName("Button test")
    public void buttonTest() {
        commonPage.focus("#button");
        assertTrue(commonPage.get(CommonPageInterface.Trait.TAG).equals("BUTTON"));
        assertEquals("BUTTON", commonPage.get(CommonPageInterface.Trait.TAG));

    }

}


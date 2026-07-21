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
        System.out.println("Setting up resources before all tests");
        commonPage = new CommonPage();
        commonPage.open().go("file:///C:/Users/Rio/AppData/Local/Temp/non426C.htm");
    }

    @AfterAll
    public void tearDownAll() {
        System.out.println("Tearing down resources after all tests");
    }

    @BeforeEach
    public void setUp() {
        System.out.println("Setting up before each test");
    }

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


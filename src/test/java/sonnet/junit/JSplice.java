package sonnet.junit;

import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;
import sonnet.Target;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static sonnet.CommonPageInterface.Trait.ID;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JSplice extends CommonPageObject {

    @BeforeAll
    public void setUpAll() {
        String home = System.getProperty("user.dir");
        open().go(home + "\\src\\test\\java\\sonnet\\junit\\html\\focus\\Splice.html");
    }

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("add")
    @DisplayName("splice order confirm test")
    public void t1() {
        Target confirm = splice("Thanks for your order, reference: ", ". We thank you for your custom.");
        focus(confirm);
        assertEquals("order confirm", get(ID));
    }

    @Test
    @Tag("add")
    @DisplayName("splice item unavailable test")
    public void t2() {
        Target unavailable= splice("The following item is not available in our store: ", ". Please check other stores.");
        focus(unavailable);
        assertEquals("order fail", get(ID));
    }

    @Test
    @Tag("add")
    @DisplayName("splice order not found test")
    public void t3() {
        Target unavailable= splice("The following item is available in our store: ", ". Go away.");
        assertThrows(NoSuchElementException.class, () -> focus(unavailable));
    }
}


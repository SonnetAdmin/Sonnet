package sonnet.junit;

import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;
import sonnet.Target;

import static org.junit.Assert.assertEquals;
import static sonnet.Enum.Trait.TEXT;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JGo extends CommonPageObject {

    @BeforeAll
    public void setUpAll() {
        open().go("https://sonnetsoft.com");
    }

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("add")
    @DisplayName("Go")
    public void t1() {
    }
}


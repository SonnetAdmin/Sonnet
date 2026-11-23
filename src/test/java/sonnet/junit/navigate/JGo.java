package sonnet.junit.navigate;

import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;

import static org.junit.Assert.assertEquals;


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


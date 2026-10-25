package sonnet.junit;


import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;
import sonnet.performables.DoComplexStuff;
import sonnet.performables.SearchGoogle;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JPerform extends CommonPageObject {

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("performable")
    @DisplayName("Basic Performable Test")
    public void t1() {
        perform(new DoComplexStuff());
    }

    @Test
    @Tag("performable")
    @DisplayName("Performable Parameterized Constructor Test")
    public void t2() {
        perform(new DoComplexStuff("Tyler"));
    }

    @Test
    @Tag("performable")
    @DisplayName("Performable Check URL Test")
    public void t3() {
        perform(new SearchGoogle("https://www.google.com"));
    }
}


package sonnet.junit.interact;

import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;
import sonnet.Target;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JCheckUncheck extends CommonPageObject {

    @BeforeAll
    public void setUpAll() {
        String home = System.getProperty("user.dir");
        open().go(home + "\\src\\test\\java\\sonnet\\junit\\html\\interact\\CheckUncheck.html");
    }

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("add")
    @DisplayName("verifying if checked")
    public void t1() {
        Target target = id("checked");
        focus(target);
        checked();
    }

    @Test
    @Tag("add")
    @DisplayName("verifying if un-checked")
    public void t2() {
        Target target = id("unchecked");
        focus(target);
        unchecked();
    }

    @Test
    @Tag("add")
    @DisplayName("Un-checking a checked box")
    public void t3() {
        Target target = id("checked");
        focus(target);
        checked();
        uncheck();
        unchecked();
    }

    @Test
    @Tag("add")
    @DisplayName("Checking an un-checked box")
    public void t4() {
        Target target = id("unchecked");
        focus(target);
        unchecked();
        check();
        checked();
    }
}


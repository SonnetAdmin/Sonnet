package sonnet.junit;

import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;
import sonnet.Target;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JCompose extends CommonPageObject {

    @BeforeAll
    public void setUpAll() {
        String home = System.getProperty("user.dir");
        open().go(home + "\\src\\test\\java\\sonnet\\junit\\html\\focus\\Compose.html");
    }

    @AfterAll
    public void tearDownAll() {}

    @BeforeEach
    public void setUp() {}

    @Test
    @Tag("add")
    @DisplayName("Text test")
    public void t1() {
        Target target = id("text");
        focus(target).compose("MyText");
    }

    @Test
    @Tag("add")
    @DisplayName("Password test")
    public void t2() {
        Target target = id("password");
        focus(target).compose("MySecretPassword");
    }

    @Test
    @Tag("add")
    @DisplayName("Email test")
    public void t3() {
        Target target = id("email");
        focus(target).compose("MyEmail");
    }

    @Test
    @Tag("add")
    @DisplayName("URL test")
    public void t4() {
        Target target = id("url");
        focus(target).compose("MyURL");
    }

}


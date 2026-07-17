package sonnet.junit;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JunitStringsTest {

    Strings strings;
    String s1;
    String s2;
    @BeforeAll
    public void setUpAll() {
        strings = new Strings();
        System.out.println("Setting up resources before all tests");
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
    @DisplayName("Length test")
    public void lengthTest() {
        s1 = "Cat";
        s2 = "Fox";
        assertEquals(3, strings.length(s1));
        assertNotEquals(4, strings.length(s2));
        assertThrows(NullPointerException.class, () -> strings.length(null));
    }

    @Test
    @Tag("add")
    @DisplayName("Concatenate test")
    public void concatenateTest() {
        s1 = "Germ";
        s2 = "any";
        assertEquals("Germany", strings.concatenate(s1, s2));
        assertNotEquals("Russia", strings.concatenate(s1, s2));

    }

    @Test
    @Tag("add")
    @DisplayName("Remove test")
    public void removeTest() {
        s1 = "Germ";
        s2 = "any";
        assertEquals("any", strings.remove(s1, s2));
        assertNotEquals("Germ", strings.remove(s1, s2));

    }

    @Test
    @Tag("add")
    @DisplayName("Contains test")
    public void containsTest() {
        s1 = "Cat";
        s2 = "Caterpillar";
        String s3 = "Dog";
        assertTrue(strings.contains(s1, s2));
        assertFalse(strings.contains(s3, s2));

    }

}


package sonnet.junit;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JunitPersonsTest {
    private Person p;

    @BeforeAll
    public void setUpAll() {
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
    @DisplayName("Fields test")
    public void fieldsTest() {
        p = new Person("Super", "Man", 21);
        assertEquals("Super", p.getFirstName());
        assertTrue(p.getFirstName().equals("Super"));
        assertEquals("Man", p.getLastName());
        assertTrue(p.getLastName().equals("Man"));
        assertEquals(21, p.getAge());
        assertTrue(p.getAge() == 21);
    }

    @Test
    @Tag("add")
    @DisplayName("Null And Empty String test")
    public void nullAndEmptyStringTest() {
        p = new Person("Spider", "Man", 18);
        try{
            p.setFirstName(null);
        }
        catch (IllegalArgumentException iae) {
            assertEquals("First name cannot be null or empty.", iae.getMessage());
        }

        try{
            p.setLastName("");
        }
        catch (IllegalArgumentException iae) {
            assertEquals("Last name cannot be null or empty.", iae.getMessage());
        }
    }

    @Test
    @Tag("add")
    @DisplayName("Age Boundaries test")
    public void ageBoundariesTest() {
        p = new Person("Bat", "Man", 30);
        try {
            p.setAge(-1);
        } catch (IllegalArgumentException iae) {
            assertEquals("Age must be between 0 and 150.", iae.getMessage());
        }

        try {
            p.setAge(151);
        } catch (IllegalArgumentException iae) {
            assertEquals("Age must be between 0 and 150.", iae.getMessage());
        }

    }
    @Test
    @Tag("add")
    @DisplayName("Exceptions test")
    public void exceptionsTest() {
        p = new Person("Iron", "Man", 22);
        assertThrows(IllegalArgumentException.class, () -> p.setFirstName(null));
        assertThrows(IllegalArgumentException.class, () -> p.setLastName(""));
        assertThrows(IllegalArgumentException.class, () -> p.setAge(-1));
        assertThrows(IllegalArgumentException.class, () -> p.setAge(151));

    }

}


package sonnet.junit;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JunitPersonsTest {

    Strings strings;
    String s1;
    String s2;
    @BeforeAll
    public void setUpAll() {
        strings = new Strings();
        System.out.println("Setting up resources before all tests");
    }
    Person p = new Person("A","B",1);

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
        assertEquals(1, p.getAge());
    }

    @Test
    @Tag("add")
    @DisplayName("Null And Empty String test")
    public void nullAndEmptyStringTest() {
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
        try {
            p.setAge(-1);
        } catch (IllegalArgumentException iae) {
            assertEquals("Age must be between 0 and 150.", iae.getMessage());
        }

        try {
            p.setAge(150);
        } catch (IllegalArgumentException iae) {
            assertEquals("Age must be between 0 and 150.", iae.getMessage());
        }

    }

}


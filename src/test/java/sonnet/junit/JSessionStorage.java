package sonnet.junit;


import org.junit.jupiter.api.*;
import sonnet.CommonPageObject;

import static org.junit.Assert.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JSessionStorage extends CommonPageObject {

    @AfterAll
    public void tearDownAll() {
    }

    @BeforeEach
    public void setUp() {
    }

    @Test
    @Tag("SessionStorage")
    @DisplayName("Basic Session Storage Test")
    public void t1() {
        store("key1", "value1");
        assertEquals("value1", retrieve("key1"));
    }

    @Test
    @Tag("SessionStorage")
    @DisplayName("Empty Session Storage Test")
    public void t2() {
        store("key2", "value2");
        assertFalse(retrieve("key1").isEmpty());
        assertEquals("value2", retrieve("key2"));
    }

    @Test
    @Tag("SessionStorage")
    @DisplayName("Fake Key Session Storage Test")
    public void t3() {
        assertThrows(IllegalArgumentException.class, () -> {
            assertNull(retrieve("nonexistentKey"));
            throw new IllegalArgumentException("Key does not exist in session storage.");
        });
    }

}


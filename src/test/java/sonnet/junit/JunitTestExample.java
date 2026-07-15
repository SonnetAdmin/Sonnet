package org.sonnet.junit;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class JunitTestExample {

    Calculator calculator;
    @BeforeAll
    public void setUpAll() {
        calculator = new Calculator();
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
    @DisplayName("Addition test")
    public void add() {
        assertEquals(calculator.add(2, 3), 5);
        assertNotEquals(calculator.add(2, 3), 6);
    }

    @Test
    @Tag("add")
    @DisplayName("Subtract test")
    public void subtract() {
        assertEquals(calculator.subtract(3, 2), 1);
        assertNotEquals(calculator.subtract(3, 3),1);
    }

    @Test
    @Tag("add")
    @DisplayName("Multiply test")
    public void multiply() {
        assertEquals(calculator.multiply(2, 3), 6);
        assertNotEquals(calculator.multiply(3, 3),5);
    }

    @Test
    @Tag("add")
    @DisplayName("Division test")
    public void division() {
        assertEquals(calculator.division(10, 5), 2);
        assertNotEquals(calculator.division(0, 3),3);
    }

    @Test
    @Tag("add")
    @DisplayName("Division by Zero test")
    public void divisionByZero() {
        assertThrows(ArithmeticException.class, () -> calculator.division(3, 0));
    }
}


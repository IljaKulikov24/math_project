package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstantFunctionTest {

    @Test
    void apply() {
        assertEquals(666, new ConstantFunction(666).apply(123456789));
    }

    @Test
    void getX() {
        assertEquals(999, new ConstantFunction(999).getX());
    }
}
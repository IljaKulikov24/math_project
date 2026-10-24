package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitFunctionTest {

    @Test
    public void apply() {
        assertEquals(1, new UnitFunction().apply(123456789));
    }
}
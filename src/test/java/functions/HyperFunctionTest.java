package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HyperFunctionTest {

    HyperFunction function = new HyperFunction();

    @Test
    void apply() {
        assertEquals(0.5, function.apply(2));
        assertEquals(Double.NaN, function.apply(Double.NaN));
        assertEquals(Double.POSITIVE_INFINITY, function.apply(0.0));
        assertEquals(Double.NEGATIVE_INFINITY, function.apply(-0.0));
    }
}
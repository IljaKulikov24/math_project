package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class SqrtFunctionTest {

    SqrtFunction function = new SqrtFunction();

    @Test
    void apply() {
        assertEquals(12, function.apply(144));
        assertTrue(Double.isNaN(function.apply(-999)));
    }
}
package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdentityFunctionTest {

    IdentityFunction function = new IdentityFunction();
    double[] values = {1.0, 2.0, 3.0, -69.0};

    @Test
    void apply() {
        for (double val : values) {
            assertEquals(val, function.apply(val));
        }
    }
}
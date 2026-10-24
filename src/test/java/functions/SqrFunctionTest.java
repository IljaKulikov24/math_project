package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SqrFunctionTest {

    SqrFunction function = new SqrFunction();
    double[] values = {1.0, 2.666, 3.0, -69.0};

    @Test
    void apply() {
        for (double val : values) {
            double res = val * val;
            assertEquals(res, function.apply(val));
        }
    }
}
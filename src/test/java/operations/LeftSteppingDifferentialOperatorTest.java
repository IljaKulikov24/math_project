package operations;

import functions.MathFunction;
import functions.SqrFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeftSteppingDifferentialOperatorTest {

    @Test
    void derive() {
        double step = 0.001;
        SteppingDifferentialOperator differentialOperator = new LeftSteppingDifferentialOperator(step);

        MathFunction function = new SqrFunction();

        MathFunction derivative = differentialOperator.derive(function);

        // f(x) = x * x     -> x = 4    -> f(4) = 16
        // f`(x) = 2 * X    -> x = 4    -> f`(4) = 8
        assertEquals(16, function.apply(4));
        assertEquals(7.999, derivative.apply(4), 1e-9);
    }
}
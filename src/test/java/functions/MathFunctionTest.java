package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathFunctionTest {

    @Test
    void andThen() {
        CompositeFunction compFunc = new CompositeFunction(
                new SqrtFunction(), new SqrFunction());
        SqrtFunction function = new SqrtFunction();
        double value = 111;

        assertEquals(compFunc.apply(value), function.andThen(new SqrFunction()).apply(value));

        assertEquals(
                compFunc.andThen(new HyperFunction()).apply(value),
                function.andThen(new SqrFunction()).andThen(new HyperFunction()).apply(value)
        );
    }
}
package functions.factory;

import functions.ArrayTabulatedFunction;
import functions.TabulatedFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TabulatedFunctionFactoryTest {

    double[] xVal = {1.0, 2.0, 3.0, 4.0};
    double[] yVal = {1.0, 4.0, 9.0, 16.0};

    TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();

    @Test
    void create() {
        TabulatedFunction function = factory.create(xVal, yVal);

        assertEquals(ArrayTabulatedFunction.class, function.getClass());

        function.apply(0.67);
        function.setY(0, 67);
        function.apply(1);
    }

    @Test
    void createStrict() {
        TabulatedFunction function = factory.createStrict(xVal, yVal);

        assertThrows(UnsupportedOperationException.class, () -> function.apply(0.67));
        function.setY(0, 67);
        function.apply(1);
    }

    @Test
    void createUnmodifiable() {
        TabulatedFunction function = factory.createUnmodifiable(xVal, yVal);

        function.apply(0.67);
        assertThrows(UnsupportedOperationException.class, () -> function.setY(0, 67));
        function.apply(1);
    }

    @Test
    void createStrictUnmodifiable() {
        TabulatedFunction function = factory.createStrictUnmodifiable(xVal, yVal);

        assertThrows(UnsupportedOperationException.class, () -> function.apply(0.67));
        assertThrows(UnsupportedOperationException.class, () -> function.setY(0, 67));
        function.apply(1);
    }
}
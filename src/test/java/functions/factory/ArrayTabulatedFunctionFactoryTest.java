package functions.factory;

import functions.ArrayTabulatedFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayTabulatedFunctionFactoryTest {

    @Test
    void create() {
        ArrayTabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        assertEquals(ArrayTabulatedFunction.class, factory.create(new double[2], new double[2]).getClass());
    }
}
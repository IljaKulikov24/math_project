package functions.factory;

import functions.LinkedListTabulatedFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTabulatedFunctionFactoryTest {

    @Test
    void create() {
        LinkedListTabulatedFunctionFactory factory = new LinkedListTabulatedFunctionFactory();
        assertEquals(LinkedListTabulatedFunction.class, factory.create(new double[2], new double[2]).getClass());
    }
}
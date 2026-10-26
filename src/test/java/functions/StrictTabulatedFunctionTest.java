package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StrictTabulatedFunctionTest {

    @Test
    void apply() {
        LinkedListTabulatedFunction functionList = new LinkedListTabulatedFunction(new SqrFunction(), 1, 5, 5);

        ArrayTabulatedFunction functionArrays = new ArrayTabulatedFunction(new SqrFunction(), 1, 5, 5);

        StrictTabulatedFunction strictFunctionList = new StrictTabulatedFunction(functionList);
        StrictTabulatedFunction strictFunctionArrays = new StrictTabulatedFunction(functionArrays);

        assertEquals(9, strictFunctionList.apply(3));
        assertEquals(9, strictFunctionArrays.apply(3));

        assertThrows(UnsupportedOperationException.class, () -> {
            strictFunctionList.apply(3.5);
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            strictFunctionArrays.apply(3.5);
        });
    }

    @Test
    void wrappers() {
        LinkedListTabulatedFunction functionList = new LinkedListTabulatedFunction(new SqrFunction(), 1, 5, 5);
        UnmodifiableTabulatedFunction unmodifiableFunction = new UnmodifiableTabulatedFunction(functionList);
        StrictTabulatedFunction strictAndUnmodifiableFunction = new StrictTabulatedFunction(unmodifiableFunction);

        assertThrows(UnsupportedOperationException.class, () -> {
            strictAndUnmodifiableFunction.apply(3.5);
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            strictAndUnmodifiableFunction.setY(0, 67);
        });
    }
}
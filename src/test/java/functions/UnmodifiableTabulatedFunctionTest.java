package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnmodifiableTabulatedFunctionTest {

    @Test
    void setY() {
        LinkedListTabulatedFunction functionList =
                new LinkedListTabulatedFunction(new SqrFunction(), 1, 5, 5);

        ArrayTabulatedFunction functionArrays =
                new ArrayTabulatedFunction(new SqrFunction(), 1, 5, 5);

        UnmodifiableTabulatedFunction unmodifiableFunctionList = new UnmodifiableTabulatedFunction(functionList);
        UnmodifiableTabulatedFunction unmodifiableFunctionArrays = new UnmodifiableTabulatedFunction(functionArrays);

        assertThrows(UnsupportedOperationException.class, () -> {
            unmodifiableFunctionList.setY(0, 67);
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            unmodifiableFunctionArrays.setY(0, 67);
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
package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayTabulatedFunctionTest {

    double[] xVal = {1.0, 2.0, 3.0, 4.0};
    double[] yVal = {1.0, 4.0, 9.0, 16.0};

    ArrayTabulatedFunction function1 = new ArrayTabulatedFunction(xVal, yVal);
    ArrayTabulatedFunction function2 = new ArrayTabulatedFunction(
            new SqrFunction(), 1.0, 4.0, 4);



    @Test
    void constructors() {
        assertEquals(function1.getCount(), function2.getCount());

        for (int i = 0; i < function1.getCount(); i++) {
            assertEquals(function1.getX(i), function2.getX(i));
            assertEquals(function1.getY(i), function2.getY(i));
        }

        ArrayTabulatedFunction f = new ArrayTabulatedFunction(new SqrFunction(), 4, 4, 10);
        for (int i = 0; i < f.getCount(); i++) {
            assertEquals(4, f.getX(i));
            assertEquals(16, f.getY(i));
        }
    }

    @Test
    void floorIndexOfX() {
        assertEquals(0, function1.floorIndexOfX(-1));
        assertEquals(function2.getCount(), function2.floorIndexOfX(10));
        assertEquals(2, function1.floorIndexOfX(3.5));
    }

    @Test
    void extrapolateLeft() {
        double x = -100;
        double xLeft = 1.0, xRight = 2.0, yLeft = 1.0, yRight = 4.0;
        double result = yLeft + (yRight - yLeft) * (x - xLeft) / (xRight - xLeft);

        assertEquals(result, function2.extrapolateLeft(x));
    }

    @Test
    void extrapolateRight() {
        double x = 100;
        double xLeft = 3.0, xRight = 4.0, yLeft = 9.0, yRight = 16.0;
        double result = yLeft + (yRight - yLeft) * (x - xLeft) / (xRight - xLeft);

        assertEquals(result, function1.extrapolateRight(x));
    }

    // xVal = {1.0, 2.0, 3.0, 4.0};
    // yVal = {1.0, 4.0, 9.0, 16.0};
    @Test
    void interpolate() {
        double x = 3.5;
        double xLeft = 3.0, xRight = 4.0, yLeft = 9.0, yRight = 16.0;
        double result = yLeft + (yRight - yLeft) * (x - xLeft) / (xRight - xLeft);

        double interpolateValue = function1.interpolate(x, function1.floorIndexOfX(x));

        assertEquals(result, interpolateValue);
    }

    @Test
    void getCount() {
        assertEquals(4, function1.getCount());
        assertEquals(4, function2.getCount());
    }

    @Test
    void setY() {
        function1.setY(0, 69);
        assertEquals(69, function1.getY(0));
    }

    @Test
    void indexOfX() {
        assertEquals(function1.getCount() - 1, function1.indexOfX(function1.rightBound()));
        assertEquals(-1, function1.indexOfX(-1));
        assertEquals(-1, function1.indexOfX(69));
        assertEquals(2, function1.indexOfX(3));
    }

    @Test
    void indexOfY() {
        assertEquals(function2.getCount() - 1, function2.indexOfY(
                function2.getY(function2.getCount() - 1)));
        assertEquals(-1, function2.indexOfY(-1));
        assertEquals(-1, function2.indexOfY(69));
        assertEquals(2, function1.indexOfY(9));
    }

    @Test
    void leftBound() {
        assertEquals(1, function2.leftBound());
    }

    @Test
    void rightBound() {
        assertEquals(4, function1.rightBound());
    }

    @Test
    void insert() {

        // Вставка существующего X
        function1.insert(2, 67);
        assertEquals(67, function1.getY(1));

        // вставка в конец
        function1.insert(10, 1111);
        assertEquals(1111, function1.getY(4));

        // вствка в середину
        function1.insert(2.5, 2222);
        assertEquals(2222, function1.getY(2));

        // вставка в начало
        function1.insert(0, 3333);
        assertEquals(3333, function1.getY(0));
    }

    @Test
    void remove() {

        // xVal = {1.0, 2.0, 3.0, 4.0}
        // yVal = {1.0, 4.0, 9.0, 16.0}

        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xVal, yVal);

        function.remove(1);
        assertEquals(9, function.getY(1));

        function.remove(0);
        assertEquals(9, function.getY(0));

        function.remove(0);
        assertEquals(16, function.getY(0));

        function.remove(0);
        assertEquals(0, function.getCount());
    }
}
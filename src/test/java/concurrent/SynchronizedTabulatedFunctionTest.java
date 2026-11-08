package concurrent;

import functions.*;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class SynchronizedTabulatedFunctionTest {

    double[] xVal = {1.0, 2.0, 3.0, 4.0};
    double[] yVal = {1.0, 4.0, 9.0, 16.0};

    TabulatedFunction function1 = new ArrayTabulatedFunction(xVal, yVal);
    TabulatedFunction function2 = new LinkedListTabulatedFunction(new SqrFunction(), 1.0, 4.0, 4);

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
        assertEquals(function2.getCount() - 1, function2.indexOfY(function2.getY(function2.getCount() - 1)));
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
    void apply() {
        MathFunction sqrFunction = new SqrFunction();
        for (Point point : function2) {
            assertEquals(sqrFunction.apply(point.getX()), point.getY());
        }
    }

    @Test
    void iterator() {
        int index = 0;
        for (Point point : function1) {
            assertEquals(function1.getX(index), point.getX());
            assertEquals(function1.getY(index), point.getY());
            index++;
        }

        index = 0;
        Iterator<Point> iterator = function1.iterator();
        while (iterator.hasNext()) {
            Point point =iterator.next();
            assertEquals(function1.getX(index), point.getX());
            assertEquals(function1.getY(index), point.getY());
            index++;
        }
    }
}
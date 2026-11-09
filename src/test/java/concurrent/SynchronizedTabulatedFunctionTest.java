package concurrent;

import functions.*;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class SynchronizedTabulatedFunctionTest {

    double[] xVal = {1.0, 2.0, 3.0, 4.0};
    double[] yVal = {1.0, 4.0, 9.0, 16.0};


    @Test
    void getCount() {
        TabulatedFunction function1 = new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xVal, yVal));
        TabulatedFunction function2 = new SynchronizedTabulatedFunction(
                new LinkedListTabulatedFunction(new SqrFunction(), 1.0, 4.0, 4));

        assertEquals(4, function1.getCount());
        assertEquals(4, function2.getCount());
    }

    @Test
    void setY() {
        TabulatedFunction function = new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xVal, yVal));

        function.setY(0, 69);
        assertEquals(69, function.getY(0));
    }

    @Test
    void indexOfX() {
        TabulatedFunction function = new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xVal, yVal));

        assertEquals(function.getCount() - 1, function.indexOfX(function.rightBound()));
        assertEquals(-1, function.indexOfX(-1));
        assertEquals(-1, function.indexOfX(69));
        assertEquals(2, function.indexOfX(3));
    }

    @Test
    void indexOfY() {
        TabulatedFunction function1 = new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xVal, yVal));
        TabulatedFunction function2 = new SynchronizedTabulatedFunction(
                new LinkedListTabulatedFunction(new SqrFunction(), 1.0, 4.0, 4));

        assertEquals(function2.getCount() - 1, function2.indexOfY(function2.getY(function2.getCount() - 1)));
        assertEquals(-1, function2.indexOfY(-1));
        assertEquals(-1, function2.indexOfY(69));
        assertEquals(2, function1.indexOfY(9));
    }

    @Test
    void leftBound() {
        TabulatedFunction function2 = new SynchronizedTabulatedFunction(
                new LinkedListTabulatedFunction(new SqrFunction(), 1.0, 4.0, 4));

        assertEquals(1, function2.leftBound());
    }

    @Test
    void rightBound() {
        TabulatedFunction function = new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xVal, yVal));

        assertEquals(4, function.rightBound());
    }

    @Test
    void apply() {
        TabulatedFunction function = new SynchronizedTabulatedFunction(
                new LinkedListTabulatedFunction(new SqrFunction(), 1.0, 4.0, 4));

        MathFunction sqrFunction = new SqrFunction();
        for (Point point : function) {
            assertEquals(sqrFunction.apply(point.getX()), point.getY());
        }
    }

    @Test
    void iterator() {
        TabulatedFunction function = new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(xVal, yVal));

        int index = 0;
        for (Point point : function) {
            assertEquals(function.getX(index), point.getX());
            assertEquals(function.getY(index), point.getY());
            index++;
        }

        index = 0;
        Iterator<Point> iterator = function.iterator();
        while (iterator.hasNext()) {
            Point point =iterator.next();
            assertEquals(function.getX(index), point.getX());
            assertEquals(function.getY(index), point.getY());
            index++;
        }
    }

    @Test
    void doSynchronously() {
        SynchronizedTabulatedFunction function = new SynchronizedTabulatedFunction(
                new LinkedListTabulatedFunction(new SqrFunction(), 1.0, 4.0, 4));

        // Считаем атомарно среднее арифметическое оординат функции: (1 + 4 + 9 + 16) / 4 = 7.5
        assertEquals(7.5, function.doSynchronously(func -> {
            double sum = 0;
            for (Point point : func) {
                sum += point.getY();
            }
            return sum / function.getCount();
        }));

        // Меняем все значения оординат
        function.doSynchronously(func -> {
            int index = 0;
            for (Point point : func) {
                func.setY(index++, point.getY() * 100);
            }
            return null;
        });
        assertEquals(100, function.getY(0));
        assertEquals(400, function.getY(1));
        assertEquals(900, function.getY(2));
        assertEquals(1600, function.getY(3));
    }
}
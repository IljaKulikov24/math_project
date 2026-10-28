package operations;

import functions.*;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class TabulatedDifferentialOperatorTest {

    // Тестим производную
    @Test
    void derive() {
        // f(x) = x^2
        MathFunction function = new SqrFunction();
        TabulatedFunction tabulatedFunction = new ArrayTabulatedFunction(function, 1, 5, 5);

        TabulatedFunctionFactory arrayFactory = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionFactory listFactory = new LinkedListTabulatedFunctionFactory();

        // f`(x) = 2x
        TabulatedFunction derivative = new TabulatedDifferentialOperator(arrayFactory).derive(tabulatedFunction);

        Iterator<Point> iteratorFunction = tabulatedFunction.iterator(), iteratorDerivative = derivative.iterator();

        while (iteratorFunction.hasNext() && iteratorDerivative.hasNext()) {
            Point functionPoint = iteratorFunction.next(), derivativePoint = iteratorDerivative.next();
            assertEquals(functionPoint.x, derivativePoint.x);

            double x = functionPoint.x, y = functionPoint.y;
            if (iteratorDerivative.hasNext()) {
                double xRight = x + 1, yRight = function.apply(xRight);
                assertEquals((yRight - y) / (xRight - x), derivativePoint.y);
            }
            else {
                double xLeft = x - 1, yLeft = function.apply(xLeft);
                assertEquals((y - yLeft) / (x - xLeft), derivativePoint.y);
            }
        }
    }

    @Test
    void getFactoryAndSetFactory() {
        TabulatedDifferentialOperator diffOperator = new TabulatedDifferentialOperator();

        assertEquals(ArrayTabulatedFunctionFactory.class, diffOperator.getFactory().getClass());

        diffOperator.setFactory(new LinkedListTabulatedFunctionFactory());

        assertEquals(LinkedListTabulatedFunctionFactory.class, diffOperator.getFactory().getClass());
    }
}
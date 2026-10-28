package operations;

import functions.*;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class TabulatedDifferentialOperatorTest {

    // Тестим производную
    @Test
    void derive() {

        { // Проверка вычисления производной с помощью фабрики ArrayTabulatedFunctionFactory
            MathFunction function = new SqrFunction();
            TabulatedFunction tabulatedFunction = new LinkedListTabulatedFunction(function, 1, 5, 5);
            TabulatedFunction derivative = new TabulatedDifferentialOperator().derive(tabulatedFunction);

            Iterator<Point> iteratorFunction = tabulatedFunction.iterator(), iteratorDerivative = derivative.iterator();
            while (iteratorFunction.hasNext() && iteratorDerivative.hasNext()) {
                Point functionPoint = iteratorFunction.next(), derivativePoint = iteratorDerivative.next();

                assertEquals(functionPoint.getX(), derivativePoint.getX());

                double x = functionPoint.getX(), y = functionPoint.getY();
                if (iteratorDerivative.hasNext()) {
                    double xRight = x + 1, yRight = function.apply(xRight);
                    assertEquals((yRight - y) / (xRight - x), derivativePoint.getY());
                } else {
                    double xLeft = x - 1, yLeft = function.apply(xLeft);
                    assertEquals((y - yLeft) / (x - xLeft), derivativePoint.getY());
                }
            }
            // Проверка на то какая фабрика использовалась
            assertEquals(ArrayTabulatedFunction.class, derivative.getClass());
        }
        { // Проверка вычисления производной с помощью фабрики LinkedListTabulatedFunctionFactory
            MathFunction function = new HyperFunction();
            TabulatedFunction tabulatedFunction = new ArrayTabulatedFunction(function, 1, 5, 5);
            TabulatedFunction derivative = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory())
                    .derive(tabulatedFunction);

            Iterator<Point> iteratorFunction = tabulatedFunction.iterator(), iteratorDerivative = derivative.iterator();
            while (iteratorFunction.hasNext() && iteratorDerivative.hasNext()) {
                Point functionPoint = iteratorFunction.next(), derivativePoint = iteratorDerivative.next();

                assertEquals(functionPoint.getX(), derivativePoint.getX());

                double x = functionPoint.getX(), y = functionPoint.getY();
                if (iteratorFunction.hasNext()) {
                    double xRight = x + 1, yRight = tabulatedFunction.apply(xRight);
                    assertEquals((yRight - y) / (xRight - x), derivativePoint.getY());
                } else {
                    double xLeft = x - 1, yLeft = tabulatedFunction.apply(xLeft);
                    assertEquals((y - yLeft) / (x - xLeft), derivativePoint.getY());
                }
            }
            // Проверка на то какая фабрика использовалась
            assertEquals(LinkedListTabulatedFunction.class, derivative.getClass());
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
package operations;

import exceptions.InconsistentFunctionsException;
import functions.*;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TabulatedFunctionOperationServiceTest {

    @Test
    void asPoints() {

        TabulatedFunction function = new LinkedListTabulatedFunction(new SqrFunction(), 1, 5, 5);
        Point[] points = TabulatedFunctionOperationService.asPoints(function);

        int index = 0;
        for (Point point : points) {
            assertEquals(function.getX(index), point.getX());
            assertEquals(function.getY(index), point.getY());
            index++;
        }
    }

    TabulatedFunction firstFunction = new ArrayTabulatedFunction(new SqrFunction(), 1, 5, 5);
    TabulatedFunction secondFunction = new LinkedListTabulatedFunction(new UnitFunction(), 1, 5, 5);

    @Test
    void addition() {
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();
        TabulatedFunction answer = new ArrayTabulatedFunction(new double[] {1, 2, 3, 4, 5}, new double[] {2, 5, 10, 17, 26});

        TabulatedFunction resultOfTheOperation = service.addition(firstFunction, secondFunction);

        for (int i = 0; i < answer.getCount(); i++) {
            assertEquals(answer.getY(i), resultOfTheOperation.getY(i));
        }
    }

    @Test
    void subtraction() {
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(new LinkedListTabulatedFunctionFactory());
        TabulatedFunction answer = new ArrayTabulatedFunction(new double[] {1, 2, 3, 4, 5}, new double[] {0, 3, 8, 15, 24});

        TabulatedFunction resultOfTheOperation = service.subtraction(firstFunction, secondFunction);

        for (int i = 0; i < answer.getCount(); i++) {
            assertEquals(answer.getY(i), resultOfTheOperation.getY(i));
        }
    }

    @Test
    void multiplication() {
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();
        TabulatedFunction answer = new ArrayTabulatedFunction(new double[] {1, 2, 3, 4, 5}, new double[] {1, 4, 9, 16, 25});

        TabulatedFunction resultOfTheOperation = service.multiplication(firstFunction, secondFunction);

        for (int i = 0; i < answer.getCount(); i++) {
            assertEquals(answer.getY(i), resultOfTheOperation.getY(i));
        }
    }

    @Test
    void division() {
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(new ArrayTabulatedFunctionFactory());
        TabulatedFunction answer = new ArrayTabulatedFunction(new double[] {1, 2, 3, 4, 5}, new double[] {1, 4, 9, 16, 25});

        TabulatedFunction resultOfTheOperation = service.division(firstFunction, secondFunction);

        for (int i = 0; i < answer.getCount(); i++) {
            assertEquals(answer.getY(i), resultOfTheOperation.getY(i));
        }

        TabulatedFunction zeroTabulatedFunction = new ArrayTabulatedFunction(new ZeroFunction(),1, 5, 5);
        assertThrows(ArithmeticException.class, () -> service.division(firstFunction, zeroTabulatedFunction));

        // Разные значения X
        assertThrows(InconsistentFunctionsException.class, () -> service.division(
                firstFunction, new ArrayTabulatedFunction(new UnitFunction(), 0, 1, 5))
        );
        // Разное количество точек
        assertThrows(InconsistentFunctionsException.class, () -> service.division(
                firstFunction, new ArrayTabulatedFunction(new UnitFunction(), 0, 1, 1))
        );
    }
}
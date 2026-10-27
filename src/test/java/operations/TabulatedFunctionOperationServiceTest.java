package operations;

import functions.LinkedListTabulatedFunction;
import functions.Point;
import functions.SqrFunction;
import functions.TabulatedFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TabulatedFunctionOperationServiceTest {

    @Test
    void asPoints() {

        TabulatedFunction function = new LinkedListTabulatedFunction(new SqrFunction(), 1, 5, 5);
        Point[] points = TabulatedFunctionOperationService.asPoints(function);

        int index = 0;
        for (Point point : points) {
            assertEquals(function.getX(index), point.x);
            assertEquals(function.getY(index), point.y);
            index++;
        }
    }
}
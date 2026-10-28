package functions;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class AbstractTabulatedFunctionTest {

//    AbstractTabulatedFunction mockFunction =
//            Mockito.mock(AbstractTabulatedFunction.class);

    @Test
    void interpolate() {
        // Потом
    }

    @Test
    void apply() {
        // Потом
    }

    @Test
    void outputWithToString() {

        double[] xVal = {67, 69};
        double[] yVal = {69, 67};

        System.out.println(new ArrayTabulatedFunction(xVal, yVal));
        System.out.println(new LinkedListTabulatedFunction(xVal, yVal));
    }
}
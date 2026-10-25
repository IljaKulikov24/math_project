package functions;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class AbstractTabulatedFunctionTest {

    AbstractTabulatedFunction mockFunction =
            Mockito.mock(AbstractTabulatedFunction.class);

    @Test
    void interpolate() {
        Mockito.when(mockFunction.apply(100)).thenReturn(100.0);
        assertEquals(100, mockFunction.apply(100));
    }

    @Test
    void apply() {

    }
}
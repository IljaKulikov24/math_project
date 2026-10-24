package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompositeFunctionTest {

    @Test
    void apply() {
        { // с простыми функциями
            CompositeFunction compFunc = new CompositeFunction(
                    new SqrFunction(), new HyperFunction());
            assertEquals(0.04, compFunc.apply(5));
        }
        { // с одной сложной фуекцией
            CompositeFunction compFunc = new CompositeFunction(new CompositeFunction(
                    new HyperFunction(), new SqrFunction()),
                    new SqrtFunction());
            assertEquals(0.2, compFunc.apply(5));
        }
        { // с двумя сложными фкнкциями c(x) = s(h(g(f(x))))
            CompositeFunction compFunc = new CompositeFunction(
                    new CompositeFunction(new IdentityFunction(), new SqrtFunction()),
                    new CompositeFunction(new HyperFunction(), new SqrFunction())
            );
            assertEquals(0.0625, compFunc.apply(16));
        }
    }
}
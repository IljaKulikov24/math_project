package functions;

// сложная функция
public class CompositeFunction implements MathFunction {

    private final MathFunction firstFunction;
    private final MathFunction secondFunction;

    public CompositeFunction(MathFunction functionOne, MathFunction functionTwo) {
        firstFunction = functionOne;
        secondFunction = functionTwo;
    }

    @Override
    public double apply(double x) {
        return secondFunction.apply(firstFunction.apply(x));
    }
}

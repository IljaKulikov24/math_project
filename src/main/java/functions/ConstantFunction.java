package functions;

public class ConstantFunction implements MathFunction {

    private final double CONSTANT;

    public ConstantFunction(double constant) {
        CONSTANT = constant;
    }

    @Override
    public double apply(double x) {
        return CONSTANT;
    }

    public double getX() {
        return CONSTANT;
    }
}

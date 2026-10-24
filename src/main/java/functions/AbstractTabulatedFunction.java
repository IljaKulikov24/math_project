package functions;

public abstract class AbstractTabulatedFunction implements TabulatedFunction {

    protected abstract int floorIndexOfX(double x);

    protected abstract double extrapolateLeft(double x);

    protected abstract double extrapolateRight(double x);

    protected abstract double interpolate(double x, int floorIndex);

    private double interpolate(double x, double leftX, double rightX,
                               double leftY, double rightY) {

        return (rightY - leftY) * (x - leftX) / (rightX - leftX) + leftY;
    }

    @Override
    public double apply(double x) {
        if (x < leftBound()) return extrapolateLeft(x);
        if (x > rightBound()) return extrapolateRight(x);

        int xIndex = indexOfX(x);
        if (xIndex != -1) return getY(xIndex);

        return interpolate(x, floorIndexOfX(x));
    }
}

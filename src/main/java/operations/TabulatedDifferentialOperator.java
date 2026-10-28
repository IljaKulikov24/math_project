package operations;

import functions.Point;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;

public class TabulatedDifferentialOperator implements DifferentialOperator<TabulatedFunction> {

    private TabulatedFunctionFactory factory;

    public TabulatedDifferentialOperator() {
        this.factory = new ArrayTabulatedFunctionFactory();
    }

    public TabulatedDifferentialOperator(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    @Override
    public TabulatedFunction derive(TabulatedFunction function) {
        Point[] points = TabulatedFunctionOperationService.asPoints(function);

        int size = points.length;
        double[] xValues = new double[size];
        double[] yValues = new double[size];

        for (int i = 0; i < size - 1; i++) {
            double x = points[i].x, xRight = points[i + 1].x;
            double y = points[i].y, yRight = points[i + 1].y;

            xValues[i] = x;
            yValues[i] = (yRight - y) / (xRight - x);
        }
        xValues[size - 1] = points[size - 1].x;
        yValues[size - 1] = yValues[size - 2];

        return factory.create(xValues, yValues);
    }

    public TabulatedFunctionFactory getFactory() {
        return factory;
    }

    public void setFactory(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }
}

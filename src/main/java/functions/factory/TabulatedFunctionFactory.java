package functions.factory;

import functions.TabulatedFunction;

public interface TabulatedFunctionFactory {

    public TabulatedFunction create(double[] xValues, double[] yValues);
}

package functions.factory;

import functions.LinkedListTabulatedFunction;

public class LinkedListTabulatedFunctionFactory implements TabulatedFunctionFactory {

    @Override
    public LinkedListTabulatedFunction create(double[] xValues, double[] yValues) {
        return new LinkedListTabulatedFunction(xValues, yValues);
    }
}

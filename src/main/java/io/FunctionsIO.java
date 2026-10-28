package io;

import functions.Point;
import functions.TabulatedFunction;
import functions.factory.TabulatedFunctionFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public final class FunctionsIO {

    private FunctionsIO() {
        throw new UnsupportedOperationException();
    }

    public static void writeTabulatedFunctions(BufferedWriter writer, TabulatedFunction function) {
        PrintWriter printWriter = new PrintWriter(writer);

        printWriter.println(function.getCount());
        for (Point point : function) {
            printWriter.printf("%f %f\n", point.getX(), point.getY());
        }

        printWriter.flush();
    }

    public static TabulatedFunction readTabulatedFunctions(BufferedReader reader, TabulatedFunctionFactory factory)
            throws IOException {

        int count = Integer.parseInt(reader.readLine());

        double[] xValues = new double[count];
        double[] yValues = new double[count];

        NumberFormat numberFormat = NumberFormat.getInstance(Locale.forLanguageTag("ru"));
        for (int i = 0; i < count; i++) {
            String[] point  = reader.readLine().split(" ");
            try {
                xValues[i] = numberFormat.parse(point[0]).doubleValue();
                yValues[i] = numberFormat.parse(point[1]).doubleValue();
            } catch (ParseException e) {
                throw new IOException();
            }
        }

        return factory.create(xValues, yValues);
    }
}

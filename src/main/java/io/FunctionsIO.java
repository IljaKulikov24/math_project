package io;

import functions.Point;
import functions.TabulatedFunction;

import java.io.BufferedWriter;
import java.io.PrintWriter;

public final class FunctionsIO {

    private FunctionsIO() {
        throw new UnsupportedOperationException();
    }

    public static void writeTabulatedFunctions(BufferedWriter writer, TabulatedFunction function) {
        PrintWriter printWriter = new PrintWriter(writer);

        printWriter.println("size = " + function.getCount());
        for (Point point : function) {
            printWriter.printf("%f %f\n", point.getX(), point.getY());
        }

        printWriter.flush();
    }
}

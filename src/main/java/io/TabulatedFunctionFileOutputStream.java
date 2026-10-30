package io;

import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TabulatedFunctionFileOutputStream {
    static void main(String[] args) {
        double[] xValues = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] yValues = {2.0, 4.0, 6.0, 8.0, 10.0};

        TabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        TabulatedFunction listFunction = new LinkedListTabulatedFunction(xValues, yValues);

        try (BufferedOutputStream arrayOutputStream  =
                     new BufferedOutputStream(new FileOutputStream("output/array function.bin"));
             BufferedOutputStream listOutputStream  =
                     new BufferedOutputStream(new FileOutputStream("output/linked list function.bin"))
        ) {
            FunctionsIO.writeTabulatedFunction(arrayOutputStream, arrayFunction);
            FunctionsIO.writeTabulatedFunction(listOutputStream, listFunction);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

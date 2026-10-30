package io;

import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import operations.TabulatedDifferentialOperator;

import java.io.*;

public class TabulatedFunctionFileInputStream {
    static void main(String[] args) {
        try (BufferedInputStream bufferedInputStream =
                     new BufferedInputStream(new FileInputStream("input/binary function.bin")))
        {
            TabulatedFunction function =
                    FunctionsIO.readTabulatedFunction(bufferedInputStream, new ArrayTabulatedFunctionFactory());

            System.out.println(function);

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Введите размер и значения функции");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            TabulatedFunction function = FunctionsIO.readTabulatedFunction(reader, new LinkedListTabulatedFunctionFactory());
            TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator();
            TabulatedFunction derivative = operator.derive(function);

            System.out.println(derivative);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

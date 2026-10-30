package io;

import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TabulatedFunctionFileWriter {

    static void main(String[] args) {
        double[] xValues = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] yValues = {2.0, 4.0, 6.0, 8.0, 10.0};

        TabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
        TabulatedFunction listFunction = new LinkedListTabulatedFunction(xValues, yValues);

        try (
            BufferedWriter arrayWriter = new BufferedWriter(new FileWriter("output/array function.txt"));
            BufferedWriter listWriter = new BufferedWriter(new FileWriter("output/linked list function.txt"));
        ) {
            FunctionsIO.writeTabulatedFunction(arrayWriter, arrayFunction);
            FunctionsIO.writeTabulatedFunction(listWriter, listFunction);

            System.out.println("Запись в файлы успешно произведена");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

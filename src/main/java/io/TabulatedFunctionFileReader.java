package io;

import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TabulatedFunctionFileReader {

    static void main(String[] args) throws FileNotFoundException {

        try (BufferedReader readerOne = new BufferedReader(new FileReader("input/function.txt"));
             BufferedReader readerTwo = new BufferedReader(new FileReader("input/function.txt"));
        ) {
            System.out.println(FunctionsIO.readTabulatedFunction(readerOne, new ArrayTabulatedFunctionFactory()));
            System.out.println(FunctionsIO.readTabulatedFunction(readerTwo, new LinkedListTabulatedFunctionFactory()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

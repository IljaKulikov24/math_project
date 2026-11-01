package io;

import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.SqrFunction;
import functions.TabulatedFunction;

import java.io.*;

public class TabulatedFunctionSerialization {
    static void main(String[] args) {
        TabulatedFunction list = new LinkedListTabulatedFunction(new SqrFunction(), 1, 5, 5);
        TabulatedFunction array = new ArrayTabulatedFunction(new SqrFunction(), 1, 5, 5);

        try (BufferedOutputStream writerList = new BufferedOutputStream(new FileOutputStream("temp/serialize list.ser"));
             BufferedOutputStream writerArray = new BufferedOutputStream(new FileOutputStream("temp/serialize array.ser")))
        {
            FunctionsIO.serialize(writerList, list);
            FunctionsIO.serialize(writerArray, array);

            BufferedInputStream readerList = new BufferedInputStream(new FileInputStream("temp/serialize list.ser"));
            BufferedInputStream readerArray = new BufferedInputStream(new FileInputStream("temp/serialize array.ser"));

            LinkedListTabulatedFunction function1 = (LinkedListTabulatedFunction) FunctionsIO.deserialize(readerList);
            ArrayTabulatedFunction function2 = (ArrayTabulatedFunction) FunctionsIO.deserialize(readerArray);

            System.out.println(function1);
            System.out.println(function2);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

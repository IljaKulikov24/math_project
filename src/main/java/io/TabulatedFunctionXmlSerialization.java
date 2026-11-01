package io;

import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.SqrFunction;
import functions.TabulatedFunction;

import java.io.*;

public class TabulatedFunctionXmlSerialization {
    static void main(String[] args) {
        TabulatedFunction list = new LinkedListTabulatedFunction(new SqrFunction(), 1, 5, 5);
        TabulatedFunction array = new ArrayTabulatedFunction(new SqrFunction(), 1, 5, 5);

        try (BufferedWriter writerList = new BufferedWriter(new FileWriter("temp/xml-serialize list"));
             BufferedWriter writerArray = new BufferedWriter(new FileWriter("temp/xml-serialize array")))
        {
            FunctionsIO.serializeXml(writerList, list);
            FunctionsIO.serializeXml(writerArray, array);

            BufferedReader readerList = new BufferedReader(new FileReader("temp/xml-serialize list"));
            BufferedReader readerArray = new BufferedReader(new FileReader("temp/xml-serialize array"));

            LinkedListTabulatedFunction function1 = (LinkedListTabulatedFunction) FunctionsIO.deserializeXml(readerList);
            ArrayTabulatedFunction function2 = (ArrayTabulatedFunction) FunctionsIO.deserializeXml(readerArray);

            System.out.println(function1);
            System.out.println(function2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

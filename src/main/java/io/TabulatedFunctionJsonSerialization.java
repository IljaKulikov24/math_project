package io;

import functions.*;

import java.io.*;

public class TabulatedFunctionJsonSerialization {
    static void main() {
        TabulatedFunction arrayFunction = new ArrayTabulatedFunction(new SqrFunction(), 1, 5, 5);
        TabulatedFunction listFunction = new LinkedListTabulatedFunction(new HyperFunction(), 1, 5, 5);

        try (BufferedWriter writerArray = new BufferedWriter(new FileWriter("temp/json-serialize array"));
             BufferedWriter writeList = new BufferedWriter(new FileWriter("temp/json-serialize list")))
        {
            FunctionsIO.serializeJson(writerArray, arrayFunction);
            FunctionsIO.serializeJson(writeList, listFunction);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader readerArray = new BufferedReader(new FileReader("temp/json-serialize array"));
             BufferedReader readerList = new BufferedReader(new FileReader("temp/json-serialize list")))
        {
            System.out.println(FunctionsIO.deserializeJson(readerArray));
            System.out.println(FunctionsIO.deserializeJson(readerList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

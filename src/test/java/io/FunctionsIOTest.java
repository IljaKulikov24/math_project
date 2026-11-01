package io;

import functions.ArrayTabulatedFunction;
import functions.SqrFunction;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class FunctionsIOTest {

    TabulatedFunction function = new ArrayTabulatedFunction(new SqrFunction(), 1, 5, 5);

    @Test
    void writeAndReadTabulatedFunction() {
        try (BufferedWriter streamWrite = new BufferedWriter(new FileWriter("temp/function.txt"));
             BufferedReader streamRead = new BufferedReader(new FileReader("temp/function.txt")))
        {
            FunctionsIO.writeTabulatedFunction(streamWrite, function);
            TabulatedFunction functionFromFile =
                    FunctionsIO.readTabulatedFunction(streamRead, new ArrayTabulatedFunctionFactory());

            assertEquals(function.getCount(), functionFromFile.getCount());
            for (int i = 0; i < function.getCount(); i++) {
                assertEquals(function.getX(i), functionFromFile.getX(i));
                assertEquals(function.getY(i), functionFromFile.getY(i), 1e-6);
            }

        } catch (IOException e) {
            fail(e.getMessage());
        }


        try (BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream("temp/function.bin"));
             BufferedInputStream reader = new BufferedInputStream(new FileInputStream("temp/function.bin")))
        {
            FunctionsIO.writeTabulatedFunction(writer, function);
            TabulatedFunction functionFromByteStream =
                    FunctionsIO.readTabulatedFunction(reader, new LinkedListTabulatedFunctionFactory());

            assertEquals(function.getCount(), functionFromByteStream.getCount());
            for (int i = 0; i < function.getCount(); i++) {
                assertEquals(function.getX(i), functionFromByteStream.getX(i));
                assertEquals(function.getY(i), functionFromByteStream.getY(i), 1e-6);
            }

        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void serializeAndDeserialize() {
        try (BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream("temp/serialize function.ser"));
             BufferedInputStream reader = new BufferedInputStream(new FileInputStream("temp/serialize function.ser")))
        {
            FunctionsIO.serialize(writer, function);
            TabulatedFunction serializeFunction = FunctionsIO.deserialize(reader);

            assertEquals(function.getCount(), serializeFunction.getCount());
            for (int i = 0; i < function.getCount(); i++) {
                assertEquals(function.getX(i), serializeFunction.getX(i));
                assertEquals(function.getY(i), serializeFunction.getY(i));
            }

        } catch (IOException | ClassNotFoundException e) {
            fail(e.getMessage());
        }
    }
}
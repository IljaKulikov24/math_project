package io;

import functions.LinkedListTabulatedFunction;
import functions.SqrFunction;
import functions.TabulatedFunction;
import functions.factory.LinkedListTabulatedFunctionFactory;
import operations.TabulatedDifferentialOperator;

import java.io.*;

public class LinkedListTabulatedFunctionSerialization {
    static void main(String[] args) {
        try (BufferedOutputStream stream =
                     new BufferedOutputStream(new FileOutputStream("output/serialized linked list functions.bin")))
        {
            TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator(
                    new LinkedListTabulatedFunctionFactory()
            );

            TabulatedFunction function = new LinkedListTabulatedFunction(new SqrFunction(), 0, 10, 6);
            TabulatedFunction firstDerivative = (TabulatedFunction) operator.derive(function);
            TabulatedFunction secondDerivative = (TabulatedFunction) operator.derive(firstDerivative);

            FunctionsIO.serialize(stream, function);
            FunctionsIO.serialize(stream, firstDerivative);
            FunctionsIO.serialize(stream, secondDerivative);

            stream.close();;

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedInputStream stream =
                     new BufferedInputStream(new FileInputStream("output/serialized linked list functions.bin")))
        {
            System.out.println("Функция");
            System.out.println(FunctionsIO.deserialize(stream));

            System.out.println("Первая производная");
            System.out.println(FunctionsIO.deserialize(stream));

            System.out.println("Вторая производная");
            System.out.println(FunctionsIO.deserialize(stream));

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

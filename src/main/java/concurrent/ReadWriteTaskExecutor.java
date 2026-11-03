package concurrent;

import functions.ConstantFunction;
import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;

public class ReadWriteTaskExecutor {

    static void main() {
        TabulatedFunction linkedListFunction = new LinkedListTabulatedFunction(new ConstantFunction(-1), 1, 1000, 1000);

        Thread thread1 = new Thread(new ReadTask(linkedListFunction));
        Thread thread2 = new Thread(new WriteTask(linkedListFunction, 1));

        thread1.start();
        thread2.start();
    }
}

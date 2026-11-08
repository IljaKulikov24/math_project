package concurrent;

import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import functions.UnitFunction;

import java.util.ArrayList;
import java.util.List;

public class MultiplyingTaskExecutor {

    static void main() throws InterruptedException {
        TabulatedFunction function = new LinkedListTabulatedFunction(new UnitFunction(), 1, 1000, 1000);

        List<Thread> threads = new ArrayList<>();
        List<MultiplyingTask> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MultiplyingTask task = new MultiplyingTask(function);
            tasks.add(task);
            threads.add(new Thread(task));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        while (!tasks.isEmpty()) {
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).isCompleted()) {
                    tasks.remove(i--);
                }
            }
        }

        System.out.println(function);
    }
}

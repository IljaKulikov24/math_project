package concurrent;

import functions.TabulatedFunction;

public class ReadTask implements Runnable {

    private final TabulatedFunction function;

    public ReadTask(TabulatedFunction function) {
        this.function = function;
    }

    @Override
    public synchronized void run() {
        for (int i = 0; i < function.getCount(); i++) {
            synchronized (function) {
                System.out.printf("After read: %d, %f, %f\n", i, function.getX(i), function.getY(i));
            }
        }
    }
}

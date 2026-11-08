package concurrent;

import functions.TabulatedFunction;

public class MultiplyingTask implements Runnable {

    private final TabulatedFunction function;
    private boolean flag = false;

    public MultiplyingTask(TabulatedFunction function) {
        this.function = function;
    }

    @Override
    public void run() {
        for (int i = 0; i < function.getCount(); i++) {
            synchronized (function) {
                function.setY(i, function.getY(i) * 2);
            }
        }
        flag = true;
        System.out.println("Поток MultiplyingTask завершил выполнение");
    }

    public boolean isCompleted() {
        return flag;
    }
}

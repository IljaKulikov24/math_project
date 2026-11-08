package concurrent;

import functions.CompositeFunction;
import functions.MathFunction;
import functions.Point;
import functions.TabulatedFunction;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class SynchronizedTabulatedFunction implements TabulatedFunction {

    private final TabulatedFunction function;
    private final Object mutex;

    public SynchronizedTabulatedFunction(TabulatedFunction function) {
        this.function = function;
        this.mutex = new Object();
    }

    @Override
    public int getCount() {
        synchronized (mutex) {
            return function.getCount();
        }
    }

    @Override
    public double getX(int index) {
        synchronized (mutex) {
            return function.getX(index);
        }
    }

    @Override
    public double getY(int index) {
        synchronized (mutex) {
            return function.getY(index);
        }
    }

    @Override
    public void setY(int index, double value) {
        synchronized (mutex) {
            function.setY(index, value);
        }
    }

    @Override
    public int indexOfX(double x) {
        synchronized (mutex) {
            return function.indexOfX(x);
        }
    }

    @Override
    public int indexOfY(double y) {
        synchronized (mutex) {
            return function.indexOfY(y);
        }
    }

    @Override
    public double leftBound() {
        synchronized (mutex) {
            return function.leftBound();
        }
    }

    @Override
    public double rightBound() {
        synchronized (mutex) {
            return function.rightBound();
        }
    }

    @Override
    public double apply(double x) {
        synchronized (mutex) {
            return function.apply(x);
        }
    }

    @Override
    public CompositeFunction andThen(MathFunction afterFunction) {
        synchronized (mutex) {
            return function.andThen(afterFunction);
        }
    }

    @NotNull
    @Override
    public Iterator<Point> iterator() {
        synchronized (mutex) {
            Iterator<Point> iterator = function.iterator();

            return new Iterator<Point>() {

                @Override
                public boolean hasNext() {
                    synchronized (mutex) {
                        return iterator.hasNext();
                    }
                }

                @Override
                public Point next() {
                    synchronized (mutex) {
                        return iterator.next();
                    }
                }
            };
        }
    }
}

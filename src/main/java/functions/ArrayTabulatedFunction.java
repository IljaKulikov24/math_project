package functions;

import exceptions.InterpolationException;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayTabulatedFunction extends AbstractTabulatedFunction
        implements Insertable, Removable {

    private double[] xValues;
    private double[] yValues;
    private int count;

    public ArrayTabulatedFunction(double[] xValues, double[] yValues) {

        checkLengthIsTheSame(xValues, yValues);
        checkSorted(xValues);

        if (xValues.length < 2) throw new IllegalArgumentException("Недопустим размер таблицы меньше 2");

        count = xValues.length;
        this.xValues = Arrays.copyOf(xValues, count + 100);
        this.yValues = Arrays.copyOf(yValues, count + 100);
    }

    public ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {

        if (count < 2) throw new IllegalArgumentException("Недопустим размер таблицы меньше 2");

        this.count = count;

        if (xFrom > xTo) {
            double temp = xFrom;
            xFrom = xTo;
            xTo = temp;
        }

        xValues = new double[count + 100];
        yValues = new double[count + 100];

        if (xFrom == xTo) {
            for (int i = 0; i < count; i++) {
                xValues[i] = xFrom;
                yValues[i] = source.apply(xFrom);
            }
        } else {
            xValues[0] = xFrom;
            yValues[0] = source.apply(xFrom);
            xValues[count - 1] = xTo;
            yValues[count - 1] = source.apply(xTo);

            double step = (xTo - xFrom) / (count - 1);
            for (int i = 1; i < count - 1; i++) {
                xValues[i] = xFrom + i * step;
                yValues[i] = source.apply(xValues[i]);
            }
        }
    }


    @Override
    protected int floorIndexOfX(double x) {
        if (x < leftBound()) return 0;
        if (x > rightBound()) return count;

        int floorIndex = 0;
        for (int i = 1; i < count; i++) {
            if (x < xValues[i]) {
                floorIndex = i - 1;
                break;
            }
        }
        return floorIndex;
    }

    @Override
    protected double extrapolateLeft(double x) {
        return interpolate(x, getX(0), getX(1), getY(0), getY(1));
    }

    @Override
    protected double extrapolateRight(double x) {
        return interpolate(x, getX(count - 2), getX(count - 1), getY(count - 2), getY(count - 1));
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        if (x < getX(floorIndex) || x > getX(floorIndex + 1)) {
            throw new InterpolationException();
        }

        return interpolate(x, xValues[floorIndex], xValues[floorIndex + 1], yValues[floorIndex], yValues[floorIndex + 1]);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public double getX(int index) {
        if (index >= count || index < 0) {
            throw new IllegalArgumentException("Неверный индекс");
        }

        return xValues[index];
    }

    @Override
    public double getY(int index) {
        if (index >= count || index < 0) {
            throw new IllegalArgumentException("Неверный индекс");
        }

        return yValues[index];
    }

    @Override
    public void setY(int index, double value) {
        if (index >= count || index < 0) {
            throw new IllegalArgumentException("Неверный индекс");
        }

        yValues[index] = value;
    }

    @Override
    public int indexOfX(double x) {
        for (int i = 0; i < count; i++) {
            if (xValues[i] == x) return i;
        }
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        for (int i = 0; i < count; i++) {
            if (yValues[i] == y) return i;
        }
        return -1;
    }

    @Override
    public double leftBound() {
        return xValues[0];
    }

    @Override
    public double rightBound() {
        return xValues[count - 1];
    }

    @Override
    public void insert(double x, double y) {
        int index = indexOfX(x);
        // если X уже присутствует в функции
        if (index != -1) {
            setY(index, y);
            return;
        }

        if (count == xValues.length) {
            xValues = Arrays.copyOf(xValues, count + 100);
            yValues = Arrays.copyOf(yValues, count + 100);
        }

        int insertionIndex = floorIndexOfX(x);
        if (insertionIndex != 0 && insertionIndex != count) {
            insertionIndex++;
        }

        System.arraycopy(xValues, insertionIndex, xValues, insertionIndex + 1, count - insertionIndex);
        System.arraycopy(yValues, insertionIndex, yValues, insertionIndex + 1, count - insertionIndex);
        xValues[insertionIndex] = x;
        yValues[insertionIndex] = y;

        count++;
    }

    @Override
    public void remove(int index) {
        if (index >= count || index < 0) {
            throw new IllegalArgumentException("Неверный индекс");
        }
        if (count == 2) {
            throw new IllegalArgumentException("Удаление невозможно, в таблице не может находится меньше 2 точек");
        }

        System.arraycopy(xValues, index + 1, xValues, index, count - index);
        System.arraycopy(yValues, index + 1, yValues, index, count - index);

        count--;
    }

    @NotNull
    @Override
    public Iterator<Point> iterator() {
        return new Iterator<Point>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < count;
            }

            @Override
            public Point next() {
                if (!hasNext()) throw new NoSuchElementException();
                return new Point(xValues[index], yValues[index++]);
            }
        };
    }
}

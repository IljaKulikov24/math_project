package functions;

import exceptions.InterpolationException;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction
        implements Insertable, Removable, Serializable {

    @Serial
    private static final long serialVersionUID = 2757719858146810667L;

    private int count;
    private Node head;

    public static class Node {
        Node next;
        Node prev;
        double x;
        double y;

        Node(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private void addNode(double x, double y) {
        if (head == null) {
            head = new Node(x, y);
            head.next = head;
            head.prev = head;
        } else {
            Node newNode = new Node(x, y);
            newNode.next = head;
            newNode.prev = head.prev;
            head.prev.next = newNode;
            head.prev = newNode;
        }
        count++;
    }

    public LinkedListTabulatedFunction(double[] xValues, double[] yValues) {

        checkLengthIsTheSame(xValues, yValues);

        checkSorted(xValues);

        if (xValues.length < 2) {
            throw new IllegalArgumentException("Недопустим размер таблицы меньше 2");
        }

        for (int i = 0; i < xValues.length; i++) {
            addNode(xValues[i], yValues[i]);
        }
    }

    public LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if (count < 2) {
            throw new IllegalArgumentException("Недопустим размер таблицы меньше 2");
        }

        addNode(xFrom, source.apply(xFrom));

        if (xFrom == xTo) {
            for (int i = 1; i < count - 1; i++) {
                addNode(xFrom, source.apply(xFrom));
            }
        } else {
            double step = (xTo - xFrom) / (count - 1);
            for (int i = 1; i < count - 1; i++) {
                double value = xFrom + step * i;
                addNode(value, source.apply(value));
            }
        }

        addNode(xTo, source.apply(xTo));
    }

    @Override
    public double apply(double x) {
        if (x < leftBound()) return extrapolateLeft(x);
        if (x > rightBound()) return extrapolateRight(x);

        Node node = floorNodeOfX(x);
        if (node.next.x == x) return node.next.y;

        return interpolate(x, node.x, node.next.x, node.y, node.next.y);
    }

    protected Node floorNodeOfX(double x) {
        if (x < leftBound()) return head;
        if (x > rightBound()) return head.prev;

        Node current = head.next;
        while (current.x < x) {
            current = current.next;
        }
        return current.prev;
    }

    @Override
    protected int floorIndexOfX(double x) {
        if (x < leftBound()) return 0;
        if (x > rightBound()) return count;

        int floorIndex = 0;
        Node current = head;
        while (current.x < x) {
            floorIndex++;
            current = current.next;
        }
        return --floorIndex;
    }

    @Override
    protected double extrapolateLeft(double x) {
        return interpolate(x, head.x, head.next.x, head.y, head.next.y);
    }

    @Override
    protected double extrapolateRight(double x) {
        return interpolate(x, head.prev.prev.x, head.prev.x, head.prev.prev.y, head.prev.y);
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        Node node = getNode(floorIndex);
        if (x < node.x || x > node.next.x) {
            throw new InterpolationException();
        }

        return interpolate(x, node.x, node.next.x, node.y, node.next.y);
    }

    @Override
    public int getCount() {
        return count;
    }

    private Node getNode(int index) {
        if (index >= count || index < 0) {
            throw new IllegalArgumentException("Неверный индекс");
        }

        int i;
        Node current;
        if (index < count / 2) {
            i = 0;
            current = head;
            while (i < index) {
                current = current.next;
                i++;
            }
        } else {
            i = count - 1;
            current = head.prev;
            while (i > index) {
                current = current.prev;
                i--;
            }
        }
        return current;
    }

    @Override
    public double getX(int index) {
        if (index >= count || index < 0) {
            throw new IllegalArgumentException("Неверный индекс");
        }

        return getNode(index).x;
    }

    @Override
    public double getY(int index) {
        if (index >= count || index < 0) {
            throw new IllegalArgumentException("Неверный индекс");
        }

        return getNode(index).y;
    }

    @Override
    public void setY(int index, double value) {
        if (index >= count || index < 0) {
            throw new IllegalArgumentException("Неверный индекс");
        }

        getNode(index).y = value;
    }

    @Override
    public int indexOfX(double x) {
        Node current = head;
        int index = 0;
        while (index < count) {
            if (current.x == x) return index;
            current = current.next;
            index++;
        }
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        Node current = head;
        int index = 0;
        while (index < count) {
            if (current.y == y) return index;
            current = current.next;
            index++;
        }
        return -1;
    }

    @Override
    public double leftBound() {
        return head.x;
    }

    @Override
    public double rightBound() {
        return head.prev.x;
    }

    @Override
    public void insert(double x, double y) {
        // Вставка при путом списке
        if (count == 0) {
            addNode(x, y);
            return;
        }

        Node node = floorNodeOfX(x);
        // Вставка при уже существующем X
        if (node.next.x == x) {
            node.next.y = y;
            return;
        }
        // Вставка в начало
        if (node == head) {
            addNode(x, y);
            head = head.prev;
        }
        // Вставка в конец
        else if (node == head.prev) {
            addNode(x, y);
        }
        // Вставка в середину
        else {
            Node insertNode = new Node(x, y);
            Node next = node.next;
            node.next = insertNode;
            insertNode.next = next;
            insertNode.prev = node;
            next.prev = insertNode;

            count++;
        }
    }

    @Override
    public void remove(int index) {
        if (index >= count || index < 0) {
            throw new IllegalArgumentException("Неверный индекс");
        }
        if (count == 2) {
            throw new IllegalArgumentException("Удаление невозможно, в таблице не может находится меньше 2 точек");
        }

        if (count == 1) {
            head = null;
            count--;
            return;
        }

        Node node = getNode(index), next = node.next, prev = node.prev;
        prev.next = next;
        next.prev = prev;

        if (index == 0) {
            head = next;
        }

        count--;
    }

    @NotNull
    @Override
    public Iterator<Point> iterator() {
        return new Iterator<Point>() {
            Node node = head;
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < count;
            }

            @Override
            public Point next() {
                if (!hasNext()) throw new NoSuchElementException();
                index++;
                node = node.next;
                return new Point(node.prev.x, node.prev.y);
            }
        };
    }
}

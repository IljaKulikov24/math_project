package functions;

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction implements Insertable {

    private int count;
    private Node head;

    public class Node {
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
        }
        else {
            Node newNode = new Node(x, y);
            newNode.next = head;
            newNode.prev = head.prev;
            head.prev.next = newNode;
            head.prev = newNode;
        }
        count++;
    }

    public LinkedListTabulatedFunction(double[] xValues, double[] yValues) {
        for (int i = 0; i < xValues.length; i++) {
            addNode(xValues[i], yValues[i]);
        }
    }

    public LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        addNode(xFrom, source.apply(xFrom));

        if (xFrom == xTo) {
            for (int i = 1; i < count - 1; i++) {
                addNode(xFrom, source.apply(xFrom));
            }
        }
        else {
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
        return interpolate(x, 0);
    }

    @Override
    protected double extrapolateRight(double x) {
        return interpolate(x, count - 2);
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        if (count == 1) return x;

        return interpolate(x, getX(floorIndex), getX(floorIndex + 1),
                getY(floorIndex), getY(floorIndex + 1));
    }

    @Override
    public int getCount() {
        return count;
    }

    private Node getNode(int index) {
        int i;
        Node current;
        if (index < count / 2) {
            i = 0;
            current = head;
            while (i < index) {
                current = current.next;
                i++;
            }
        }
        else {
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
        return getNode(index).x;
    }

    @Override
    public double getY(int index) {
        return getNode(index).y;
    }

    @Override
    public void setY(int index, double value) {
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

            count++;
        }
    }
}

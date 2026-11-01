package functions;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS, // Сохранять полное имя класса в JSON
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type" // Поле в JSON, куда запишется класс (например, "io.ArrayTabulatedFunction")
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ArrayTabulatedFunction.class, name = "array"),
        @JsonSubTypes.Type(value = LinkedListTabulatedFunction.class, name = "linked")
})

public interface TabulatedFunction extends MathFunction, Iterable<Point> {

    int getCount();

    double getX(int index);

    double getY(int index);

    void setY(int index, double value);

    int indexOfX(double x);

    int indexOfY(double y);

    double leftBound();

    double rightBound();
}

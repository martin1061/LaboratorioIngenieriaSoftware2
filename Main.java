import java.util.ArrayList;
import java.util.List;
//clase abstracta Figura que define los métodos para calcular el área y el perímetro de una figura geométrica.
abstract class Figure {
    double x;
    double y;

    abstract double calculateArea();
    abstract double getPerimeter();
}
//clase Triángulo que extiende la clase Figura y proporcona implementaciones concretas de los métodos para calcular el área y el perímetro de un triángulo.
class Triangle extends Figure {

    Triangle(double x, double y) {
        this.x = x; // base
        this.y = y; // altura
    }

    @Override
    double calculateArea() {
        return (x * y) / 2.0;
    }

    @Override
    double getPerimeter() {
        double hipotenusa = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return x + y + hipotenusa;
    }
}
//clase Círculo que extiende la clase Figura y proporciona implementaciones concretas de los métodos para calcular el área y el perímetro de un círculo.
class Circle extends Figure {

    Circle(double x) {
        this.x = x; // radio
    }

    @Override
    double calculateArea() {
        return Math.PI * x * x;
    }

    @Override
    double getPerimeter() {
        return 2 * Math.PI * x;
    }
}
//clase Cuadrado que extiende la clase Figura y proporciona implementaciones concretas de los métodos para calcular el área y el perímetro de un cuadrado.
class Square extends Figure {

    Square(double side) {
        this.x = side; // lado
    }

    @Override
    double calculateArea() {
        return x * x;
    }

    @Override
    double getPerimeter() {
        return 4 * x;
    }
}

public class Main {
    public static void main(String[] args) {
        // Crear instancias de las figuras geométricas
        Figure fig1 = new Circle(1.0);
        Figure fig2 = new Square(2.3);
        Figure fig3 = new Triangle(4.2, 4.5);
// Agregar las figuras a una lista y calcular el área y el perímetro de cada una
        List<Figure> figures = new ArrayList<>();
        figures.add(fig1);
        figures.add(fig2);
        figures.add(fig3);

        for (Figure fig : figures) {
            System.out.println("Area: " + fig.calculateArea());
            System.out.println("Perimeter: " + fig.getPerimeter());
            System.out.println("------------------------");
        }
    }
}
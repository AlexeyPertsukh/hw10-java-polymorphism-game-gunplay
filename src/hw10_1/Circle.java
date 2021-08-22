package hw10_1;

public class Circle extends Shape {

    public static final double PI = 3.1415926536;

    private double diameter;

    public Circle(String color, double diameter) {
        super(color);
        this.diameter = diameter;
    }

    @Override
    public double area() {
        double radius = diameter / 2;
        return PI * radius * radius;
    }

    @Override
    public String toString() {
        return "This is Circle, color is: " + getColor() + ", diameter " + diameter;
    }


}

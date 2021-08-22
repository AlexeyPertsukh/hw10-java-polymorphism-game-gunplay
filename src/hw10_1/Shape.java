package hw10_1;

public class Shape {
    private String color;

    public Shape(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double area() {
        return 0;
    }

    @Override
    public String toString() {
        return "This is Shape, color is: " + color;
    }

}

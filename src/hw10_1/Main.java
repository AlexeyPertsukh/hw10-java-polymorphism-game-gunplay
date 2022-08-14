/*
Добавить класс Круг.
Создать по три экземпляра каждой фигуры.
Поместить их в массив.
Посчитать общую площадь всех фигур.
 */
package hw10_1;

public class Main {

    public static void main(String[] args) {
        double totalArea = 0;
        Shape[] shapes = new Shape[0];

        shapes = addToShapes(shapes, new Rectangle("red", 23, 3));
        shapes = addToShapes(shapes, new Rectangle("yellow", 5, 3));
        shapes = addToShapes(shapes, new Rectangle("blue", 2, 4));

        shapes = addToShapes(shapes, new Triangle("green", 5, 5, 5));
        shapes = addToShapes(shapes, new Triangle("white", 3, 3, 3));
        shapes = addToShapes(shapes, new Triangle("black", 2, 2, 2));

        shapes = addToShapes(shapes, new Circle("red", 6));
        shapes = addToShapes(shapes, new Circle("black", 12));
        shapes = addToShapes(shapes, new Circle("green", 16));

        for (int i = 0; i < shapes.length; i++) {
            System.out.println((i + 1) + ". " + shapes[i].toString());
            System.out.printf("   Area: %.2f   \n", shapes[i].area());
            totalArea += shapes[i].area();
        }

        System.out.println("--------------------");
        System.out.printf("Total area: %.2f    \n", totalArea);
    }

    public static Shape[] addToShapes(Shape[] shapes, Shape shape) {
        Shape[] tmp = new Shape[shapes.length + 1];
        for (int i = 0; i < shapes.length; i++) {
            tmp[i] = shapes[i];
        }
        tmp[tmp.length - 1] = shape;
        return tmp;
    }


}

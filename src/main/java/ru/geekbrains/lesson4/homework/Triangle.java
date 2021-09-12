package ru.geekbrains.lesson4.homework;

public class Triangle {
    private int a;
    private int b;
    private int c;

    public Triangle(int a, int b, int c) {
        if (a <= 0 || b <= 0 || c <= 0) {
            throw new FalseInputDataException("Одна из сторон треугольника имеет отрицательное значение или ноль");
        } else if ((a + b) <= c || (a + c) <= b || (b + c) <= a) {
            throw new FalseInputDataException("Сумма двух сторон треугольника меньше или равна третьей стороне");
        } else {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    public double getArea() {
        double p = (double) (a + b + c) / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "Triangle{"
                + "a=" + a
                + ", b=" + b
                + ", c=" + c
                + '}';
    }

    public static void main(String[] args) {
        Triangle triangle = new Triangle(5, 5, 4);
        System.out.println("Triangle area: " + triangle.getArea());
    }
}

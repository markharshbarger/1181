public class Square {
    private double side;

    public Square(double side) {
        if (side < 0) {
            throw new IllegalSideLengthException("Negative length: " + side);
        }
        this.side = side;
    }

    @Override
    public String toString() {
        return "side = " + side;
    }

    public double getPerimeter() {
        return side * 4.0;
    }

    public double getArea() {
        return side * side;
    }
}

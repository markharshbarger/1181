
// Square class represents a square object that has 4 sides of length "side" of type (double).
// Methods to get perimeter and area are included, along with a toString method
public class Square {
    private double side;

    /**
     * Constructor for Square class, sets side with a double.
     * @param side - (double) if double is less than 0, an IllegalSideLengthException is thrown
     */
    public Square(double side) {
        if (side < 0) {
            throw new IllegalSideLengthException("Negative length: " + side);
        }
        this.side = side;
    }

    /** 
     * toString method that overrides the default
     * 
     * @return the string "side = " + the square side length
     * 
    */
    @Override
    public String toString() {
        return "side = " + side;
    }

    /**
     * Gets perimeter of the square
     * 
     * @return - (double) side * 4.0; which is perimeter for a squarex
     */
    public double getPerimeter() {
        return side * 4.0;
    }

    /**
     * Gets area of the square
     * 
     * @return - (double) side * side; which is area for a square
     */
    public double getArea() {
        return side * side;
    }
}

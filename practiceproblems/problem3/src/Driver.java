import java.util.InputMismatchException;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        System.out.print("Enter the length of the square's side: ");
        Scanner userInput = new Scanner(System.in);
        double userNum = 0;

        try {
            userNum = userInput.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Error: you must enter a number");
            System.exit(0);
        }
        userInput.close();

        Square mySquare = null;
        try {
            mySquare = new Square(userNum);
        } catch (IllegalSideLengthException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        System.out.println("The perimeter of the square is " + mySquare.getPerimeter());
        System.out.println("The area of the square is " + mySquare.getArea());
    }
}

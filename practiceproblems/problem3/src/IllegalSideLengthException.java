
// Custom Exception for Driver Class
public class IllegalSideLengthException extends IllegalArgumentException {
    /**
     * Constructor for IllegalSideLengthException that has a custom message and uses super class constructor
     * 
     * @param message - (String) that has a custom message based on the error
     */
    public IllegalSideLengthException(String message) {
        super(message);
    }
}

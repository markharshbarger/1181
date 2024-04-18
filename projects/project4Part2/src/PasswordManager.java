import java.util.ArrayList;

/**
 * Class represents an object that keeps track of the passwords used in order to crack a password. Class is
 * meant to be used by multiple threads to speed up the time cracking the password takes.
 */
public class PasswordManager {
    private volatile boolean passwordFound = false;
    private ArrayList<String> passwords;
    private int num = 0;
    private String correctPassword = "";

    /**
     * Constructor for Passwordmanager, sets the objects passwords
     * 
     * @param passwords ArrayList<String> - the passwords to be used in order to crack the password
     */
    public PasswordManager(ArrayList<String> passwords) {
        this.passwords = passwords;
    }

    /**
     * Getter for boolean passwordFound
     * 
     * @return true if the password has been found, false otherwise
     */
    public boolean isPasswordFound() {
        return passwordFound;
    }

    /**
     * Gets the next password guess that hasn't been used yet
     * 
     * @return String - the next password guess
     */
    public synchronized String getPasswordGuess() {
        if (num < passwords.size() - 1) {
            return passwords.get(num++);
        } else {
            return null;
        }
    }

    /**
     * Sets the boolean true if the password has been found, or can set the boolean false
     * 
     * @param passwordFound - set true if the password has been found
     */
    public void setPasswordFound(boolean passwordFound) {
        this.passwordFound = passwordFound;
    }

    /**
     * Sets the correct password that successfully cracked the file
     * 
     * @param correctPassword String - the password that correctly cracked the file
     */
    public void setPassword(String correctPassword) {
        this.correctPassword = correctPassword;
    }

    /**
     * Gets the correct password
     * 
     * @return String - the correct password
     */
    public String getCorrectPassword() {
        return correctPassword;
    }
}

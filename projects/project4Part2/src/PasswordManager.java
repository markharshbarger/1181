import java.util.ArrayList;

public class PasswordManager {
    private volatile boolean passwordFound = false;
    private ArrayList<String> passwords;
    private int num = 0;
    private String correctPassword = "";

    public PasswordManager(ArrayList<String> passwords) {
        this.passwords = passwords;
    }

    public boolean isPasswordFound() {
        return passwordFound;
    }

    public synchronized String getPasswordGuess() {
        if (num < passwords.size() - 1) {
            return passwords.get(num++);
        } else {
            return null;
        }
    }

    public void setPasswordFound(boolean passwordFound) {
        this.passwordFound = passwordFound;
    }

    public void setPassword(String correctPassword) {
        this.correctPassword = correctPassword;
    }

    public String getCorrectPassword() {
        return correctPassword;
    }
}

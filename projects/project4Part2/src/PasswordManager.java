import java.util.ArrayList;

public class PasswordManager {
    private volatile boolean passwordFound = false;
    private String correctPassword = "";

    public PasswordManager() {
    }

    public synchronized boolean isPasswordFound() {
        return passwordFound;
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

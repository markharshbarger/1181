import java.util.ArrayList;

public class PasswordManager {
    private volatile boolean passwordFound = false;
    private ArrayList<String> passwordList;
    private int num = 0;
    private String correctPassword = "";

    public PasswordManager(ArrayList<String> passwordList) {
        this.passwordList = passwordList;
    }

    public boolean isPasswordFound() {
        return passwordFound;
    }

    public synchronized String getPasswordGuess() {
        return passwordList.get(num++);
    }

    public void setPasswordFound() {
        passwordFound = false;
    }

    public void setPassword(String correctPassword) {
        this.correctPassword = correctPassword;
    }

    public String getCorrectPassword() {
        return correctPassword;
    }
}

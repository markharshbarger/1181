import java.util.ArrayList;
import java.util.List;

// 13 threads for part 2
// static volitile boolean to have threads stop once the password is cracked
public class Driver {
    public static void main(String[] args) {
		int numThreads = 13;
		String zipFileLocation = "protected5.zip";
        long startTime = System.currentTimeMillis(); // start clock

		PasswordManager manager = new PasswordManager(generatePasswords(5));
		List<Thread> threadList = new ArrayList<>();
		for (int i = 0; i < numThreads; i++) {
			threadList.add(new PasswordWorker(i, zipFileLocation, manager));
		}

		for (Thread thread : threadList) {
			thread.start();
		}

		for (Thread thread : threadList) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Correct password: " + manager.getCorrectPassword());
		long endTime = System.currentTimeMillis(); // end clock
		System.out.println(endTime - startTime + " ms to crack password");
    }

	public static ArrayList<String> generatePasswords(int passwordLength) {
		ArrayList<String> passwords = new ArrayList<>();
		if (passwordLength == 1) {
			for (char c = 'a'; c <= 'z'; c++) {
				passwords.add(Character.toString(c));
			}
			return passwords;
		}

		ArrayList<String> pastPasswords = generatePasswords(passwordLength - 1);
		for (char c = 'a'; c <= 'z'; c++) {
			for (String password : pastPasswords) {
				passwords.add(Character.toString(c) + password);
			}
		}
		return passwords;
	}
}

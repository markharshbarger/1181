import java.util.ArrayList;
import java.util.List;

// time for 3 threads to break 5 char password: 82688 ms
// time for 4 threads to break 5 char password: 74658 ms
// on ryzen 7 5700x processor (Desktop)

// 273674 ms
// 63521 ms to crack password
public class Driver {
	final static int NUMBER_OF_THREADS = 3;
	final static int PASSWORD_LENGTH = 5;
	final static String ZIP_FILE_LOCATION = "protected5.zip";
	static PasswordManager manager;
	static ArrayList<String> passwords;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis(); // start clock
		manager = new PasswordManager();
		passwords = new ArrayList<>(generatePasswords(PASSWORD_LENGTH));

		crackPassword();

		System.out.println("Correct password: " + manager.getCorrectPassword());
		long endTime = System.currentTimeMillis(); // end clock
		System.out.println(endTime - startTime + " ms to crack password");
    }

	private static void crackPassword() {
		List<Thread> threadList = new ArrayList<>();
		int passwordsPerThread = (passwords.size() / NUMBER_OF_THREADS);
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			int start = i * passwordsPerThread;
			int end = start + passwordsPerThread;
			if (i + 1 == NUMBER_OF_THREADS) {
				end = passwords.size() - 1; // if work couldn't be evenly divided make sure last thread gets extra passwords
			}
			threadList.add(new PasswordWorker(i, ZIP_FILE_LOCATION, manager, passwords, start, end));
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
	}

	public static ArrayList<String> generatePasswords(int passwordLength) {
		ArrayList<String> passwords = new ArrayList<>();
		if (passwordLength < 1) {
			return passwords; // if password length is 0 then return empty list
		} else if (passwordLength == 1) {
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
import java.util.ArrayList;
import java.util.List;

// time for 3 threads to break 5 char password: 56921 ms
// time for 4 threads to break 5 char password: 44783 ms
// on ryzen 7 5700x processor (Desktop)
// Name: Mark Harshbarger
// WSU email: harshbarger.26@wright.ed
public class Driver {
	final static int numThreads = 13;
	final static int PASSWORD_LENGTH = 5; // change for desired password length (only contains lowercase letters)
	final static String ZIP_FILE_LOCATION = "protected5.zip";
	static PasswordManager manager;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis(); // start clock
		manager = new PasswordManager(generatePasswords(PASSWORD_LENGTH));

		System.out.println("Trying passwords...");
		crackPassword();

		System.out.println("Correct password: " + manager.getCorrectPassword());
		long endTime = System.currentTimeMillis(); // end clock
		System.out.println(endTime - startTime + " ms to crack password");
    }

	/**
	 * Cracks a password by starting multiple threads to start trying different passwords.
	 * Uses a PasswordManager to keep track of passwords.
	 */
	private static void crackPassword() {
		List<Thread> threadList = new ArrayList<>();
		for (int i = 0; i < numThreads; i++) {
			threadList.add(new PasswordWorker(i, ZIP_FILE_LOCATION, manager));
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

	/**
	 * Recursive function used to generate all different combinations of lowercase characters of a certain length.
	 * 
	 * @param passwordLength int - the length that you want all the passwords to be
	 * @return an ArrayList<String> containing the generated passwords
	 */
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
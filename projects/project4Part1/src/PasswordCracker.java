import java.util.ArrayList;
import java.util.List;
import net.lingala.zip4j.core.*;
import net.lingala.zip4j.exception.*;

/**
 * The password you are looking for is exactly three characters long and contains only lowercase letters.
 * No numbers, no symbols.
 */
public class PasswordCracker {
    public static void main(String[] args) {
		String zipFileName = "protected3.zip";

        long startTime = System.currentTimeMillis(); // start clock
		List<String> passwordList = generatePasswords(3);

		for (String password : passwordList) {
			try {
				ZipFile zipFile = new ZipFile(zipFileName);
				zipFile.setPassword(password);
				zipFile.extractAll("contents");
				System.out.println("Correct password: " + password);
				System.out.print("It took ");
				long endTime = System.currentTimeMillis(); // end clock
				System.out.println(endTime - startTime + " ms to crack password");
				break;
			} catch (ZipException ze) {
				continue;
			} catch (Exception e){
				e.printStackTrace();
			}
		}

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

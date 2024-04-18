import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.lingala.zip4j.core.*;
import net.lingala.zip4j.exception.*;

/**
 * Class represents an object that tries to crack a password. It extends thread in order for the ability to have 
 * simultaneous password cracking. Uses PasswordManager to keep track of the passwords and other threads cracking the password.
 */
public class PasswordWorker extends Thread {
    private String copyOfZip;
    private String mainDestinationPath = "contents";
    private String contentPath;
    private PasswordManager passwordManager;
    private ZipFile zipFile = null;

    /**
     * Constructor of PasswordWorker, sets an ID, sets the file location of the zip file to be cracked,
     * and sets the password manager to be used for getting the passwords to try
     * 
     * @param threadID int - the ID of the current thread
     * @param fileLocationOfZip String - the file path of the zip file to be cracked
     * @param passwordManager PasswordManager - the password manager object that keeps track of the passwords to try and other threads
     */
    public PasswordWorker(int threadID, String fileLocationOfZip, PasswordManager passwordManager) {
        this.passwordManager = passwordManager;
        copyOfZip = threadID + fileLocationOfZip;
        contentPath = mainDestinationPath + "-" + threadID;

        try {
            Files.copy(Path.of(fileLocationOfZip), Path.of(copyOfZip));
        } catch (IOException e) {
            e.printStackTrace();
        }

		try {
            zipFile = new ZipFile(copyOfZip);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    /**
     * Will try different passwords until it tries the correct one or another thread using the same passwordManager
     * finds the correct password
     */
    @Override
    public void run() {
        while (passwordManager.isPasswordFound() == false) {
            String password = passwordManager.getPasswordGuess();
            if (password == null) {
                break;
            }
            System.out.println("Trying password " + password);
            try {
				zipFile.setPassword(password);
				zipFile.extractAll(contentPath);
			} catch (ZipException ze) {
				continue;
			} catch (Exception e){
				e.printStackTrace();
            }
            passwordManager.setPasswordFound(true);
            passwordManager.setPassword(password);
            try {
				ZipFile zipFile = new ZipFile(copyOfZip);
				zipFile.setPassword(password);
				zipFile.extractAll(mainDestinationPath);
			} catch (ZipException ze) {
				continue;
			} catch (Exception e){
				e.printStackTrace();
            }
        }
        
        deleteFiles(); // once password is found delete remanents
    }

    /**
     * Deletes the file that the thread used to crack the password. These files include the copy of the zip file and 
     * the folder along with the .txt document and anything else inside the folder
     */
    private void deleteFiles() {
        try {
            Files.deleteIfExists(Path.of(copyOfZip));
            Stream<Path> walk = Files.walk(Paths.get(contentPath)); // https://mkyong.com/java/java-files-walk-examples/
            List<Path> result = walk.filter(Files::isRegularFile).collect(Collectors.toList());
            walk.close();
            result.forEach(y -> {
                try {
                    Files.deleteIfExists(y);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            Files.deleteIfExists(Path.of(contentPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

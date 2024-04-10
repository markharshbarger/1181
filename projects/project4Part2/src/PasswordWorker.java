import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.lingala.zip4j.core.*;
import net.lingala.zip4j.exception.*;

//Files.copy(Path.of(srcFilename), Path.of(destinationFilename));
//Files.delete(Path.of(filename));
public class PasswordWorker extends Thread {
    private int threadID;
    private String copyOfZip;
    private String mainDestinationPath = "contents";
    private String contentPath = mainDestinationPath + "-" + threadID;
    private PasswordManager passwordManager;
    private ZipFile zipFile = null;

    public PasswordWorker(int threadID, String fileLocationOfZip, PasswordManager passwordManager) {
        this.threadID = threadID;
        this.passwordManager = passwordManager;
        copyOfZip = threadID + fileLocationOfZip;

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

    @Override
    public void run() {
        while (passwordManager.isPasswordFound() == false) {
            String password = passwordManager.getPasswordGuess();
            try {
				zipFile.setPassword(password);
				zipFile.extractAll(contentPath);
			} catch (ZipException ze) {
				continue;
			} catch (Exception e){
				e.printStackTrace();
            }
			System.out.println("Correct password: " + password);
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
        // once password is found delete remanents
        try {
            Files.delete(Path.of(copyOfZip));
            Files.delete(Path.of(contentPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

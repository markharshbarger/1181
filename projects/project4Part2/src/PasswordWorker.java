import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.lingala.zip4j.core.*;
import net.lingala.zip4j.exception.*;

//Files.copy(Path.of(srcFilename), Path.of(destinationFilename));
//Files.delete(Path.of(filename));
public class PasswordWorker extends Thread {
    private String copyOfZip;
    private String mainDestinationPath = "contents";
    private String contentPath;
    private PasswordManager passwordManager;
    private ZipFile zipFile = null;

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

    @Override
    public void run() {
        while (passwordManager.isPasswordFound() == false) {
            String password = passwordManager.getPasswordGuess();
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
        // once password is found delete remanents
        deleteFiles();
    }

    // https://mkyong.com/java/java-files-walk-examples/
    private void deleteFiles() {
        try {
            Files.delete(Path.of(copyOfZip));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try (Stream<Path> walk = Files.walk(Paths.get(contentPath))){
            List<Path> result;
            result = walk.filter(Files::isRegularFile).collect(Collectors.toList());
            result.forEach(y -> {
                try {
                    Files.deleteIfExists(y);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            Files.deleteIfExists(Path.of(contentPath + "/message.txt"));
            Files.deleteIfExists(Path.of(contentPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

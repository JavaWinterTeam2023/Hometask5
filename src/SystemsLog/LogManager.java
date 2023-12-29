package SystemsLog;

import java.nio.file.*;
import java.io.IOException;
import java.util.zip.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class LogManager {

   
    public void createLogFile(String filePathString) throws IOException {
    	Path file = Paths.get(filePathString);
    	
    	Files.createDirectories(file.getParent());
    	
    	try {
			Files.createFile(file);
		} catch(FileAlreadyExistsException e) {
			System.out.format("File name: %s" +
			       " already exists%n", filePathString);
		} catch (IOException x) {
		    // Some other sort of failure, such as permissions.
		    System.err.format("Error: Failed to create file %s%n", filePathString);
		}
    }

    public void moveLogFile(String sourcePathString, String targetPathString) throws IOException {
        Path sourcePath = Paths.get(sourcePathString);
        Path targetPath = Paths.get(targetPathString);
        Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
    }

    public void deleteLogFile(String filePathString) throws IOException {
        Path filePath = Paths.get(filePathString);
        Files.delete(filePath);
    }
    
    public void writeLogFile(Path file, String message) {
		byte[] byteMessage = message.getBytes();
		try {
			Files.write(file, byteMessage, StandardOpenOption.APPEND);
		} catch(IOException e) {
			System.err.println(e.getMessage());
		}
	}
	

    public void archiveLogFile(String sourcePathString, String archivePathString) throws IOException {
        Path sourcePath = Paths.get(sourcePathString);
        Path archivePath = Paths.get(archivePathString);

       
        if (Files.notExists(archivePath.getParent())) {
            Files.createDirectories(archivePath.getParent());
        }

        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(archivePath))) {

            zos.putNextEntry(new ZipEntry(sourcePath.getFileName().toString()));

            byte[] bytes = Files.readAllBytes(sourcePath);
            zos.write(bytes, 0, bytes.length);

            zos.closeEntry();
        }
    }
    
}
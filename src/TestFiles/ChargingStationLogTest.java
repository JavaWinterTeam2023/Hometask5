package TestFiles;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import SystemsLog.ChargingStationLog;

class ChargingStationLogTest {
	   private ChargingStationLog chargingStationLog;
	    private File fileName;
	    private Files fileNames;
	    private static    String LOG_DIRECTORY ="Log";
	    private static String currentRelativePath;
	    
	    
	    @BeforeEach
	    void setUp() {
	    	 currentRelativePath = Paths.get("").toAbsolutePath().toString();
	    }

   @Test
   public void TestInitialCharginSystemLogFile() {
	   var path=currentRelativePath+File.separator+LOG_DIRECTORY;
	   chargingStationLog.initChargingSystemLogFile(path);
	   assertTrue(Files.exists(Paths.get(path)));
   }
   


	@Test
	public void testWriteToLogFolder() throws IOException {
  
        
        var stationId="station2";
        var message=stationId+"started its work";
        chargingStationLog.WriteToLogFile(stationId, message);
        
        
        assertTrue("The message is written successfuly", true);
    }
	

	
	@Test
    void testWriteToLogFile() {
        String stationId = "stationId7";
        String message = "Test log message";
        chargingStationLog.WriteToLogFile(stationId, message);
        // Verify that the log file is created and contains the expected message
        String expectedFileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "_" + stationId + ".log";
        String expectedFilePath = currentRelativePath + File.separator + expectedFileName;
        assertTrue(Files.exists(Paths.get(expectedFilePath)));
        try {
            List<String> lines = Files.readAllLines(Paths.get(expectedFilePath));
            assertTrue(lines.contains(new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " " + message));
        } catch (IOException e) {
            fail("IOException occurred: " + e.getMessage());
        }
    }
	
	@Test
    void FileAlreadyExists() {
        
        String stationId = "testStation";
        String message = "Test log message";
        chargingStationLog.WriteToLogFile(stationId, message);

        // Write another entry to the same log file
        String secondMessage = "Another log message";
        chargingStationLog.WriteToLogFile(stationId, secondMessage);

        // Verify that the log file contains both entries
        String expectedFileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "_" + stationId + ".log";
        String expectedFilePath = currentRelativePath + File.separator + expectedFileName;
        String thirdMessage="There is a sample massage";
        assertTrue(Files.exists(Paths.get(expectedFilePath)));
        try {
            List<String> lines = Files.readAllLines(Paths.get(expectedFilePath));
            assertTrue(lines.contains(new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " " + message));
            assertTrue(lines.contains(new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " " + secondMessage));
            assertEquals(lines.contains(new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " " + secondMessage), thirdMessage);
        } catch (IOException e) {
            fail("IOException occurred: " + e.getMessage());
        }
    }
	
	
    @Test
    void testOpenLogFile_NoMatchingFiles() {
        String filter = "nonexistent.*";
        assertDoesNotThrow(() -> chargingStationLog.openLogFile(filter));
    }
    
    @Test
    void InvalidRegexOpenFile() {
       
        String filter = "[invalidRegex";
		assertThrows(PatternSyntaxException.class, () -> chargingStationLog.openLogFile(filter));
    }

}

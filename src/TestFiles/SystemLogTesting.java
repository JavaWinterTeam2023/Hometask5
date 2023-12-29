package TestFiles;

//import static org.junit.jupiter.api.Assertions.*;
import junit.framework.Assert;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import SystemsLog.*;
import SystemsLog.SystemLog.Filter;
import SystemsLog.SystemLog.LogType;

class SystemLogTesting {

	private SystemLog systemLog;
	private String logDirectory; 

    @BeforeEach
    void setUp() {
        systemLog = new SystemLog();
        logDirectory = systemLog.getLogDirectory();
    }
    
    @Test
    void initSystem_ShouldCreateLogFile() throws IOException {
        systemLog.initSystem();
        String expectedFileName = logDirectory + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd")) + "_System.log";
        File logFile = new File(expectedFileName);
        assertTrue(logFile.exists());
        logFile.deleteOnExit();
    }
    
    @Test
    void infoLogTest() throws IOException {
        String testMessage = "Test info message";
        systemLog.infoLog(testMessage);
        String expectedLogMessage = systemLog.formatSystemLog(LocalDateTime.now(), LogType.INFO, testMessage);
        String logFileName = logDirectory + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd")) + "_System.log";
        Path logFilePath = Paths.get(logFileName);
        String logContent = Files.readString(logFilePath);
        assertTrue(logContent.contains(expectedLogMessage));
    }
	
	@SuppressWarnings("deprecation")
	@Test
	void testMonthFilter () {
		//SystemLog sysLog = new SystemLog();
		File[] fileLists = systemLog.filterFile( "2", Filter.MONTH);
		Assert.assertNull(fileLists);
	}

	@Test
	void testYearFilter () {
		//SystemLog sysLog = new SystemLog();
		System.out.println("\nTest filtering system log file by YEAR:");
		File[] fileLists = systemLog.filterFile( "2022", Filter.YEAR);
		Assert.assertNotNull(fileLists);
	}
}

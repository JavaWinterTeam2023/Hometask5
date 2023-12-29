package TestFiles;
import static org.junit.Assert.*;
import org.junit.Test;

import SystemsLog.EnergyManagementLog;

import java.io.File;


public class EnergySourceLoggerTest {

	private EnergyManagementLog EnrgySrcLog;

    private static final String LOG_DIRECTORY = "log/";
	
	



	    @Test
	    public void testValidEquipmentName() {
	        assertTrue(EnrgySrcLog.isValidEquipmentName("Solar_Panel"));
	        assertTrue(EnrgySrcLog.isValidEquipmentName("Wind_Turbine_1"));
	    }

	    @Test
	    public void testInvalidEquipmentName() {
	        assertFalse(EnrgySrcLog.isValidEquipmentName("Solar Panel")); // contains space
	        assertFalse(EnrgySrcLog.isValidEquipmentName("12345")); // contains only digits
	        assertFalse(EnrgySrcLog.isValidEquipmentName("equipment@123")); // contains special characters
	    }

	    @Test
	    public void testWriteLogToFile() {

	        String equipment = "Test_Equipment";
	        String activity = "Test_Activity";
	        EnrgySrcLog.initializeLogsDirectory(); // Ensure the logs directory exists

	        // Perform the action
	        EnrgySrcLog.logEquipmentActivity(equipment, activity);

	     
	        File logFile = new File(LOG_DIRECTORY, "Test_Equipment.log");

	        assertTrue("Log file should exist", logFile.exists());


	        assertTrue("Log file should be deletable", logFile.delete());
	    }

	    @Test
	    public void testInitializeLogsDirectory() {
	    	EnrgySrcLog.initializeLogsDirectory();
	        File logsDirectory = new File(LOG_DIRECTORY);
	        assertTrue("Logs directory should be created", logsDirectory.exists());
	    }

	    @Test
	    public void testLogEquipmentActivity() {

	        String equipment = "Test_Equipment";
	        String activity = "Test_Activity";
	        EnrgySrcLog.initializeLogsDirectory(); // Ensure the logs directory exists

	        try {
	        	EnrgySrcLog.logEquipmentActivity(equipment, activity);
	            File logFile = new File(LOG_DIRECTORY, "Test_Equipment.log");
	            assertTrue("Log file should exist", logFile.exists());

	            assertTrue("Log file should be deletable", logFile.delete());
	        } catch (Exception e) {
	            fail("Logging equipment activity should not throw an exception");
	        }
	    }

	    @Test
	    public void testOpenRequestedLogFile() {

	        String userInput = "Test_Equipment.log";
	        assertTrue(EnrgySrcLog.isValidInput(userInput));
	    }

}


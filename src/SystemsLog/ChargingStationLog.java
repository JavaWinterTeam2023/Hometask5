package SystemsLog;

import java.awt.Desktop;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.*;


public class ChargingStationLog {
    private static    String LOG_DIRECTORY ="Log";
    private static final Logger logger = Logger.getLogger(ChargingStationLog.class.getName());
    private static  String ARCHIVE_DIRECTORY = "archive/";
    //private static  String CHARGING_SYSTEM_LOG_FILE = LOG_DIRECTORY + "charging_station.log";
    private static   String ARCHIVED_CHARGING_SYSTEM_LOG_FILE = ARCHIVE_DIRECTORY + "charging_station_%s.zip"; // %s will be replaced with a timestamp
    private LogManager logManager;

    
    public ChargingStationLog() {
        this.logManager = new LogManager();
    }

    
    public void initChargingSystemLogFile(String TotalPath) {
        try { 
            logManager.createLogFile(TotalPath);
           // Files.createFile(LOG_DIRECTORY+filename);
        } catch (IOException e) {
            System.err.println("Error initializing charging system log file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void WriteToLogFile(String stationId,String message) {
    	 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	 Date now = new Date();
         String date = dateFormat.format(now);
         String filename = String.format("%s_%s.log", date, stationId);
         String currentRelativePath = Paths.get("").toAbsolutePath().toString();
         String LOG_DIRECTORY2 = currentRelativePath+File.separator+LOG_DIRECTORY;
         File newFolder = new File(LOG_DIRECTORY2);
         if (!newFolder.exists()) {
             boolean wasSuccessful = newFolder.mkdir();
         }
         String TotalPath=LOG_DIRECTORY2 +File.separator+ filename;
         Path path = Paths.get(TotalPath);
         try {
             if (!Files.exists(path)) {
            	 initChargingSystemLogFile(TotalPath);
             }

             String logEntry = date + " " + message + "\n";
             try (FileWriter writer = new FileWriter(LOG_DIRECTORY2+File.separator + filename, true)) { // true to append, false to overwrite
                 writer.write(logEntry + System.lineSeparator()); // Write single entry with new line
             }

             System.out.println("Log for " + stationId + " at " + date + " has been updated.");
         } catch (IOException e) {
             e.printStackTrace();
         }
         
    }
    
    
    public static void openLogFile(String filter) {
    	 String regex = "[a-zA-Z0-9_-]+.*";
        Pattern pattern = Pattern.compile(filter,Pattern.CASE_INSENSITIVE);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(LOG_DIRECTORY))) {
            for (Path entry : stream) {
            	String p = entry.getFileName().toString();
                Matcher matcher = pattern.matcher(p);
                if (matcher.matches()) {
                    Desktop.getDesktop().open(entry.toFile());
                    System.out.println("Opened: " + entry);
                    return;
                }
            }
            System.out.println("No matching logs found for pattern: " + filter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void archiveChargingSystemLogFile(String filename) {
        String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
        String archiveFileName = String.format(ARCHIVED_CHARGING_SYSTEM_LOG_FILE, timestamp);

        try {
            logManager.archiveLogFile(LOG_DIRECTORY, archiveFileName);
            System.out.println("Charging system log archived: " + archiveFileName);
        } catch (IOException e) {
            System.err.println("Error archiving charging system log file: " + e.getMessage());
            e.printStackTrace();
        }
    }

  
    public void deleteChargingSystemLogFile(String filename) {
        try {
            logManager.deleteLogFile(LOG_DIRECTORY+filename);
            System.out.println("Charging system log file deleted");
        } catch (IOException e) {
            System.err.println("Error deleting charging system log file: " + e.getMessage());
            e.printStackTrace();
        }
    }

}

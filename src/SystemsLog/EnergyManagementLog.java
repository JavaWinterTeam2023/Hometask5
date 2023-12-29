package SystemsLog;

import java.io.IOException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnergyManagementLog {

    private static final String LOG_DIRECTORY = "log/";

    private static final String ARCHIVE_DIRECTORY = "archive/";
    private static final String ENERGY_MANAGEMENT_LOG_FILE = LOG_DIRECTORY + "energy_management.log";
    private static final String ARCHIVED_ENERGY_MANAGEMENT_LOG_FILE = ARCHIVE_DIRECTORY + "energy_management_%s.zip"; // %s will be replaced with a timestamp
    private LogManager logManager;

    public EnergyManagementLog() {
        this.logManager = new LogManager();
    }

    public void initEnergyManagementLogFile() {
        try {
            logManager.createLogFile(ENERGY_MANAGEMENT_LOG_FILE);
        } catch (IOException e) {
            System.err.println("Error initializing energy management log file: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void archiveEnergyManagementLogFile() {
        String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
        String archiveFileName = String.format(ARCHIVED_ENERGY_MANAGEMENT_LOG_FILE, timestamp);

        try {
            logManager.archiveLogFile(ENERGY_MANAGEMENT_LOG_FILE, archiveFileName);
            System.out.println("Energy management log archived: " + archiveFileName);
        } catch (IOException e) {
            System.err.println("Error archiving energy management log file: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void deleteEnergyManagementLogFile() {
        try {
            logManager.deleteLogFile(ENERGY_MANAGEMENT_LOG_FILE);
            System.out.println("Energy management log file deleted");
        } catch (IOException e) {
            System.err.println("Error deleting energy management log file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void logEquipmentActivity(String equipment, String activity) {
        log(equipment, activity);
    }

    public void log(String equipment, String activity) {

        if (isValidEquipmentName(equipment)) {
            String logEntry = String.format("[%s] [%s] [%s] %n",
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), equipment, activity);

            String dailyLogFileName = String.format("%s_%s.log", equipment, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            writeLogToFile(dailyLogFileName, logEntry);

            String equipmentLogFileName = String.format("%s.log", equipment);
            writeLogToFile(equipmentLogFileName, logEntry);

            String systemLogFileName = "system.log";
            writeLogToFile(systemLogFileName, logEntry);
        } else {
            System.out.println("Invalid equipment name: " + equipment);
        }
    }

    public void writeLogToFile(String fileName, String logEntry) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(LOG_DIRECTORY, fileName), true))) {
            writer.println(logEntry);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    public void initializeLogsDirectory() {
        File logsDirectory = new File(LOG_DIRECTORY);
        if (!logsDirectory.exists() && logsDirectory.mkdir()) {
            System.out.println("Logs directory created.");
        }
    }

    public boolean isValidEquipmentName(String equipment) {

        String regex = "[a-zA-Z0-9_-]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(equipment);
        return matcher.matches();
    }
    public void openRequestedLogFile() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter equipment name or date (YYYY-MM-DD): ");
        String userInput = scanner.nextLine();


        if (isValidInput(userInput)) {
            String fileName = userInput.endsWith(".log") ? userInput : userInput + ".log";
            File requestedLogFile = new File(LOG_DIRECTORY, fileName);

            if (requestedLogFile.exists()) {
                System.out.println("Opening log file: " + requestedLogFile.getAbsolutePath());

            } else {
                System.out.println("Log file not found for input: " + userInput);
            }
        } else {
            System.out.println("Invalid input. Please enter a valid equipment name or date.");
        }

        scanner.close();
    }

    public  boolean isValidInput(String userInput) {
		return true;
    // Additional methods specific to energy management can be added as needed
    }
}
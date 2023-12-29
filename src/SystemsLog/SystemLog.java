package SystemsLog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum LogType {
	INFO,
	WARNING,
	ERROR
}

public class SystemLog extends LogManager{
	private static String LOG_DIRECTORY ="Log/";
	private String file;
	
	public enum Filter {
		DATE,
		MONTH,
		YEAR,
		EQUIPMENT
	}

	public SystemLog() {
		super();
		this.initSystem();
	}
	
	public void initSystem () {
		try {
			LocalDate logTime = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd");
			this.file = String.format(LOG_DIRECTORY+"%s_System.log", logTime.format(formatter));
			this.createLogFile(file);
			this.infoLog("System start successfully");
		} catch (IOException e) {
			System.out.println("Error: Failed to init system"); 
			this.errorLog("Failed to init system");
		}
	}
	
	private void updateLog(String message, LogType type) {
		LocalDateTime localTime = LocalDateTime.now();
		String logMessage = formatSystemLog(localTime, type, message);
		Path path = Paths.get(this.file);
		this.writeLogFile(path, logMessage);
	}
	
	public void infoLog(String message) {
		LogType type = LogType.INFO;
		updateLog(message, type);
	}
	
	public void warningLog(String message) {
		LogType type = LogType.WARNING;
		updateLog(message, type);
	}
	
	public void errorLog(String message) {
		LogType type = LogType.ERROR;
		updateLog(message, type);
	}
	
	public String formatSystemLog(LocalDateTime logTime, LogType type, String message) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return String.format("[%s][%s]\t %s\n", logTime.format(formatter), type, message);
	}
	
    public void filterFile(String inputFilter, Filter filter) {
    	
    	String regex = null;
		
    	switch(filter) {
    		case DATE:
    			Scanner sc = new Scanner(System.in);
    			
    			System.out.println("Enter month and year you want to filter: ");
    			int month = sc.nextInt();
    			int year = sc.nextInt();
    			
    			int inputInt = Integer.valueOf(inputFilter);
    			if ( inputInt < 10) {
    				regex = "[0]" + inputInt;
    			} else 
    				regex = inputFilter;
    			
    			inputInt = Integer.valueOf(month);
    			if ( inputInt < 10) {
    				regex = inputInt + "_" + regex;
    			} else 
    				regex = month + "_" + regex;
    			
    			regex = year + "_" + regex;
    			
    			break;
    		case MONTH:
    			Scanner sc1 = new Scanner(System.in);
    			
    			System.out.println("Enter year you want to filter: ");
    			int year1 = sc1.nextInt();
    			
    			int inputInt1 = Integer.valueOf(inputFilter);
    			if ( inputInt1 < 10) {
    				regex = "[0]" + inputInt1;
    			} else 
    				regex = inputFilter;
    			
    			regex = year1 + "_" + regex;
    			
    			break;
    		case YEAR: 
    			regex = "^" + inputFilter;
    			break;
    		case EQUIPMENT:
    			regex = ".*" + inputFilter;
    			break;
    	}    		
    	regex = regex + ".*";
    	
    	Pattern pattern = Pattern.compile(regex);
    	
    	File dir = new File(LOG_DIRECTORY);
    	
    	if (!dir.isDirectory()) {
            System.out.println("Invalid directory path.");
            return ;
        }
    	
    	File[] filterFiles = dir.listFiles((dir1, name)->{
    		Matcher matcher = pattern.matcher(name);
    		return matcher.matches();
    	});
    	
    	for (File file : filterFiles) {
    		System.out.println(file.getName());
        	Path path = Paths.get(LOG_DIRECTORY+ file.getName());
            try {
				Files.lines(path).forEach(System.out::println);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
}

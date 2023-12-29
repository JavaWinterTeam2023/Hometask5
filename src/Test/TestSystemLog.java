package Test;

import java.util.Scanner;
import SystemsLog.*;
import SystemsLog.SystemLog.Filter;

public class TestSystemLog {
    public static void main(String[] args) {
        //final int availableChargingPoints = 5;
        //final int totalVehicles = 15;

        //ChargingStation chargingStation = new ChargingStation(availableChargingPoints);

       // for (int i = 1; i <= totalVehicles; i++) {
        	//ChargingVehicleService service = new ChargingVehicleService(chargingStation, i);
          //  new Thread(service).start();

           // try {
                // Brief pause to stagger vehicle arrival times
              //  Thread.sleep(500);
            //} catch (InterruptedException e) {
               // Thread.currentThread().interrupt();
           // }
        //}
    	
    	SystemLog sysLog = new SystemLog(); 
    	sysLog.infoLog("Connecting charging stations...");
    	sysLog.warningLog("Not all charging stations conneted.");
    	sysLog.infoLog("All charging station connected.");
    	sysLog.infoLog("System shutdown.");
    	
    	Scanner scanner = new Scanner(System.in);
		
    	while(true) {
			System.out.println("\nEnter category, regex you want to filter: ");
			String filter = scanner.next();
	    	String regex = scanner.next();
	    	
	    	
	    	Filter filters = Filter.valueOf(filter.toUpperCase());
	    	
	    	sysLog.filterFile(regex, filters);
    	}
    }
}
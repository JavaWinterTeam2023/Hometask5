package SystemSimulation;

import java.io.*;

public class TextDataHandler {
    
    public void simulateTextDataTransfer(String inputFileName, String outputFileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            
            String lineData;
            while (Integer.parseInt(lineData = reader.readLine()) != -1) {
                writer.write(lineData);
                writer.newLine(); // Add new line after writing the line data
            }
            
            System.out.println("Textual data transfer simulation completed between " 
                               + inputFileName + " and " + outputFileName);
            
        } catch (IOException e) {
            System.err.println("IOException occurred during textual data transfer simulation: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

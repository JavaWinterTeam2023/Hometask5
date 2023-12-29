package SystemSimulation;

import java.io.*;

public class BinaryDataHandler {
    
    public void simulateBinaryDataTransfer(String inputFileName, String outputFileName) {
        try (FileInputStream inStream = new FileInputStream(inputFileName);
             FileOutputStream outStream = new FileOutputStream(outputFileName)) {

            int byteData;
            while ((byteData = inStream.read()) != -1) {
                outStream.write(byteData);
            }
            
            System.out.println("Binary data transfer simulation completed between " 
                               + inputFileName + " and " + outputFileName);
            
        } catch (IOException e) {
            System.err.println("IOException occurred during binary data transfer simulation: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
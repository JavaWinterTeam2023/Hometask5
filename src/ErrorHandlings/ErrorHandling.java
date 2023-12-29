package ErrorHandlings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;

public class ErrorHandling {
//Multiple
public void ThrowMultipleExceptions(String errMsg,int num1,int num2) {
	try {
		if(num1!=0 && num2==0) {
			int res=num1/num2;
		}
	}
		catch (ArithmeticException ex) {
			System.err.println(errMsg+ex.getMessage());
		}
		catch (NullPointerException ex) {
			System.err.println(errMsg+ex.getMessage());
		}
		catch (InputMismatchException ex) {
			System.err.println(errMsg+ex.getMessage());
		}
	 catch (Exception e) {
		System.err.println("An unexpeted error has occured"+ e.getMessage());
	}
	finally {
		//the Specific Statement
	}
}


public void process() throws  CustomException{
	try {
		
	} catch (Exception e) {
      LogException(e);
      
throw new CustomException("custom exception message",e);
	                      }
}
 private void LogException(Exception e) {
	 
	System.out.println("Exception occured:" + e.getMessage());

 }

 class CustomException extends Exception {
		
	 public CustomException(String message, Throwable cause) {
	 	super(message, cause);
	 }


}
 
 
 public class ChainedException extends Exception{
	 public ChainedException (String message) {
	  super(message);
	 }
 
 public ChainedException (String message, Throwable cause) {
	  super(message);
	  this.initCause(cause);
	 }
 public void ThrowIOException(String errorMessage, Throwable cause) throws IOException {
	  throw new IOException(errorMessage, cause);
	    }

 public static void ThrowInputMismatchException(String errorMessage) {
     throw new InputMismatchException(errorMessage);
   }


 }
 
 
 
 
 
 
}

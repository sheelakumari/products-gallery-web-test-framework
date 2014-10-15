package org.familysearch.products.gallery.testframework.base.launch.exec;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class AppRunner {
	
	public static final String TEST_CASE_ERROR_MESSAGE = ">>>TEST_CASE_ERROR<<<";
	public static final String APP_DID_NOT_EXIT_ERROR_MESSAGE = TEST_CASE_ERROR_MESSAGE + "  Application did not exit."; 
	public static final String KILLING_APP_ERROR_MESSAGE = TEST_CASE_ERROR_MESSAGE + "  Killing application."; 

	public static final int EXECUTOR_ERROR = 99;
	public static final int APP_KILLED_BY_TESTCASE = 100;
	public static final int APP_DESTROYED_BY_RUNNER = 101;
	
	public static final int NO_TIMEOUT = 0;
	public static final int NO_INTERVAL = 0;
	public static final int DEFAULT_TIMEOUT_CHECK_INTERVAL = 5;
	
	// Support for killing the application after a timeout period, in seconds.
	private int timeoutThreshold = NO_TIMEOUT;
	private int timeoutCheckInterval = NO_INTERVAL;
	private boolean bUseTimeout = false;
	
	
	private boolean bTestCaseErrorsReported = false;
	private int numErrors = 0;
	
			
	public AppRunner() {
		
	}
	
	public static AppRunner getInstance() {
		return new AppRunner();
	}


	private void initTimeoutValues(int timeoutThreshold, int timeoutCheckInterval) {
		this.timeoutThreshold = timeoutThreshold;
		this.timeoutCheckInterval = timeoutCheckInterval;
	
		if ( timeoutThreshold > NO_TIMEOUT ) {
			bUseTimeout = true;
		}
		else {
			bUseTimeout = false;
		}
		
		// Minimum interval should be 1 second.
		if ( timeoutCheckInterval <= NO_INTERVAL ) {
			timeoutCheckInterval = DEFAULT_TIMEOUT_CHECK_INTERVAL;
		}
	}
	
	
	
	
	public void checkLineForErrors(String line) {
		if ( line != null && line.contains(TEST_CASE_ERROR_MESSAGE) ) {
			bTestCaseErrorsReported = true;
			// Note: There could be a race condition here, and that will make the count incorrect.
			numErrors++;
			System.out.println("Error message in line: " + line);
		}
	}
	
	
	public boolean testCaseErrorsWereReported() {
		return bTestCaseErrorsReported;
	}
	
	public int getErrorCount() {
		return numErrors;
	}
	

	
	public int runJavaClass(String className, String[] args, int timeoutThreshold, int timeoutCheckInterval) {
		// className has to be a string because this runner won't always have the class to run in its class path during
		// compile time.

		StringBuilder commandBuilder = new StringBuilder("java -classpath \"" + System.getProperty("java.class.path") + "\" " );
		commandBuilder.append(" " + className );
		if ( args != null ) {
			for( String arg : args ) {
				commandBuilder.append(" " + arg );
			}
		}

		String execCommand = commandBuilder.toString();
		// Now execute the command.
		
		return execCommand( execCommand, timeoutThreshold, timeoutCheckInterval);
	
	}
	
	public int runJavaMethod(String className, String methodName, String[] args, int timeoutThreshold, int timeoutCheckInterval) {
		
		// className has to be a string because this runner won't always have the class to run in its class path during
		// compile time.

		StringBuilder commandBuilder = new StringBuilder("java -classpath \"" + System.getProperty("java.class.path") + "\" " );
		commandBuilder.append(" " + className );
		commandBuilder.append(" " + methodName );
		if ( args != null ) {
			for( String arg : args ) {
				commandBuilder.append(" " + arg );
			}
		}

		String execCommand = commandBuilder.toString();
		// Now execute the command.
		
		return execCommand( execCommand, timeoutThreshold, timeoutCheckInterval );
				
	}
	
	
//	public int execCommand(String command) {
//		return execCommand(command, NO_TIMEOUT, NO_INTERVAL);
//	}
	
	
	public int execCommand(String command, int timeoutThreshold, int timeoutCheckInterval) {
		initTimeoutValues(timeoutThreshold, timeoutCheckInterval);	
		
		int exitValue = EXECUTOR_ERROR;  // Not setting to zero, which mean no problem.

		// Add process var at this scope, in case it is to be examined later.
		Process appProcess = null;
		try {
			
			System.out.println("Executing the command: " + command + "\n\n");		
			appProcess = Runtime.getRuntime().exec(command);
						
			InputStream inStream = appProcess.getErrorStream();
			InputStream errStream = appProcess.getInputStream();
			//OutputStream outStream = appProcess.getOutputStream();

			(new InputStreamWatcher(this, inStream)).start();
			(new ErrorStreamWatcher(this, errStream)).start();

			if ( bUseTimeout ) {
				
				// TODO: Should really look at the clock.
				
				int secondsWaited = 0;
				
				boolean bProcessCompleted = false;
				while(secondsWaited < timeoutThreshold) {
					try {
						
						try {
							Thread.sleep(timeoutCheckInterval * 1000);
						} catch (InterruptedException ex) {
							// ignore it;
						}
						secondsWaited += timeoutCheckInterval;
						
						
						// An exception will be thrown if the process isn't completed.
						exitValue = appProcess.exitValue();
						bProcessCompleted = true;
						break;
						
					}
					catch (IllegalThreadStateException ex) {
						//TODO: Remove this:
						System.out.println(ex);
						// Ignore it, the process hasn't stopped yet.
					}

				}
				// The process may have ended normally, or the process might still be running.
				// If the process is running, force it to stop.
				if ( !bProcessCompleted ) {
					appProcess.destroy();
					exitValue = APP_DESTROYED_BY_RUNNER;
				}
								
			}
			else {
				// Don't poll.  Just wait for the application to complete.
				exitValue = appProcess.waitFor();
			}
			
			System.out.println("The app process exit value is: " + exitValue);
			
			// TODO: Determine what value should really be returned.
			// TODO: Should also signal somehow if the application had to be killed.
			if ( exitValue != 100 && testCaseErrorsWereReported() ) {
				exitValue = 200;
			}
			

		} catch (IOException ex) {
			// TODO: Should the exit value change here?
			ex.printStackTrace();
		} catch (InterruptedException ex) {
			// TODO: Should the exit value change here?
			ex.printStackTrace();
		}

		return exitValue;
	}

	//
	// These static methods are used by the application to signal that an error
	// occurred.  tc stands for test case.
	// 
	
	public static void tcSignalError() {
		System.out.println(TEST_CASE_ERROR_MESSAGE);
	}
		

	public static void tcSignalAppDidNotExit() {
		System.out.println(APP_DID_NOT_EXIT_ERROR_MESSAGE);
	}

	public static void tcSignalKillingApp() {
		System.out.println(KILLING_APP_ERROR_MESSAGE);
	}
	

	public static void main(String[] args) {
		// TODO: Remove this?
		//System.out.println("Main doesn't do anything right now.");
		
		// Try some reflection.
			
	}

}

class InputStreamWatcher extends Thread {
	AppRunner appRunner;
	BufferedReader inReader;

	InputStreamWatcher(AppRunner appRunner, InputStream inStream) {
		this.appRunner = appRunner;
		inReader = new BufferedReader( new InputStreamReader( inStream ));		
	}
	
	
	public void run() {

		try {

			String line = inReader.readLine();
			while (line != null) {
				System.out.println(line);
				appRunner.checkLineForErrors(line);
				line = inReader.readLine();
			}
			
		} catch (IOException ex) {
			System.out.println("\nIOException in Input Stream Watcher.");
			ex.printStackTrace();
		}
	}

}


class ErrorStreamWatcher extends Thread {
	AppRunner appRunner;
	BufferedReader inReader;

	ErrorStreamWatcher(AppRunner appRunner, InputStream errStream) {
		this.appRunner = appRunner;
		inReader = new BufferedReader( new InputStreamReader( errStream ));
	}

	
	public void run() {

		try {

			String line = inReader.readLine();
			while (line != null) {
				System.out.println(line);
				appRunner.checkLineForErrors(line);
				line = inReader.readLine();
			}
			
		} catch (IOException ex) {
			System.out.println("\nIOException in Error Stream Watcher.");
			ex.printStackTrace();
		}
		// TODO: Should I do a finally/close
	}

	
}


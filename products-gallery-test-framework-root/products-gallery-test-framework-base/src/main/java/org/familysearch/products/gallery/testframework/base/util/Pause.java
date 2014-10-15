package org.familysearch.products.gallery.testframework.base.util;

public class Pause {

	private static boolean bThinkPauseEnabled = true;
	private static int thinkPauseMultiplier = 1;
	
	
	
	public static void pause(int seconds) {
		try {
			Thread.sleep( seconds * 1000 );		
		}
		catch (InterruptedException ex) {
			// Ignore it.
		}
	}
	
	public static void pauseMillis(int milliSeconds) {
		try {
			Thread.sleep( milliSeconds );		
		}
		catch (InterruptedException ex) {
			// Ignore it.
		}
	}


	
	
	public static void thinkPause(int seconds) {
		
		if ( bThinkPauseEnabled ) {		
		
			try {
				Thread.sleep( seconds * 1000 * thinkPauseMultiplier );		
			}
			catch (InterruptedException ex) {
				// Ignore it.
			}
		}
	}
	
	
	public static void thinkPauseMillis(int milliSeconds) {
		
		if ( bThinkPauseEnabled ) {		
		
			try {
				Thread.sleep( milliSeconds * thinkPauseMultiplier );		
			}
			catch (InterruptedException ex) {
				// Ignore it.
			}
		}
	}
	
	private static void enableThinkPause() {
		bThinkPauseEnabled = true;
	}
	
	private static void disableThinkPause() {
		bThinkPauseEnabled = false;
	}
	
	private static boolean isThinkPauseEnabled() {
		return bThinkPauseEnabled;
	}
	
	private static void setThinkPauseMultiplier(int multiplier) {
		thinkPauseMultiplier = multiplier;
	}
	
	private static int getThinkPauseMultiplier() {
		return thinkPauseMultiplier;
	}
}

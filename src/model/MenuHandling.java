package model;

import java.util.Scanner;


public class MenuHandling {
	
	//validates and handles input for menu selection (Not counting searches for strings)
	public static int menuSelection(Scanner userInput, int menuLength) {
		
		boolean exitCheck = false;
		String selection = null;
		
		
		do {
			//Reads user's next input, terminates on \n
			selection = userInput.nextLine();
			
			//Checks for valid input.
			exitCheck = ErrorHandling.inputValidator(selection, menuLength);
			
		} while(exitCheck == false);
		
		
		return Integer.parseInt(selection);
		
	}

	
}

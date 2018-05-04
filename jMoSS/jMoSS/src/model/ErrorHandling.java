package model;

public class ErrorHandling {
	
	public static boolean inputValidator(String inputText, int switchLimit) {
		
		try {
			if(Integer.parseInt(inputText) >= 0 && Integer.parseInt(inputText) <= switchLimit) {
				return true;	
			}
			
			else {
				System.out.println("The input you've given, is outside the boundaries of the menu...");
				System.out.println("Please try again");
				return false;
			}
			
			
		}
		
		catch(NumberFormatException e) {
			System.out.println("The input you've given is not a number...");
			System.out.println("Please try again");
			return false;
		}

	}
	
}

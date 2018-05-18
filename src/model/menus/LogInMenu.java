package model.menus;

import java.util.Scanner;

import interfaces.MenuInterface;
import model.records.Clerk;
import model.jMossData;

public class LogInMenu implements MenuInterface {
	
	Scanner userInput = null;
	//Stores current user
	Clerk currentUser = null;
	boolean validUser = false;
	boolean running = true;
	
	//Returns current user
	public Clerk getCurrentUser() {
		return currentUser;
	}
	
	public boolean getValidUser() {
		return validUser;
	}
	
	public boolean getRunning() {
		return running;
	}
	
	//Sets the scanner
	public void setScanner(Scanner userInput) {
		this.userInput = userInput;
	}
	
	//Constructor
	public LogInMenu(Scanner userInput) {
		setScanner(userInput);
	}
	
	//Menu display
	@Override
	public void displayMenu() {
		System.out.println("------------------------------------------------------------");
		System.out.println("");
		System.out.println("             JAVA BASED MOVIE SEARCH SYSTEM");
		System.out.println("                  By ABC Cineplex Corp");
		System.out.println("");
		System.out.println("------------------------------------------------------------");
		System.out.println("");
	}
	
	//Handles menu
	@SuppressWarnings("resource")
	@Override
	public void handleMenu() {
		Boolean validUser = false;
		do {
			//Print menu
			displayMenu();
			
			System.out.println("Please enter your username (Or enter 0 to exit): ");
			
			String selection = userInput.nextLine();
			
			if(selection.equals("0")) {
				running = false;
				return;
			}
			
			String username = selection;
			System.out.println("Password: ");
			String password = userInput.nextLine();
			
			//Verifies user exists
			validUser = verifyUser(username, password);
			
			//Password or username is fault, loop repeats till combination correct.
			if (validUser == false) {
				System.out.println("Invalid username/password combination");
			}
			//User is verified as real, loop ends.
			else if (validUser == true) {
				System.out.println("Welcome " + username);
			}
			
		} while	(validUser == false);

	}
	
	// A search function that matches whether a username and password given matches something stored in the program.
	private boolean verifyUser(String username, String password)
	{
		for(Clerk clerk : jMossData.getInstance().getClerks()) {
			if(clerk.getUsername().equals(username) == true && clerk.getPassword().equals(password) == true) {
				currentUser = clerk;
				validUser = true;
				return true;
			}
		}
		return false;
	}
	
}

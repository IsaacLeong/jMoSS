package model.menus;

import java.util.Scanner;

import interfaces.MenuInterface;
import model.records.Clerk;
import model.jMossData;

public class LogInMenu implements MenuInterface {
	
	Clerk currentUser;
	
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

	@Override
	public void handleMenu() {
		Boolean validUser = false;
		do {
			//Print menu
			displayMenu();
			//opens scanner, and goes through user I/O
			Scanner userInput = new Scanner(System.in);
			
			System.out.println("Please enter your username: ");
			String username = userInput.nextLine();
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
				setCurrentUser(findUser(username, password));
				userInput.close();
			}
			
			
		} while	(validUser == false);

	}
	
	// A search function that matches whether a username and password given matches something stored in the program.
	public boolean verifyUser(String username, String password)
	{
		for(Clerk clerk : jMossData.getInstance().getClerks()) {
			if(clerk.getUsername().equals(username) == true && clerk.getPassword().equals(password) == true) {
				return true;
			}
		}
		return false;
	}
	
	public void setCurrentUser(Clerk currentUser) {
		this.currentUser = currentUser;
	}
	
	public Clerk getCurrentUser() {
		return currentUser;
	}
	
	public Clerk findUser(String username, String password) {
		for(Clerk clerk : jMossData.getInstance().getClerks()) {
			if(clerk.getUsername().equals(username) == true && clerk.getPassword().equals(password) == true) {
				return clerk;
			}
		}
		return null;
	}
}

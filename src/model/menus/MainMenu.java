package model.menus;

import java.util.Scanner;

import interfaces.MenuInterface;
import model.records.Clerk;
import model.MenuHandling;

public class MainMenu implements MenuInterface {
	
	Scanner userInput = null;
	Clerk currentUser = null;
	
	public void setScanner(Scanner userInput) {
		this.userInput = userInput;
	}
	
	public void setUser(Clerk username){
		this.currentUser = username;
	}
	
	public MainMenu(Scanner userInput, Clerk username) {
		setScanner(userInput);
		setUser(username);
	}
	
	
	
	/*
	 * This will be the primary display of the menu, designed purely for output.
	 * If sub menus exist, it may be necessary to alter or add arguements for this.
	 * In the case of sub menus for a section, consider the use switches, and a storage variable
	 * for user input selection
	 */
	@Override
	public void displayMenu() {
		
		System.out.println("============================================================");
		System.out.println("");
		System.out.println("             JAVA BASED MOVIE SEARCH SYSTEM");
		System.out.println("             Logged in as: " + currentUser.getUsername());
		System.out.println("");
		System.out.println("============================================================");
		System.out.println("");
		System.out.println("1. Book a movie session");
		System.out.println("2. Delete a booking");
		System.out.println("3. Find Session");
		System.out.println("4. Find Booking");
		System.out.println("");
		System.out.println("0. Logout");
		
	}

	@Override
	public void handleMenu() {
		
		//initialise other menus
		BookingMenu bookingMenu;
		DeleteMenu deleteMenu;
		SearchMenu searchMenu;
		EmailSearchMenu emailSearchMenu;
		
		//Doesn't count exit, avoid hard coded values
		int NUMBER_OF_MENU_ELEMENTS = 4;
		//menu selection buffer
		int selection;
		//Exit checking
		boolean exitCheck = false;
		
		
		do {
		displayMenu();
		selection = MenuHandling.menuSelection(userInput, NUMBER_OF_MENU_ELEMENTS);

		switch(selection) {
		//Make a booking
		case 1:
			bookingMenu = new BookingMenu(userInput);
			bookingMenu.handleMenu();
			break;
		//Delete a booking
		case 2:
			deleteMenu = new DeleteMenu(userInput);
			deleteMenu.handleMenu();
			break;
		//Search for a session
		case 3:
			searchMenu = new SearchMenu(userInput);
			searchMenu.handleMenu();
			break;
		//Search for bookings
		case 4:
			emailSearchMenu = new EmailSearchMenu(userInput);
			emailSearchMenu.handleMenu();
			break;
		case 0:
			exitCheck = true;
		}
		}while(exitCheck == false);
		
	}


}

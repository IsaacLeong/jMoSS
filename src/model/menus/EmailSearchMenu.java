package model.menus;

import java.util.ArrayList;
import java.util.Scanner;

import interfaces.MenuInterface;
import model.records.Booking;
import model.MenuHandling;
import model.jMossData;

public class EmailSearchMenu implements MenuInterface {
	
	
	Scanner userInput = null;
	
	public void setScanner(Scanner userInput) {
		this.userInput = userInput;
	}
	
	public EmailSearchMenu(Scanner userInput) {
		setScanner(userInput);
	}
	
	//Display's head menu, upon launch
	@Override
	public void displayMenu() {
		System.out.println("=============================");
		System.out.println();
		System.out.println("Search for bookings");
		System.out.println();
		System.out.println();
		System.out.println("=============================");
		System.out.println();
		
	}
	
	//Handles controlling this menu.
	@Override
	public void handleMenu() {
		
		//Checks to see if the user wishes to exit the menu
		boolean exitCheck = false;
		//Displays the menu
		displayMenu();
		
		//Input Handling
		
		do {
			
			String selection;
			//Declare holding ArrayList for bookings to be Printed.
			
			//Alerts user to required input
			System.out.println("Please input the customer's email address or \"0\" to exit ");
			//Reads user's next input, terminates on \n
			selection = userInput.nextLine();
			
			//Checks for exit.
			if(selection.equals("0")) {
				if(Integer.parseInt(selection) == 0) {	
				exitCheck = true;
				break;
				}
			}
				
			selection = refineEmailSearch(selection);
			
			//checks for a failed search, or an back command by a user.
			if(selection == null) {
				continue;
			}
			
			
			//Casts all valid bookings to the holding Array List
			if(selection != null) {
				Booking selectedBooking = refineBookingSearch(selection);
				selectedBooking.printBookingDetails();
			}
			
			
			
			
		//Loop ends
		}while(exitCheck == false);

	}
	
	private ArrayList<Booking> searchBooking(String emailAddress)
	{
		//Holding array list, for sorting valid search results
		ArrayList<Booking> validBookings = new ArrayList<Booking>();
		
		//iterates through all bookings.
		for(Booking booking : jMossData.getInstance().getBookings()) {
			
			//If the email address if valid, adds it to holding array for processing.
			if(booking.getCustomerEmail().toLowerCase().contains(emailAddress.toLowerCase())) {
				validBookings.add(booking);
			}
		}
		
		//Prints if there is no valid results to the given search criteria.
		if(validBookings.isEmpty() == true) {
			System.out.println("There are no bookings with the email address: " + emailAddress);
			System.out.println("Please check your input and try again");
			System.out.println();
			return null;
		}
		
		//return the array regardless.
		else {
			return validBookings;
		}
		
	}
	
	private String refineEmailSearch(String partialToCompare) {
		
		//Holding array for search matches
		ArrayList<Booking> validEmails = new ArrayList<Booking>();
		
		//Declarations for menus
		int matchCount = 1;
		int selection = 0;
		
		//Forms menu, populates holding array
		
		validEmails = searchBooking(partialToCompare);
		
		if(validEmails == null) {
			System.out.println("There are no email addresses that match the search term, please try again");
			return null;
		}
		
		else {
			
			System.out.println("Select an Email Address, or \"0\" to go back: ");
			for(Booking booking : validEmails) {
				System.out.println(matchCount + ": " + booking.getCustomerEmail());
				matchCount++;
			}
			
			//waits for user input
			selection = MenuHandling.menuSelection(userInput, matchCount);
			
			if(selection == 0) {
				return null;
			}
			else {
				return validEmails.get(selection - 1).getCustomerEmail();
			}
				
		}	
	}
	
	private Booking refineBookingSearch(String customerEmail) {
		
		//Holding array for search matches
		ArrayList<Booking> validEmails = new ArrayList<Booking>();
		
		//Declarations for menus
		int matchCount = 1;
		int selection = 0;
		
		//Forms menu, populates holding array
		
		validEmails = searchBooking(customerEmail);
		
		
		if(validEmails == null) {
			System.out.println("There are no bookings that match the search term, please try again");
			return null;
		}
		
		else {
			
			
			for(Booking booking : validEmails) {
				System.out.println(matchCount + ": " 
								 + booking.getBookingNumber() + ", "
								 + booking.getMovieSession().getDay() + ", "
								 + booking.getMovieSession().getSessionTime());
				matchCount++;
			}
			System.out.println("Select a booking, or \"0\" to go back: ");
			//waits for user input
			selection = MenuHandling.menuSelection(userInput, matchCount);
			
			if(selection == 0) {
				return null;
			}
			else {
				return validEmails.get(selection - 1);
			}
				
		}
	}	
}

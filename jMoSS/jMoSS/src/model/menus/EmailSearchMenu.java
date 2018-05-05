package model.menus;

import java.util.ArrayList;
import java.util.Scanner;

import interfaces.MenuInterface;
import model.ErrorHandling;
import model.jMossData;
import model.records.Booking;

public class EmailSearchMenu implements MenuInterface {

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
		Scanner inputBuffer = new Scanner(System.in);

		
		do {
			
			String userInput;
			//Declare holding ArrayList for bookings to be Printed.
			ArrayList<Booking> printBookings = new ArrayList<Booking>();
			
			//Alerts user to required input
			System.out.println("Please input the customer's email address or \"0\" to exit ");
			//Reads user's next input, terminates on \n
			userInput = inputBuffer.nextLine();
			
			//Checks for exit.
			if(userInput.equals("0")) {
				if(Integer.parseInt(userInput) == 0) {	
				exitCheck = true;
				break;
				}
			}
				
			userInput = refineEmailSearch(userInput, inputBuffer);
			
			//checks for a failed search, or an back command by a user.
			if(userInput == null) {
				continue;
			}
			
			
			//Casts all valid bookings to the holding Array List
			if(userInput != null) {
				printBookings = searchBooking(userInput);
			}
			
			if(printBookings.isEmpty() == false) {
				//Lists all valid inputs.
				listBookingDetails(printBookings);
			}
			
			
		//Loop ends
		}while(exitCheck == false);
		
		//Scanner closes.
		inputBuffer.close();
	}
	
	//A function that should search through the bookings, and iterate through matching them based on email address.
	public ArrayList<Booking> searchBooking(String emailAddress)
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
		}
		
		//return the array regardless.
		return validBookings;
	}
	
	//Prints out a booking result.
	public void printBooking(Booking booking, int noMatches) {
		System.out.println();
		System.out.println("==============================================");
		System.out.println("Result " + noMatches + ": ");
		System.out.println("==============================================");
		System.out.println();
		System.out.println("Booking Number: " + booking.getBookingNumber());
		System.out.println("Email Address: " + booking.getCustomerEmail());
		System.out.println("Customer Suburb: " + booking.getSuburb());
		System.out.println("Number of Seats booked: " + booking.getBookedSeats());
		System.out.println();
		System.out.println("Movie Session ID: " + booking.getMovieSession().getMovieSessionId());
		System.out.println("Movie Name: " + booking.getMovieSession().getMovieName());
		System.out.println("Cineplex: " + booking.getMovieSession().getCineplex());
		System.out.println("Session Time: " + booking.getMovieSession().getSessionTime() + ", " + booking.getMovieSession().getDay());
		System.out.println();
		System.out.println("==============================================");
		System.out.println();
	}
	
	//iterates through all matching bookings, and prints them.
	public void listBookingDetails(ArrayList<Booking> validBookings) {
		
		//Checks to see if the user wishes to exit the menu
		int noMatches = 1;
			
		for(Booking booking : validBookings) {
			//Prints all valid bookings.
			printBooking(booking, noMatches);
			noMatches++;
		}
		
		
	}
	
	public String refineEmailSearch(String partialToCompare, Scanner input) {
		
		//Holding array for search matches
		ArrayList<Booking> validEmails = new ArrayList<Booking>();
		
		//Declarations for menus
		int matchCount = 1;
		boolean exitCheck = false;
		
		String userInput;
		
		//Forms menu, populates holding array
		
		for(Booking booking : jMossData.getInstance().getBookings()) {
			if(booking.getCustomerEmail().contains(partialToCompare) == true) {
				validEmails.add(booking);
				
			}
		}
		
		if(validEmails.isEmpty() == true) {
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
			do {
				userInput = input.nextLine();
				
				//returns null if user wishes to go back
				if(userInput.equals("0")) {
					return null;
				}
				//returns the actual customer email if the user selects a valid email address.
				else if(ErrorHandling.inputValidator(userInput, validEmails.size())) {
					return validEmails.get(Integer.parseInt(userInput)).getCustomerEmail();
				}
				
				
				
			}while(exitCheck == false);
		}
		return null;
	}

	
}

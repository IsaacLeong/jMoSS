package model.menus;

import java.util.ArrayList;
import java.util.Scanner;

import interfaces.MenuInterface;
import model.MenuHandling;
import model.jMossData;
import model.records.Booking;

public class DeleteMenu implements MenuInterface {
	
	Scanner userInput = null;
	
	//Constructors
	public DeleteMenu(Scanner userInput) {
		setScanner(userInput);
	}
	
	public void setScanner(Scanner userInput) {
		this.userInput = userInput;
	}
	
	//Display's mainmenu
	@Override
	public void displayMenu() {
		/*
		 * This will be the primary display of the menu, designed purely for output.
		 * If sub menus exist, it may be necessary to alter or add arguements for this.
		 * In the case of sub menus for a section, consider the use switches, and a storage variable
		 * for user input selection
		 */
		System.out.println("============================================================");
		System.out.println("");
		System.out.println("Delete a booking");
		System.out.println("Remove an existing booking");
		System.out.println("");
		System.out.println("============================================================");
		System.out.println("");
	}
	
	//Primary handler for menu
	@Override
	public void handleMenu() {

		boolean exitCheck = false;
		
		do {
			//Storage string for user input
			String selection;
			//Displays menu
			displayMenu();
			//Alerts user to required input
			System.out.println("Please input a booking number or \"0\" to exit ");
			//Reads user's next input, terminates on \n
			selection = userInput.nextLine();
			
			//Checks for exit.
			if(selection.equals("0")) {
				if(Integer.parseInt(selection) == 0) {	
				exitCheck = true;
				break;
				}
			}
			
			//Refines to specific booking
			Booking selectedBooking = refineBookingSearch(selection);
			
			//checks for a failed search, or an back command by a user.
			if(selectedBooking == null) {
				continue;
			}
			
			//Checks for the correct booking.
			if(selectedBooking != null) {
				
				//checks for deletion confirmation
				Boolean deleteResult = deleteBooking(selectedBooking);
				
				//return true if deletion successful
				if(deleteResult == true) {
					System.out.println("Cancellation Successful");
				}
				//return false if deletion failed.
				else {
					System.out.println("Cancellation Unsuccessful");
				}
				
				
			}
			
		//Loop ends
		}while(exitCheck == false);
		
	}
	
	 //A function to handle the removal of bookings, remember to call the jMossData method of removeBooking(); to store it in the records.
	private boolean deleteBooking(Booking booking)
	{
		//initial declarations
		boolean exitCheck = false;
		String selection = null;

		do {
			//Print booking details, ask for confirmation
			booking.printBookingDetails();
			System.out.println();
			System.out.println("Cancel booking? (Y/N): ");
			
			//Store user input
			selection = userInput.nextLine();
			
			//User selects yes, remove booking from storage
			if(selection.equalsIgnoreCase("Y") == true || selection.equalsIgnoreCase("YES") == true) {
				jMossData.getInstance().removeBooking(booking);
				return true;
			}
			//false, return false
			else if(selection.equalsIgnoreCase("N") == true || selection.equalsIgnoreCase("NO")){
				return false;
			}
			//invalid selection
			else {
				System.out.println("Invalid Selection, please try again.");
			}
			
		}while (exitCheck == false);
		
		//failed return...
		return false;
	}
	
	//Refines and allows exact booking selection from a partial search
	private Booking refineBookingSearch(String bookingNumber) {
		
		//Holding array for search matches
		ArrayList<Booking> validEmails = new ArrayList<Booking>();
		
		//Declarations for menus
		int matchCount = 1;
		int selection = 0;
		
		//Forms menu, populates holding array
		
		for(Booking booking : jMossData.getInstance().getBookings()) {
			if(booking.getBookingNumber().contains(bookingNumber) == true) {
				validEmails.add(booking);
				
			}
		}
		
		if(validEmails.isEmpty() == true) {
			System.out.println("There are no bookings that match the search term, please try again");
			return null;
		}
		else {
			
			System.out.println("Select a booking, or \"0\" to go back: ");
			for(Booking booking : validEmails) {
				System.out.println(matchCount + ": " + booking.getBookingNumber());
				matchCount++;
			}
			
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

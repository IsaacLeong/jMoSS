package model.menus;

import interfaces.MenuInterface;
import model.jMossData;
import model.records.MovieSession;
import java.util.*;


public class BookingMenu implements MenuInterface {
	
	@Override
	public void displayMenu() {
		/*
		 * This will be the primary display of the menu, designed purely for output.
		 * If sub menus exist, it may be necessary to alter or add arguements for this.
		 * In the case of sub menus for a section, consider the use switches, and a storage variable
		 * for user input selection
		 */
		
	}

	@Override
	public void handleMenu() {
		/*
		 * This will be the primary handler of the menu, for dealing with user input.
		 * Here will be how the system responds to user input, and thus correct and incorrect responses, in conjunction with the
		 * displayMenu(); function.
		 */
		
	}
	
	
	public void displaySessionTime()
	{
		System.out.println("=============================");
		System.out.println();
		System.out.println("Book A Movie Session");
		System.out.println("Choose A Session Time (Available Seats)");
		System.out.println();
		System.out.println("=============================");
		System.out.println();
		
		getSessionTimes("", "");
		
	}
	
	
	
	//This function should pass in what is returned from a handleMovieName function
	// and pass in what is returned from a handleCineplex function
	// and then from those two pieces of information, run through the array of movie sessions and return
	// sessions times that match the users chosen movieName and cineplex. 
	public String getSessionTimes(String movieName, String cineplex)
	{
		Scanner userInput = new Scanner(System.in);
		String selection;
		
		int matchCount = 0; //make this global to use in other functions? (ie displaySessionTime)
		
		//creating an array list of MovieSession
		ArrayList<MovieSession> validMovieSessions = new ArrayList<MovieSession>();
		
		//Iterate through all movie sessions
		for(MovieSession movieSession : jMossData.getInstance().getMovieSessions()) 
		{
			//If the movie sessions movieName == movieName AND movie sessions cineplex == cineplex
			// print out the session times and available seats
			if(movieSession.getMovieName().equalsIgnoreCase(movieName) == true && movieSession.getCineplex().equalsIgnoreCase(cineplex) == true) 
			{
				
				if(movieSession.getNumberOfSeats() > 0)
				{
					System.out.println((matchCount + 1) + ". " + movieSession.getSessionTime() + " " + "(" + movieSession.getNumberOfSeats() + ")");
					validMovieSessions.add(movieSession);
					matchCount++;
				}
				
			}
			
			
		}
		
		if(matchCount > 0) {
			
			System.out.println();
			System.out.println("0. Back");
			System.out.println();
			
			System.out.println("=============================");
			System.out.println("Choose an option (0 - " + matchCount + "): ");
			
			selection = userInput.nextLine();
			
			if(selection.equals("0") == true)
			{
				userInput.close();
				return null;
				
			}
			
			userInput.close();
			return validMovieSessions.get(Integer.parseInt(selection) - 1).getMovieSessionId();
			
		}
		else {
			System.out.println("There are no session times for " + movieName + " at " + cineplex + " .");
			System.out.println("Please choose a different movie or cineplex.");
		}
		
		userInput.close();
		return null;
		
	}
	
	public void displayNumberOfPpl()
	{
		System.out.println("=============================");
		System.out.println();
		System.out.println("Book A Movie Session");
		System.out.println("Specify the number of people attending");
		System.out.println();
		System.out.println("=============================");
		System.out.println();
		
		getNumberOfPpl("3002");
		
	}
	
	public int getNumberOfPpl(String sessionID)
	{
		Scanner userInput = new Scanner(System.in);
		String selection;
		
		//Iterate through all movie sessions
		for(MovieSession movieSession : jMossData.getInstance().getMovieSessions()) 
		{
			//sessionID == sessionID of a movieSession
			if(movieSession.getMovieSessionId().equals(sessionID) == true) 
			{
				//if a user enters an invalid input - keep them in the loop
				while(true)
				{
					System.out.println("Please enter a number from 1 - " + movieSession.getNumberOfSeats() + " (0 to go back): ");
					selection = userInput.nextLine();
					
					//if input is a number and is less than the number of available seats
					if(isNumeric(selection) == true && Integer.parseInt(selection) < movieSession.getNumberOfSeats())
					{
						//return the number of people specified
						userInput.close();
						return Integer.parseInt(selection);
					}
					else
					{
						System.out.println("Invalid input. Please try again.");
						System.out.println();
					}

				}
			}
			
		}
		
		userInput.close();
		return 0;
		
	}
	
	public boolean isNumeric(String s) {  
	    return s != null && s.matches("[-+]?\\d*\\.?\\d+");  
	}
	
	//A function to handle the adding of bookings, remember to call the jMossData method of addBooking(); to store it in the records.
	public boolean newBooking(String customerEmail, String suburb, MovieSession movieSession, int bookedSeats)
	{
		return false;
	}

}

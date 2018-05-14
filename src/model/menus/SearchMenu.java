package model.menus;
import java.util.*;
import interfaces.MenuInterface;
import model.MenuHandling;
import model.jMossData;
import model.records.MovieSession;

public class SearchMenu implements MenuInterface {
	
	Scanner userInput = null;
	
	public void setScanner(Scanner userInput) {
		this.userInput = userInput;
	}
	
	public SearchMenu(Scanner userInput) {
		setScanner(userInput);
	}

	/*
	 * This will be the primary display of the menu, designed purely for output.
	 * If sub menus exist, it may be necessary to alter or add arguements for this.
	 * In the case of sub menus for a section, consider the use switches, and a storage variable
	 * for user input selection
	 */
	@Override
	public void displayMenu() {
		
		System.out.println("=============================");
		System.out.println();
		System.out.println("Search");
		System.out.println("Search via a movie or cineplex");
		System.out.println();
		System.out.println("=============================");
		System.out.println();
		System.out.println("1. Search movie");
		System.out.println("2. Search cineplex");
		System.out.println();	
		System.out.println("0. Back");
		System.out.println();
		System.out.println("=============================");
		System.out.println("Choose an option (0-2):");
	}
	
	/*
	 * This will be the primary handler of the menu, for dealing with user input.
	 * Here will be how the system responds to user input, and thus correct and incorrect responses, in conjunction with the
	 * displayMenu(); function.
	 */
	@Override
	public void handleMenu() {
		//Avoid hard coded values
		int NUMBER_OF_MENU_ELEMENTS = 2;
		int selection = 0;
		//exit check
		boolean exitCheck = false;
		
		do {
		//print menu
		displayMenu();
		
		//validate user input
		selection = MenuHandling.menuSelection(userInput, NUMBER_OF_MENU_ELEMENTS);
		
		//switch for menu selection
			switch(selection) {
			//search via movie name
			case 1:
				searchMovieName();
				break;
			//search via cineplex location
			case 2:
				searchCineplex();
				break;
			//user exits
			case 0:
				return;
			}
		}while(exitCheck == false);
		
		
	}
	
	//Submenu for searching via movie name
	public void searchMovieName() {
		
		boolean exitCheck = false;
		
		do {
		//Print menu header
		System.out.println("=============================");
		System.out.println();
		System.out.println("Search");
		System.out.println("Search movie");
		System.out.println();
		System.out.println("=============================");
		System.out.println();
		System.out.println("Please enter a movie title (0 to go back): ");
		//User input
		String selection = userInput.nextLine();
		
		//User exits
		if(selection.equals("0")) {
			return;
		}
		
		//Refines search to exacts
		ArrayList<MovieSession> validSessions = findMoviesViaName(refineNameSearch(selection));
		
		if(validSessions == null) {
			continue;
		}
		
		//select session to view more details or book seats
		selectSession(validSessions);
		
		}while(exitCheck == false);
		
	}
	
	//Submenu for searching via cineplex location
	public void searchCineplex() {
		
		
		boolean exitCheck = false;
		
		do {
		//Print menu header
		System.out.println("=============================");
		System.out.println();
		System.out.println("Search");
		System.out.println("Search cineplex");
		System.out.println();
		System.out.println("=============================");
		System.out.println();
		System.out.println("Please enter a location (0 to go back): ");
		//User input
		String selection = userInput.nextLine();
		//User exits
		if(selection.equals("0")) {
			return;
		}
		//Refines search to exacts
		ArrayList<MovieSession> validSessions = findMoviesViaCineplex(refineCineplexSearch(selection));
		//select session to view more details or book seats
		
		if(validSessions == null) {
			continue;
		}
		
		selectSession(validSessions);
		
		}while(exitCheck == false);
		
	}
	
	//Methods for searching and search validation for searching via name.
	//Find all sessions with a given movie name
	private ArrayList<MovieSession> findMoviesViaName(String movieName)
	{
		//in case no matches to search term
		if(movieName == null) {
			return null;
		}
		
		//Holding array list, for sorting valid search results
		ArrayList<MovieSession> validSessions = new ArrayList<MovieSession>();
		
		//iterates through all MovieSessions.
		for(MovieSession movieSession : jMossData.getInstance().getMovieSessions()) {
			
			//If the email address if valid, adds it to holding array for processing.
			if(movieSession.getMovieName().equals(movieName)) {
				validSessions.add(movieSession);
			}
		}
		
		
		
		//return the array regardless.
		return validSessions;

		
	}
	//refines movie names search from partial comparison
	private String refineNameSearch(String partialToCompare) {
		

		//Declarations for menus
		int matchCount = 1;
		int selection = 0;
		
		ArrayList<String> movieNames = new ArrayList<String>();
		
	//iterates through all moviesessions
	for(MovieSession movieSession : jMossData.getInstance().getMovieSessions()) {
		//loop check
		boolean movieExists = false;
		//checks for distinct movie names
		for(String movieName : movieNames) {
			if(movieName.equalsIgnoreCase(movieSession.getMovieName())) {
				//if it exists loop breaks, iterates next movie
				movieExists = true;
				break;
			}
		}
		//if movie doesn't exist in array add to array for processing
		if(movieExists == false && movieSession.getMovieName().toLowerCase().contains(partialToCompare) == true) {
			movieNames.add(movieSession.getMovieName());
		}

	}

		if(movieNames.isEmpty() == true) {
			//Prints if there is no valid results to the given search criteria.
			System.out.println("There are no movies with the name: " + partialToCompare);
			System.out.println("Please check your input and try again");
			System.out.println();
			return null;
		}
			

		//print valid movie names to partial
		for(String movieName : movieNames) {
			System.out.println(matchCount + ". " + movieName);
			matchCount++;
		}
		System.out.println("Select a matching movieName, or \"0\" to go back: ");
		//waits for user input
		selection = MenuHandling.menuSelection(userInput, matchCount);
		
		if(selection == 0) {
			return null;
		}
		else {
			return movieNames.get(selection - 1);
		}
	}	
	
	//Methods for searching and search validation via cineplex name
	//Find all sessions given a cineplex
	private ArrayList<MovieSession> findMoviesViaCineplex(String cineplex)
	{
		
		//in case no matches to search term
		if(cineplex == null) {
			return null;
		}
				
		
		//Holding array list, for sorting valid search results
		ArrayList<MovieSession> validSessions = new ArrayList<MovieSession>();
		
		//iterates through all MovieSessions.
		for(MovieSession movieSession : jMossData.getInstance().getMovieSessions()) {
			
			//If the email address if valid, adds it to holding array for processing.
			if(movieSession.getCineplex().equals(cineplex)) {
				validSessions.add(movieSession);
			}
		}
		
		
		
		//return the array regardless.
		return validSessions;
		
	}
	
	//Refines search of cineplexes from partial comparison
	private String refineCineplexSearch(String partialToCompare) {
		

		//Declarations for menus
		int matchCount = 1;
		int selection = 0;
		
			
			ArrayList<String> cineplexNames = new ArrayList<String>();
			

			for(MovieSession movieSession : jMossData.getInstance().getMovieSessions()) {
				//loop check
				boolean movieExists = false;
				//checks for distinct movie names
				for(String cineplexName : cineplexNames) {
					//Check for containing search term

						if(cineplexName.equalsIgnoreCase(movieSession.getCineplex())) {
							//if it exists loop breaks, iterates next movie
							movieExists = true;
							break;
						}
				}
				//if movie doesn't exist in array add to array for processing
				if(movieExists == false && movieSession.getCineplex().toLowerCase().contains(partialToCompare) == true) {
					cineplexNames.add(movieSession.getCineplex());
				}

			}
			
			//Prints if there is no valid results to the given search criteria.
			if(cineplexNames.isEmpty() == true) {
				System.out.println("There are no movies with the name: " + partialToCompare);
				System.out.println("Please check your input and try again");
				System.out.println();
				return null;
			}
			
			
			//print valid movie names to partial
			for(String cineplex : cineplexNames) {
				System.out.println(matchCount + ". " + cineplex);
				matchCount++;
			}
			System.out.println("Select a matching cineplex, or \"0\" to go back: ");
			//waits for user input
			selection = MenuHandling.menuSelection(userInput, matchCount);
			
			if(selection == 0) {
				return null;
			}
			else {
				return cineplexNames.get(selection - 1);
			}
				
		}
	//Select a valid Session
	private void selectSession(ArrayList<MovieSession> validSessions) {
		
		boolean exitCheck = false;
		int matchCount = 1;
		int selection = 0;
		
		System.out.println("Relevant sessions: ");
		for(MovieSession movieSession : validSessions) {
			System.out.println(matchCount + ". " + movieSession.getDay() + ": " + movieSession.getSessionTime());
			matchCount++;
		}
		System.out.println("Select a session to view more details (Or 0 to go back): ");
		//waits for user input
		selection = MenuHandling.menuSelection(userInput, matchCount);
		
		if(selection == 0) {
			return;
		}
		else {
			MovieSession selectedSession = validSessions.get(selection - 1);
			//print session details
			System.out.println("=============================");
			selectedSession.printMovieSession();
			System.out.println("=============================");
			System.out.println();
			if(selectedSession.getNumberOfSeats() > 0) {
				do {
					//Confirmation with user
					System.out.println("Would you like to book for this session?(Y/N): ");
					String confirmInput = userInput.nextLine();
					
					//Y or yes confirms and saves booking to Data container jMossData
					if(confirmInput.equalsIgnoreCase("Y") || confirmInput.equalsIgnoreCase("YES")) {
						//Opens a booking menu for an impromptu booking
						interimBookSession(selectedSession);
						exitCheck = true;
					}
					//N or no cancels booking brings back to main screen
					else if(confirmInput.equalsIgnoreCase("N") || confirmInput.equalsIgnoreCase("NO")) {
						System.out.println("Returning...");
						exitCheck = true;
					}
					//invalid selection
					else {
						System.out.println("Invalid selection, please try again...");
					}
			
				}while(exitCheck == false);
				
				
			}
		}
	}
	//Make a booking from a valid search
	private void interimBookSession(MovieSession selectedSession) {
		BookingMenu impromptuBooking = new BookingMenu(userInput);
		impromptuBooking.setCineplex(selectedSession.getCineplex());
		impromptuBooking.setMovieName(selectedSession.getMovieName());
		impromptuBooking.setSelectedMovieSession(selectedSession);
		impromptuBooking.handleMenu();
		
	}

}

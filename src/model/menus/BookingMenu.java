package model.menus;

import interfaces.MenuInterface;
import model.MenuHandling;
import model.jMossData;
import model.records.*;
import java.util.*;

public class BookingMenu implements MenuInterface {
	
	//holding values for constructing a booking object
	private Cineplex bookingCineplex = null;
	private String bookingMovieName = null;
	private MovieSession selectedMovieSession = null;
	private int numberOfPeople = 0;
	private String emailAddress = null;
	private String suburb = null;
	Scanner userInput = null;
	
	//sets scanner for input
	public void setScanner(Scanner userInput) {
		this.userInput = userInput;
	}
	
	//Constructor
	public BookingMenu(Scanner userInput) {
		setScanner(userInput);
	}
	
	//outputs main display for booking menu
	@Override
	public void displayMenu() {
		System.out.println("=============================");
		System.out.println();
		System.out.println("Book A Movie Session");
		System.out.println("Make a booking by choosing a cineplex, movie, session time and number of people.");
		System.out.println();
		System.out.println("=============================");
		System.out.println("1. Choose a Cineplex. " + getCineplex());
		System.out.println("2. Choose a Movie. " + getBookingMovieName());
		System.out.println("3. Choose a Session. " + getSelectedMovieSession());
		System.out.println("4. Enter a Number of People. " + getNumberOfSeats());
		System.out.println("5. Enter email address. " + getEmailAddress());
		System.out.println("6. Enter home suburb. " + getSuburb());
		System.out.println("7. Confirm booking.\n");
		System.out.println("0. Main menu.");
		System.out.println("=============================");
	}
	
	//primary handler for menu
	@Override
	public void handleMenu() {
		//declaration for loop checker
		boolean exitCheck = false;
		//Avoid hard coded values
		int NUMBER_OF_MENU_ELEMENTS = 7;
		
		do {
			//show menu whenever re-entering menu
			displayMenu();
			//check's user's int selection is valid.
			int selection = MenuHandling.menuSelection(userInput, NUMBER_OF_MENU_ELEMENTS);
			
			//primary handling of menu selection
			switch (selection) {
			//exit
			case 0:
				exitCheck = true;
				break;
			//Select cineplex from a list	
			case 1:
				chooseCineplex();
				break;
			//Select movie from a list of availble movies
			case 2:
				chooseMovie();
				break;
			//Select movie session, only available after selecting both a movie and cineplex
			case 3:
				if(bookingCineplex == null || bookingMovieName == null) {
					System.out.println("You must select a movie and cineplex before choosing a session.");
				}
				else {
				chooseSession();
				}
				break;
			//Select number of seats only availble after selecting a movie session.
			case 4:
				if(selectedMovieSession == null) {
					System.out.println("You must select a movie session, before selecting seats");
				}
				else {
					chooseNumberOfPeople();
				}
				break;
			//Enter email address
			case 5:
				enterEmail();
				break;
			//Enter suburb
			case 6:
				enterSuburb();
				break;
			//Confirm and validate booking
			case 7:
				if(validateBooking() == true) {
					exitCheck = commitBooking();
				}
			}
		}while(exitCheck == false);

	}

	//select a cineplex from available options
	private void chooseCineplex() {
		System.out.println("=============================");
		System.out.println("Book A Movie Session");
		System.out.println("Choose a Cineplex");
		System.out.println("=============================");
		System.out.println("1. Fitzroy");
		System.out.println("2. Lilydale");
		System.out.println("3. Melbourne CBD");
		System.out.println("4. St Kilda");
		System.out.println("5. Sunshine");
		System.out.println();
		System.out.println("0. Back");
		System.out.println("=============================");
		
		
		int cineplexID = MenuHandling.menuSelection(userInput, 5);
		String selection;
		switch (cineplexID) {
		case 1:
			selection = "Fitzroy";
			break;
		case 2:
			selection = "Lilydale";
			break;
		case 3:
			selection = "Melbourne CBD";
			break;
		case 4:
			selection = "St Kilda";
			break;
		case 5:
			selection = "Sunshine";
			break;
		default:
			return;
		}
		setCineplex(selection);
		setSelectedMovieSession(null);
		setNumberOfPeople(0);
		return;
	}

	//select a movie from available options
	private void chooseMovie() {
		//print menu header
		System.out.println("=============================");
		System.out.println("Book A Movie Session");
		System.out.println("Choose a Movie");
		System.out.println("=============================");
		//counts number of matches
		int matchCount = 1;
		
		//Holding arraylist for movie names
		ArrayList<String> movieNames = getAvailableMovies();
		
		//iterates and prints out movies to select from
		for(String movieName : movieNames) {
			System.out.println(matchCount + ". " + movieName);
			matchCount++;
		}
		//if no movies are available or all movie sessions are full
		if(matchCount == 1) {
			System.out.println("Error, no movies sessions availble, please check with system administrator.");
			return;
		}
		//print end of menu
		System.out.println();
		System.out.println("0. Back");
		System.out.println("=============================");
		
		//check user input is valid
		int selection = MenuHandling.menuSelection(userInput, movieNames.size());
		
		//user goes back
		if(selection == 0) {
			return;
		}
		//movie is selected, movieSession and number of seats reset for data integrity
		else {
			setMovieName(movieNames.get(selection - 1));
			setSelectedMovieSession(null);
			setNumberOfPeople(0);
		}
		
	}
	
	//lists all available movies, returns arraylist of movie names
	private ArrayList<String> getAvailableMovies(){
		
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
			if(movieExists == false) {
				movieNames.add(movieSession.getMovieName());
			}

		}
		
		return movieNames;
	}

	//select an available session
	private void chooseSession() {
		//print menu header
		System.out.println("=============================");
		System.out.println("Book A Movie Session");
		System.out.println("Choose a Movie Session");
		System.out.println("=============================");
		//counts menu items
		int matchCount = 1;
		//stores availble matching movie sessions
		ArrayList<MovieSession> validMovieSessions = getAvailableMovieSessions();
		
		//prints menu
		for(MovieSession movieSession : validMovieSessions) {
			System.out.println(matchCount + ". " + "Movie Session: "
						     + movieSession.getDay() + ", "
							 + movieSession.getSessionTime() + " (" 
						     + movieSession.getNumberOfSeats() + ")");
			matchCount++;
		}
		//incase no available sessions
		if(matchCount == 1) {
			System.out.println("Error, no sessions with available seating, please try other cineplexes/movies");
			return;
		}
		//menu footer
		System.out.println();
		System.out.println("0. Back");
		System.out.println("=============================");
		
		//handle and validate user input
		int selection = MenuHandling.menuSelection(userInput, validMovieSessions.size());
		
		//user cancels
		if(selection == 0) {
			return;
		}
		//input saved
		else {
			setSelectedMovieSession(validMovieSessions.get(selection - 1));
		}
	}
	
	//lists all sessions that meet cineplex and movie name requirements
	private ArrayList<MovieSession> getAvailableMovieSessions(){
		
		//MovieSession data container
		ArrayList<MovieSession> validMovieSessions = new ArrayList<MovieSession>();
		
		//iterates through all saved movie sessions
		for(MovieSession movieSession : jMossData.getInstance().getMovieSessions()) {
			//if it matches the cineplex and movie name, lists session and seats availble
			if(movieSession.getCineplex().equalsIgnoreCase(getCineplex()) == true && movieSession.getMovieName().equals(bookingMovieName) == true) {
				//does not list sessions with no seats available
				if(movieSession.getNumberOfSeats() > 0) {
				//adds movie session to array for processing
				validMovieSessions.add(movieSession);
				}
			}
		}
			return validMovieSessions;
	}
	
	//select number of seats to book
	private void chooseNumberOfPeople() {
		//print menu header
		System.out.println("=============================");
		System.out.println("Book A Movie Session");
		System.out.println("Number of people");
		System.out.println("=============================");
		System.out.println();
		//print menu prompt
		System.out.println("Please enter a number from 1 - " + selectedMovieSession.getNumberOfSeats() + " (enter 0 to go back)");
		//validate user input
		int selection = MenuHandling.menuSelection(userInput, selectedMovieSession.getNumberOfSeats());
		//user input saved
		setNumberOfPeople(selection);
	}

	//enter email address (Add email verification regex?)
	private void enterEmail() {
		//print menu header
		System.out.println("=============================");
		System.out.println("Book A Movie Session");
		System.out.println("Enter Email Address");
		System.out.println("=============================");
		System.out.println();
		//main declarations (Loop exit check, user input buffer
		boolean exitCheck = false;
		String emailInput = null;
		
		//validation loop
		do {
			//user inputs email address
			System.out.println("Please enter an email address (enter 0 to go back): ");
			emailInput = userInput.nextLine();
			
			//user exit
			if(emailInput.equals("0")) {
				return;
			}
			
			do {
				//Confirm with user
				System.out.println("Is this correct?(Y/N): " + emailInput);
				String selection = userInput.nextLine();
				
				//Y or yes to confirm
				if(selection.equalsIgnoreCase("Y") || selection.equalsIgnoreCase("YES")) {
					setEmailAddress(emailInput);
					exitCheck = true;
				}
				//N or NO to cancel
				else if(selection.equalsIgnoreCase("N") || selection.equalsIgnoreCase("NO")) {
					break;
				}
				//invalid selections
				else {
					System.out.println("Invalid selection, please try again...");
				}
			
			}while(exitCheck == false);
			
		}while(exitCheck == false);
	}
	
	//enter suburb (validation methods are very complex?. database seems like overkill)
	private void enterSuburb() {
		//print menu header
		System.out.println("=============================");
		System.out.println("Book A Movie Session");
		System.out.println("Enter Suburb");
		System.out.println("=============================");
		System.out.println();
		
		//initial declarations (Exit check and user input buffer)
		boolean exitCheck = false;
		String suburbInput = null;
		
		do {
			//Take user input
			System.out.println("Please enter your home suburb (enter 0 to go back): ");
			suburbInput = userInput.nextLine();
			
			//user exit
			if(suburbInput.equals("0")) {
				return;
			}
			
			//validation loop
			do {
				//check user validation
				System.out.println("Is this correct?(Y/N): " + suburbInput);
				String selection = userInput.nextLine();
				
				//If Y or yes confirm
				if(selection.equalsIgnoreCase("Y") || selection.equalsIgnoreCase("YES")) {
					setSuburb(suburbInput);
					exitCheck = true;
				}
				//If N or no exit
				else if(selection.equalsIgnoreCase("N") || selection.equalsIgnoreCase("NO")) {
					break;
				}
				//Invalid selection handling
				else {
					System.out.println("Invalid selection, please try again...");
				}
			
			}while(exitCheck == false);
			
		}while(exitCheck == false);
	}
	
	//checks for all fields filled, if any not filled out, returns false, else true.
	private boolean validateBooking() {
		//checks for cineplex or movie name confirmed
		if(bookingCineplex == null || bookingMovieName == null) {
			System.out.println("Please select a Cineplex and Movie before continuing.");
			return false;
		}
		//checks for movie session confirmed
		if(selectedMovieSession == null) {
			System.out.println("Please select a Session before continuing.");
			return false;
		}
		//checks for number of seats booked
		if(numberOfPeople == 0) {
			System.out.println("Please select a number of seats greater than 0.");
			return false;
		}
		//checks for email address
		if(emailAddress == null) {
			System.out.println("Please enter your email address before continuing");
			return false;
		}
		//checks for suburb added
		if(suburb == null) {
			System.out.println("Please enter your suburb before continuing");
			return false;
		}
		//return true if all confirmed.
		return true;
	}
	
	//commits the booking to the singleton
	private boolean commitBooking() {
		
		//Generate booking number
		String bookingNumber = jMossData.getInstance().getBookingNumbers();
		
		//Print booking details
		System.out.println("=============================");
		System.out.println("Booking Number: " + bookingNumber);
		System.out.println("Cineplex: " + getCineplex());
		System.out.println("Movie: " + getBookingMovieName());
		System.out.println("Session: " + getSelectedMovieSession());
		System.out.println("Number of People: " + getNumberOfSeats());
		System.out.println("Email address: " + getEmailAddress());
		System.out.println("Suburb: " + getSuburb());
		System.out.println("=============================");
		System.out.println();
		//exit check
		boolean exitCheck = false;
		do {
			//Confirmation with user
			System.out.println("Are the above details correct, would you like to commit the booking?(Y/N): ");
			String selection = userInput.nextLine();
			
			//Y or yes confirms and saves booking to Data container jMossData
			if(selection.equalsIgnoreCase("Y") || selection.equalsIgnoreCase("YES")) {
				
				jMossData.getInstance().addBooking(new Booking(emailAddress, suburb, selectedMovieSession, numberOfPeople, bookingNumber));
				exitCheck = true;
				System.out.println("Booking Successful...");
				return exitCheck;
			}
			//N or no cancels booking brings back to main screen
			else if(selection.equalsIgnoreCase("N") || selection.equalsIgnoreCase("NO")) {
				System.out.println("Booking Cancelled...");
				return exitCheck;
			}
			//invalid selection
			else {
				System.out.println("Invalid selection, please try again...");
			}
		
		}while(exitCheck == false);
		//Technically unreachable exists for error checking.
		return false;
	}
	
	//Setters and getters for ease of use.
	public void setCineplex(String cineplex) {
		cineplex = cineplex.replace(' ', '_');
		this.bookingCineplex = Cineplex.valueOf(cineplex.toUpperCase());
	}
	
	private String getCineplex() {
		if (bookingCineplex == null) {
			return "";
		}
		String outputCineplex = bookingCineplex.toString();
		return outputCineplex.replace('_', ' ');
	}

	public void setMovieName(String movieName) {
		this.bookingMovieName = movieName;
	}

	public void setSelectedMovieSession(MovieSession selectedMovieSession) {
		this.selectedMovieSession = selectedMovieSession;
	}
	
	private String getSelectedMovieSession() {
		if(selectedMovieSession == null) {
			return "";
		}
		else {
			return selectedMovieSession.getDay() + ": " + selectedMovieSession.getSessionTime();
		}
	}
	
	private void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	private String getEmailAddress() {
		if(emailAddress == null) {
			return "";
		}
		else {
			return emailAddress;
		}
	}
	
	private void setNumberOfPeople(int numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}
	
	private String getBookingMovieName() {
		if(bookingMovieName == null) {
			return "";
		}
		else {
			return bookingMovieName;
		}
	}

	private int getNumberOfSeats() {
		return numberOfPeople;
	}

	
	public String getSuburb() {
		if(suburb == null) {
			return "";
		}
		else {
			return suburb;
		}
	}

	
	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}
}

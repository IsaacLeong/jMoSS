package model.menus;

import interfaces.MenuInterface;
import model.jMossData;
import model.records.MovieSession;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BookingMenu implements MenuInterface {

	// A String array to keep hold of the options that a User has entered.
	private String[] options = new String[4];

	@Override
	public void displayMenu() {
		System.out.println("=============================");
		System.out.println("Book A Movie Session");
		// Prints the status of the Booking.
		System.out.println(getBookingProgress());
		System.out.println("=============================");
		// Prints each option, where the wording can be changed if an option has already been chosen.
		System.out.println("1. Choose a" + showDifference(0) + " Cineplex." + showCurrent(0));
		System.out.println("2. Choose a" + showDifference(1) + " Movie." + showCurrent(1));
		System.out.println("3. Choose a" + showDifference(2) + " Session." + showCurrent(2));
		System.out.println("4. Enter a" + showDifference(3) + " Number of People." + showCurrent(3));
		System.out.println("5. Confirm booking.\n");
		System.out.println("0. Main menu.");
		System.out.println("=============================");
	}

	@Override
	public void handleMenu() {
		boolean exit = false;
		while (!exit) {
			displayMenu();
			int option = getOptionInput(0, 5);
			switch (option) {
			case 0:
				exit = true;
				break;
			case 1:
				options[0] = chooseCineplex();
				break;
			case 2:
				options[1] = chooseMovie();
				break;
			case 3:
				options[2] = chooseSession();
				break;
			case 4:
				options[3] = chooseNumberOfPeople();
				break;
			case 5:
				// Return Data \ Make Booking
				if (countNonInputs() == 0)
					// Here you would do 3.9 & 4.3 - create a booking with the variables of the options array.
					exit = true;
				else
					System.out.println("You must satisfy every option before confirming the Booking!");
				break;
			}
		}

	}

	// Menu for choosing a Cinemaplex.
	private String chooseCineplex() {
		// NOTE: No file exists for a list of Cinemaplex's? I think we should make one and refactor this.
		System.out.println("=============================");
		System.out.println("Book A Movie Session");
		System.out.println("Choose a Cineplex");
		System.out.println("=============================");
		System.out.println("1. Fitzroy");
		System.out.println("2. Lilydale");
		System.out.println("3. Melbourne CBD");
		System.out.println("4. St Kilda");
		System.out.println("5. Sunshine\n");
		System.out.println("0. Back");
		System.out.println("=============================");

		int cinemaplexID = getOptionInput(0, 5);
		switch (cinemaplexID) {
		case 1:
			return "Fitzroy";
		case 2:
			return "Lilydale";
		case 3:
			return "Melbourne CBD";
		case 4:
			return "St Kilda";
		case 5:
			return "Sunshine";
		default:
			return null;
		}
	}

	private String chooseMovie() {
		System.out.println("=============================");
		System.out.println("Book A Movie Session");
		System.out.println("Choose a Movie");
		System.out.println("=============================");
		// Print each of the movies with an added number and '.'
		int id = 1;
		List<String> movieList = getMovieList();
		for (String movie : movieList) {
			System.out.println(id + ". " + movie);
			id += 1;
		}
		System.out.println("=============================");
		
		// Dynamically set the amount of options (0 - x) allowed depending on how many movies there are.
		int movieID = getOptionInput(0, movieList.size());
		// User wants to go back, return null
		if (movieID == 0)
			return null;
		else {
			// Iterate through each Movie until it gets to the selection id, then return it.
			Iterator<String> movieIterator = movieList.iterator();
			int currentMovieID = 1;
			String selection = "";
			while (currentMovieID <= movieID) {
				selection = movieIterator.next();
				currentMovieID += 1;
			}
			return selection;	
		}
	}

	// To be implemented when we implement session time.
	private String chooseSession() {
		return null;
	}

	// To be implemented when we implement Number of People.
	private String chooseNumberOfPeople() {
		return null;
	}

	// Returns an empty string or "different" for the wording of the booking menu.
	private String showDifference(int optionID) {
		if (options[optionID] == null)
			return "";
		else
			return " different";
	}

	// Returns an empty string or the currently chosen option for each line in the booking menu.
	private String showCurrent(int optionID) {
		if (options[optionID] == null)
			return "";
		else
			return " (Current: \"" + options[optionID] + "\")";
	}

	// Returns the name of each option by an id starting at 0.
	private String getOptionNameByID(int optionID) {
		if (optionID == 0)
			return "Cineplex";
		else if (optionID == 1)
			return "Movie";
		else if (optionID == 2)
			return "Session Time";
		else if (optionID == 3)
			return "Number of People";
		else
			return "UKNOWN_OPTION_NAME";
	}

	// Returns how many options have not been allocated a value.
	private int countNonInputs() {
		int totalNonInputs = 0;
		for (int i = 0; (i < options.length); i++) {
			if (options[i] == null)
				totalNonInputs += 1;
		}
		return totalNonInputs;
	}

	private String getBookingProgress() {

		int totalNonInputs = countNonInputs();

		// No options have been given a value.
		if (totalNonInputs == options.length)
			return "Make a booking by choosing a Cineplex, Movie, Session Time and Number of people.";
		// All options have been given a value
		else if (totalNonInputs == 0)
			return "All options have been entered. Confirm the booking to continue.";
		else {
			// The following options need to be given a value...
			StringBuilder nonCompletedOptions = new StringBuilder("Continue the Booking process by entering a: | ");
			for (int i = 0; (i < options.length); i++) {
				if (options[i] == null)
					nonCompletedOptions.append(getOptionNameByID(i) + " | ");
			}
			return nonCompletedOptions.toString();
		}
	}

	// NOTE: the Scanner is not closed, because other classes use Scanner and closing it here makes it unusable again.
	// NOTE2: I highly suggest that this function is made available to everyone, as it will allow users to only enter numbers
	// Between a set min and max value. It's very useful across our entire application.
	@SuppressWarnings("resource")
	private int getOptionInput(int min, int max) {

		boolean isValidInput = false;
		Scanner optionInput = new Scanner(System.in);
		String lineInput;
		int option = 0;

		while (isValidInput == false) {
			try {
				System.out.print("Choose an Option (" + min + " - " + max + "): ");
				lineInput = optionInput.nextLine();
				option = Integer.parseInt(lineInput);

				if ((option >= min) && (option <= max))
					isValidInput = true;
				else
					System.out.println("The input entered is not between (" + min + " - " + max + ")!");

			} catch (NumberFormatException e) {
				System.out.println("The input entered is not a Number!");
			}
		}

		return option;
	}

	@SuppressWarnings("null")
	private List<String> getMovieList() {
		List<String> movieList = new ArrayList<String>();
		try {
			// Iterate through each line in the file.
			FileReader fileReader = new FileReader("MovieSession.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				// Break it up wherever there is a '/'
				String[] tokens = line.split("\\/");
				boolean alreadyExists = false;
				// Go through the movieList and add the movie if its not already in the list.
				if (movieList != null) {
					for (String movie : movieList) {
						if (movie.equals(tokens[1])) {
							alreadyExists = true;
							break;
						}
					}
					if (!alreadyExists)
						movieList.add(tokens[1]);
				} else
					// This is the first movie, so add it without doing check.
					movieList.add(tokens[1]);
			}
			bufferedReader.close();
		} catch (IOException e) {
			System.out.println("ERROR: Couldn't read from the Movie Session File!");
		}
		return movieList;
	}

	
	
	
	
	
	
	
	// BELOW IS OLD PBI 3.1 and 3.2!!
	
	
	// This function should pass in what is returned from a handleMovieName
	// function
	// and pass in what is returned from a handleCineplex function
	// and then from those two pieces of information, run through the array of
	// movie sessions and return
	// sessions times that match the users chosen movieName and cineplex.
	public String getSessionTimes(String movieName, String cineplex) {
		Scanner userInput = new Scanner(System.in);
		String selection;

		int matchCount = 0; // make this global to use in other functions? (ie
							// displaySessionTime)

		// creating an array list of MovieSession
		ArrayList<MovieSession> validMovieSessions = new ArrayList<MovieSession>();

		// Iterate through all movie sessions
		for (MovieSession movieSession : jMossData.getInstance().getMovieSessions()) {
			// If the movie sessions movieName == movieName AND movie sessions
			// cineplex == cineplex
			// print out the session times and available seats
			if (movieSession.getMovieName().equalsIgnoreCase(movieName) == true
					&& movieSession.getCineplex().equalsIgnoreCase(cineplex) == true) {

				if (movieSession.getNumberOfSeats() > 0) {
					System.out.println((matchCount + 1) + ". " + movieSession.getSessionTime() + " " + "("
							+ movieSession.getNumberOfSeats() + ")");
					validMovieSessions.add(movieSession);
					matchCount++;
				}

			}

		}

		if (matchCount > 0) {

			System.out.println();
			System.out.println("0. Back");
			System.out.println();

			System.out.println("=============================");
			System.out.println("Choose an option (0 - " + matchCount + "): ");

			selection = userInput.nextLine();

			if (selection.equals("0") == true) {
				userInput.close();
				return null;

			}

			userInput.close();
			return validMovieSessions.get(Integer.parseInt(selection) - 1).getMovieSessionId();

		} else {
			System.out.println("There are no session times for " + movieName + " at " + cineplex + " .");
			System.out.println("Please choose a different movie or cineplex.");
		}

		userInput.close();
		return null;

	}

	public void displayNumberOfPpl() {
		System.out.println("=============================");
		System.out.println();
		System.out.println("Book A Movie Session");
		System.out.println("Specify the number of people attending");
		System.out.println();
		System.out.println("=============================");
		System.out.println();

		getNumberOfPpl("3002");

	}

	public int getNumberOfPpl(String sessionID) {
		Scanner userInput = new Scanner(System.in);
		String selection;

		// Iterate through all movie sessions
		for (MovieSession movieSession : jMossData.getInstance().getMovieSessions()) {
			// sessionID == sessionID of a movieSession
			if (movieSession.getMovieSessionId().equals(sessionID) == true) {
				// if a user enters an invalid input - keep them in the loop
				while (true) {
					System.out.println(
							"Please enter a number from 1 - " + movieSession.getNumberOfSeats() + " (0 to go back): ");
					selection = userInput.nextLine();

					// if input is a number and is less than the number of
					// available seats
					if (isNumeric(selection) == true && Integer.parseInt(selection) < movieSession.getNumberOfSeats()) {
						// return the number of people specified
						userInput.close();
						return Integer.parseInt(selection);
					} else {
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

	// A function to handle the adding of bookings, remember to call the
	// jMossData method of addBooking(); to store it in the records.
	public boolean newBooking(String customerEmail, String suburb, MovieSession movieSession, int bookedSeats) {
		Scanner userInput = new Scanner(System.in);
		String selection;
		
		System.out.println("Please confirm your booking.");
		System.out.println("customerEmail");
		System.out.println("Suburb");
		System.out.println(MovieSession);
		System.out.println(bookedSeats);
		System.out.println("Would you like to confirm your booking?");
		selection = userInput.nextLine();
		
		
		if (selection.equals("Yes")== true){
			
		}
		
		
		
		return false;
	}

}

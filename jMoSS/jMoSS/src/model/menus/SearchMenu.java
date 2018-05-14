package model.menus;
import java.io.*;
import java.util.*;
import model.jMossData;
import interfaces.MenuInterface;
import model.records.Booking;
import model.records.MovieSession;

public class SearchMenu implements MenuInterface {

	@Override
	public void displayMenu() {
		/*
		 * This will be the primary display of the menu, designed purely for output.
		 * If sub menus exist, it may be necessary to alter or add arguements for this.
		 * In the case of sub menus for a section, consider the use switches, and a storage variable
		 * for user input selection
		 */
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

	@Override
	public void handleMenu() {
		/*
		 * This will be the primary handler of the menu, for dealing with user input.
		 * Here will be how the system responds to user input, and thus correct and incorrect responses, in conjunction with the
		 * displayMenu(); function.
		 */
		String input = null;
		do {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				input = reader.readLine();
				if(input.equals("1")) {
					searchMovie();
				}
				else if(input.equals("2")) {
					searchCineplex();
				}
				reader.close();
			} catch (IOException e) {}
		} while(input == "0");
	}
	
	/*
	 * These functions are currently placeholders.
	 * in reality, the return type will likely be different. (Possibly boolean or void?)
	 * These methods will have to print out the results of a search and wait for user input to select a given result.
	 * Remember that anything outside of displayMenu and handleMenu can be freely changed to fit required implementation.
	 */
	public MovieSession searchTheatre(String theatreName)
	{
		ArrayList<MovieSession> movieSessions = new ArrayList<MovieSession>();
		for(MovieSession movies : jMossData.getInstance().getMovieSessions()) {
			if(movies.getCineplex().equalsIgnoreCase(theatreName)) {
				System.out.println(movies.getAsFormattedString());
				movieSessions.add(movies);
			}
		}
		if(movieSessions.size() == 0) {
			System.out.println("No Results Found");
		}else {
			for(int i = 0; i < movieSessions.size(); i++) {
				int index = i + 1;
				System.out.println((index) + " " + movieSessions.get(i).getAsFormattedString());
			}
			//there are movies which can be selected
			System.out.println();
			System.out.println("Press any key to make a booking or enter 0 to go back: ");
			String input = null;
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				while((input = reader.readLine()) != null) {
					if(input.equals("0")) {
						//user ends function and goes back to search menu
						break;
					}else {
						//user will make a booking
						makeAbooking(movieSessions);
					}		
				}
			}catch(IOException e) {	}
		}
		return null;
	}

	public MovieSession searchMovieName(String movieName)
	{
		ArrayList<MovieSession> movieSessions = new ArrayList<MovieSession>();
		for(MovieSession movies :  jMossData.getInstance().getMovieSessions()) {
			if(movies.getMovieName().equalsIgnoreCase(movieName)) {
				movieSessions.add(movies);
			}
		}
		if(movieSessions.size() == 0) {
			System.out.println("No Results Found");
		}
		else{
			for(int i = 0; i < movieSessions.size(); i++) {
				int index = i + 1;
				System.out.println((index) + " " + movieSessions.get(i).getAsFormattedString());
			}
			//there are movies which can be selected
			System.out.println();
			System.out.println("Press any key to make a booking or enter 0 to go back: ");
			String input = null;
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				while((input = reader.readLine()) != null) {
					if(input.equals("0")) {
						//user ends function and goes back to search menu
						break;
					}else {
						//user will make a booking
						makeAbooking(movieSessions);
					}		
				}
			}catch(IOException e) {	}
			
		}
		return null;
	}
	
	public void searchMovie() {
		String title = null;
		System.out.println("=============================");
		System.out.println();
		System.out.println("Search");
		System.out.println("Search movie");
		System.out.println();
		System.out.println("=============================");
		System.out.println();
		System.out.println("Please enter a movie title (0 to go back): ");
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			while((title = reader.readLine()) != null){
				if(title.equals("0")) {
					displayMenu();
					break;
				}
				searchMovieName(title);
				searchMovie();
			}
			reader.close();
		} catch (IOException e) {}
		System.out.println();
	}
	
	public void searchCineplex() {
		String title = null;
		System.out.println("=============================");
		System.out.println();
		System.out.println("Search");
		System.out.println("Search cineplex");
		System.out.println();
		System.out.println("=============================");
		System.out.println();
		System.out.println("Please enter a location (0 to go back): ");
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			while((title = reader.readLine()) != null){
				if(title.equals("0")) {
					displayMenu();
					break;
				}
				searchMovieName(title);
				searchMovie();
			}
			reader.close();
		} catch (IOException e) {}
		System.out.println();
	}
	
	public void makeAbooking(ArrayList<MovieSession> movieSessions) {
		System.out.println("Please select a booking from 1 to " + movieSessions.size());
		System.out.println("Enter 0 to go back to the search menu: ");
		
		//user input to select booking
		//user input to select booking
		String input = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			while((input = reader.readLine()) != null) {
				if(input.equals("0")) {
					//break loop and head back to search menu
					break;
				}
				else {
					int value = Integer.valueOf(input);
					//create booking based on user input
					if(value >= 1 && value <= movieSessions.size()) {
						value--;
						MovieSession dummy = movieSessions.get(value);
						String custEmail = null;
						String custsub = null;
						String bookingNum = null;
						Booking booking = new Booking(custEmail, custsub, dummy, 2, bookingNum);
					}
				}
			}
		} catch (IOException e) {}
	}
}

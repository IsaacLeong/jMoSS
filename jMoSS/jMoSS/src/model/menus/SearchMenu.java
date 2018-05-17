package model.menus;
import java.io.*;
import java.util.*;
import model.jMossData;
import interfaces.MenuInterface;
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
		int matchCount = 0;
		ArrayList<MovieSession> movieSessions = jMossClient.data.getMovieSessions();
		for(int i = 0; i < movieSessions.size(); i++) {
			if(movieSessions.get(i).getCineplex().equalsIgnoreCase(theatreName)) {
				System.out.println(movieSessions.get(i).getAsFormattedString());
				matchCount++;
			}
		}
		if(matchCount == 0) {
			System.out.println("No Results Found");
		}
		return null;
	}

	public MovieSession searchMovieName(String movieName)
	{
		int matchCount = 0;
		ArrayList<MovieSession> movieSessions = jMossClient.data.getMovieSessions();
		for(int i = 0; i < movieSessions.size(); i++) {
			if(movieSessions.get(i).getMovieName().equals(movieName)) {
				System.out.println(movieSessions.get(i).getAsFormattedString());
				matchCount++;
			}
		}
		if(matchCount == 0) {
			System.out.println("No Results Found");
		}
		return null;
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

}

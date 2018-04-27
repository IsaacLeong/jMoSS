package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import interfaces.jMossIOInterface;
import model.records.*;

public class jMossData implements jMossIOInterface {
	
	/*
	 * This in combination with the getInstance(); method, guarantees there will only be one instance of this objects.
	 * This is for record consistency
	 */
	public static jMossData instance = null;
	
	
	/*
	 * These ArrayLists, are data containers, holding all instances of the objects created for the program
	 * When save or load is called, these are what will be affected
	 * and when searching for data, it will all be centralized here.
	 */
	private ArrayList<Booking> bookings = new ArrayList<Booking>();
	private ArrayList<Clerk> clerks = new ArrayList<Clerk>();
	private ArrayList<MovieSession> movieSessions = new ArrayList<MovieSession>();
	
	//Constructor
	private jMossData() {
		/* 
		 * Private constructor for this object, used to guarantee that there will only be one
		 * instance for data consistency.
		 */
	}
	
	/*
	 * Call this method to access the class.
	 * for example, if you need to access the bookings records, to say, write a search function
	 * when calling the ArrayList of bookings you would call jMossDataExampleName.getInstance().getBookings();
	 */
	static public jMossData getInstance() {
		if (instance == null) {
			instance = new jMossData();
		}

		return instance;
	}
	
	//This method will store and save all data into the container for access, when exiting the program.
	@Override
	public void save() {
		saveClerks();
		saveBookings();
		saveMovieSessions();
	}
	
	//This method will store and save all data into the container for access, when initializing the program.
	@Override
	public void load() {
		loadClerks();
		loadMovieSessions();
		loadBookings();
	}
	
	//Used to return all bookings for access in other classes.
	public ArrayList<Booking> getBookings() {
		return bookings;
	}
	
	//Used to return all Clerks for access in other classes.
	public ArrayList<Clerk> getClerks() {
		return clerks;
	}
	
	//Used to return all MovieSessions for access in other classes.
	public ArrayList<MovieSession> getMovieSessions() {
		return movieSessions;
	}
	
	//Used to add a new booking to the array of bookings.
	public boolean addBooking(Booking booking) {
		
		int checkSeatNum = booking.getMovieSession().getNumberOfSeats() - booking.getBookedSeats();
		
		if(checkSeatNum >= 0) {
			booking.getMovieSession().setNumberOfSeats(checkSeatNum);
			bookings.add(booking);
			return true;
		}
		else {
			System.out.println("Insufficient Seating to make this booking");
			return false;
		}
	}
	
	//Used to remove bookings from the list.
	public void removeBooking(String bookingNumber) {
		
	}
	
	//Used to add a new movie session into the array. Typically for population of data container from file.
	public void addMovieSession(MovieSession movieSession) {
		this.movieSessions.add(movieSession);
	}
	
	//Used to add a new clerk into the array. Typically for population of data container from file.
	public void addClerk(Clerk clerk) {
		this.clerks.add(clerk);
	}
	
	//Returns a movie session with a given ID value, used for loading and linking data containers.
	public MovieSession getMovieSession(String movieSessionId) {
		for(MovieSession movieSession : movieSessions)
		{
			if(movieSession.getMovieSessionId().equals(movieSessionId) == true)
			{
				return movieSession;
			}
		}
		return null;
	}
	
	//Writes Clerk records to Clerks.txt
	public void saveClerks() {
		try {
			PrintWriter writer = new PrintWriter("Clerks.txt");
		
			for(Clerk clerk : clerks) {
				writer.println(clerk.getAsFormattedString());
			}
			
			writer.close();
		}
		
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	//Writes Booking records to Bookings.txt
	public void saveBookings() {
		try {
			PrintWriter writer = new PrintWriter("Booking.txt");
		
			for(Booking booking : bookings) {
				writer.println(booking.getAsFormattedString());
			}
			
			writer.close();
		}
		
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	//Writes MovieSession records to MovieSessions.txt
	public void saveMovieSessions() {
		try {
			PrintWriter writer = new PrintWriter("MovieSession.txt");
		
			for(MovieSession movieSession : movieSessions) {
				writer.println(movieSession.getAsFormattedString());
			}
			
			writer.close();
		}
		
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	//Loads Clerks from file to the System.
	public void loadClerks() {
		try {
			
			//Initialize file readers
			FileReader loader = new FileReader("Clerks.txt");
			BufferedReader inputRead = new BufferedReader(loader);
			
			//Tokenizer loop
			do {
				
				//Reads a line
				String inputLine = inputRead.readLine();
				
				//If line is null, break loop
				if (inputLine == null){
					break;
				}
				
				//Initialize tokenizer
				StringTokenizer tokenizer = new StringTokenizer(inputLine);
				
				//Iterates through the inputLine, then separates tokens into variables
				String username = tokenizer.nextToken("/");
				String password = tokenizer.nextToken("/");
				
				//Creates a new clerkObject
				Clerk loadedClerk = new Clerk(username, password);
				
				//Adds clerk to system
				addClerk(loadedClerk);
				
			} while(true);
			
			inputRead.close();
			
		} 
		
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Loads MovieSessions from file to System (NOTE: ALWAYS CALL BEFORE loadBookings)
	public void loadMovieSessions() {
		try {
			//Initialize file readers
			FileReader loader = new FileReader("MovieSession.txt");
			BufferedReader inputRead = new BufferedReader(loader);
			
			//Tokenizer loop
			do {
				
				//Loop ends, if line is null
				String inputLine = inputRead.readLine();
				
				if (inputLine == null){
					break;
				}
				
				//Initialize tokenizer;
				StringTokenizer tokenizer = new StringTokenizer(inputLine);
				
				//Variables tokenized, held in variables to create new movieSession
				String movieSessionId = tokenizer.nextToken("/");
				String movieName = tokenizer.nextToken("/");
				String day = tokenizer.nextToken("/");
				String sessionTime = tokenizer.nextToken("/");
				//Number of Seats is determined by the bookings, set to default 20 upon initializing system
				int numberOfSeats = 20;
				tokenizer.nextToken("/");
				String cineplex = tokenizer.nextToken("/");
				
				//Placeholder movieSession object created.
				MovieSession loadedMovieSession = new MovieSession(movieSessionId, movieName, day, sessionTime, numberOfSeats, cineplex);
				
				//MovieSession added to Arraylist.
				addMovieSession(loadedMovieSession);
				
			} while(true);
			
			inputRead.close();
			
		} 
		
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Loads Bookings from file to System
	public void loadBookings() {
		try {
			//Initialize readers
			FileReader loader = new FileReader("Booking.txt");
			BufferedReader inputRead = new BufferedReader(loader);
			
			//Tokenizer loop
			do {
				
				String inputLine = inputRead.readLine();
				
				//Loop breaks if input is null
				if (inputLine == null){
					break;
				}
				
				//initialize tokenizer
				StringTokenizer tokenizer = new StringTokenizer(inputLine);
				
				//Variables hold values to initialize a placeholder booking object
				String customerEmail = tokenizer.nextToken("/");
				String suburb = tokenizer.nextToken("/");
				
				//MovieSession is determined by the MovieSessionId, connects data containers
				MovieSession movieSession = getMovieSession(tokenizer.nextToken("/"));
				int bookedSeats = Integer.parseInt(tokenizer.nextToken("/"));
				String theatreName = tokenizer.nextToken("/");
				
				//Placeholder booking object created
				Booking loadedBooking = new Booking(customerEmail, suburb, movieSession, bookedSeats, theatreName);
				
				//Booking object added to Arraylist
				addBooking(loadedBooking);
				
			} while(true);
			
			inputRead.close();
			
		} 
		
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}



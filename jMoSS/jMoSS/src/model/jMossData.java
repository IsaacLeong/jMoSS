package model;

import java.util.ArrayList;

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
	private ArrayList<Booking> bookings;
	private ArrayList<Clerk> clerks;
	private ArrayList<MovieSession> movieSessions;
	
	
	

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
		// TODO Auto-generated method stub
	}
	
	//This method will store and save all data into the container for access, when initializing the program.
	@Override
	public void load() {
		// TODO Auto-generated method stub
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
	public void addBooking(Booking booking) {
		
	}
	
	//Used to remove bookings from the list.
	public void removeBooking(String bookingNumber) {
		
	}
	
	//Used to add a new movie session into the array. Typically for population of data container from file.
	public void addMovieSession(MovieSession movieSession) {
		
	}
	
	//Used to add a new clerk into the array. Typically for population of data container from file.
	public void addClerk(Clerk clerk) {
		
	}
	
}

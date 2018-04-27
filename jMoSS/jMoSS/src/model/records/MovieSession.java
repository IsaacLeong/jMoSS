package model.records;

import interfaces.RecordInterface;

public class MovieSession implements RecordInterface{
	
	private String movieSessionId;
	private String movieName;
	private Day day;
	private String sessionTime;
	private int numberOfSeats;
	private Cineplex cineplex;
	
	// Constructor for bookings, call whenever making a new booking.
	public MovieSession(String movieSessionId, String movieName, String day, String sessionTime, int numberOfSeats, String cineplex) {
		
		//MovieSessionId is for differentiating movie sessions, and for storing a callable, unique save value for bookings.
		setMovieSessionId(movieSessionId);
		setMovieName(movieName);
		setDay(day);
		setSessionTime(sessionTime);
		setNumberOfSeats(numberOfSeats);
		setCineplex(cineplex);
	}
	
	


	//Returns the Booking a formatted string for saving to files.
	@Override
	public String getAsFormattedString() {
		String saveString = getMovieSessionId() + 
					  "/" + getMovieName() +  
					  "/" + getDay() + 
					  "/" + getSessionTime() + 
					  "/" + getNumberOfSeats() + 
					  "/" + getCineplex() + "/";
		return saveString;
	}
	
	//Below here are just traditional getters and setters.
	
	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getSessionTime() {
		return sessionTime;
	}

	public void setSessionTime(String sessionTime) {
		this.sessionTime = sessionTime;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public String getCineplex() {
		String outputCineplex = cineplex.toString();
		return outputCineplex.replace('_', ' ');
	}

	
	public void setCineplex(String cineplex) {
		cineplex = cineplex.replace(' ', '_');
		this.cineplex = Cineplex.valueOf(cineplex.toUpperCase());
	}


	public String getMovieSessionId() {
		return movieSessionId;
	}


	public void setMovieSessionId(String movieSessionId) {
		this.movieSessionId = movieSessionId;
	}

	public String getDay() {
		return day.toString();
	}


	public void setDay(String day) {
		this.day = Day.valueOf(day.toUpperCase());
	}

}

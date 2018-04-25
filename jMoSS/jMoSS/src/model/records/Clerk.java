package model.records;

import interfaces.RecordInterface;

public class Clerk implements RecordInterface{

	private String username;
	private String password;
	
	// Constructor for bookings, call whenever making a new booking.
	public Clerk(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}
	
	
	//Returns the Booking a formatted string for saving to files.
	@Override
	public String getAsFormattedString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//Below here are just traditional getters and setters.
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	

}

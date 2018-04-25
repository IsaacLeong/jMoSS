package model.menus;

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
		
	}

	@Override
	public void handleMenu() {
		/*
		 * This will be the primary handler of the menu, for dealing with user input.
		 * Here will be how the system responds to user input, and thus correct and incorrect responses, in conjunction with the
		 * displayMenu(); function.
		 */
		
	}
	
	/*
	 * These functions are currently placeholders.
	 * in reality, the return type will likely be different. (Possibly boolean or void?)
	 * These methods will have to print out the results of a search and wait for user input to select a given result.
	 * Remember that anything outside of displayMenu and handleMenu can be freely changed to fit required implementation.
	 */
	public MovieSession searchTheatre(String theatreName)
	{
		return null;
	}

	public MovieSession searchMovieName(String movieName)
	{
		return null;
	}

}

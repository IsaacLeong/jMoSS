package client;

import model.jMossData;
import model.menus.BookingMenu;
import model.menus.EmailSearchMenu;
import model.menus.LogInMenu;
import model.menus.MainMenu;
import model.menus.SearchMenu;

public class testClient {
	//public static jMossData data = new jMossData();
	public static void main(String[] args) {
		
		jMossData.getInstance().load();
		
		BookingMenu bookingMenu = new BookingMenu();
		MainMenu main = new MainMenu();
		EmailSearchMenu email = new EmailSearchMenu();
		SearchMenu search = new SearchMenu();
		
		
		
		/**SPRINT 2 TESTING **/
		//email.handleMenu();
		
		//bookingMenu.handleMenu();
		
		//searching for a cineplex doesn't work because it's using the wrong function
		//search.displayMenu();
		//search.handleMenu();
		
		//logging out works, but if No is chosen then the menu will not print again
		main.handleMenu();
		
		
		/** SPRINT 1 TESTING **/
		//testing if get sessios times and options work
		//bookingMenu.displaySessionTime();
		//System.out.println(bookingMenu.getSessionTimes("Jurrasic World: Fallen Kingdom", "ST KILDA"));
		
		
		//bookingMenu.displayNumberOfPpl();
		
		//testing if logout works
		//main.handleMenu();
		
		/* Handles invalid user and password combinations */
		/*LogInMenu logInMenu = new LogInMenu();
		
		logInMenu.displayMenu();
		logInMenu.handleMenu();*/
		
//		data.load();
//		SearchMenu menu = new SearchMenu();
//		menu.displayMenu();
//		menu.handleMenu();
		
	}
}

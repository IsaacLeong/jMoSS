package client;

import java.util.Scanner;

import model.jMossData;
import model.menus.MainMenu;
import model.records.Clerk;
import java.awt.*;

public class testClient {
	//public static jMossData data = new jMossData();
	public static void main(String[] args) {
		
		jMossData.getInstance().load();
		
		Scanner userInput = new Scanner(System.in);
		Clerk clerk = new Clerk("Beep", "Beep");
		MainMenu test1 = new MainMenu(userInput, clerk);
		
		test1.handleMenu();
		Toolkit.getDefaultToolkit().beep();
		//testing if get sessions times and options work
		//bookingMenu.displaySessionTime();
		//System.out.println(bookingMenu.getSessionTimes("Jurrasic World: Fallen Kingdom", "ST KILDA"));
		
		
		//bookingMenu.displayNumberOfPpl();
		
		//testing if logout works
		//main.handleMenu();
		
		/* Handles invalid user and password combinations */
		/*LogInMenu logInMenu = new LogInMenu();
		
		logInMenu.displayMenu();
		logInMenu.handleMenu();*/
		
		//data.load();
		//SearchMenu menu = new SearchMenu();
		//menu.displayMenu();
		//menu.handleMenu();
		
	}
}

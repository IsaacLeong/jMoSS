package client;

import java.awt.Toolkit;
import java.util.Scanner;

import model.jMossData;
import model.menus.LogInMenu;
import model.menus.MainMenu;

public class jMossClient {

	public static void main(String[] args) {
		//Checks if program is still running (Validated by loginmenu)
		boolean running = true;
		//Checks if logged in correctly
		boolean loggedIn = false;
		//User input scanner
		Scanner userInput = new Scanner(System.in);
		
		//System loads.
		jMossData.getInstance().load();
		
		do {
			//Login menu created
			LogInMenu login = new LogInMenu(userInput);
			//login handled
			login.handleMenu();
			//Checked for failed login, or exit attempt
			loggedIn = login.getValidUser();
			running = login.getRunning();
			
			//If login Successful, main menu runs
			while(loggedIn == true){
				MainMenu mainMenu = new MainMenu(userInput, login.getCurrentUser());
				mainMenu.handleMenu();
				loggedIn = false;
			}
			
		}while(running == true);
		
		
		//System Saves
		jMossData.getInstance().save();
		
		//final userInput Closed
		userInput.close();
		
		//System beep
		Toolkit.getDefaultToolkit().beep();
	}

}

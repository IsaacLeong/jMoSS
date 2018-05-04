package model.menus;

import interfaces.MenuInterface;
import model.records.Clerk;
import java.util.*;


public class MainMenu implements MenuInterface {
	
	Clerk activeClerk;
	
	@Override
	public void displayMenu() {
		/*
		 * This will be the primary display of the menu, designed purely for output.
		 * If sub menus exist, it may be necessary to alter or add arguements for this.
		 * In the case of sub menus for a section, consider the use switches, and a storage variable
		 * for user input selection
		 */
		
		//fill here the rest of the menu
		System.out.println("0. Logout");
		System.out.println("=============================");
		System.out.println("Choose an option (0 - 4): ");
		

		
	}

	@Override
	public void handleMenu() {
		/*
		 * This will be the primary handler of the menu, for dealing with user input.
		 * Here will be how the system responds to user input, and thus correct and incorrect responses, in conjunction with the
		 * displayMenu(); function.
		 */
		
		displayMenu();
		
		Scanner userInput = new Scanner(System.in);
		
		String selection;
		
		//keep the user in this loop until they enter a valid option
		while(true)
		{		
			//user selects option they want
			selection = userInput.nextLine();
			
			//if user selects 0, break loop and return to login menu (which is also another loop)
			if(selection.equals("0") == true)
			{
				//keep the user in the loop until they enter a valid selection
				do 
				{
					//confirming if the user wants to logout
					System.out.println("Are you sure you want to logout? (Y/N): ");
					selection = userInput.nextLine();
					
					
					if(selection.equalsIgnoreCase("y") == true || selection.equalsIgnoreCase("yes") == true)
					{
						userInput.close();
						return;
					}
					else if(selection.equalsIgnoreCase("n") == true || selection.equalsIgnoreCase("no") == true)
					{
						break;
					}
					else
					{
						System.out.println("Invalid selection. Please try again.");
					}
					
				}while (true);
				
			}
			//further else if statements to call the other menus - you can move to switch statement if you want
				
		}
			
			
	}

}

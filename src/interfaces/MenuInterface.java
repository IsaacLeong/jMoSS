package interfaces;

import java.util.Scanner;

public interface MenuInterface {
	
	Scanner userInput = null;
	
	public abstract void displayMenu();
	
	public abstract void handleMenu();
	
	public abstract void setScanner(Scanner userInput);
}

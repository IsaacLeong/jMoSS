package client;

import model.jMossData;
import model.menus.BookingMenu;

public class testClient {
	
	public static void main(String[] args) {
		
		jMossData.getInstance().load();
		BookingMenu bookingMenu = new BookingMenu();
		
		bookingMenu.getSessionTimes("Jurrasic World: Fallen Kingdom", "ST KILDA");
		
	}
}

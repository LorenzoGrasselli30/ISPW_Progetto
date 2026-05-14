package application.view.cli;

import java.util.Scanner;

import application.controller.application.BookingApplicationController;
import application.model.bean.BookingContext;
import application.model.bean.BookingDTO;

public class TicketCLIView implements StartCLI {
	
	private BookingDTO currentBooking;
	private BookingApplicationController bookingController;
	private Scanner scanner;
	private NavigatorCLI navigator;
	
	public TicketCLIView(NavigatorCLI navigator, BookingDTO booking) {
		this.bookingController = new BookingApplicationController(); 
		this.scanner = new Scanner(System.in);
		this.navigator = navigator;
		this.currentBooking = booking;
	}

	@Override
	public void start() {
		System.out.println("\n");
		System.out.println("|#### Ticket: " + currentBooking.getBookingID() + "####|");
		
		
	}

	private void pause() {
	    System.out.println("Premi Invio per continuare...");
	    if (scanner.hasNextLine()) {
	        scanner.nextLine();
	    }
	}
}

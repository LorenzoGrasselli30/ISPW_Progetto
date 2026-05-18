package application.view.cli;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import application.controller.application.BookingApplicationController;
import application.model.bean.BookingContext;
import application.model.bean.BookingDTO;
import application.model.bean.GuestInformationDTO;

//Dalle istruzioni del progetto: System.out* CLI-related smells are allowed
@SuppressWarnings("java:S106")
public class TicketCLIView implements StartCLI {
	
	private BookingDTO currentBooking;
	private Scanner scanner;
	
	public TicketCLIView(NavigatorCLI navigator, BookingDTO booking) {
		this.scanner = new Scanner(System.in);
		this.currentBooking = booking;
	}

	@Override
	public void start() {
		//Formatter utilizzato per la date dd/mm/aaaa
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		System.out.println("\n");
		System.out.println("|#### Ticket: " + currentBooking.getBookingID() + " ####|");
		System.out.println("Nome traveler: " + currentBooking.getTravelerName() + " " + currentBooking.getTravelerSurname());
		System.out.println("Nome provider: " + currentBooking.getProviderName());
		System.out.println("Il biglietto è valido per il giorno: " + currentBooking.getBookedDate().format(formatter));
		
		System.out.println("\nInformazoni sull'attività");
		System.out.println("ID: " + currentBooking.getBookingID());
		System.out.println("Nome attività: " + currentBooking.getActivityName());
		if (currentBooking.getGuideService()) {
			System.out.println("Tour guidato: SI");
		} else {
			System.out.println("Tour guidato: NO");
		}
		if (currentBooking.getShuttleService()) {
			System.out.println("Servizio navetta: SI");
		} else {
			System.out.println("Servizio navetta: NO");
		}
		
		System.out.println("\nInformazioni sui partecipanti");
		int i=1;
		for (GuestInformationDTO guest : currentBooking.getGuests()) {
			System.out.println("Informazioni " + i + "° partecipante");
			System.out.println("Nome: " + guest.getName());
			System.out.println("Cognome: " + guest.getSurname());
			System.out.println("Data di nascita: " + guest.getDateOfBirth().format(formatter));
			if (currentBooking.getFullTickets() >= i) {
				System.out.println("Tipologia di biglietto: Intero");
			} else {
				System.out.println("Tipologia di biglietto: Ridotto");
			}
		}
		
		pause();
	}

	private void pause() {
	    System.out.println("Premi Invio per continuare...");
	    if (scanner.hasNextLine()) {
	        scanner.nextLine();
	    }
	}
}

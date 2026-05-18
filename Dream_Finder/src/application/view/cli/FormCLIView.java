package application.view.cli;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import application.controller.application.BookingApplicationController;
import application.model.bean.BookingContext;
import application.model.bean.GuestInformationDTO;

//Dalle istruzioni del progetto: System.out* CLI-related smells are allowed
@SuppressWarnings("java:S106")
public class FormCLIView implements StartCLI {
	
	private static final String PARTECIPANTE = "° partecipante";
	
	private BookingContext context;
	private Scanner scanner;
	private NavigatorCLI navigator;
	
	public FormCLIView(NavigatorCLI navigator, BookingContext context) {
		this.scanner = new Scanner(System.in);
		this.navigator = navigator;
		this.context = context;
	}

	@Override
	public void start() {
		//Formatter utilizzato per la date dd/mm/aaaa
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				
		List<GuestInformationDTO> guests = new ArrayList<>();
		boolean allFieldsFilled = true;
    	boolean validAgeForTicket = true;
		
		System.out.println("\n");
		System.out.println("|#### Dati sui partecipanti ####|");
		
		int i=1;		
		while (i <= (context.getnFullTickets()+context.getnReducedTickets())) {
			GuestInformationDTO guest = new GuestInformationDTO();
			
			if (i <= context.getnFullTickets()) {
				System.out.println("Tipo partecipante: Biglietto intero");
			} else {
				System.out.println("Tipo partecipante: Biglietto ridotto");
			}
			
			System.out.println("Inserisci il nome del " + i + PARTECIPANTE);
			guest.setName(scanner.nextLine());
			System.out.println("Inserisci il cognome del " + i + PARTECIPANTE);
			guest.setSurname(scanner.nextLine());
			System.out.println("Inserisci la data di nascita del " + i + PARTECIPANTE);
			guest.setDateOfBirth(LocalDate.parse(scanner.nextLine(), formatter));
				
			allFieldsFilled = this.validateFieldsFilled(guest.getName(), guest.getSurname(), guest.getDateOfBirth());
			validAgeForTicket = this.validateAgeForTicket(guest.getDateOfBirth(), i);
			
			if (!allFieldsFilled || !validAgeForTicket) {
				pause();
				continue;
			}
			
			guests.add(guest);
			i++;
		}
		
		//Inserisco la lista degli ospiti nel booking context
	    context.setGuests(guests);
	    
	    navigator.navigateToPayment(context);
	}
	
	private boolean validateFieldsFilled(String name, String surname, LocalDate dob) {
		if (name == null || name.trim().isEmpty() || 
            surname == null || surname.trim().isEmpty() || 
            dob == null) {
            
			System.out.println("Dati mancanti: Per favore, compila tutti i campi richiesti.");
            
            return false;
        }
	
    return true;
	}

	private boolean validateAgeForTicket(LocalDate dob, int participantIndex) {
		int age = Period.between(dob, LocalDate.now()).getYears();
		boolean isFullTicket = participantIndex <= context.getnFullTickets();

		if (isFullTicket && age <= 12) {
			System.out.println("Dati errati: I biglietti interi NON valgono per partecipanti con un età minore o uguale ai 12 anni.");
			return false;
		}

		if (!isFullTicket && age > 12) {
			System.out.println("Dati errati: I biglietti ridotti NON valgono per partecipanti con un età superiore ai 12 anni.");
			return false;
		}

    return true;
	}
	
	private void pause() {
	    System.out.println("Premi Invio per continuare...");
	    if (scanner.hasNextLine()) {
	        scanner.nextLine();
	    }
	}
}

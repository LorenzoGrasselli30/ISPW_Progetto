package application.view.cli;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import application.controller.application.BookingApplicationController;
import application.exception.AvailabilityException;
import application.exception.PaymentProcessingException;
import application.model.bean.BookingContext;

//Dalle istruzioni del progetto: System.out* CLI-related smells are allowed
@SuppressWarnings("java:S106")
public class PaymentCLIView implements StartCLI {
	
	private BookingContext context;
	private BookingApplicationController bookingController;
	private Scanner scanner;
	private NavigatorCLI navigator;
	
	public PaymentCLIView(NavigatorCLI navigator, BookingContext context) {
		this.context = context;
		this.bookingController = new BookingApplicationController(); 
		this.scanner = new Scanner(System.in);
		this.navigator = navigator;
	}

	@Override
	public void start() {
		boolean running = true; 
		while (running) {
			System.out.println("\n");
			System.out.println("|#### Schermata di pagamento ####|");
			
			System.out.println("Resoconto del pagamento:");
			System.out.println("x" + context.getnFullTickets() + " Biglietto intero " + context.getActivity().getPrice()*context.getnFullTickets() + "€");
			System.out.println("x" + context.getnReducedTickets() + " Biglietto ridotto " + (context.getActivity().getPrice()/3.0)*context.getnReducedTickets() + "€");
			
			if (context.isShuttleService() && context.getShuttlePrice() != null) {
				System.out.println("Servizio navetta: " + context.getShuttlePrice() + "€");
			} else {
				System.out.println("Servizio navetta: No");
			}
			
			if (context.isGuideService() && context.getGuidePrice() != null) {
				System.out.println("Tour guidato: " + context.getGuidePrice() + "€");
			} else {
				System.out.println("Tour guidato: No");
			}
			
			System.out.println("Totale: " + context.getTotalPrice() + "€");
			System.out.println("Procedere con il pagamento? [y: si, n: no]");
			String confirm = scanner.nextLine();
			
			if (confirm.equals("y")) {
				doPayment();
				running = false;
			} else if (confirm.equals("n")) {
				navigator.navigateToActivity(context.getActivity());
				running = false;
			} else {
				System.out.println("Inserisci una scelta valida");
				pause();
			}
		}
	}
	
	private void doPayment() {
		
		String cardNumber = null;
		String cvv = null;
		String ownerName = null;
		LocalDate expiredDate = null;
		
		boolean running = true; 
		while (running) {
			System.out.println("Vuoi utilizzare la carta salvata? [y: si, n: no]");
			String confirm = scanner.nextLine();
			
			if (confirm.equals("y")) {
				
				cardNumber = "4242424242424242";
				ownerName = "Mario Rossi";
				expiredDate = LocalDate.parse("2029-08-01");
				
				System.out.println("Inserisci il cvv");
				cvv = scanner.nextLine().trim();
				
				running = false;
			} else if (confirm.equals("n")) {
				
				System.out.println("Inserisci il numero della carta");
				cardNumber = scanner.nextLine().trim();
				System.out.println("Inserisci la data di scadenza della carta");
				expiredDate = fromString(scanner.nextLine().trim());
				System.out.println("Inserisci il cvv");
				cvv = scanner.nextLine().trim();
				System.out.println("Inserisci il nome del proprietario della carta");
				ownerName = scanner.nextLine().trim();
				
				running = false;
			} else {
				
				System.out.println("Inserisci una scelta valida");
				pause();
				
			}
			
			if (!cardNumber.trim().matches("\\d{16}")) {
				System.out.println("Il numero della carta deve contenere esattamente 16 cifre.");
				pause();
				running = true;
			}
			
			if (!cvv.trim().matches("\\d{3}")) {
				System.out.println("Il CVV deve contenere esattamente 3 cifre.");
				pause();
				running = true;
			}
			
			if (expiredDate == null) {
				System.out.println("Inserisci una data di scadenza della carta.");
				pause();
				running = true;
			}
			
			if (ownerName.isEmpty()) {
				System.out.println("Inserisci il nome del titolare della carta.");
				pause();
				running = true;
			}	
		}
		
		context.setCardNumber(cardNumber.trim());
		context.setCvv(cvv.trim());
		context.setExpiredDate(expiredDate);
		context.setOwnerName(ownerName);
		
		BookingContext updatedContext = null;
		
		try {
			
			updatedContext = bookingController.makeBooking(context);
			
		} catch (AvailabilityException ae) {
			System.out.println("Errore durante la prenotazione" + ae.getMessage());
			navigator.navigateToActivity(context.getActivity());
		} catch (PaymentProcessingException pe) {
			System.out.println("Errore durante la transazione" + pe.getMessage());
		}
		
		System.out.println("Richiesta di pagamento inviata dall'utente: " + updatedContext.getOwnerName());

		navigator.navigateToRecommendedActivities(updatedContext);
	}
	
	private void pause() {
	    System.out.println("Premi Invio per continuare...");
	    if (scanner.hasNextLine()) {
	        scanner.nextLine();
	    }
	}
	
	private LocalDate fromString(String text) {
		//Formatter utilizzato per la date mm/yy
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
		
        if (text == null || text.trim().isEmpty()) return null;
        YearMonth ym = YearMonth.parse(text, formatter);
        return ym.atDay(1); // giorno tecnico fisso
    }
}

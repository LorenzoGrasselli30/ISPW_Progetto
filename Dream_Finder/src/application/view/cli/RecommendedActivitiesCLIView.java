package application.view.cli;

import java.util.List;
import java.util.Scanner;

import application.controller.application.BookingApplicationController;
import application.model.bean.ActivityDTO;
import application.model.bean.BookingContext;
import application.model.bean.BookingDTO;
import application.model.bean.ReceiptDTO;

//Dalle istruzioni del progetto: System.out* CLI-related smells are allowed
@SuppressWarnings("java:S106")
public class RecommendedActivitiesCLIView implements StartCLI {
	
	private BookingContext context;
	private BookingApplicationController bookingController;
	private Scanner scanner;
	private NavigatorCLI navigator;
	
	public RecommendedActivitiesCLIView(NavigatorCLI navigator, BookingContext context) {
		this.bookingController = new BookingApplicationController(); 
		this.scanner = new Scanner(System.in);
		this.navigator = navigator;
		this.context = context;
	}
	
	@Override
	public void start() {
		ReceiptDTO currentReceipt;
		BookingDTO currentBooking;
		
		List<ActivityDTO> relatedInfo= bookingController.fetchRelatedInfo(
				context.getActivity().getActivityName(), 
				context.getActivity().getActivityType(),
				context.getActivity().getProviderName()
				);
		
		System.out.println("\n");
		System.out.println("Pronatazione effettuata correttamente");
		pause();
		
		while (true) {
			System.out.println("|#### Checkout ####|");
			System.out.println("1) Prenota un'attività consigliata");
			System.out.println("2) Visualizza il biblietto");
			System.out.println("3) Visualizza la ricevuta");
			System.out.println("4) Torna alla homepage");
			String choice = scanner.nextLine().trim();
			
			switch (choice) {
			case "1":
				int i = 1;
				System.out.println("|#### Attività Consigliate ####|");
				for (ActivityDTO activity : relatedInfo) {
					System.out.println(i + ")" + activity.getActivityName());
					i++;
				}
				System.out.println(i + ")Indietro");
				
				System.out.println("Inserisci la tua scelta:");
				
				choice = scanner.nextLine().trim();
					
				//Controlla se l'utente ha inserito una scelta valida 
				if (i < Integer.parseInt(choice)) {
					System.out.println("La tua scelta non è valida");
					pause();
					break;
				}
				
				//Controlla se l'utente ha cliccato indietro
				if (i == Integer.parseInt(choice)) {
					break;
				}
				
				navigator.navigateToActivity(relatedInfo.get(Integer.parseInt(choice)));
				
				break;
			case "2":
				currentBooking= bookingController.fetchCurrentTicket(context.getBookingID());
				
				navigator.navigateToTicket(currentBooking);
				break;
			case "3":
				currentReceipt= bookingController.fetchCurrentReceipt(context.getPaymentID());
				
				navigator.navigateToReceipt(currentReceipt);
				break;
			case "4":
				navigator.navigateToHomepage();
				break;
			default:
				System.out.println("Scelta non valida. Riprova.");
				pause();
			}
		}	
	}
	
	private void pause() {
	    System.out.println("Premi Invio per continuare...");
	    if (scanner.hasNextLine()) {
	        scanner.nextLine();
	    }
	}
}

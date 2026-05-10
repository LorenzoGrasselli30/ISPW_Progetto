package application.view.cli;

import java.util.List;
import java.util.Scanner;

import application.controller.application.BookingApplicationController;
import application.model.bean.ActivityDTO;

public class ActivityCLIView implements StartCLI {
	
	private BookingApplicationController bookingController;
	private ActivityDTO currentActivity;
	private Scanner scanner;
	private NavigatorCLI navigator;
	
	public ActivityCLIView(NavigatorCLI navigator, ActivityDTO activity) {
		this.bookingController= new BookingApplicationController();
		this.scanner = new Scanner(System.in);
		this.navigator = navigator;
		this.currentActivity = activity;
	}

	@Override
	public void start() {
		currentActivity = bookingController.fetchActivityInfo(currentActivity.getActivityName(), currentActivity.getProviderName());
		String unit = Boolean.TRUE.equals(currentActivity.getTimeInMinutes()) ? "minuti" : "ore";
		
		System.out.println("\n");
		System.out.println("|#### Info Attivita' ####|");
		System.out.println("Titolo: " + currentActivity.getActivityName());
		System.out.println("Descrizione: " + currentActivity.getDescription());
		System.out.println("Punteggio: " + currentActivity.getRate() + "/5");
		System.out.println("Numero di recenzioni: " + currentActivity.getnRating());
		System.out.println("Fornitore dell'attività: " + currentActivity.getProviderName());
		System.out.println("\n-----Informazioni sull'attività-----");
		System.out.println("Durata dell'attività: " + currentActivity.getDuration() + " " + unit);
		if (currentActivity.getFreeCancellation()) {
			System.out.println("Cancellazione gratuita fino a 24 ore prima");
		}
		if (currentActivity.getPayLater()) {
			System.out.println("Prenota ora e paga dopo");
		}
		if (currentActivity.getSkipLine()) {
			System.out.println("Salta la fila per la biblietteria");
		}
		
		Boolean running = true;
		while (running) {
			System.out.println("");
			System.out.println("1) Verifica la disponibilità");
			System.out.println("2) Aggiungi ai preferiti");
			System.out.println("3) Visualizza attività correlate");
			System.out.println("Inserisci la tua scelta:");
			
			String choice = scanner.nextLine().trim();
			
			switch (choice) {
				case "1":
					System.out.println("Inserisci il numero di biglietti interi");
					String a = scanner.nextLine().trim();
					System.out.println("Inserisci il numero di biglietti ridotti");
					String a = scanner.nextLine().trim();
					for () {
						
					}
					
					System.out.println("Inserisci la data di prenotazione");
					String a = scanner.nextLine().trim();
					System.out.println("Desideri il servizio di visita guidata? (y: si, n: no)");
					String a = scanner.nextLine().trim();
					if (a.equals("y")) {
						System.out.println("Lingue disponibili: Italiano, Inglese, Spagnolo, Francese, Tedesco");
						System.out.println("Seleziona una lingua disponibile");
						String a = scanner.nextLine().trim();
					}
					System.out.println("Desideri di usufruire del servizio navetta? (y: si, n: no)");
					String a = scanner.nextLine().trim();
					running = false;
					break;
				case "2":
					System.out.println("Funzionalità in fase di sviluppo...");
					pause();
					break;
				case "3":
					
					running = false;
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



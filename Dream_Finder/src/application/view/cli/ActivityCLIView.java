package application.view.cli;

import java.time.LocalDate;
import java.util.Scanner;

import application.configuration.UserSession;
import application.controller.application.BookingApplicationController;
import application.exception.AvailabilityException;
import application.model.bean.ActivityDTO;
import application.model.bean.BookingContext;
import application.observer.Observer;
import application.observer.PriceCalculator;

//Dalle istruzioni del progetto: System.out* CLI-related smells are allowed
@SuppressWarnings("java:S106")
public class ActivityCLIView implements StartCLI, Observer {
	
	private BookingApplicationController bookingController;
	private ActivityDTO currentActivity;
	private Scanner scanner;
	private NavigatorCLI navigator;

	//Pattern observer
	private PriceCalculator subject;
	
	public ActivityCLIView(NavigatorCLI navigator, ActivityDTO activity) {
		this.bookingController= new BookingApplicationController();
		this.scanner = new Scanner(System.in);
		this.navigator = navigator;
		this.currentActivity = activity;
		this.subject = new PriceCalculator();
	}

	@Override
	public void start() {
		currentActivity = bookingController.fetchActivityInfo(currentActivity.getActivityName(), currentActivity.getProviderName());
		
		//Registra l'ActivityGraphicController come Observer
		subject.registerObserver(this);
		Boolean running = true;
		while (running) {
			
			System.out.println("\n");
			System.out.println("|#### Info Attivita' ####|");
			System.out.println("Titolo: " + currentActivity.getActivityName());
			System.out.println("Descrizione: " + currentActivity.getDescription());
			System.out.println("Punteggio: " + currentActivity.getRate() + "/5");
			System.out.println("Numero di recenzioni: " + currentActivity.getnRating());
			System.out.println("Fornitore dell'attività: " + currentActivity.getProviderName());
		
			System.out.println("\n-----Informazioni sull'attività-----");
			String unit = Boolean.TRUE.equals(currentActivity.getTimeInMinutes()) ? "minuti" : "ore";
			System.out.println("Durata dell'attività: " + currentActivity.getDuration() + " " + unit);
			if (currentActivity.getFreeCancellation()) {
				System.out.println("Cancellazione gratuita fino a 24 ore prima");
			}
			if (currentActivity.getPayLater()) {
				System.out.println("Prenota ora e paga dopo");
			}
			if (currentActivity.getSkipLine()) {
				System.out.println("Salta la fila per la biglietteria");
			}
		
		
			System.out.println("");
			System.out.println("1) Verifica la disponibilità");
			System.out.println("2) Aggiungi ai preferiti");
			System.out.println("3) Visualizza attività correlate");
			System.out.println("4) Torna alla homepage");
			System.out.println("Inserisci la tua scelta:");
			
			String choice = scanner.nextLine().trim();
			
			switch (choice) {
				case "1":
					System.out.println("Inserisci il numero di biglietti interi");
					Integer fullTicketCount = Integer.parseInt(scanner.nextLine().trim());
					System.out.println("Inserisci il numero di biglietti ridotti");
					Integer reducedTicketCount = Integer.parseInt(scanner.nextLine().trim());
					for (LocalDate date : currentActivity.getAvaiblePlaces().keySet()) {
						System.out.print(" |" + date.toString() + "| ");
					}
					System.out.println("\nInserisci la data di prenotazione");
					LocalDate date = LocalDate.parse(scanner.nextLine().trim());
					System.out.println("Desideri il servizio di visita guidata? [y: si, n: no]");
					String confirm = scanner.nextLine().trim();
					Boolean guideTour = false;
					if (confirm.equals("y")) {
						guideTour = true;
						
						System.out.println("Lingue disponibili: ");
						System.out.println("1) Italiano");
						System.out.println("2) Inglese");
						System.out.println("3) Spagnolo");
						System.out.println("Seleziona una lingua disponibile");
						confirm = scanner.nextLine().trim();
					}
					System.out.println("Desideri di usufruire del servizio navetta? [y: si, n: no]");
					confirm = scanner.nextLine().trim();
					Boolean shuttleService = false;
					if (confirm.equals("y")) {
						shuttleService = true;
					} 
					
					recalculateTotal(fullTicketCount, reducedTicketCount, guideTour, shuttleService);
					confirm = scanner.nextLine().trim();
					if (confirm.equals("y")) {
						try {
							submitActivityForm(date, fullTicketCount, fullTicketCount, guideTour, shuttleService);
						} catch (AvailabilityException e) {
							System.out.println(e.getMessage());
							pause();
							break;
						}
					} else {
						break;
					}
					
					running = false;
					break;
				case "2":
					System.out.println("Funzionalità in fase di sviluppo...");
					pause();
					break;
				case "3":
					System.out.println("Funzionalità in fase di sviluppo...");
					pause();
					break;
				case "4":
					running = false;
					break;
				default:
					System.out.println("Scelta non valida. Riprova.");
					pause();
			}
		}
	}
	
	//Pattern observer
	@Override
	public void update() {
		Double observerState = subject.getPrice();
		System.out.println("Il totale della prenotazione ammonta a: " + observerState + "€ continuare? [y: si, n: no]");
	}
	
	private void recalculateTotal(Integer fullTicketCount, Integer reducedTicketCount, Boolean guideTour, Boolean shuttleService) {
		subject.calculatePrice(currentActivity, fullTicketCount, reducedTicketCount, guideTour, shuttleService);
	}
	
	private void pause() {
	    System.out.println("Premi Invio per continuare...");
	    if (scanner.hasNextLine()) {
	        scanner.nextLine();
	    }
	}
	
	public void submitActivityForm(LocalDate date, Integer fullTicketCount, Integer reducedTicketCount,
			Boolean guideTour, Boolean shuttleService) throws AvailabilityException {
		if (fullTicketCount < 1) {
			throw new AvailabilityException("Attenzione: La prenotazione deve comprendere almeno un biglietto intero");
		}
		
		bookingController.checkAvailability(currentActivity, date, (fullTicketCount+reducedTicketCount));
		
		BookingContext context = new BookingContext();
		
		context.setActivity(currentActivity);
		context.setnFullTickets(fullTicketCount);
		context.setnReducedTickets(reducedTicketCount);
		context.setGuideService(guideTour);
		context.setShuttleService(shuttleService);
		context.setBookedDate(date);
		
		// Prende i prezzi calcolati dal Subject
		context.setTotalPrice(subject.getPrice()); 
		context.setShuttlePrice(subject.getShuttlePrice());
		context.setGuidePrice(subject.getGuidePrice());
		
		UserSession session = UserSession.getInstance();
        
        if (session.getCurrentUser() != null) { //Caso utente già loggato
            String role = session.getCurrentUser().getUserRole().getStringName();
            
            if ("traveler".equals(role)) { 
            	navigator.navigateToForm(context);
            } else {
                throw new AvailabilityException("Attenzione: Per prenotare questa attivita' accedi o crea un account viaggiatore.");
            }
            
        } else { //Caso utente non loggato
        	navigator.navigateToLogin(context);
        }
	}
}



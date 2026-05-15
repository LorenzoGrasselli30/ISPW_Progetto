package application.view.cli;

import java.util.List;
import java.util.Scanner;

import application.controller.application.HomeApplicationController;
import application.model.bean.ActivityDTO;

//Dalle istruzioni del progetto: System.out* CLI-related smells are allowed
@SuppressWarnings("java:S106")
public class HomepageCLIView implements StartCLI {
	
	private static final String COMING_SOON = "Funzionalità in fase di sviluppo...";
	
	private HomeApplicationController homeController;
	private Scanner scanner; 
	private final NavigatorCLI navigator;
	
	public HomepageCLIView(NavigatorCLI navigator) {
		this.homeController= new HomeApplicationController();
		this.scanner = new Scanner(System.in);
		this.navigator = navigator;
	}

	@Override
	public void start() {
		boolean running = true;
		
		while (running) {
			System.out.println("\n");
			System.out.println("|#### Homepage ####|");
			System.out.println("1) Prenota un'attività");
			System.out.println("2) Scopri l'attività che fa per te");
			System.out.println("3) Cerca un itinerario");
			System.out.println("4) Effettua il login");
			System.out.println("5) Crea un itinerario");
			System.out.println("6) Crea un attività");
			System.out.println("7) Esci dal programma");
			System.out.println("Inserisci la tua scelta (es. '4' per effettuare il login):");
			
			String choice = scanner.nextLine();
			
			switch (choice) {
			case "1":
				List<ActivityDTO> activities= homeController.fetchActivities();
				choiceActivityCLI(activities);
				break;
			case "4":
				navigator.navigateToLogin(null);
				break;
			case "2":
			case "3":
			case "5":
			case "6":
				System.out.println(COMING_SOON);
				pause();
				break;
			case "7":
				System.out.println("Uscita dal programma in corso. A presto!");
				running = false; 
				break;
			default:
				System.out.println("Scelta non valida. Riprova.");
				pause();
			}
		}
		
		System.exit(0);
	}
	
	private void pause() {
	    System.out.println("Premi Invio per continuare...");
	    if (scanner.hasNextLine()) {
	        scanner.nextLine();
	    }
	}
	
	private void choiceActivityCLI(List<ActivityDTO> activities) {
		int i = 1;
		
		System.out.println("\n");
		System.out.println("|#### Homepage ####|");
		for (ActivityDTO activity : activities) {
			System.out.println(i + ")" + activity.getActivityName());
			i++;
		}
		System.out.println(i + ")Indietro");
		System.out.println("Inserisci la tua scelta:");
			
		String choice = scanner.nextLine().trim();
			
		//Controlla se l'utente ha inserito una scelta valida 
		if (i < Integer.parseInt(choice)) {
			System.out.println("La tua scelta non è valida");
			pause();
			return;
		}
		
		//Controlla se l'utente ha cliccato indietro
		if (i == Integer.parseInt(choice)) {
			return;
		}
			
		new ActivityCLIView(navigator, activities.get(Integer.parseInt(choice))).start();
	}
}

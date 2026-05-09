package application.view.cli;

import java.util.Scanner;

public class HomepageCLIView implements StartCLI {
	
	private Scanner scanner; 
	private final NavigatorCLI navigator;
	
	public HomepageCLIView(NavigatorCLI navigator) {
		this.scanner = new Scanner(System.in);
		this.navigator = navigator;
	}

	@Override
	public void start() {
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
		
		
		
	}
	
}

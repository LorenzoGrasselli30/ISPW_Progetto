package application.view.cli;

import java.util.Scanner;

import application.controller.application.LoginApplicationController;
import application.exception.ValidationException;
import application.model.bean.BookingContext;

//Dalle istruzioni del progetto: System.out* CLI-related smells are allowed
@SuppressWarnings("java:S106")
public class LoginCLIView implements StartCLI {
	
	private LoginApplicationController loginController; 
	private BookingContext context;
	private Scanner scanner;
	private NavigatorCLI navigator;
	
	public LoginCLIView(NavigatorCLI navigator, BookingContext context) {
		this.loginController = new LoginApplicationController(); 
		this.scanner = new Scanner(System.in);
		this.navigator = navigator;
		this.context = context;
	}

	@Override
	public void start() {
		boolean isAuthenticated = false;
		 
		System.out.println("\n");
		System.out.println("|#### Login ####|");

		// Acquisisci i dati dalla View
		System.out.println("Inserisci la mail");
		String email = scanner.nextLine().toLowerCase().trim();
		System.out.println("Inserisci la password");
		String password = scanner.nextLine().trim();
		
		//Invia i dati al Controller Applicativo dovrebbe utilizzare una bean ma in questo caso per il login non è necessario
		try {
			isAuthenticated = loginController.authenticate(email, password);
		} catch (ValidationException ve) {
			System.out.println("Errore durante il login: " + ve.getMessage());
		} catch (Exception e) {
			System.out.println("Qualcosa è andato storto, riprova più tardi.");		
	    }
        
        if (isAuthenticated) {
        	String userRole = loginController.getUserRole();
        	System.out.println("Login effettuato correttamente, tipo di utente loggato: " + userRole);
        	
        	if (context != null) {
        		navigator.navigateToForm(context);
        	} else {
        		navigator.navigateToHomepage();
        	}
        }        
	}
}

package application.view.cli;

import java.util.Scanner;

import application.controller.application.BookingApplicationController;
import application.model.bean.BookingContext;
import application.model.bean.ReceiptDTO;

//Dalle istruzioni del progetto: System.out* CLI-related smells are allowed
@SuppressWarnings("java:S106")
public class ReceiptCLIView implements StartCLI {
	
	private ReceiptDTO currentReceipt;
	private Scanner scanner;
	
	public ReceiptCLIView(ReceiptDTO receipt) {
		this.scanner = new Scanner(System.in);
		this.currentReceipt = receipt;
	}

	@Override
	public void start() {
		String maskedCard = "************";
		maskedCard += currentReceipt.getCardNumber().substring(currentReceipt.getCardNumber().length() - 4);
		
		System.out.println("\n");
		System.out.println("|#### Ricevuta: " + currentReceipt.getPaymentOutcome().getID() + "####|");
		
		System.out.println("Nominativo cliente: " + currentReceipt.getOwnerName());
		System.out.println("Nominativo esercente: " + currentReceipt.getProviderName());
		System.out.println("ID della transazione: " + currentReceipt.getPaymentOutcome().getID());
		System.out.println("Stato della transazione: " + currentReceipt.getPaymentOutcome());
		System.out.println("\nInformazioni della carta");
		System.out.println("Numero: " + maskedCard);
		System.out.println("Data di scadenza: " + currentReceipt.getExpiredDate());
		System.out.println("\nInformazioni sul prodotto acquistato");
		System.out.println("Nome dell'attività: " + currentReceipt.getPaymentOutcome().getDescription());
		System.out.println("Ingresso intero: " + currentReceipt.getnFullTicket() + " | Ingresso ridotto: " + currentReceipt.getnReducedTicket());
		System.out.println("Servizio navetta: " + currentReceipt.getShuttlePrice());
		System.out.println("Tour guidato: " + currentReceipt.getGuidePrice());
		System.out.println("Totale: " + currentReceipt.getTotalPrice());
		
		pause();
	}
	
	private void pause() {
	    System.out.println("Premi Invio per continuare...");
	    if (scanner.hasNextLine()) {
	        scanner.nextLine();
	    }
	}
}

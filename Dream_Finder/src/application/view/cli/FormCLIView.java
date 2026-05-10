package application.view.cli;

import application.model.bean.BookingContext;

@SuppressWarnings("java:S106")
public class FormCLIView implements StartCLI {

	public FormCLIView(NavigatorCLI navigator, BookingContext context) {
		
	}

	@Override
	public void start() {
		System.out.println("\n");
		System.out.println("|#### Dati sui partecipanti ####|");
		
	}

}

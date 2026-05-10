package application.view.cli;

import application.model.bean.BookingContext;

//Dalle istruzioni del progetto: System.out* CLI-related smells are allowed
@SuppressWarnings("java:S106")
public class LoginCLIView implements StartCLI {

	public LoginCLIView(NavigatorCLI navigatorCLIImplementation, BookingContext context) {
		
	}

	@Override
	public void start() {
		System.out.println("\n");
		System.out.println("|#### Login ####|");
	}

}

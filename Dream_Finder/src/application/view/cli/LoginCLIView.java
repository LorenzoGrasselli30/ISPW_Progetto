package application.view.cli;

import application.model.bean.BookingContext;

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

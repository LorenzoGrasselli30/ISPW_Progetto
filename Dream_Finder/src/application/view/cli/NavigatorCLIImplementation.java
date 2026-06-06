package application.view.cli;

import application.model.bean.ActivityDTO;
import application.model.bean.BookingContext;
import application.model.bean.BookingDTO;
import application.model.bean.ReceiptDTO;

public class NavigatorCLIImplementation implements NavigatorCLI {

	@Override
	public void navigateToHomepage() {
		new HomepageCLIView(this).start();
	}

	@Override
	public void navigateToLogin(BookingContext context) {
		new LoginCLIView(this, context).start();
	}

	@Override
	public void navigateToSignup() {
		//La schermata sign up per la versione CLI non è stata ancora implementata
	}

	@Override
	public void navigateToActivity(ActivityDTO activity) {
		new ActivityCLIView(this, activity).start();
	}

	@Override
	public void navigateToForm(BookingContext context) {
		new FormCLIView(this, context).start();
	}

	@Override
	public void navigateToPayment(BookingContext context) {
		new PaymentCLIView(this, context).start();
		
	}

	@Override
	public void navigateToReceipt(ReceiptDTO receipt) {
		new ReceiptCLIView(receipt).start();
		
	}

	@Override
	public void navigateToRecommendedActivities(BookingContext context) {
		new RecommendedActivitiesCLIView(this, context).start();	
	}

	@Override
	public void navigateToTicket(BookingDTO booking) {
		new TicketCLIView(this, booking).start();
		
	}

}

package application.view.cli;

import application.model.bean.ActivityDTO;
import application.model.bean.BookingContext;

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
		// TODO Auto-generated method stub
		
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
	public void navigateToReceipt() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void navigateToRecommendedActivities() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void navigateToTicket() {
		// TODO Auto-generated method stub
		
	}

}

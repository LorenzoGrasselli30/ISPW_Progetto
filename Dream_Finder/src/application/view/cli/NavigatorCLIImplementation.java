package application.view.cli;

import application.model.bean.ActivityDTO;

public class NavigatorCLIImplementation implements NavigatorCLI {

	@Override
	public void navigateToHomepage() {
		new HomepageCLIView(this).start();
	}

	@Override
	public void navigateToLogin() {
		// TODO Auto-generated method stub
		
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
	public void navigateToForm() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void navigateToPayment() {
		// TODO Auto-generated method stub
		
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

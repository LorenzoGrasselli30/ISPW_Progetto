package application.view.cli;

import application.model.bean.ActivityDTO;

public interface NavigatorCLI {
	void navigateToHomepage();
	void navigateToLogin();
	void navigateToSignup();
	void navigateToActivity(ActivityDTO activity);
	void navigateToForm();
	void navigateToPayment();
	void navigateToReceipt();
	void navigateToRecommendedActivities();
	void navigateToTicket();
}

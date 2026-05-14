package application.view.cli;

import application.model.bean.ActivityDTO;
import application.model.bean.BookingContext;

public interface NavigatorCLI {
	void navigateToHomepage();
	void navigateToLogin(BookingContext context);
	void navigateToSignup();
	void navigateToActivity(ActivityDTO activity);
	void navigateToForm(BookingContext context);
	void navigateToPayment(BookingContext context);
	void navigateToReceipt();
	void navigateToRecommendedActivities(BookingContext context);
	void navigateToTicket();
}

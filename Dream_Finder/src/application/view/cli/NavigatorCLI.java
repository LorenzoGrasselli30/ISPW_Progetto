package application.view.cli;

import application.model.bean.ActivityDTO;
import application.model.bean.BookingContext;
import application.model.bean.BookingDTO;
import application.model.bean.ReceiptDTO;

public interface NavigatorCLI {
	void navigateToHomepage();
	void navigateToLogin(BookingContext context);
	void navigateToSignup();
	void navigateToActivity(ActivityDTO activity);
	void navigateToForm(BookingContext context);
	void navigateToPayment(BookingContext context);
	void navigateToReceipt(ReceiptDTO receipt);
	void navigateToRecommendedActivities(BookingContext context);
	void navigateToTicket(BookingDTO booking);
}

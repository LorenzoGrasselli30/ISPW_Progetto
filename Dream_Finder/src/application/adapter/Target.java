package application.adapter;

public interface Target {
	Boolean verifyPayment(String cardNumber, String expiredDate, String activityName, 
			String customerName, String providerName, String amount);
}

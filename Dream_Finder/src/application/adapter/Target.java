package application.adapter;

public interface Target {
	void verifyPayment(String cardNumber, String expiredDate, String activityName, 
			String customerName, String providerName, Double amount);
}

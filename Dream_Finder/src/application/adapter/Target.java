package application.adapter;

import application.model.bean.PaymentOutcomeDTO;

public interface Target {
	PaymentOutcomeDTO verifyPayment(String cardNumber, String expiredDate, String Cvv, String activityName, 
			String customerName, String providerName, Double amount);
}

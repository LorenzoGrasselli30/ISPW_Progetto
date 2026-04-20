package application.adapter;

import java.time.LocalDate;

import application.exception.PaymentProcessingException;
import application.model.bean.PaymentOutcomeDTO;

public interface Target {
	PaymentOutcomeDTO verifyPayment(String cardNumber, LocalDate localDate, String cvv, String activityName, 
			String customerName, String providerName, Double amount) throws PaymentProcessingException;
}

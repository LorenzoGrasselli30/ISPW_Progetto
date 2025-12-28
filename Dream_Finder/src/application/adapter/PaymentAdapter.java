package application.adapter;

import application.payment.StripePayment;

public class PaymentAdapter implements Target {
	
	private StripePayment stripePayment;
	
	public PaymentAdapter(StripePayment adaptee) {
		this.stripePayment= adaptee;
	}
	
	@Override
	public Boolean verifyPayment(String cardNumber, String expiredDate, String activityName, String customerName,
			String providerName, String amount) {
		// TODO Auto-generated method stub
		return null;
	}

}

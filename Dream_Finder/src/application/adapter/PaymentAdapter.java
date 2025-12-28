package application.adapter;

import java.time.LocalDate;

import application.payment.StripePayment;

public class PaymentAdapter implements Target {
	
	private StripePayment stripePayment;
	
	private final static String SUCCESSFULL= "pm_card_visa";
	private final static String EXPIRED= "pm_card_chargeDeclinedExpiredCard";
	private final static String DECLINED= "pm_card_chargeDeclined";
	
	public PaymentAdapter(StripePayment adaptee) {
		this.stripePayment= adaptee;
	}
	
	@Override
	public void verifyPayment(String cardNumber, String expiredDate, String activityName, String customerName,
			String providerName, Double amount) {
			//In base al Expired date e al card number si hanno diversi tipi di risultati del pagamento
			String paymentResult = null;
				
			LocalDate expiration = LocalDate.parse(expiredDate);
				
			// Se la data di scadenza è prima di oggi (o fine mese corrente), è scaduta
			if (expiration.isBefore(LocalDate.now())) {
				paymentResult = EXPIRED;
			} else {
				if(cardNumber.trim().equals("4242424242424242")) {
					paymentResult = SUCCESSFULL;
				} else {
					paymentResult = DECLINED;
				}
			}
			
			
			
	}

}

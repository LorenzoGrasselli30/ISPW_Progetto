package application.adapter;

import java.io.IOException;
import java.time.LocalDate;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import application.model.bean.PaymentOutcomeDTO;
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
	public PaymentOutcomeDTO verifyPayment(String cardNumber, String expiredDate, String Cvv, String activityName, String customerName,
			String providerName, Double amount) {
			
		PaymentOutcomeDTO newOutcome = new PaymentOutcomeDTO();
		
			//In base al Expired date e al card number si hanno diversi tipi di risultati del pagamento
			String paymentResult = null;
				
			LocalDate expiration = LocalDate.parse(expiredDate);
				
			//Se la data di scadenza è prima di oggi (o fine mese corrente), è scaduta
			if (expiration.isBefore(LocalDate.now())) {
				paymentResult = EXPIRED;
			} else {
				if(cardNumber.trim().equals("4242424242424242") && Cvv != null) {
					paymentResult = SUCCESSFULL;
				} else {
					paymentResult = DECLINED;
				}
			}
			
			//Conversione dell'amount da Double (in euro) a Long (in centesimi)
			Long amountInCents = Math.round(amount * 100);
			
			try {
				PaymentIntent paymentIntent = StripePayment.createPayment(
						paymentResult, 
						expiredDate, 
						activityName, 
						customerName, 
						providerName, 
						amountInCents
					);
				
				newOutcome.setPaymentID(paymentIntent.getId());
				newOutcome.setPaymentDescription(paymentIntent.getDescription());
				newOutcome.setPaymentOutcome(paymentIntent.getStatus());
				
			} catch (StripeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return newOutcome;	
	}

}

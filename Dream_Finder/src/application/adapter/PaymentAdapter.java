package application.adapter;

import java.time.LocalDate;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import application.exception.PaymentProcessingException;
import application.model.bean.PaymentOutcomeDTO;
import application.model.enums.PaymentErrorReason;
import application.payment.StripePayment;

public class PaymentAdapter implements Target {
	
	private StripePayment stripePayment;
	
	private static final String SUCCESSFULL= "pm_card_visa";
	private static final String EXPIRED= "pm_card_chargeDeclinedExpiredCard";
	private static final String DECLINED= "pm_card_chargeDeclined";
	
	public PaymentAdapter(StripePayment adaptee) {
		this.stripePayment= adaptee;
	}
	
	@Override
	public PaymentOutcomeDTO verifyPayment(String cardNumber, LocalDate expiredDate, String cvv, String activityName, String customerName,
			String providerName, Double amount) throws PaymentProcessingException {
			
		PaymentOutcomeDTO newOutcome = new PaymentOutcomeDTO();
		
			//In base al Expired date e al card number si hanno diversi tipi di risultati del pagamento
			String paymentResult = null;
				
			//Se la data di scadenza è prima di oggi (o fine mese corrente), è scaduta
			if (expiredDate.isBefore(LocalDate.now())) {
				paymentResult = EXPIRED;
			} else {
				if(cardNumber.trim().equals("4242424242424242") && cvv != null) {
					paymentResult = SUCCESSFULL;
				} else {
					paymentResult = DECLINED;
				}
			}
			
			//Conversione dell'amount da Double (in euro) a Long (in centesimi)
			Long amountInCents = Math.round(amount * 100);
			
			try {
				//Chiamata al metodo specificRequest()
				PaymentIntent paymentIntent = stripePayment.createPayment(
						paymentResult, 
						activityName, 
						customerName, 
						providerName, 
						amountInCents
					);
				
				newOutcome.setPaymentID(paymentIntent.getId());
				newOutcome.setPaymentDescription(paymentIntent.getDescription());
				newOutcome.setPaymentOutcome(paymentIntent.getStatus());
				
			} catch (StripeException e) {
				this.handlePaymentException(e.getCode());
			} 
			
		return newOutcome;	
	}
	
	private void handlePaymentException(String outcomeCode) throws PaymentProcessingException {
		if (outcomeCode.equals(PaymentErrorReason.CARD_EXPIRED.getStringReason())) {
			throw new PaymentProcessingException("Errore di pagamento: La carta inserita è scaduta");
		}
		
		if (outcomeCode.equals(PaymentErrorReason.CARD_DECLINED.getStringReason())) {
			throw new PaymentProcessingException("Errore di pagamento: La carta è stata rifiutata o fondi insufficienti");
		}
		
		if (outcomeCode.equals(PaymentErrorReason.INVALID_CVV.getStringReason())) {
			throw new PaymentProcessingException("Errore di pagamento: Il CVV inserito non è valido");
		}
		
		if (outcomeCode.equals(PaymentErrorReason.NETWORK_ERROR.getStringReason())) {
			throw new PaymentProcessingException("Errore di pagamento: Connessione a internet instabile");
		}
		
		if (outcomeCode.equals(PaymentErrorReason.TEMPORARY_ERROR.getStringReason())) {
			throw new PaymentProcessingException("Errore di pagamento: Errore temporaneo riprova più tardi");
		}
		
		if (outcomeCode.equals(PaymentErrorReason.UNKNOWN_ERROR.getStringReason())) {
			throw new PaymentProcessingException("Errore di pagamento: Errore sconosciuto");
		}
	}
}

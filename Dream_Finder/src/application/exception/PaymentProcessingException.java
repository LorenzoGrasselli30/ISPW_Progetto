package application.exception;

import com.stripe.exception.StripeException;

public class PaymentProcessingException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public PaymentProcessingException(String message) {
	        super(message);
	    }
	
	
}

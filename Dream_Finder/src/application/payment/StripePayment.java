package application.payment;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.exception.StripeException;

public class StripePayment {
	
	public static PaymentIntent createPayment() throws StripeException {
		Stripe.apiKey ="sk_test_51SiysiIBVjqjBXXmrOY6y8hERG08N1O3I0m7n4BJmRqz5wFQxH4WJI7KQcpLtYkmswAIiD2emhHh8k0DoHAfwmQb00k6baD1rx";
		
	    PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
	        .setAmount(2000L) // Importo in centesimi (2000 = 20.00 EUR)
	        .setCurrency("eur") 
	        .addPaymentMethodType("card")
	        .setDescription("Pagamento di esempio") //Si va a mettere il nome dell'attività
	        .putMetadata("customer_name", "Mario Rossi")
	        .putMetadata("provider_name", "Luigi Verdi")
	        .build();
	    
	    return PaymentIntent.create(params);
	}
}


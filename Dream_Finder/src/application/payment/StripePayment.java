package application.payment;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodCreateParams;
import com.stripe.exception.StripeException;

//Classe adeptee
public class StripePayment {
	
	private final static String SUCCESSFULL= "pm_card_visa";
	private final static String EXPIRED= "pm_card_chargeDeclinedExpiredCard";
	private final static String DECLINED= "pm_card_chargeDeclined";
	
	public static PaymentIntent createPayment(String paymentResult, String expiredDate, String activityName, 
			String customerName, String providerName, Long amount) throws StripeException, IOException {
		String secretKey= loadApiKey();
		Stripe.apiKey= secretKey;
		
		PaymentIntent paymentIntent = new PaymentIntent();
		
		//Creazione del PaymentIntent
	    PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
	        .setAmount(amount) // Importo in centesimi (2000 = 20.00 EUR)
	        .setCurrency("eur") //Default
	        .setPaymentMethod(paymentResult)
	        .setConfirm(true)
	        .setDescription(activityName)
	        .putMetadata("customer_name", customerName)
	        .putMetadata("provider_name", providerName)
	        .setAutomaticPaymentMethods(
			        PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
			            .setEnabled(true)
			            .setAllowRedirects(PaymentIntentCreateParams.AutomaticPaymentMethods.AllowRedirects.NEVER)
			            .build()
			)
	        .build();
	    
	    return paymentIntent.create(params);
	}
	
	private static String loadApiKey() throws IOException {
        Properties properties = new Properties();
        
        // Prova a caricare dal file nella root del progetto
        try (FileInputStream input = new FileInputStream("src/config.properties")) {
            properties.load(input);
            String apiKey = properties.getProperty("stripe.api.key");
            
            if (apiKey == null || apiKey.trim().isEmpty()) {
                throw new IOException("La chiave stripe.api.key non è configurata in config.properties");
            }
            
            System.out.println("Chiave API caricata da config.properties");
            return apiKey;
            
        } catch (IOException e) {
            System.err.println("File config.properties non trovato");
            throw e;
        }
    }
	
}


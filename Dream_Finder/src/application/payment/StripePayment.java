package application.payment;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.exception.StripeException;

public class StripePayment {
	
	public static PaymentIntent createPayment() throws StripeException, IOException {
		String secretKey= loadApiKey();
		Stripe.apiKey= secretKey;
		
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


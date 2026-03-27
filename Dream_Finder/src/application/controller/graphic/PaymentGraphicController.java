package application.controller.graphic;

import java.io.IOException;
import application.controller.application.BookingApplicationController;
import application.exception.AvailabilityException;
import application.model.bean.ActivityDTO;
import application.model.bean.BookingContext;
import application.view.AlertUtils;
import application.view.WindowsNavigatorUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class PaymentGraphicController {
	
	private BookingContext context;
	
	private BookingApplicationController bookingController;
	
	@FXML
	private TextField cardNumberField;
	
	@FXML
	private TextField cvvField;
	
	@FXML
	private TextField ownerField;
	
	@FXML
	private DatePicker dateField;
	
	@FXML
	private Label providerNameLabel;
	
	@FXML
	private Label nPeopleLabel;
	
	@FXML
	private Label fullTicketLabel;
	
	@FXML
	private Label reducedTicketLabel;
	
	@FXML
	private Label shuttleServiceLabel;
	
	@FXML
	private Label tourGuideLabel;
	
	@FXML
	private Label totalLabel;
	
	@FXML
	private Label payLaterLabel;
	
	@FXML
	private Label freeCancellationLabel;
	
	@FXML
	private Label skipLineLabel;
	
	public PaymentGraphicController() {
		bookingController= new BookingApplicationController();
	}	
	
	public void initPayment(BookingContext context) {
		this.context= context;
		
		// Inizializzazione label di riepilogo
		ActivityDTO activity = context.getActivity();
		
		if (activity != null) { 
			providerNameLabel.setText(activity.getProviderName());
			
			fullTicketLabel.setText("x" + context.getnFullTickets() + " Biglietto intero " 
					+ String.format("%.2f", activity.getPrice()*context.getnFullTickets()) + "€"); 
			
			reducedTicketLabel.setText("x" + context.getnReducedTickets() + " Biglietto ridotto " 
					+ String.format("%.2f", (activity.getPrice()/3.0)*context.getnReducedTickets()) + "€"); 
		}
		
		int totalPeople = context.getnFullTickets() + context.getnReducedTickets();
		nPeopleLabel.setText(String.valueOf(totalPeople));
		
		if (context.isShuttleService() && context.getShuttlePrice() != null) {
			shuttleServiceLabel.setText("Servizio navetta: " + String.format("%.2f", context.getShuttlePrice()) + "€");
		} else {
			shuttleServiceLabel.setText("Servizio navetta: No");
		}
		
		if (context.isGuideService() && context.getGuidePrice() != null) {
			tourGuideLabel.setText("Tour guidato: " + String.format("%.2f", context.getGuidePrice()) + "€");
		} else {
			tourGuideLabel.setText("Tour guidato: No");
		}
		
		if (context.getTotalPrice() != null) {
			totalLabel.setText(String.format("%.2f", context.getTotalPrice()) + "€");
		} else {
			totalLabel.setText("0.00$");
		}
		
		if (payLaterLabel != null) {
			boolean isPayLater = Boolean.TRUE.equals(activity.getPayLater());
			payLaterLabel.setVisible(isPayLater);
			payLaterLabel.setManaged(isPayLater);
		}

		if (freeCancellationLabel != null) {
			boolean isFreeCancel = Boolean.TRUE.equals(activity.getFreeCancellation());
			freeCancellationLabel.setVisible(isFreeCancel);
			freeCancellationLabel.setManaged(isFreeCancel);
		}
		
		if (skipLineLabel != null) {
			boolean isSkipLine = Boolean.TRUE.equals(activity.getSkipLine());
			skipLineLabel.setVisible(isSkipLine);
			skipLineLabel.setManaged(isSkipLine);
		}
		
	}
	
	@FXML
	public void initialize() {
	    if (cardNumberField != null) {
	        cardNumberField.setText("4242424242424242");
	    }
	}
	
	@FXML
	private void doPayment(MouseEvent event) throws IOException{
		
		String fxmlFile = "recommendedActivitiesView.fxml";
		String title = "Attività Consigliate";
		
		context.setCardNumber(cardNumberField.getText().trim());
		context.setCvv(cvvField.getText().trim());
		
		context.setOwnerName(ownerField.getText());
		
		if (dateField.getValue() != null) {
			context.setExpiredDate(dateField.getValue().toString());
		}
		
		BookingContext updatedContext;
		try {
			updatedContext = bookingController.makeBooking(context);
			
			System.out.println("Richiesta di pagamento inviata dall'utente: "+updatedContext.getOwnerName());
			WindowsNavigatorUtils.openRecommendedActivitiesWindow(event, fxmlFile, title, updatedContext);
			
		} catch (AvailabilityException ae) {
			AlertUtils.showAlert(Alert.AlertType.ERROR, "Errore durante la prenotazione", ae.getMessage());
			WindowsNavigatorUtils.closePaymentWindow(event, "activityView.fxml", "Info Attivita'", context);
		}
   }
	
	@FXML
	private void goBackPayment(MouseEvent event) throws IOException{
		WindowsNavigatorUtils.closePaymentWindow(event, "activityView.fxml", "Info Attivita'", context);
   }
	
}

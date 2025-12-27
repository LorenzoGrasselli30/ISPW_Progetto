package application.controller.graphic;

import java.io.IOException;
import java.util.List;

import com.stripe.exception.StripeException;

import application.controller.application.BookingApplicationController;
import application.model.bean.ActivityDTO;
import application.model.bean.BookingContext;
import application.observer.PriceCalculator;
import application.payment.StripePayment;
import application.view.WindowsNavigatorUtils;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class PaymentGraphicController {
	
	private BookingContext context;
	
	private BookingApplicationController bookingController;
	
	@FXML
	private TextField cardNumeberField;
	
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
	private void doPayment(MouseEvent event) throws IOException{
		
		String fxmlFile = "recommendedActivitiesView.fxml";
		String title = "Attività Consigliate";
		
		StripePayment payment = new StripePayment();
		try {
			payment.createPayment();
		} catch (StripeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		WindowsNavigatorUtils.changeParentWindow(event, fxmlFile, title);
		
   }
	
	@FXML
	private void goBackPayment(MouseEvent event) throws IOException{
		WindowsNavigatorUtils.openActivityWindow(event, "activityView.fxml", "Info Attivita'", context.getActivity());
   }
	
}

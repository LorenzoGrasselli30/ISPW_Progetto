package application.controller.graphic;

import java.io.IOException;
import java.util.List;

import application.controller.application.BookingApplicationController;
import application.model.bean.ActivityDTO;
import application.model.bean.BookingContext;
import application.model.bean.BookingDTO;
import application.model.bean.ReceiptDTO;
import application.view.ActivityLayoutUtils;
import application.view.WindowsNavigatorUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RecommendedActivitiesGraphicController {
	
	private BookingApplicationController bookingController;
	
	private BookingContext context;
	
	static final String LOGINPATH = "loginView.fxml";
	static final String LOGINTITLE = "Login";
	static final String HOMEPAGEPATH = "homeView.fxml";
	static final String HOMEPAGETITLE = "Homepage"; 
	static final String ACTIVITYPATH = "activityView.fxml"; 
	static final String ACTIVITYTITLE = "Info Attivita'"; 
	
	@FXML
    private HBox relatedContainer;
	
	@FXML
    private Label activityNameLabel;
	
	ReceiptDTO currentReceipt;
	
	BookingDTO currentBooking;
	
	public RecommendedActivitiesGraphicController() {
		bookingController= new BookingApplicationController();
	}
	
	public void initRecommendedActivities(BookingContext context) {
		this.context= context;
		
		List<ActivityDTO> relatedInfo= bookingController.fetchRelatedInfo(
				context.getActivity().getActivityName(), 
				context.getActivity().getActivityType(),
				context.getActivity().getProviderName()
				);
		
		populateRelatedSection(relatedInfo);
		
		if (activityNameLabel != null) {
			activityNameLabel.setText(context.getActivity().getActivityName());
		}
	}
	
	private void populateRelatedSection(List<ActivityDTO> activities) {
    	if (relatedContainer == null) {
			System.out.println("Errore: relatedContainer non è stato inizializzato");
			return;
		}
		
		// Pulisce il container prima di popolarlo
    	relatedContainer.getChildren().clear();
		
		// Crea una card per ogni attività
		for (ActivityDTO activity : activities) {
			VBox activityCard = ActivityLayoutUtils.createActivityCard(
					activity, 
					event -> handleActivityClick(event, activity), 
					event -> handleHeartClick(event)
					//Scrivere "this::handleHeartClick" per risolvere l'issues
			);
			relatedContainer.getChildren().add(activityCard);
		}
    }
	
	
	
	private void handleActivityClick(MouseEvent event, ActivityDTO activity) {
		try {
			WindowsNavigatorUtils.openActivityWindow(event, ACTIVITYPATH, ACTIVITYTITLE, activity);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	private void handleHeartClick(MouseEvent event) {
		try {
			WindowsNavigatorUtils.openModalWindow(event, LOGINPATH, LOGINTITLE, null, null, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void useRecommendedActivities(MouseEvent event) throws IOException {
		 // Controlla l'ID del bottone per decidere quale finestra aprire
    	 String fxmlFile = "";
    	 String title = "";
    	 
    	 
    	    switch (((Node) event.getSource()).getId()) {
    	        case "areaUserButton":
    	        case "newActivityButton":
    	            fxmlFile = LOGINPATH;
    	            title = LOGINTITLE;
    	            break;
    	        case "homeButton":
    	        case "returnHomeButton":
    	        	fxmlFile = HOMEPAGEPATH;
    	        	title = HOMEPAGETITLE;
    	        	break;
    	        case "activityButton":
    	        	fxmlFile = ACTIVITYPATH;
    	        	title = ACTIVITYTITLE;
    	        	break;
    	        case "receiptButton":
    	        	//Apre pagina con ricevuta e da la possibilità di scaricarla in versione non demo
    	        	if (currentReceipt == null) {
    	        	currentReceipt= bookingController.fetchCurrentReceipt(context.getPaymentID());
    	        	}
    	        	
    	        	WindowsNavigatorUtils.openModalWindow(event, "receiptView.fxml", "Ricevuta: "+currentReceipt.getPaymentID(), null, currentReceipt, null);
    	        	
    	        	break;
    	        case "ticketButton":
    	        	//Apre pagina con ticket e da la possibilità di scaricarla in versione non demo
    	        	if (currentBooking == null) {
    	        		currentBooking= bookingController.fetchCurrentTicket(context.getBookingID());
        	        	}
    	        	
    	        	WindowsNavigatorUtils.openModalWindow(event, "ticketView.fxml", "Ticket: "+currentBooking.getBookingID(), null, null, currentBooking);
    	        	
    	        	break;
    	        default: 
    	        	break;
    	    }
    	
        	if (LOGINTITLE.equals(title)) {
        		WindowsNavigatorUtils.openModalWindow(event, fxmlFile, title, null, null, null);
        	} 
        	
        	
        	if (HOMEPAGETITLE.equals(title)) {
        		WindowsNavigatorUtils.openWindow(event, fxmlFile, title);
        	}
        	
        	if (ACTIVITYTITLE.equals(title)) {
        		WindowsNavigatorUtils.openActivityWindow(event, fxmlFile, title, context.getActivity());
        	}
        	
	}
}

package application.controller.graphic;

import java.io.IOException;
import java.util.List;

import application.controller.application.BookingApplicationController;
import application.model.bean.ActivityDTO;
import application.model.bean.BookingContext;
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
	
	@FXML
    private HBox relatedContainer;
	
	@FXML
    private Label activityNameLabel;
	
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
			//System.err.println("Errore: forYouContainer non è stato inizializzato");
			return;
		}
		
		// Pulisce il container prima di popolarlo
    	relatedContainer.getChildren().clear();
		
		// Crea una card per ogni attività
		for (ActivityDTO activity : activities) {
			VBox activityCard = ActivityLayoutUtils.createActivityCard(
					activity, 
					event -> {
						handleActivityClick(event, activity);
					}, 
					event -> {
						handleHeartClick(event);
					}
			);
			relatedContainer.getChildren().add(activityCard);
		}
    }
	
	
	
	private void handleActivityClick(MouseEvent event, ActivityDTO activity) {
		try {
			WindowsNavigatorUtils.openActivityWindow(event, "activityView.fxml", "Info Attività", activity);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	private void handleHeartClick(MouseEvent event) {
		try {
			WindowsNavigatorUtils.openModalWindow(event, "loginView.fxml", "Login", null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void useRecommendedActivities(MouseEvent event) throws IOException {
		 // Controlla l'ID del bottone per decidere quale finestra aprire
    	 String fxmlFile = "";
    	 String title = "";
    	 
    	 final String loginPath = "loginView.fxml";
    	 final String titleLogin = "Login";
    	 final String homepagePath = "homeView.fxml";
    	 final String homepageTitle = "Homepage"; 
    	 
    	    switch (((Node) event.getSource()).getId()) {
    	        case "areaUserButton":
    	        case "newActivityButton":
    	            fxmlFile = loginPath;
    	            title = titleLogin;
    	            break;
    	        case "homeButton":
    	        case "returnHomeButton":
    	        	fxmlFile = homepagePath;
    	        	title = homepageTitle;
    	        	break;
    	        case "activityButton":
    	        	fxmlFile = "activityView.fxml";
    	        	title = "Info Attività";
    	        	break;
    	        case "receiptButton":
    	        	//Apre pagina con ricevuta e da la possibilità di scaricarla in versione non demo
    	        	break;
    	        case "ticketButton":
    	        	//Apre pagina con ticket e da la possibilità di scaricarla in versione non demo
    	        	break;
    	    }
    	
        	if (title.equals("Login")) {
        		WindowsNavigatorUtils.openModalWindow(event, fxmlFile, title, null);
        	} 
        	
        	
        	if (("Homepage".equals(title)) || ("Info Attività".equals(title))) {
        		WindowsNavigatorUtils.openWindow(event, fxmlFile, title);
        	}
        	
	}
}

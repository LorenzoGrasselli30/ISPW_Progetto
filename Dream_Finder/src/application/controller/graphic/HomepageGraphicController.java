package application.controller.graphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import application.controller.application.HomeApplicationController;
import application.model.bean.ActivityDTO;
import application.view.AlertUtils;
import application.view.WindowsNavigatorUtils;

public class HomepageGraphicController {
	
	private HomeApplicationController homeController;
	
	public HomepageGraphicController() {
		this.homeController= new HomeApplicationController();
	}
	
	@FXML
    private void initialize() {
		System.out.println("Il sistema sta per essere inizializzato");
		
		List<ActivityDTO> activities= homeController.fetchActivities();
		for (ActivityDTO activity: activities) {
			System.out.println(activity.getActivityName());
			System.out.println(activity.getDescription());
			System.out.println(activity.getPrice());
			System.out.println(activity.getRate()+" "+activity.getnRating());
		}
		System.out.println("Inizializzazione completata");
    }
	
	@FXML
	private void useHomepage(MouseEvent event) throws IOException {
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
    	        	fxmlFile = homepagePath;
    	        	title = homepageTitle;
    	        	break;
    	        case "activityButton":
    	        	fxmlFile = "activityView.fxml";
    	        	title = "Info Attività";
    	        	break;
    	    }
    	
        	if (title.equals("Login")) {
        		WindowsNavigatorUtils.openModalWindow(event, fxmlFile, title);
        	} 
        	
        	
        	if (("Homepage".equals(title)) || ("Info Attività".equals(title))) {
        		WindowsNavigatorUtils.openWindow(event, fxmlFile, title);
        	}	
	}
	
	@FXML
	private void notImplementedYet(MouseEvent event) {
   	 	AlertUtils.notImplementedYet();
    }
}

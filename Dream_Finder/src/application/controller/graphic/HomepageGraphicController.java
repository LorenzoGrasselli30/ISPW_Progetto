package application.controller.graphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import application.controller.application.HomeApplicationController;
import application.model.bean.ActivityDTO;
import application.view.ActivityLayoutUtils;
import application.view.AlertUtils;
import application.view.WindowsNavigatorUtils;

public class HomepageGraphicController {
	
	private HomeApplicationController homeController;
	
	@FXML
	private HBox forYouContainer;
	
	public HomepageGraphicController() {
		this.homeController= new HomeApplicationController();
	}
	
	@FXML
    private void initialize() {
		System.out.println("Il sistema sta per essere inizializzato");
		
		List<ActivityDTO> activities= homeController.fetchActivities();
		
		populateForYouSection(activities);
    }
	
	private void populateForYouSection(List<ActivityDTO> activities) {
		
		if (forYouContainer == null) {
			System.err.println("Errore: forYouContainer non è stato inizializzato");
			return;
		}
		
		// Pulisce il container prima di popolarlo
		forYouContainer.getChildren().clear();
		
		// Crea una card per ogni attività
		for (ActivityDTO activity : activities) {
			VBox activityCard = ActivityLayoutUtils.createActivityCard(
					activity, 
					event -> {
						try {
							handleActivityClick(event, activity);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}, 
					event -> {
						try {
							handleHeartClick(event);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
			);
			forYouContainer.getChildren().add(activityCard);
		}
	}
	
	//Gestisce il click su una card attività
	private void handleActivityClick(MouseEvent event, ActivityDTO activity) throws IOException {
		WindowsNavigatorUtils.openActivityWindow(event, "activityView.fxml", "Info Attività", activity);
	}
	
	//Gestisce il click sull'icona del cuore 
	private void handleHeartClick(MouseEvent event) throws IOException {
	    WindowsNavigatorUtils.openModalWindow(event, "loginView.fxml", "Login");
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
    	 final String activityPath = "activityView.fxml";
    	 final String activityTitle= "Info Attività";
    	 
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
    	        	fxmlFile = activityPath;
    	        	title = activityTitle;
    	        	break;
    	    }
    	
        	if (titleLogin.equals(title)) {
        		WindowsNavigatorUtils.openModalWindow(event, fxmlFile, title);
        	} 
        	
        	
        	if ((homepageTitle.equals(title)) || (activityPath.equals(title))) {
        		WindowsNavigatorUtils.openWindow(event, fxmlFile, title);
        	}	
	}
	
	@FXML
	private void notImplementedYet(MouseEvent event) {
   	 	AlertUtils.notImplementedYet();
    }
}

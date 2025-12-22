package application.controller.graphic;

import java.io.IOException;

import application.view.WindowsNavigatorUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class RecommendedActivitiesGraphicController {
	
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
    	    }
    	
        	if (title.equals("Login")) {
        		WindowsNavigatorUtils.openModalWindow(event, fxmlFile, title, null);
        	} 
        	
        	
        	if (("Homepage".equals(title)) || ("Info Attività".equals(title))) {
        		WindowsNavigatorUtils.openWindow(event, fxmlFile, title);
        	}
        	
	}
}

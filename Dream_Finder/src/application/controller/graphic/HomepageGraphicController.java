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

import application.view.WindowsNavigatorUtils;

public class HomepageGraphicController {
	
	@FXML
	private void useHomepage(MouseEvent event) throws IOException {
		 // Controlla l'ID del bottone per decidere quale finestra aprire
    	 String fxmlFile = "";
    	 String title = "";
    	 
    	 final String loginPath = "/application/view/loginView.fxml";
    	 final String titleLogin = "Login";
    	 final String homepagePath = "/application/view/homeView.fxml";
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
    	        	fxmlFile = "/application/view/activityView.fxml";
    	        	title = "Info Attività";
    	        	break;
    	    }
			Stage parentStage = (Stage)((Node)(event.getSource())).getScene().getWindow();
    	
        	if (title.equals("Login")) {
        		WindowsNavigatorUtils.openModalWindow(event, fxmlFile, title);
        	} 
        	
        	
        	if (("Homepage".equals(title)) || ("Info Attività".equals(title))) {
        		WindowsNavigatorUtils.openWindow(event, fxmlFile, title);
        	}
        	
	}
}

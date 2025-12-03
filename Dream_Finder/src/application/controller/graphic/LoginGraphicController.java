package application.controller.graphic;

import java.io.IOException;

import application.controller.application.LoginApplicationController;
import application.exception.ValidationException;
import application.util.Validator;
import application.view.AlertUtils;
import application.view.WindowsNavigatorUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginGraphicController {
	
	@FXML
	private TextField emailField;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
    private void openSignUp(MouseEvent event) throws IOException{
		 
		 String fxmlFile = "signUpView.fxml";
		 String title = "Sign Up";
		 
		 WindowsNavigatorUtils.openWindow(event, fxmlFile, title);
    }
	 
	@FXML
	private void doLogin(ActionEvent event) {
		// Acquisisci i dati dalla View
		String email = emailField.getText();
		String password = passwordField.getText();
	    
		try {
	    //Controlla che i dati siano nel formato corretto 
		//String formattedEmail = Formatter.removeBlanks(email.toLowerCase());
		//Utilizza una classe formatter?
		String formattedEmail = email.toLowerCase().trim();
		String fomattedPassword = password.trim();
        
        //Invia i dati al Controller Applicativo dovrebbe utilizzare una bean ma in questo caso per il login non è necessario
        LoginApplicationController loginController = new LoginApplicationController();  
        boolean isAuthenticated = loginController.authenticate(formattedEmail, fomattedPassword);
	    
	        // Aggiorna la View in base al risultato
	        if (isAuthenticated) {
	        	String userRole = loginController.getUserRole();
	        	System.out.println("Login effettuato correttamente, tipo di utente loggato: " + userRole);
	        	/*
	        	// Devo visualizzare una homepage in base al ruolo e con bentornato: nome dell'utente
	        	//String userRole = loginController.getUserRole();
	        	String fxmlPath = loginController.getPathWindow();
	        	String title = loginController.getTitleWindow();
	        	WindowsNavigatorUtils.changeParentWindow(event, fxmlPath, title);
	        	*/
	        }
		}
		catch (ValidationException ve) {
	           // Gestione specifica per errori di validazione
		    	AlertUtils.showAlert(Alert.AlertType.WARNING, "Errore durante il login:", ve.getMessage());
	    }
	    catch (Exception e) {
	    	AlertUtils.showAlert(Alert.AlertType.ERROR, "Fatal error", "Qualcosa è andato storto, riprova più tardi.");		
	    }

	}
}


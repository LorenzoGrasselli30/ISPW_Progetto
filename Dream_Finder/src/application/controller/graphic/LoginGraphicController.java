package application.controller.graphic;

import java.io.IOException;

import application.controller.application.LoginApplicationController;
import application.exception.ValidationException;
import application.view.AlertUtils;
import application.view.WindowsNavigatorUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class LoginGraphicController {
	
	@FXML
	private TextField emailField;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private ImageView showPasswordButton;
	
	@FXML
    private void useLogin(MouseEvent event) throws IOException{
   	 
   	    switch (((Node) event.getSource()).getId()) {
   	        case "showPasswordButton":
   	            break;
   	        case "signUpButton":
   	        	WindowsNavigatorUtils.openWindow(event, "signUpView.fxml", "Sign Up");
   	        	break;
   	    }
    }
	 
	@FXML
	private void doLogin(MouseEvent event) {
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
	        	
	        	WindowsNavigatorUtils.loginToWindow(event, userRole);
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


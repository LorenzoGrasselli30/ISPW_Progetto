package application.controller.graphic;

import java.io.IOException;

import application.view.WindowsNavigatorUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginGraphicController {
	
	/*
	@FXML
	public void goToHomepage(ActionEvent event) throws IOException {
		//Chiusura della finestra di Login
		Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.close();
    }
    */
	
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
	        
	        
	        
	        // Invia i dati al Controller Applicativo dovrebbe utilizzare una bean ma in questo caso per il login non è necessario
	        //LoginApplicationController loginController = new LoginApplicationController();  
	        //boolean isAuthenticated = loginController.authenticate(email, password);
	        
	        /*
	        // Aggiorna la View in base al risultato
	        if (isAuthenticated) {
	        	// Lancio schermata in base al ruolo 
	        	String userRole = loginController.getUserRole();  
               // Apre la schermata in base al ruolo 
               OpenWindowUtils.openRoleView(event, userRole);		
	        }
			*/
	    
	    /*
	    Così il cana he gestito le eccezioni bisogna aggiungere un blocco try catch
	    catch (Exception e) {
	    	AlertUtils.showAlert(Alert.AlertType.ERROR, "Error", "Something went wrong, try again.");		
	    }
	    catch (ValidationException ve) {
	           // Gestione specifica per errori di validazione
		    	AlertUtils.showAlert(Alert.AlertType.ERROR, "Login Error", ve.getMessage());
	    }
	     
	     */
	}
}


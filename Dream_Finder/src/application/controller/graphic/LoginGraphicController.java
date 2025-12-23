package application.controller.graphic;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.controller.application.LoginApplicationController;
import application.exception.ValidationException;
import application.model.bean.BookingContext;
import application.view.AlertUtils;
import application.view.WindowsNavigatorUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class LoginGraphicController implements Initializable {
	
	private BookingContext context;
	
	@FXML
	private TextField emailField;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private ImageView showPasswordButton;
	
	// Campo creato via codice, non presente nell'FXML
	private TextField passwordTextField;
	private boolean isPasswordVisible = false;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.passwordTextField = new TextField();
		
		//Rende il textfield invisibile
		this.passwordTextField.setVisible(false);
		this.passwordTextField.setManaged(false);
				
		// Bind bidirezionale: se scrivi in uno, si aggiorna anche l'altro
		this.passwordTextField.textProperty().bindBidirectional(passwordField.textProperty());
		
		this.passwordTextField.promptTextProperty().bind(passwordField.promptTextProperty());
		this.passwordTextField.prefHeightProperty().bind(passwordField.prefHeightProperty());
		this.passwordTextField.prefWidthProperty().bind(passwordField.prefWidthProperty());
	}
	
	public void initLogin(BookingContext context) {
		this.context= context;
	}
	
	@FXML
    private void useLogin(MouseEvent event) throws IOException{
   	 
   	    switch (((Node) event.getSource()).getId()) {
   	        case "showPasswordButton":
   	        	togglePasswordVisibility();
   	            break;
   	        case "signUpButton":
   	        	WindowsNavigatorUtils.openWindow(event, "signUpView.fxml", "Sign Up");
   	        	break;
   	    }
    }
	
	private void togglePasswordVisibility() {
		// Recuperiamo il genitore (il layout che contiene la password)
		Pane parent = (Pane) passwordField.getParent();
		
		// Se è la prima volta che clicchiamo, iniettiamo il TextField nel layout
		if (!parent.getChildren().contains(passwordTextField)) {
			int index = parent.getChildren().indexOf(passwordField);
			parent.getChildren().add(index, passwordTextField);
		}
		
		isPasswordVisible = !isPasswordVisible;

		if (isPasswordVisible) {
			// Mostra testo in chiaro
			passwordTextField.setVisible(true);
			passwordTextField.setManaged(true);
			passwordField.setVisible(false);
			passwordField.setManaged(false);
		} else {
			// Mostra password mascherata
			passwordTextField.setVisible(false);
			passwordTextField.setManaged(false);
			passwordField.setVisible(true);
			passwordField.setManaged(true);
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
	        	
	        	
	        	WindowsNavigatorUtils.loginToWindow(event, userRole, context);
	        } 
		}
		
		catch (ValidationException ve) {
			// Gestione specifica per errori di validazione
			AlertUtils.showAlert(Alert.AlertType.WARNING, "Errore durante il login:", ve.getMessage());
	    }
	    catch (Exception e) {
	    	AlertUtils.showAlert(Alert.AlertType.ERROR, "Error", "Qualcosa è andato storto, riprova più tardi.");		
	    }

	}

}


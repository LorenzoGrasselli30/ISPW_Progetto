package application.controller.graphic;

import java.io.IOException;

import application.view.WindowsNavigatorUtils;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SignUpGraphicController {
	
	@FXML
	private TextField emailField;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private PasswordField confirmPasswordField;
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private DatePicker dateField;
	
	@FXML
	private CheckBox privacyCheck;
	
	@FXML
	private void doSignUp(MouseEvent event) throws IOException{
		
		//Chiama il login controller per effettuare il sign up
		
		WindowsNavigatorUtils.closeWindow(event);
   }
}

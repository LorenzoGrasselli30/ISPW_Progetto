package application.controller.graphic;

import java.io.IOException;

import application.view.WindowsNavigatorUtils;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class PaymentGraphicController {
	
	@FXML
	private TextField cardNumeberField;
	
	@FXML
	private TextField cvvField;
	
	@FXML
	private TextField ownerField;
	
	@FXML
	private DatePicker dateField;
	
	@FXML
	private void doPayment(MouseEvent event) throws IOException{
		
		//Creare un medoto nel windowsNavigatorUtils che chiude la modal page e cambia la parent page
	    
		String fxmlFile = "recommendedActivitiesView.fxml";
		String title = "Attività Consigliate";
		
		WindowsNavigatorUtils.changeParentWindow(event, fxmlFile, title);
		
   }
	
	@FXML
	private void goBackPayment(MouseEvent event) throws IOException{
		
		WindowsNavigatorUtils.closeWindow(event);
   }
	
}

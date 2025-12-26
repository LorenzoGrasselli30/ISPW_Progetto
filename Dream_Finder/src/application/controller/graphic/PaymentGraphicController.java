package application.controller.graphic;

import java.io.IOException;
import java.util.List;

import application.controller.application.BookingApplicationController;
import application.model.bean.ActivityDTO;
import application.model.bean.BookingContext;
import application.observer.PriceCalculator;
import application.view.WindowsNavigatorUtils;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class PaymentGraphicController {
	
	private BookingContext context;
	
	private BookingApplicationController bookingController;
	
	@FXML
	private TextField cardNumeberField;
	
	@FXML
	private TextField cvvField;
	
	@FXML
	private TextField ownerField;
	
	@FXML
	private DatePicker dateField;
	
	public PaymentGraphicController() {
		bookingController= new BookingApplicationController();
	}	
	
	public void initPayment(BookingContext context) {
		this.context= context;
		
		
	}
	
	@FXML
	private void doPayment(MouseEvent event) throws IOException{
		
		String fxmlFile = "recommendedActivitiesView.fxml";
		String title = "Attività Consigliate";
		
		WindowsNavigatorUtils.changeParentWindow(event, fxmlFile, title);
		
   }
	
	@FXML
	private void goBackPayment(MouseEvent event) throws IOException{
		WindowsNavigatorUtils.openActivityWindow(event, "activityView.fxml", "Info Attivita'", context.getActivity());
   }
	
}

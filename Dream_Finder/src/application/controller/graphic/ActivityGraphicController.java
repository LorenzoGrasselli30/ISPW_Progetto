package application.controller.graphic;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.view.WindowsNavigatorUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ActivityGraphicController {
    
	@FXML
	public void goToHomepage(ActionEvent event) throws IOException {
		String fxmlFile = "homeView.fxml";
		String title = "Homepage";
        WindowsNavigatorUtils.openWindow(event, fxmlFile, title);
    }
	
	@FXML
	public void goToLogin(ActionEvent event) throws IOException {
		String fxmlFile = "loginView.fxml";
		String title = "Login";
		WindowsNavigatorUtils.openModalWindow(event, fxmlFile, title);
    }
}

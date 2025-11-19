package application.controller.graphic;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginGraphicController {
	
	@FXML
	public void goToHomepage(ActionEvent event) throws IOException {
		//Chiusura della finestra di Login
		Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.close();
    }
}

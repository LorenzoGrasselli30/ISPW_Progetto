package application.controller.graphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class HomepageGraphicController {
	/*
	private void useHompage(MouseEvent event) throws IOException {
		
	}
	*/
	
	@FXML
	public void goToLogin(ActionEvent event) throws IOException {
        // Carica il login FXML
        Parent root = FXMLLoader.load(getClass().getResource("/application/view/loginView.fxml"));
        // Recupera la stage corrente
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Imposta nuova scena
        stage.setResizable(false); //Rende la finestra NON ridimensionabile per il login, pagamento
        stage.setScene(new Scene(root, 640, 480));
        stage.show();
    }
	
	@FXML
	public void goToNewActivity(ActionEvent event) throws IOException {
        // Carica il login FXML
        Parent root = FXMLLoader.load(getClass().getResource("/application/view/newActivityView.fxml"));
        // Recupera la stage corrente
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Imposta nuova scena
        stage.setResizable(false); //Rende la finestra NON ridimensionabile per il login, pagamento
        stage.setScene(new Scene(root, 640, 480));
        stage.show();
    }
}

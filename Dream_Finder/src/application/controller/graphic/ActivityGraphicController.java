package application.controller.graphic;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ActivityGraphicController {
	
	@FXML
	public void goToHomepage(ActionEvent event) throws IOException {
		// Carica il login FXML
        Parent root = FXMLLoader.load(getClass().getResource("/application/view/homeView.fxml"));
        // Recupera la stage corrente
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Imposta nuova scena
        stage.setScene(new Scene(root, 640, 480));
        stage.show();
    }
	
	@FXML
	public void goToLogin(ActionEvent event) throws IOException {
        // Carica FXML login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/loginView.fxml"));
        Parent root = loader.load();

        // Ottieni la stage dalla finestra parent
        Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Crea nuova Stage modale
        Stage loginStage = new Stage();
        loginStage.setTitle("Login");
        loginStage.initOwner(parentStage);
        loginStage.initModality(Modality.WINDOW_MODAL); // blocca la homepage padre si può mettere anche APPLICATION_MODAL blocca tutte le altre finestre dell’applicazione
        loginStage.setScene(new Scene(root, 640, 480));
        loginStage.setResizable(false);
        
        loginStage.setOnHidden(e -> {
            // eventuale refresh della homepage o azioni post-login
            // es: homeStage.requestFocus();
        });
        
        loginStage.show();
    }
}

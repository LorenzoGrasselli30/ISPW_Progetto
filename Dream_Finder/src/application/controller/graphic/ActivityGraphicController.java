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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ActivityGraphicController implements Initializable{
    
	@FXML
	private ImageView mainActivityImg;
	
	@FXML
	private ImageView secondaryActivityImg1;
	
	@FXML
	private ImageView secondaryActivityImg2;
	
	@FXML
    private HBox imageGalleryContainer;
    
    @FXML
    private VBox secondaryImagesContainer;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	System.out.println("Initialize chiamato");
    	if (imageGalleryContainer != null && mainActivityImg != null) {
            // L'immagine principale prende il 50% della larghezza dell'HBox
            // e tutta l'altezza disponibile
			mainActivityImg.fitWidthProperty().bind(
                imageGalleryContainer.widthProperty().multiply(0.5)
            );
			mainActivityImg.fitHeightProperty().bind(
                imageGalleryContainer.heightProperty()
            );
			mainActivityImg.setPreserveRatio(false);

			imageGalleryContainer.sceneProperty().addListener((obs, oldScene, newScene) -> {
	            if (newScene != null) {
	                // Binda l'altezza dell'HBox al 40% dell'altezza della finestra
	                imageGalleryContainer.prefHeightProperty().bind(
	                    newScene.heightProperty().multiply(0.4)
	                );
	            }
			});
        }
        
        if (imageGalleryContainer != null && secondaryActivityImg1 != null) {
            // Le immagini secondarie prendono il 50% della larghezza
            // e il 50% dell'altezza ciascuna
        	secondaryActivityImg1.fitWidthProperty().bind(
                imageGalleryContainer.widthProperty().multiply(0.5)
            );
        	secondaryActivityImg1.fitHeightProperty().bind(
                imageGalleryContainer.heightProperty().multiply(0.5)
            );
        	secondaryActivityImg1.setPreserveRatio(false);
        }
        
        if (imageGalleryContainer != null && secondaryActivityImg2 != null) {
        	secondaryActivityImg2.fitWidthProperty().bind(
                imageGalleryContainer.widthProperty().multiply(0.5)
            );
        	secondaryActivityImg2.fitHeightProperty().bind(
                imageGalleryContainer.heightProperty().multiply(0.5)
            );
        	secondaryActivityImg2.setPreserveRatio(false);
        }
	}
	
	@FXML
	public void goToHomepage(MouseEvent event) throws IOException {
		String fxmlFile = "homeView.fxml";
		String title = "Homepage";
        WindowsNavigatorUtils.openWindow(event, fxmlFile, title);
    }
	
	@FXML
	public void goToLogin(MouseEvent event) throws IOException {
		String fxmlFile = "loginView.fxml";
		String title = "Login";
		WindowsNavigatorUtils.openModalWindow(event, fxmlFile, title);
    }
	
	@FXML
	public void submitActivityForm(MouseEvent event) throws IOException {
		String fxmlFile = "paymentView.fxml";
		String title = "Schermata di pagamento";
		WindowsNavigatorUtils.openModalWindow(event, fxmlFile, title);
    }
	
}

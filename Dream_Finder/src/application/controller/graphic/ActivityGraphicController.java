package application.controller.graphic;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.controller.application.ActivityApplicationController;
import application.model.bean.ActivityDTO;
import application.model.entity.Activity;
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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ActivityGraphicController implements Initializable{
    
	private ActivityApplicationController activityController;
	private ActivityDTO clickedActivity;
	
	//Variabili per le immagini
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
    
    //Variabili per i campi di testo
    @FXML
    private Label titleLabel;
    
    @FXML
    private Label descriptionLabel;
    
    @FXML
    private Label ratingLabel;
    
    @FXML
    private Label nReviewsLabel;
    
    @FXML
    private Label providerLabel;
    
    @FXML
    private Label priceLabel;
    
    @FXML
    private Label durationLabel;
    
    @FXML
    private VBox cancellationSection;
    
    @FXML
    private VBox paylaterSection;
    
    @FXML
    private HBox skiplineSection;
    
    public ActivityGraphicController() {
		activityController = new ActivityApplicationController();
	}
	
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	System.out.println("Initialize chiamato");
    	
    	//Gestione delle immagini
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
	
    public void selectActivityInfo(ActivityDTO activity) {
		String activityName= activity.getActivityName();
		String providerName= activity.getProviderName();
		
		ActivityDTO activityInfo= activityController.fetchActivityInfo(activityName, providerName);
		
		// Popola la UI con i dati recuperati
				if (titleLabel != null) {
					titleLabel.setText(activityInfo.getActivityName());
				}
				
				if (descriptionLabel != null) {
					descriptionLabel.setText(activityInfo.getDescription());
				}
				
				if (ratingLabel != null) {
					ratingLabel.setText("Punteggio: " + activityInfo.getRate() + "/5");
				}
				
				if (nReviewsLabel != null) {
					nReviewsLabel.setText("(" + activityInfo.getnRating() + " recensioni)");
				}
				
				if (providerLabel != null) {
					providerLabel.setText("Fornitore dell'attività: " + activityInfo.getProviderName());
				}
				
				if (priceLabel != null) {
					priceLabel.setText(String.format("%.2f€", activityInfo.getPrice()));
				}
				
				if (durationLabel != null) {
					String unit = Boolean.TRUE.equals(activityInfo.getTimeInMinutes()) ? "minuti" : "ore";
					durationLabel.setText("Durata dell'attività: " + activityInfo.getDuration() + " " + unit);
				}
				if (cancellationSection != null) {
					cancellationSection.setVisible(activityInfo.getFreeCancellation());
					cancellationSection.setManaged(activityInfo.getFreeCancellation());
				}

				if (paylaterSection != null) {
					paylaterSection.setVisible(activityInfo.getPayLater());
					paylaterSection.setManaged(activityInfo.getPayLater());
				}

				if (skiplineSection != null) {
					skiplineSection.setVisible(activityInfo.getSkipLine());
					skiplineSection.setManaged(activityInfo.getSkipLine());
				}	
		
				List<ActivityDTO> relatedInfo= activityController.fetchRelatedInfo(activityInfo.getActivityName(), 
						activityInfo.getActivityType(), activityInfo.getProviderName());
				
				int i=0;
				for (ActivityDTO activityDTO: relatedInfo) {
					i++;
					System.out.println(i+")");
					System.out.println(activityDTO.getActivityName());
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
		String fxmlFile = "loginView.fxml";
		String title = "Login";
		WindowsNavigatorUtils.openModalWindow(event, fxmlFile, title);
    }
	
}

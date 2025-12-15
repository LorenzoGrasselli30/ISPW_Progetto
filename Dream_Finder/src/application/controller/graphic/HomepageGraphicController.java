package application.controller.graphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import application.controller.application.HomeApplicationController;
import application.model.bean.ActivityDTO;
import application.view.AlertUtils;
import application.view.WindowsNavigatorUtils;

public class HomepageGraphicController {
	
	private HomeApplicationController homeController;
	
	@FXML
	private HBox forYouContainer;
	
	public HomepageGraphicController() {
		this.homeController= new HomeApplicationController();
	}
	
	@FXML
    private void initialize() {
		System.out.println("Il sistema sta per essere inizializzato");
		
		List<ActivityDTO> activities= homeController.fetchActivities();
		
		populateForYouSection(activities);
		
		for (ActivityDTO activity: activities) {
			System.out.println(activity.getActivityName());
			System.out.println(activity.getDescription());
			System.out.println(activity.getPrice());
			System.out.println(activity.getRate()+" "+activity.getnRating());
		}

    }
	
	private void populateForYouSection(List<ActivityDTO> activities) {
		
		if (forYouContainer == null) {
			System.err.println("Errore: forYouContainer non è stato inizializzato");
			return;
		}
		
		// Pulisce il container prima di popolarlo
		forYouContainer.getChildren().clear();
		
		// Crea una card per ogni attività (potrebbe essere utilizzato uno util)
		for (ActivityDTO activity : activities) {
			VBox activityCard = createActivityCard(activity); 
			forYouContainer.getChildren().add(activityCard);
		}
	}
	
	/**
	 * Crea una card per una singola attività
	 */
	private VBox createActivityCard(ActivityDTO activity) {
		VBox card = new VBox();
		card.setAlignment(Pos.CENTER);
		card.setSpacing(5.0);
		card.setStyle("-fx-border-color: black; -fx-border-width: 0.2;");
		card.setCursor(Cursor.HAND);
		
		// Header con titolo e icona cuore
		HBox header = new HBox();
		header.setAlignment(Pos.TOP_CENTER);
		header.setSpacing(10.0);
		
		Label titleLabel = new Label(activity.getActivityName());
		titleLabel.setFont(Font.font("System", FontWeight.BOLD, 18.0));
		
		ImageView heartIcon = new ImageView();
		try {
			heartIcon.setImage(new Image(getClass().getResource("/Images/Heart.png").toExternalForm()));
			heartIcon.setFitHeight(30.0);
			heartIcon.setFitWidth(30.0);
			heartIcon.setPreserveRatio(true);
			heartIcon.setPickOnBounds(true);
		} catch (Exception e) {
			System.err.println("Errore nel caricamento dell'icona cuore: " + e.getMessage());
		}
		
		header.getChildren().addAll(titleLabel, heartIcon);
		
		// Descrizione
		HBox descriptionBox = new HBox();
		Label descriptionLabel = new Label(activity.getDescription());
		descriptionLabel.setFont(Font.font("System", 14.0));
		descriptionBox.getChildren().add(descriptionLabel);
		
		// Immagine attività
		ImageView activityImage = new ImageView();
		activityImage.setFitHeight(150.0);
		activityImage.setFitWidth(200.0);
		activityImage.setPreserveRatio(true);
		activityImage.setPickOnBounds(true);
		
		try {
			// Carica l'immagine dell'attività se disponibile
			// Altrimenti usa un'immagine di default
			activityImage.setImage(new Image(getClass().getResource("/Images/caption (3).jpg").toExternalForm()));
		} catch (Exception e) {
			System.err.println("Errore nel caricamento dell'immagine: " + e.getMessage());
		}
		
		// Rating
		HBox ratingBox = new HBox();
		Label ratingLabel = new Label(String.format("Punteggio: %.1f/5", activity.getRate()));
		ratingLabel.setFont(Font.font("System", FontWeight.BOLD, 14.0));
		ratingLabel.setMinHeight(20.0);
		ratingLabel.setPrefHeight(20.0);
		
		Label reviewsLabel = new Label(String. format("(%d recensioni)", activity.getnRating()));
		reviewsLabel.setFont(Font.font("System", 14.0));
		
		ratingBox.getChildren().addAll(ratingLabel, reviewsLabel);
		
		// Prezzo
		HBox priceBox = new HBox();
		priceBox.setMinHeight(20.0);
		priceBox.setPrefHeight(20.0);
		
		Label priceLabel = new Label(String.format("%.2f$ ", activity.getPrice()));
		priceLabel.setFont(Font.font("System", 14.0));
		
		Label perPersonLabel = new Label("A persona");
		perPersonLabel.setFont(Font.font("System", 14.0));
		
		priceBox.getChildren().addAll(priceLabel, perPersonLabel);
		System.out.println("Inizializzazione...");
		// Aggiungi tutti gli elementi alla card
		card.getChildren().addAll(header, descriptionBox, activityImage, ratingBox, priceBox);
		
		// Aggiungi event handler per click sulla card
		card.setOnMouseClicked(event -> {
			try {
				handleActivityClick(event, activity);
			} catch (IOException e) {
				System.err.println("Errore nell'apertura della vista attività: " + e.getMessage());
			}
		});
		
		return card;
	}
	
	/**
	 * Gestisce il click su una card attività
	 */
	private void handleActivityClick(MouseEvent event, ActivityDTO activity) throws IOException {
		System.out.println("Click su attività: " + activity.getActivityName());
		// Qui puoi passare i dati dell'attività alla vista di dettaglio
		WindowsNavigatorUtils.openWindow(event, "activityView.fxml", "Info Attività");
	}
	
	
	@FXML
	private void useHomepage(MouseEvent event) throws IOException {
		 // Controlla l'ID del bottone per decidere quale finestra aprire
    	 String fxmlFile = "";
    	 String title = "";
    	 
    	 final String loginPath = "loginView.fxml";
    	 final String titleLogin = "Login";
    	 final String homepagePath = "homeView.fxml";
    	 final String homepageTitle = "Homepage"; 
    	 
    	    switch (((Node) event.getSource()).getId()) {
    	        case "areaUserButton":
    	        case "newActivityButton":
    	            fxmlFile = loginPath;
    	            title = titleLogin;
    	            break;
    	        case "homeButton":
    	        	fxmlFile = homepagePath;
    	        	title = homepageTitle;
    	        	break;
    	        case "activityButton":
    	        	fxmlFile = "activityView.fxml";
    	        	title = "Info Attività";
    	        	break;
    	    }
    	
        	if (title.equals("Login")) {
        		WindowsNavigatorUtils.openModalWindow(event, fxmlFile, title);
        	} 
        	
        	
        	if (("Homepage".equals(title)) || ("Info Attività".equals(title))) {
        		WindowsNavigatorUtils.openWindow(event, fxmlFile, title);
        	}	
	}
	
	@FXML
	private void notImplementedYet(MouseEvent event) {
   	 	AlertUtils.notImplementedYet();
    }
}

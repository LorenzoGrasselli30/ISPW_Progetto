package application.view;

import java.io.IOException;
import java.util.function.Consumer;

import application.model.bean.ActivityDTO;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ActivityLayoutUtils {
	
	private static final String DEFAULT_FONT = "System";
	private static final String HBOX_PADDING = "-fx-padding: 0 5 0 5;";
	private static final String CARD_STYLE = "-fx-border-color: black; -fx-border-width: 0.2; -fx-padding: 5 0 5 0;";
		
	//Impedisco la creazione di istanze
	private ActivityLayoutUtils() {
		throw new IllegalStateException("AlertUtils class");
	}
	
	public static VBox createActivityCard(ActivityDTO activity, Consumer<MouseEvent> onCardClick, Consumer<MouseEvent> onHeartClick) {
		
		VBox card = new VBox();
	    card.setAlignment(Pos.CENTER);
	    card.setMinWidth(250.0);
	    card.setPrefWidth(250.0);
	    card.setSpacing(5.0);
	    card.setStyle(CARD_STYLE);
	    card.setCursor(Cursor.HAND);
	    
	    // --- Header con titolo e icona cuore ---
	    HBox header = new HBox();
	    header.setAlignment(Pos.TOP_CENTER);
	    header.setSpacing(10.0);
	    header.setStyle(HBOX_PADDING);
	    
	    Label titleLabel = new Label(activity.getActivityName());
	    titleLabel.setFont(Font.font(DEFAULT_FONT, FontWeight.BOLD, 18.0));
	    
	    Region spacer = new Region();
	    HBox.setHgrow(spacer, Priority.ALWAYS);
	    
	    ImageView heartIcon = new ImageView();
	    try {
	    	// Nota: Assicurati che il percorso dell'immagine sia corretto rispetto a dove viene caricata la classe
	        heartIcon.setImage(new Image(ActivityLayoutUtils.class.getResource("/Images/Heart.png").toExternalForm()));
	        heartIcon.setFitHeight(30.0);
	        heartIcon.setFitWidth(30.0);
	        heartIcon.setPreserveRatio(true);
	        heartIcon.setPickOnBounds(true);
	        
	        // Gestione click cuore
	        heartIcon.setOnMouseClicked(event -> {
	            event.consume(); // Previene la propagazione alla card
	            if (onHeartClick != null) {
	            	onHeartClick.accept(event);
	            }
	        });
	      
	    } catch (Exception e) {
	        System.err.println("Errore nel caricamento dell'icona cuore: " + e.getMessage());
	    }
	    
	    header.getChildren().addAll(titleLabel, spacer, heartIcon);
	    
	    // --- Descrizione ---
	    HBox descriptionBox = new HBox();
	    descriptionBox.setStyle(HBOX_PADDING);
	    Label descriptionLabel = new Label(activity.getDescription());
	    descriptionLabel.setFont(Font.font(DEFAULT_FONT, 14.0));
	    descriptionBox.getChildren().add(descriptionLabel);
	    
	    // --- Immagine attività ---
	    ImageView activityImage = new ImageView();
	    activityImage.setFitHeight(180.0);
	    activityImage.setFitWidth(250.0);
	    activityImage.setPreserveRatio(true);
	    activityImage.setPickOnBounds(true);
	    
	    try {
	        activityImage.setImage(new Image(ActivityLayoutUtils.class.getResource("/Images/caption (3).jpg").toExternalForm()));
	    } catch (Exception e) {
	        System.err.println("Errore nel caricamento dell'immagine attività: " + e.getMessage());
	    }
	    
	    // --- Rating ---
	    HBox ratingBox = new HBox();
	    ratingBox.setStyle(HBOX_PADDING);
	    Label ratingLabel = new Label(String.format("Punteggio: %.1f/5", activity.getRate()));
	    ratingLabel.setFont(Font.font(DEFAULT_FONT, FontWeight.BOLD, 14.0));
	    ratingLabel.setMinHeight(20.0);
	    ratingLabel.setPrefHeight(20.0);
	    
	    Region ratingSpacer = new Region();
	    HBox.setHgrow(ratingSpacer, Priority.ALWAYS);
	    
	    Label reviewsLabel = new Label(String.format("(%d recensioni)", activity.getnRating()));
	    reviewsLabel.setFont(Font.font(DEFAULT_FONT, 14.0));
	    
	    ratingBox.getChildren().addAll(ratingLabel, ratingSpacer, reviewsLabel);
	    
	    // --- Prezzo ---
	    HBox priceBox = new HBox();
	    priceBox.setStyle(HBOX_PADDING);
	    priceBox.setMinHeight(20.0);
	    priceBox.setPrefHeight(20.0);
	    
	    Label priceLabel = new Label(String.format("%.2f$ ", activity.getPrice()));
	    priceLabel.setFont(Font.font(DEFAULT_FONT, FontWeight.BOLD, 14.0));
	    
	    Label perPersonLabel = new Label("a persona");
	    perPersonLabel.setFont(Font.font(DEFAULT_FONT, 14.0));
	    
	    priceBox.getChildren().addAll(priceLabel, perPersonLabel);

	    // Assemblaggio card
	    card.getChildren().addAll(header, descriptionBox, activityImage, ratingBox, priceBox);
	    
	    // Gestione click card
	    card.setOnMouseClicked(event -> {
	    	if (onCardClick != null) {
	    		onCardClick.accept(event);
	    	}
	    });
		
		return card;
	}
}

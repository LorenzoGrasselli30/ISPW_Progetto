package application.controller.graphic;

import java.io.IOException;

import application.model.bean.BookingContext;
import application.view.AlertUtils;
import application.view.WindowsNavigatorUtils;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class FormGraphicController {
	
	@FXML
    private VBox participantsContainer;
	
	private BookingContext context; // Lo stato corrente

    // Metodo chiamato dal controller precedente
    public void initForm(BookingContext context) {
        this.context = context;
        
     // Pulisce eventuali elementi placeholder presenti nell'FXML
        participantsContainer.getChildren().clear();

        int totalParticipants = context.getnFullTickets() + context.getnReducedTickets();

        for (int i = 1; i <= totalParticipants; i++) {
            // Creazione del VBox per il singolo partecipante
            VBox participantBox = new VBox();
            participantBox.setStyle("-fx-background-color: #E7EFD6;");
            participantBox.setPadding(new Insets(5, 10, 5, 10));
            participantBox.setSpacing(5);

            //Titolo
            Label titleLabel = new Label("Informazioni " + i + "° partecipante:");
            titleLabel.setFont(Font.font("System", FontWeight.BOLD, 18));

            //Label e TextField Nome
            Label nameLabel = new Label("Nome partecipante");
            nameLabel.setFont(new Font(18));
            TextField nameField = new TextField();
            nameField.setPromptText("Inserisci il nome");
            nameField.setPrefHeight(30);

            //Label e TextField Cognome
            Label surnameLabel = new Label("Cognome partecipante");
            surnameLabel.setFont(new Font(18));
            TextField surnameField = new TextField();
            surnameField.setPromptText("Inserisci il cognome");
            surnameField.setPrefHeight(30);

            //Label e DatePicker Data di nascita
            Label dateLabel = new Label("Data di nascita");
            dateLabel.setFont(new Font(18));
            DatePicker datePicker = new DatePicker();
            datePicker.setPromptText("Seleziona");
            datePicker.setPrefHeight(30);

            //Aggiunta degli elementi al box del partecipante
            participantBox.getChildren().addAll(
                titleLabel, 
                nameLabel, nameField, 
                surnameLabel, surnameField, 
                dateLabel, datePicker
            );

            //Aggiunta del box al container principale
            participantsContainer.getChildren().add(participantBox);
        }
    }
    
    @FXML
   	private void useForm(MouseEvent event) throws IOException {
    	
    	switch (((Node) event.getSource()).getId()) {
        case "homeButton":
        	WindowsNavigatorUtils.openWindow(event, "homeView.fxml", "Homepage");
        	break;
        	
        case "areaUserButton":
        	AlertUtils.showAlert(AlertType.INFORMATION, "Logout", "Logout dell'utente");
        	WindowsNavigatorUtils.openWindow(event, "homeView.fxml", "Homepage");
            break;
            
        case "goBackButton":
        	WindowsNavigatorUtils.openActivityWindow(event, "activityView.fxml", "Info Attivita'", context.getActivity());
        	break;
        	
        case "confirmFormButton":
        	
        	break;
    	}
    }
}

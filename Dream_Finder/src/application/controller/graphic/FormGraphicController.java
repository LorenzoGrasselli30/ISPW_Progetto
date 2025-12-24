package application.controller.graphic;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import application.configuration.UserSession;
import application.controller.application.BookingApplicationController;
import application.model.bean.BookingContext;
import application.model.bean.GuestInformationDTO;
import application.model.bean.TravelerDTO;
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
	
	private BookingApplicationController bookingController;
	
	@FXML
    private VBox participantsContainer;
	
	private BookingContext context; // Lo stato corrente
	
	public FormGraphicController() {
		bookingController= new BookingApplicationController();
	}
	
    // Metodo chiamato dal controller precedente
    public void initForm(BookingContext context) {
        this.context = context;
        
        //Metodo che chiama l'application controller che trova le informazioni del traveler dalla mail
        TravelerDTO currentTraveler= bookingController.fetchCurrentTraveler(UserSession.getInstance());
        System.out.println(currentTraveler.getName());
    	System.out.println(currentTraveler.getSurname());
    	System.out.println(currentTraveler.getDob());
    	
        //Pulisce eventuali elementi placeholder presenti nell'FXML
        participantsContainer.getChildren().clear();

        int totalParticipants = context.getnFullTickets() + context.getnReducedTickets();

        for (int i = 1; i <= totalParticipants; i++) {
            //Creazione del VBox per il singolo partecipante
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
        	List<GuestInformationDTO> guests = new ArrayList<>();
        	boolean allFieldsFilled = true;
        	
        	// Iterazione su ogni VBox nel participantsContainer
        	for (Node node : participantsContainer.getChildren()) {
        	    if (node instanceof VBox) { //L'oggetto contenuto nella variabile node è un'istanza della classe VBox di una sua sottoclasse
        	        VBox box = (VBox) node;
        	        
        	        // Estrazione dei controlli dal VBox
        	        // Gli indici dipendono dall'ordine di aggiunta in initForm:
        	        // 0: titleLabel, 1: nameLabel, 2: nameField, 
        	        // 3: surnameLabel, 4: surnameField, 5: dateLabel, 6: datePicker
        	        
        	        TextField nameField = (TextField) box.getChildren().get(2);
        	        TextField surnameField = (TextField) box.getChildren().get(4);
        	        DatePicker datePicker = (DatePicker) box.getChildren().get(6);
        	        
        	        String name = nameField.getText();
        	        String surname = surnameField.getText();
        	        
        	        if (name == null || name.trim().isEmpty() || 
        	            surname == null || surname.trim().isEmpty() || 
        	            datePicker.getValue() == null) {
        	            
        	        	AlertUtils.showAlert(AlertType.WARNING, "Dati mancanti", "Per favore, compila tutti i campi richiesti.");
        	            allFieldsFilled = false;
        	            break;
        	        }
        	        
        	        GuestInformationDTO guest = new GuestInformationDTO();
        	        guest.setName(name);
        	        guest.setSurname(surname);
        	        guest.setDateOfBirth(datePicker.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE));
        	        
        	        guests.add(guest);
        	    }
        	}
        	
        	if (allFieldsFilled) {
        	    context.setGuests(guests);
        	    for (GuestInformationDTO guest : context.getGuests()) {
        	    	System.out.println(guest.getName());
        	    	System.out.println(guest.getSurname());
        	    	System.out.println(guest.getDateOfBirth());
        	    }
        	    
        	    
        	    
        	    AlertUtils.showAlert(AlertType.INFORMATION, "Successo", "Dati partecipanti salvati correttamente!");
        	}
        	break;
    	}
    }
}

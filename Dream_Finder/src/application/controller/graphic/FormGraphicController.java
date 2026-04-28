package application.controller.graphic;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
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
    	
        //Pulisce eventuali elementi placeholder presenti nell'FXML
        participantsContainer.getChildren().clear();

        int totalParticipants = context.getnFullTickets() + context.getnReducedTickets();

        for (int i = 1; i <= totalParticipants; i++) {
            //Creazione del VBox per il singolo partecipante
            VBox participantBox = new VBox();
            participantBox.setStyle("-fx-background-color: #E7EFD6;");
            participantBox.setPadding(new Insets(5, 10, 5, 10));
            participantBox.setSpacing(5);

            // HBox intestazione: titolo partecipante + tipo biglietto
            HBox headerBox = new HBox();
            //headerBox.setAlignment(Pos.CENTER_LEFT);
      
            Label titleLabel = new Label("Informazioni " + i + "° partecipante:");
            titleLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
            
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            
            String ticketTypeText = (i <= context.getnFullTickets()) ? "Biglietto intero" : "Biglietto ridotto";
            Label ticketTypeLabel = new Label(ticketTypeText);

            headerBox.getChildren().addAll(titleLabel, spacer, ticketTypeLabel);
            
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
            datePicker.setEditable(false);    
            
            // Impedisce la selezione di date future nel datepicker
            datePicker.setDayCellFactory(dp -> new javafx.scene.control.DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    setDisable(empty || item.isAfter(LocalDate.now()));
                }
            });
            
            //I campi delle informazioni del primo partecipante vengono inserite automaticamente
            if (i == 1) {
                if (currentTraveler.getName() != null) {
                    nameField.setText(currentTraveler.getName());
                }
                
                if (currentTraveler.getSurname() != null) {
                    surnameField.setText(currentTraveler.getSurname());
                }
                
                if (currentTraveler.getDob() != null) {
                    datePicker.setValue(currentTraveler.getDob());
                }
            }
            
            //Aggiunta degli elementi al box del partecipante
            participantBox.getChildren().addAll(
            	headerBox, 
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
        	boolean validAgeForTicket = true;
        	
        	int i=0;
        	// Iterazione su ogni VBox nel participantsContainer
        	for (Node node : participantsContainer.getChildren()) {
        	    if (node instanceof VBox) { //L'oggetto contenuto nella variabile node è un'istanza della classe VBox di una sua sottoclasse
        	    	VBox box = (VBox) node;
        	        
        	        TextField nameField = (TextField) box.getChildren().get(2);
        	        TextField surnameField = (TextField) box.getChildren().get(4);
        	        DatePicker datePicker = (DatePicker) box.getChildren().get(6);
        	        
        	        String name = nameField.getText();
        	        String surname = surnameField.getText();
        	        
        	        allFieldsFilled = this.validateFieldsFilled(name, surname, datePicker.getValue());
        	        
        	        validAgeForTicket = this.validateAgeForTicket(datePicker.getValue(), i);
        	        
        	        if (allFieldsFilled == false || validAgeForTicket == false) {
        	        	break;
        	        }
        	        
        	        GuestInformationDTO guest = new GuestInformationDTO();
        	        guest.setName(name);
        	        guest.setSurname(surname);
        	        guest.setDateOfBirth(datePicker.getValue());
        	        
        	        i++;
        	        guests.add(guest);
        	    }
        	}
        	
        	if (allFieldsFilled && validAgeForTicket) {
        		//Inserisco la lista degli ospiti nel booking context
        	    context.setGuests(guests);
        	    
        	    WindowsNavigatorUtils.openPaymentWindow(event, "paymentView.fxml", "Schermata di pagamento", context);
        	}
        	break;
        	
        default: 
        	break;
        	
    	}
    }
    
    private boolean validateFieldsFilled(String name, String surname, LocalDate dob) {
    		if (name == null || name.trim().isEmpty() || 
	            surname == null || surname.trim().isEmpty() || 
	            dob == null) {
	            
	        	AlertUtils.showAlert(AlertType.WARNING, "Dati mancanti", "Per favore, compila tutti i campi richiesti.");
	            
	            return false;
	        }
    	
        return true;
    }

    private boolean validateAgeForTicket(LocalDate dob, int participantIndex) {
        int age = Period.between(dob, LocalDate.now()).getYears();
        boolean isFullTicket = participantIndex < context.getnFullTickets();

        if (isFullTicket && age <= 12) {
            AlertUtils.showAlert(AlertType.WARNING, "Dati errati",
                "I biglietti interi NON valgono per partecipanti con un età minore o uguale ai 12 anni.");
            return false;
        }

        if (!isFullTicket && age > 12) {
            AlertUtils.showAlert(AlertType.WARNING, "Dati errati",
                "I biglietti ridotti NON valgono per partecipanti con un età superiore ai 12 anni.");
            return false;
        }

        return true;
    }
}

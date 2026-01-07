package application.controller.graphic;

import application.model.bean.BookingDTO;
import application.model.bean.GuestInformationDTO;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TicketGraphicController {
	
	@FXML
    private Label nameLabel;

    @FXML
    private Label providerNameLabel;

    @FXML
    private Label bookingIDLabel;
    
    @FXML
    private Label activityNameLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label guideLabel;

    @FXML
    private Label shuttleLabel;

    @FXML
    private VBox participantsBox;
    
	public void initBooking(BookingDTO booking) {
		/*
		if (booking == null) {
            return;
        }
	*/
        // Imposta info Traveler e Provider (Activity Name usato come provider name/titolo in questo contesto)
        if (nameLabel != null) {
            nameLabel.setText(booking.getTravelerName() != null ? booking.getTravelerName() : "N/D");
        }
        if (providerNameLabel != null) {
            providerNameLabel.setText(booking.getProviderName() != null ? booking.getProviderName() : "N/D");
        }

        // Imposta info prenotazione
        if (bookingIDLabel != null) {
            bookingIDLabel.setText("- ID: " + (booking.getBookingID() != null ? booking.getBookingID() : "N/D"));
        }
        if (activityNameLabel != null) {
        	activityNameLabel.setText("- Nome dell'attività: " + (booking.getActivityName() != null ? booking.getActivityName() : "N/D"));
        }
        if (dateLabel != null) {
            dateLabel.setText("- Data: " + (booking.getBookingDate() != null ? booking.getBookingDate() : "N/D"));
        }
        if (guideLabel != null) {
            guideLabel.setText("- Tour guidato: " + (booking.isGuideService() ? "Sì" : "No"));
        }
        if (shuttleLabel != null) {
            shuttleLabel.setText("- Servizio navetta: " + (booking.isShuttleService() ? "Sì" : "No"));
        }

        // Popola la lista dei partecipanti
        if (participantsBox != null) {
            participantsBox.getChildren().clear(); // Pulisce eventuali placeholder

            if (booking.getGuests() != null) {
                int count = 1;
                /*
                int fullTicketsLeft = booking.getnFullTickets();
                int reducedTicketsLeft = booking.getnReducedTickets();
	*/
                
                for (GuestInformationDTO guest : booking.getGuests()) {
                    VBox guestBox = new VBox();
                    guestBox.setSpacing(2);
                    guestBox.setPadding(new Insets(10, 0, 10, 0));

                    // Titolo Partecipante
                    Label titleLabel = new Label(count + "° Partecipante");
                    titleLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
                    
                    // Info Partecipante
                    Label nameLbl = new Label("Nome: " + guest.getName());
                    nameLbl.setFont(new Font(18));
                    
                    Label surnameLbl = new Label("Cognome: " + guest.getSurname());
                    surnameLbl.setFont(new Font(18));
                    
                    Label dobLbl = new Label("Data di nascita: " + guest.getDateOfBirth());
                    dobLbl.setFont(new Font(18));

                    String ticketType = "Intero";
                    try {
                        java.time.LocalDate dob = java.time.LocalDate.parse(guest.getDateOfBirth());
                        java.time.LocalDate now = java.time.LocalDate.now();
                        int age = java.time.Period.between(dob, now).getYears();
                        
                        if (age <= 7) {
                            ticketType = "Ridotto";
                        }
                    } catch (Exception e) {
                        System.err.println("Errore nel parsing della data di nascita: " + guest.getDateOfBirth());
                    }
                    
                    Label ticketTypeLbl = new Label("Tipologia di biglietto: " + ticketType);
                    ticketTypeLbl.setFont(new Font(18));

                    // Aggiunge tutto al box del partecipante
                    guestBox.getChildren().addAll(titleLabel, nameLbl, surnameLbl, dobLbl, ticketTypeLbl);
                    
                    // Aggiunge il box del partecipante alla lista principale
                    participantsBox.getChildren().add(guestBox);
                    
                    count++;
                }
            }
        }
	}

}

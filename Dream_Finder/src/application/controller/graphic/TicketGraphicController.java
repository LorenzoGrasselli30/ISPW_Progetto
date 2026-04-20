package application.controller.graphic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import application.model.bean.BookingDTO;
import application.model.bean.GuestInformationDTO;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TicketGraphicController {
	
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
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
 
        if (nameLabel != null) {
            nameLabel.setText(booking.getTravelerName()+" "+booking.getTravelerSurname());
        }
        if (providerNameLabel != null) {
            providerNameLabel.setText(booking.getProviderName());
        }

        // Imposta info prenotazione
        if (bookingIDLabel != null) {
            bookingIDLabel.setText("- ID: " + booking.getBookingID());
        }
        if (activityNameLabel != null) {
        	activityNameLabel.setText("- Nome dell'attività: " + booking.getActivityName());
        }
        if (dateLabel != null) {
            dateLabel.setText("Il biglietto è valido per il giorno: " + booking.getBookedDate().format(DATE_FORMATTER));
        }
        if (guideLabel != null) {
            guideLabel.setText("- Tour guidato: " + (booking.isGuideService() ? "Si" : "No"));
        }
        if (shuttleLabel != null) {
            shuttleLabel.setText("- Servizio navetta: " + (booking.isShuttleService() ? "Si" : "No"));
        }

        // Popola la lista dei partecipanti
        populateParticipants(booking);
	}
	
	private void populateParticipants(BookingDTO booking) {
        if (participantsBox == null) {
            return;
        }

        participantsBox.getChildren().clear(); // Pulisce eventuali placeholder

        if (booking.getGuests() == null) {
            return;
        }

        int count = 1;
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

            Label dobLbl = new Label("Data di nascita: " + guest.getDateOfBirth().format(DATE_FORMATTER));
            dobLbl.setFont(new Font(18));

            String ticketType = "Intero";
            try {
                LocalDate dob = guest.getDateOfBirth();
                LocalDate now = LocalDate.now();
                int age = java.time.Period.between(dob, now).getYears();

                if (age <= 12) {
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

package application.model.dao.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import application.exception.DAOException;
import application.model.dao.BookingDAO;
import application.model.dao.db.queries.SQLQueries;
import application.model.entity.Activity;
import application.model.entity.ActivityAvailableDates;
import application.model.entity.ActivityOtherInformation;
import application.model.entity.ActivityRating;
import application.model.entity.Booking;
import application.model.entity.BookingPriceInformation;
import application.model.entity.GuestInformation;
import application.model.entity.Provider;
import application.model.entity.Traveler;
import application.model.enums.ActivityType;
import application.model.enums.ProviderType;

public class BookingDAODB implements BookingDAO {

	@Override
	public String confirmBooking(Booking booking) {
		booking.setBookingID(UUID.randomUUID().toString());
		
		String result= booking.getBookingID();
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmBooking = conn.prepareStatement(SQLQueries.INSERT_BOOKING);
				PreparedStatement stmGuests = conn.prepareStatement(SQLQueries.INSERT_GUEST)) {
			
			stmBooking.setString(1, result);
			stmBooking.setDate(2, Date.valueOf(booking.getBookingDate()));
			stmBooking.setDate(3, Date.valueOf(booking.getBookedDate()));
			stmBooking.setInt(4, booking.getPriceInformation().getnFullTickets());
			stmBooking.setInt(5, booking.getPriceInformation().getnReducedTickets());
			stmBooking.setBoolean(6, booking.getPriceInformation().isShuttleService());
			stmBooking.setBoolean(7, booking.getPriceInformation().isGuideService());
			stmBooking.setDouble(8, booking.getPriceInformation().getShuttlePrice());
			stmBooking.setDouble(9, booking.getPriceInformation().getGuidePrice());
			stmBooking.setDouble(10, booking.getPriceInformation().getTotalPrice());
			stmBooking.setString(11, booking.getTraveler().getEmail());
			stmBooking.setString(12, booking.getActivity().getActivityName());
			
			stmBooking.executeUpdate();
			
			stmGuests.setString(1, result);
			for (GuestInformation guest : booking.getGuests()) {
				stmGuests.setString(2, guest.getName());
				stmGuests.setString(3, guest.getSurname());
				stmGuests.setDate(4, Date.valueOf(guest.getDateOfBirth()));
				
				stmGuests.addBatch();
			}
			
			stmGuests.executeBatch();
			
		} catch (SQLException e) {
			throw new DAOException("Errore nel salvataggio della prenotazione");
	    }
		
		return result;
	}

	@Override
	public Booking findByID(String bookingID) {
		Booking newBooking = null;
		
		Connection conn;
		try {
			conn = DatabaseConnection.getConnection();
		} catch (SQLException e) {
			throw new DAOException("Errore di accesso al database");
		}
		
		try (PreparedStatement stmBooking = conn.prepareStatement(SQLQueries.FIND_BOOKING)) {
			
			stmBooking.setString(1, bookingID);
			ResultSet rsBooking = stmBooking.executeQuery();
			
			
			while(rsBooking.next()) {
				
				Provider provider = new Provider(
						rsBooking.getString("providerEmail"), 
						null,
						rsBooking.getString("providerName"), 
						ProviderType.fromString(rsBooking.getString("providerType")), 
						rsBooking.getInt("nOfferedActivities"), 
						rsBooking.getString("location"), 
						rsBooking.getString("pname"), 
						rsBooking.getString("psurname")
				);
				
				Activity activity = new Activity(
						rsBooking.getString("activityName"), 
						rsBooking.getDouble("price"), 
						ActivityType.fromString(rsBooking.getString("activityType")), 
						provider, 
						new ActivityRating(
								rsBooking.getDouble("activityRate"), 
								rsBooking.getInt("nRating")
								), 
						new ActivityOtherInformation (
								rsBooking.getString("activityDescription"), 
								rsBooking.getBoolean("freeCancellation"), 
								rsBooking.getBoolean("payLater"), 
								rsBooking.getBoolean("skipLine"), 
								rsBooking.getInt("duration"), 
								rsBooking.getBoolean("timeInMinutes")
								), 
						null
						);
				
				newBooking = new Booking (
						bookingID, 
						new Traveler (
								rsBooking.getString("email"), 
								rsBooking.getString("password"), 
								rsBooking.getString("username"), 
								rsBooking.getString("travelerName"), 
								rsBooking.getString("travelerSurname"), 
								rsBooking.getDate("dob").toLocalDate()
								), 
						null, 
						activity, 
						new BookingPriceInformation (
								rsBooking.getInt("nFullTickets"), 
								rsBooking.getInt("nReducedTickets"),
								rsBooking.getBoolean("shuttleService"),
								rsBooking.getBoolean("guideService"),
								rsBooking.getDouble("shuttlePrice"), 
								rsBooking.getDouble("guidePrice"), 
								rsBooking.getDouble("totalPrice")
								), 
						rsBooking.getDate("bookingDate").toLocalDate(), 
						rsBooking.getDate("bookedDate").toLocalDate()
						);
			}
			
		} catch (SQLException e) {
	    	throw new DAOException("Errore di ricerca della prenotazione");
	    }
		
		//Recupero dei partecipanti
		try (PreparedStatement stmGuests = conn.prepareStatement(SQLQueries.FIND_GUESTS)) {
			List<GuestInformation> guests= new ArrayList<>();
			
			stmGuests.setString(1, bookingID);
			ResultSet rsGuests = stmGuests.executeQuery();
			
			while(rsGuests.next()) {
				GuestInformation guest = new GuestInformation(
						rsGuests.getString("guestName"), 
						rsGuests.getString("guestSurname"), 
						rsGuests.getDate("dob").toLocalDate()
						);
				
				guests.add(guest);
			}
			
			newBooking.setGuests(guests);
		} catch (SQLException e) {
	    	throw new DAOException("Errore di ricerca della prenotazione");
	    }
		
		//Recupero delle date disponibili
		try (PreparedStatement stmDates = conn.prepareStatement(SQLQueries.FIND_AVAILABLE_DATES)) {
			Map<LocalDate, Integer> availablePlaces = new HashMap<>();
			
			stmDates.setString(1, newBooking.getActivity().getActivityName());
			stmDates.setString(2, newBooking.getActivity().getProvider().getEmail());
			ResultSet rsDates = stmDates.executeQuery();
			
			while(rsDates.next()) {
				   LocalDate date = rsDates.getDate("aDay").toLocalDate(); 
				   Integer places = rsDates.getInt("nPlaces");              
				   availablePlaces.put(date, places);
			}
				
			newBooking.getActivity().setAvaibleDates(new ActivityAvailableDates(availablePlaces));
			
			
		} catch (SQLException e) {
	    	throw new DAOException("Errore di ricerca della prenotazione");
	    }

		return newBooking;
	}
}

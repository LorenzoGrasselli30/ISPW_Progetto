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
import application.model.entity.User;
import application.model.enums.ActivityType;
import application.model.enums.ProviderType;
import application.model.enums.UserRole;

public class BookingDAODB implements BookingDAO {

	@Override
	public String confirmBooking(Booking booking) {
		booking.setBookingID(UUID.randomUUID().toString());
		
		String result= booking.getBookingID();
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmBooking = conn.prepareStatement(SQLQueries.INSERT_BOOKING)) {
			
			stmBooking.setString(1, result);
			stmBooking.setDate(2, Date.valueOf(booking.getBookingDate()));
			stmBooking.setDate(3, Date.valueOf(booking.getBookingDate()));
			stmBooking.setInt(4, booking.getPriceInformation().getnFullTickets());
			stmBooking.setInt(5, booking.getPriceInformation().getnReducedTickets());
			stmBooking.setBoolean(6, booking.getPriceInformation().isShuttleService());
			stmBooking.setBoolean(7, booking.getPriceInformation().isGuideService());
			stmBooking.setDouble(8, booking.getPriceInformation().getShuttlePrice());
			stmBooking.setDouble(9, booking.getPriceInformation().getGuidePrice());
			stmBooking.setDouble(10, booking.getPriceInformation().getTotalPrice());
			stmBooking.setString(11, booking.getTraveler().getTravelerUser().getEmail());
			stmBooking.setString(12, booking.getActivity().getActivityName());
			
			stmBooking.executeUpdate();
			
		} catch (SQLException e) {
	    	throw new DAOException("Errore nel salvataggio della prenotazione");
	    }
		
		return result;
	}

	@Override
	public Booking findByID(String bookingID) {
		Booking newBooking = null;
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmBooking = conn.prepareStatement(SQLQueries.FIND_BOOKING);
				PreparedStatement stmGuests = conn.prepareStatement(SQLQueries.FIND_GUESTS);
				PreparedStatement stmDates = conn.prepareStatement(SQLQueries.FIND_AVAILABLE_DATES)) {
			
			stmBooking.setString(1, bookingID);
			ResultSet rsBooking = stmBooking.executeQuery();
			
			while(rsBooking.next()) {
				
				stmGuests.setString(1, bookingID);
				ResultSet rsGuests = stmGuests.executeQuery();
				
				List<GuestInformation> guests= new ArrayList<>();
				
				while(rsGuests.next()) {
					GuestInformation guest = new GuestInformation(
							rsGuests.getString("guestName"), 
							rsGuests.getString("guestSurname"), 
							rsGuests.getString("dob")
							);
					
					guests.add(guest);
				}
				
				Map<LocalDate, Integer> availablePlaces = new HashMap<>();
				
				stmDates.setString(1, rsBooking.getString("activityName"));
				stmDates.setString(2, rsBooking.getString("email"));
				ResultSet rsDates = stmDates.executeQuery();
				
				while(rsDates.next()) {
					   LocalDate date = rsDates.getDate("aDay").toLocalDate(); 
					   Integer places = rsDates.getInt("nPlaces");              
					   availablePlaces.put(date, places);
				}
					
				ActivityAvailableDates availableDates = new ActivityAvailableDates(availablePlaces);
				
				Provider provider = new Provider(
						null, 
						bookingID, 
						null, 
						null, 
						bookingID, 
						bookingID, 
						bookingID
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
						availableDates
						);
				
				new Booking (
						bookingID, 
						new Traveler (
								rsBooking, 
								rsBooking, 
								rsBooking, 
								rsBooking, 
								rsBooking
								), 
						guests, 
						activity, 
						new BookingPriceInformation (
								rsBooking, 
								rsBooking, 
								rsBooking, 
								rsBooking, 
								rsBooking
								), 
						rsBooking, 
						rsBooking
						);
			}
			
		} catch (SQLException e) {
	    	throw new DAOException("Errore di ricerca della prenotazione");
	    }
		
		return newBooking;
	}
}

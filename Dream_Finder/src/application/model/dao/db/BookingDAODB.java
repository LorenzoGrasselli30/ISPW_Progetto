package application.model.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import application.exception.DAOException;
import application.model.dao.BookingDAO;
import application.model.dao.db.queries.SQLQueries;
import application.model.entity.Booking;
import application.model.entity.Provider;
import application.model.entity.User;
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
			stmBooking.setDate(2, booking.getBookingDate());
			stmBooking.setDate(3, booking.getBookedDate());
			stmBooking.setInt(4, booking.getPriceInformation().getnFullTickets());
			stmBooking.setInt(5, booking.getPriceInformation().getnReducedTickets());
			stmBooking.setBoolean(6, booking.getPriceInformation().isShuttleService());
			stmBooking.setBoolean(7, booking.getPriceInformation().isGuideService());
			stmBooking.setDouble(8, booking.getPriceInformation().getShuttlePrice());
			stmBooking.setDouble(9, booking.getPriceInformation().getGuidePrice());
			stmBooking.setDouble(10, booking.getPriceInformation().getTotalPrice());
			stmBooking.setString(11, booking.getTraveler().getTravelerUser().getEmail());
			stmBooking.setString(12, booking.getActivity().getActivityName());
			
			ResultSet rsBooking = stmBooking.executeQuery();
			
		} catch (SQLException e) {
	    	throw new DAOException("Errore di ricerca del provider");
	    }
		
		return result;
	}

	@Override
	public Booking findByID(String bookingID) {
		// TODO Auto-generated method stub
		return null;
	}
}

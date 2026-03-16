package application.model.dao.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import application.model.dao.BookingDAO;
import application.model.entity.Booking;

public class BookingDAODemo implements BookingDAO {
	
	private Map<String, Booking> bookings = new HashMap<>();

	@Override
	public String confirmBooking(Booking booking) {
		booking.setBookingID(UUID.randomUUID().toString());
		
		String result= booking.getBookingID();
				
		bookings.put(result, booking);
		
		return result;
	}

	@Override
	public Booking findByID(String bookingID) {
		return bookings.get(bookingID);
	}
	
}

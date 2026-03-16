package application.model.dao;

import application.model.entity.Booking;

public interface BookingDAO {
	String confirmBooking(Booking booking);

	Booking findByID(String bookingID);
}

package application.model.dao.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.model.dao.BookingDAO;
import application.model.entity.Activity;
import application.model.entity.Booking;
import application.model.entity.GuestInformation;
import application.model.entity.Traveler;

public class BookingDAODemo implements BookingDAO {
	
	private Map<String, Booking> bookings = new HashMap<>();
	
	@Override
	public Boolean confirmBooking(String bookingID, Traveler traveler, List<GuestInformation> guests, Activity activity,
			int nFullTickets, int nReducedTickets, boolean shuttleService, boolean guideService, Double totalPrice,
			String bookingDate) {
		// TODO Auto-generated method stub
		return null;
	}


}

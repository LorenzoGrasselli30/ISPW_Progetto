package application.model.dao.demo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import application.model.dao.BookingDAO;
import application.model.entity.Activity;
import application.model.entity.Booking;
import application.model.entity.GuestInformation;
import application.model.entity.Traveler;

public class BookingDAODemo implements BookingDAO {
	
	private Map<String, Booking> bookings = new HashMap<>();
	
	@Override
	public Boolean confirmBooking(Traveler traveler, List<GuestInformation> guests, Activity activity,
			int nFullTickets, int nReducedTickets, boolean shuttleService, boolean guideService, Double totalPrice) {

		String uniqueBookingID = UUID.randomUUID().toString();
		
		String bookingDate = LocalDateTime.now().toString();
		
		Booking newBooking = new Booking(uniqueBookingID, traveler, guests, activity, nFullTickets, 
				nReducedTickets, shuttleService, guideService, totalPrice, bookingDate);
				
		bookings.put(uniqueBookingID, newBooking);
				
		return true;
	}

}

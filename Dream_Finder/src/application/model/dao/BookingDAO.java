package application.model.dao;

import java.util.List;

import application.model.entity.Activity;
import application.model.entity.GuestInformation;
import application.model.entity.Traveler;

public interface BookingDAO {
	Boolean confirmBooking(
			Traveler traveler,
			List<GuestInformation> guests,
			Activity activity,
			
			int nFullTickets,
			int nReducedTickets,
		    boolean shuttleService,
		    boolean guideService,
		    Double totalPrice
			);
}

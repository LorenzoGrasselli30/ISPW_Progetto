package application.model.dao.db;

import java.util.List;

import application.model.dao.BookingDAO;
import application.model.entity.Activity;
import application.model.entity.GuestInformation;
import application.model.entity.Traveler;

public class BookingDAODB implements BookingDAO {

	@Override
	public Boolean confirmBooking(Traveler traveler, List<GuestInformation> guests, Activity activity, int nFullTickets,
			int nReducedTickets, boolean shuttleService, boolean guideService, Double totalPrice) {
		// TODO Auto-generated method stub
		return null;
	}


}

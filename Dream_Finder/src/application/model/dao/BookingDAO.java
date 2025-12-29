package application.model.dao;

import java.util.List;

import application.model.entity.Activity;
import application.model.entity.Booking;
import application.model.entity.GuestInformation;
import application.model.entity.Traveler;

public interface BookingDAO {
	Boolean confirmBooking(Booking booking);
}

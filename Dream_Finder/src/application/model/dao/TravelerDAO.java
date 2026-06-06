package application.model.dao;

import java.util.List;

import application.model.entity.Provider;
import application.model.entity.Traveler;

public interface TravelerDAO extends UserDAO {
	Traveler findByEmail(String formattedEmail);
	List<Provider> fetchBookingHistory(Traveler traveler);
}

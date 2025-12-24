package application.model.dao;

import application.configuration.UserSession;
import application.model.entity.Traveler;

public interface TravelerDAO {
	Traveler findByEmail(String formattedEmail);
}

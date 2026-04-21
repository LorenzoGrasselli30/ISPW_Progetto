package application.model.dao.demo;

import java.util.Map;

import application.model.dao.ProviderDAO;
import application.model.dao.TravelerDAO;
import application.model.dao.UserDAO;
import application.model.entity.Provider;
import application.model.entity.Traveler;
import application.model.entity.User;

public class UserDAODemo implements UserDAO {
	
	private final TravelerDAO travelerDAO;
    private final ProviderDAO providerDAO;

    // Dependency Injection: più testabile e O.O.
    public UserDAODemo(TravelerDAO travelerDAO, ProviderDAO providerDAO) {
        this.travelerDAO = travelerDAO;
        this.providerDAO = providerDAO;
    }

    @Override
    public User findByEmail(String email) {
        // 1) cerco tra i traveler
        Traveler traveler = travelerDAO.findByEmail(email);
        if (traveler != null) {
            return traveler; 
        }

        // 2) se non trovato, cerco tra i provider
        Provider provider = providerDAO.findByEmail(email);
        if (provider != null) {
            return provider; 
        }

        // 3) non trovato
        return null;
    }
}

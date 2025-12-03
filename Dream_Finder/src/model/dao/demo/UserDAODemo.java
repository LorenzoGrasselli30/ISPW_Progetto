package model.dao.demo;

import java.util.HashMap;
import java.util.Map;

import model.dao.UserDAO;
import model.entity.User;
import model.enums.UserRole;

public class UserDAODemo implements UserDAO {
	
	private Map<String, User> users = new HashMap<>();

    public UserDAODemo() {
    	initializeUsersDemo();
    }
    
    private void initializeUsersDemo() {
    	// Inizializzazione traveler nella demo
    	users.put("mario.rossi@mail.com", new User("mario.rossi@mail.com", "Mariorossi1!", UserRole.TRAVELER));
    	users.put("utente1@mail.com", new User("utente1@mail.com", "Utente1!", UserRole.TRAVELER));
    	users.put("utente2@mail.com", new User("utente2@mail.com", "Utente2!", UserRole.TRAVELER));
    	users.put("utente3@mail.com", new User("utente3@mail.com", "Utente3!", UserRole.TRAVELER));
    	
    	//Inizializzazione provider nella demo
    	users.put("luigi.verdi@mail.com", new User("luigi.verdi@mail.com", "Luigiverdi1!", UserRole.PROVIDER));
    	users.put("fornitore1@mail.com", new User("fornitore1@mail.com", "Fornitore1!", UserRole.PROVIDER));
    	users.put("fornitore2@mail.com", new User("fornitore2@mail.com", "Fornitore2!", UserRole.PROVIDER));
    	users.put("foenitore3@mail.com", new User("foenitore3@mail.com", "Fornitore3!", UserRole.PROVIDER));
    } 

    @Override
    public User findByEmail(String email) {
        return users.get(email);
    }

	@Override
	public void saveNewUser(User user) {
		// Salvataggio dell'utente
		users.put(user.getEmail(), user);
	}
	
}

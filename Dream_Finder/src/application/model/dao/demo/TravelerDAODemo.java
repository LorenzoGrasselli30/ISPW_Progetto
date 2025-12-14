package application.model.dao.demo;

import java.util.HashMap;
import java.util.Map;

import application.model.dao.TravelerDAO;
import application.model.entity.Traveler;

public class TravelerDAODemo implements TravelerDAO {
	
	private Map<String, Traveler> travelers = new HashMap<>();
	
    public TravelerDAODemo() {
    	inizialiveTravelersDemo();
    }

	private void inizialiveTravelersDemo() {
		travelers.put("mario.rossi@mail.com", new Traveler("mario.rossi@mail.com", "Mariorossi1!", "Mariorossi1", "2004/10/21"));
		travelers.put("utente1@mail.com", new Traveler("utente1@mail.com", "Utente1!", "Utente1", "1999/07/10"));
		travelers.put("utente2@mail.com", new Traveler("utente2@mail.com", "Utente2!", "Utente2", "1984/08/08"));
		travelers.put("utente3@mail.com", new Traveler("utente3@mail.com", "Utente3!", "Utente3", "1969/04/30"));
	}
    
    
}

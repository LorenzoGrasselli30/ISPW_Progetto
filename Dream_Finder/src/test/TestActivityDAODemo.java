package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import application.exception.DAOException;
import application.exception.ValidationException;
import application.model.dao.ActivityDAO;
import application.model.dao.demo.ActivityDAODemo;
import application.model.dao.ProviderDAO;
import application.model.dao.demo.ProviderDAODemo;
import application.model.entity.Activity;
import application.model.entity.Provider;
import application.model.enums.ActivityType;

public class TestActivityDAODemo {

	ProviderDAO providerDAO = new ProviderDAODemo();
	ActivityDAO activityDAO = new ActivityDAODemo(providerDAO);
	
	@Test
    void testfindTopActivities_invalidProviders() {
		// Verifica che venga lanciata una DAOException quando si immette una lista di provider non valida
		List<Provider> providers = providerDAO.findTopProviders();
		
		// Ipotizziamo che non venga inizializzato correttamente l'email di un provider
		providers.get(0).setEmail("EmailNonValida");
		
		assertThrows(DAOException.class, () -> {
			activityDAO.findTopActivities(providers); 
        });
    }
	
	@Test
    void testfindTopActivities_unfoundedActivity() {
		// Verifica che viene restituito null quando si immette un'attività che non appartiene a quel provider
		// l'attività "Roma: tour guidato del Colosseo" appartiene al provider di nome "LuigiSRL"
		assertEquals(null, activityDAO.findByProvider("Roma: tour guidato del Colosseo", "Provider2Group"));
    }
	
	@Test
    void testfindRelatedActivities_invalidActivityName() {
		// Verifica che venga lanciata una DAOException quando si immette un nome di un attività non valido
		// l'attività "Roma: tour guidato del Colosseo" appartiene al provider di nome "LuigiSRL"
		assertThrows(DAOException.class, () -> {
			activityDAO.findRelatedActivities("Roma: tour guidato del Colosseo", ActivityType.CULTURE , "Provider2Group"); 
        });
    }
	
	@Test
    void testreservePlaces_zeroRequestedPlaces() {
		// Verifica che venga lanciata una DAOException si richiedono un numero di posti della prenotazione pari a zero
		// Prendo una attività
		Activity activity = activityDAO.findByProvider("Roma: tour guidato del Colosseo", "LuigiSRL");
		
		// Prendo un giorno valido per la prenotazione: i giorni disponibili sono tutti i giorni a partire da domani della settimana tranne domenica
		LocalDate current = java.time.LocalDate.now();
		LocalDate day;
		if (current.getDayOfWeek() == java.time.DayOfWeek.SATURDAY) {
			day = current.plusDays(2);
        } else {
        	day = current.plusDays(1); //Aumenta di un giorno
        }
		
		assertThrows(DAOException.class, () -> {
			activityDAO.reservePlaces(activity, day, 0); 
        });
    }
	
	@Test
    void testreservePlaces_invalidDay() {
		// Verifica che venga lanciata una DAOException si richiede un giorno che non ha posti disponibili
		// Prendo una attività
		Activity activity = activityDAO.findByProvider("Roma: tour guidato del Colosseo", "LuigiSRL");
		
		// Prendo un giorno non valido per la prenotazione
		LocalDate current = java.time.LocalDate.now();
		
		assertThrows(DAOException.class, () -> {
			activityDAO.reservePlaces(activity, current, 1); 
        });
    }
	
	@Test
    void testreservePlaces_validInput() {
		// Verifica che venga restituito il valore true su input corretti
		// Prendo una attività
		Activity activity = activityDAO.findByProvider("Roma: tour guidato del Colosseo", "LuigiSRL");
		
		// Prendo un giorno valido per la prenotazione: i giorni disponibili sono tutti i giorni a partire da domani della settimana tranne domenica
		LocalDate current = java.time.LocalDate.now();
		LocalDate day;
		if (current.getDayOfWeek() == java.time.DayOfWeek.SATURDAY) {
			day = current.plusDays(2);
		} else {
		    day = current.plusDays(1); //Aumenta di un giorno
		}	
			
		assertEquals(true, activityDAO.reservePlaces(activity, day, 1));
    }
}

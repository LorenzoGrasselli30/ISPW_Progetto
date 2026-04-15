package application.model.entity;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;

public class ActivityAvailableDates {
	private Map<LocalDate, Integer> avaiblePlaces = new HashMap<>();

	public ActivityAvailableDates(Map<LocalDate, Integer> avaiblePlaces) {
		this.avaiblePlaces = avaiblePlaces;
	}
	
	//Ritorna true se i posti sono disponibili
	public boolean hasRequiredPlaces(LocalDate day, Integer requestedPlaces) {
		if (day == null || requestedPlaces == null || requestedPlaces <= 0) {
			return false;
		}

		Integer available = avaiblePlaces.get(day);
		return (available != null && available >= requestedPlaces);
	}
	
	/*
	public boolean reservePlaces(LocalDate day, Integer requestedPlaces) {
		Integer available = avaiblePlaces.get(day);
		
		if (this.hasRequiredPlaces(day, requestedPlaces)) {
			avaiblePlaces.put(day, available - requestedPlaces);
			return true;
		}
		
		return false;
	}
	 */
	public Map<LocalDate, Integer> getAvaiblePlaces() {
		return avaiblePlaces;
	}
}

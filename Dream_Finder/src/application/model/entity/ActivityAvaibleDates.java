package application.model.entity;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;

public class ActivityAvaibleDates {
	private Map<LocalDate, Integer> avaiblePlaces = new HashMap<>();

	public ActivityAvaibleDates(Map<LocalDate, Integer> avaiblePlaces) {
		this.avaiblePlaces = avaiblePlaces;
	}
	
	
}

package application.model.dao.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import application.model.dao.ActivityDAO;
import application.model.entity.Activity;
import application.model.entity.Provider;

public class ActivityDAODemo implements ActivityDAO{
	
	private Map<String, Activity> activities = new HashMap<>();
	
	public ActivityDAODemo() {
		
    }
    
	@Override
	public List<Activity> findTopActivities(List<Provider> providers) {
		List<Activity> topActivities = new ArrayList<>();
		
		//Trova le due attività con il rating più alto proposte da un provider e le inserisce in una lista 
		for (Provider provider: providers) {
			List<Activity> providerTopActivities = provider.getActivities().stream()
			.sorted((a1, a2) -> Double.compare(a2.getRate(), a1.getRate()))
			.limit(2)
			.collect(Collectors.toList());
			
			topActivities.addAll(providerTopActivities);
		}
		
		Collections.shuffle(topActivities);
		
		return topActivities;
	}

	@Override
	public void saveNewActivity() {
		// TODO Auto-generated method stub
		
	} 
    
}

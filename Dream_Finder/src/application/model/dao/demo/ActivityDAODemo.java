package application.model.dao.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import application.model.dao.ActivityDAO;
import application.model.dao.ProviderDAO;
import application.model.entity.Activity;
import application.model.entity.Provider;
import application.model.enums.ActivityType;

public class ActivityDAODemo implements ActivityDAO {
	
	//Dependency Injection di ProviderDAO
	private ProviderDAO providerDAO;
		
	public ActivityDAODemo(ProviderDAO providerDAO) {
		this.providerDAO = providerDAO;
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
	public Activity findByProvider(String activityName, String providerName) {
		List<Provider> availableProviders = providerDAO.providersList();
				
		Provider targetProvider = availableProviders.stream()
			.filter(p -> p.getProviderName().equals(providerName))
			.findFirst()
			.orElse(null);
			
		//Caso provider non trovato
		if (targetProvider == null) {
			return null; 
		}
				
		return targetProvider.getActivities().stream()
				.filter(a -> a.getActivityName().equals(activityName))
				.findFirst()
				.orElse(null); // Caos attività non trovata
		}

	@Override
	public List<Activity> findRelatedActivities(String activityName, ActivityType activityType, String providerName) {
		// TODO Auto-generated method stub
		return null;
	}
}


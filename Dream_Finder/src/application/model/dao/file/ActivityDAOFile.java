package application.model.dao.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import application.exception.DAOException;
import application.model.dao.ActivityDAO;
import application.model.dao.ProviderDAO;
import application.model.entity.Activity;
import application.model.entity.ActivityAvailableDates;
import application.model.entity.ActivityOtherInformation;
import application.model.entity.ActivityRating;
import application.model.entity.Provider;
import application.model.enums.ActivityType;

public class ActivityDAOFile implements ActivityDAO {
	
	//Dependency Injection di ProviderDAO
	private ProviderDAO providerDAO;
		
	private static final String ACTIVITY_FILE_PATH = "data/Activity.csv";
    private static final String ACTIVITY_HEADER = "providerEmail,activityName,price,activityType,rating,nRating,description,freeCancellation,bookNowPayLater,skipTheLine,duration,durationInMinutes";
    private static final String DATES_FILE_PATH = "data/AvailableDates.csv";
    private static final String DATES_HEADER = "activityName,providerEmail,aDay,nPlaces";
    
    public ActivityDAOFile(ProviderDAO providerDAO) {
    	this.providerDAO = providerDAO;
    	
    	UtilsFile.ensureFileExists(ACTIVITY_FILE_PATH, ACTIVITY_HEADER);
    	UtilsFile.ensureFileExists(DATES_FILE_PATH, DATES_HEADER);
    }
    
	@Override
	public List<Activity> findTopActivities(List<Provider> providers) {
		List<Activity> topActivities = new ArrayList<>();
		
        String line;
            for (Provider provider: providers) {
            	List<Activity> providerTopActivities = new ArrayList<>();
            	try (BufferedReader activityReader = new BufferedReader(new FileReader(ACTIVITY_FILE_PATH))) {
            		while ((line = activityReader.readLine()) != null) {
                		String[] parts = line.split(",");
                        if (parts[0].equals(provider.getEmail())) { 
                        	Activity activity = UtilsFile.activityHelper(parts, provider);
                        	
                        	providerTopActivities.add(activity);
                        }
                    }
            	} catch (IOException e) {
                	throw new DAOException("Errore di ricerca delle attività");
                }
            	
            	providerTopActivities = providerTopActivities.stream()
        				.sorted((a1, a2) -> Double.compare(a2.getRating().getRate(), a1.getRating().getRate()))
        				.limit(2)
        				.collect(Collectors.toList());
            	
            	topActivities.addAll(providerTopActivities);
            }
            
            for (Activity activity : topActivities) {
            	try {
					activity.setAvaibleDates(UtilsFile.availableDatesHelper(activity));
				} catch (IOException e) {
					throw new DAOException("Errore di ricerca delle attività");
				}
            }
            
            
		Collections.shuffle(topActivities);
		
		return topActivities;
	}

	@Override
	public Activity findByProvider(String activityName, String providerName) {
		/* Trovare una spiegazione del perche è fatto cosi 
		//Dependency Injection di ProviderDAO
		private ProviderDAO providerDAO;
			
		public ActivityDAODemo(ProviderDAO providerDAO) {
			this.providerDAO = providerDAO;
		}
		*/
		List<Provider> availableProviders = providerDAO.providersList();
		
		Provider targetProvider = availableProviders.stream()
				.filter(p -> p.getProviderName().equals(providerName))
				.findFirst()
				.orElse(null);
				
			// Caso provider non trovato
			if (targetProvider == null) {
				return null; 
			}
					
			// Ritorna l'attività corrispondente o null se non trovata
			return targetProvider.getActivities().stream()
				.filter(a -> a.getActivityName().equals(activityName))
				.findFirst()
				.orElse(null);
	}

	@Override
	public List<Activity> findRelatedActivities(String activityName, ActivityType activityType, String providerName) {
		List<Activity> activities = new ArrayList<>();
		
		List<Provider> availableProviders = providerDAO.providersList();
		
		// Raccoglie tutte le attività disponibili
		for (Provider provider :  availableProviders) {
			activities.addAll(provider.getActivities());
		}
				
		// Rimuove l'attività passata come parametro
		activities = activities.stream()
			.filter(a -> !a. getActivityName().equals(activityName) || ! a.getProvider().getProviderName().equals(providerName))
			.collect(Collectors.toList());
				
		// Divide le attività in tre gruppi di priorità
		List<Activity> highScore = new ArrayList<>(); // Stesso provider e stesso tipo
		List<Activity> mediumScore = new ArrayList<>(); // Solo stesso provider o stesso tipo
		List<Activity> others = new ArrayList<>();   // Tutte le altre
				
		for (Activity activity : activities) {
			if (activity.calculateRelevanceScore(activity, activityType, providerName)==2) {
				highScore.add(activity);
			} else if (activity.calculateRelevanceScore(activity, activityType, providerName)==1) {
				mediumScore.add(activity);
			} else {
				others.add(activity);
			}
		}
				
		// Costruisce la lista finale rispettando le priorità
		List<Activity> relatedActivities = new ArrayList<>();
		relatedActivities.addAll(highScore);
		relatedActivities.addAll(mediumScore);
		relatedActivities.addAll(others);
				
		// Limita a 10 attività
		return relatedActivities.stream()
			.limit(10)
			.collect(Collectors.toList());
	}

	@Override
	public boolean reservePlaces(Activity activity, LocalDate day, Integer requestedPlaces) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/*
	
	private Activity activityHelper(String[] parts, Provider provider) {
		return new Activity(
				parts[1], //activityName
				Double.parseDouble(parts[2]), //price
				ActivityType.fromString(parts[3]), //activityType
				provider, 
				new ActivityRating(
						Double.parseDouble(parts[4]), //rate
						Integer.parseInt(parts[5]) //nRating
						), 
				new ActivityOtherInformation (
						parts[6], //description
						Boolean.parseBoolean(parts[7]),  // freeCancellation
						Boolean.parseBoolean(parts[8]),  // bookNowPayLater
						Boolean.parseBoolean(parts[9]),  // skipTheLine
						Integer.parseInt(parts[10]),     // duration
						Boolean.parseBoolean(parts[11])  // durationInMinutes
						), 
				null
				); 
	}
	
	private ActivityAvailableDates availableDatesHelper(Activity activity) throws IOException {
		Map<LocalDate, Integer> availablePlaces = new HashMap<>();
		String line;
		
		try (BufferedReader datesReader = new BufferedReader(new FileReader(DATES_FILE_PATH))) {
			while ((line = datesReader.readLine()) != null) {
	    		String[] parts = line.split(",");
	            if (parts[0].equals(activity.getActivityName()) && parts[1].equals(activity.getProvider().getEmail())) { 
	            	LocalDate date = LocalDate.parse(parts[2]);
	     			Integer places = Integer.parseInt(parts[3]);
	     			availablePlaces.put(date, places);
	            }
	        }
		}
    	
		return new ActivityAvailableDates(availablePlaces);
    }
    */
}

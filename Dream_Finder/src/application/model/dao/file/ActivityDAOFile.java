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
import application.model.entity.Activity;
import application.model.entity.ActivityAvailableDates;
import application.model.entity.ActivityOtherInformation;
import application.model.entity.ActivityRating;
import application.model.entity.Provider;
import application.model.enums.ActivityType;

public class ActivityDAOFile implements ActivityDAO {

	private static final String ACTIVITY_FILE_PATH = "data/Activity.csv";
    private static final String ACTIVITY_HEADER = "providerEmail,activityName,price,activityType,rating,nRating,description,freeCancellation,bookNowPayLater,skipTheLine,duration,durationInMinutes";
    private static final String DATES_FILE_PATH = "data/AvailableDates.csv";
    private static final String DATES_HEADER = "activityName,providerEmail,aDay,nPlaces";
    
    public ActivityDAOFile() {
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
                        	Activity activity = this.activityHelper(parts, provider);
                        	
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
					activity.setAvaibleDates(this.availableDatesHelper(activity));
				} catch (IOException e) {
					throw new DAOException("Errore di ricerca delle attività");
				}
            }
            
            
		Collections.shuffle(topActivities);
		
		return topActivities;
	}

	@Override
	public Activity findByProvider(String activityName, String providerName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Activity> findRelatedActivities(String activityName, ActivityType activityType, String providerName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean reservePlaces(Activity activity, LocalDate day, Integer requestedPlaces) {
		// TODO Auto-generated method stub
		return false;
	}
	
	//Helpers
	
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
}

package application.model.dao.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import application.exception.DAOException;
import application.model.dao.ActivityDAO;
import application.model.entity.Activity;
import application.model.entity.ActivityOtherInformation;
import application.model.entity.ActivityRating;
import application.model.entity.Provider;
import application.model.entity.ProviderPersonalInfo;
import application.model.enums.ActivityType;
import application.model.enums.ProviderType;

public class ActivityDAOFile implements ActivityDAO {

	private static final String FILE_PATH = "data/Activity.csv";
    private static final String HEADER = "providerEmail,activityName,price,activityType,rating,nRating,description,freeCancellation,bookNowPayLater,skipTheLine,duration,durationInMinutes";
    
    public ActivityDAOFile() {
    	UtilsFile.ensureFileExists(FILE_PATH, HEADER);
    }
    
	@Override
	public List<Activity> findTopActivities(List<Provider> providers) {
		List<Activity> topActivities = new ArrayList<>();
		
		
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            for (Provider provider: providers) {
            	System.out.println(provider.getEmail());
            	List<Activity> providerTopActivities = new ArrayList<>();
            	
            	while ((line = reader.readLine()) != null) {
            		String[] parts = line.split(",");
                    if (parts[0].equals(provider.getEmail())) { 
                    	Activity activity = this.activityHelper(parts, provider);
                    	
                    	providerTopActivities.add(activity);
                    }
                }
            	
            	providerTopActivities = providerTopActivities.stream()
        				.sorted((a1, a2) -> Double.compare(a2.getRating().getRate(), a1.getRating().getRate()))
        				.limit(2)
        				.collect(Collectors.toList());
            	
            	topActivities.addAll(providerTopActivities);
            }
        } catch (IOException e) {
        	throw new DAOException("Errore di ricerca del provider");
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
}

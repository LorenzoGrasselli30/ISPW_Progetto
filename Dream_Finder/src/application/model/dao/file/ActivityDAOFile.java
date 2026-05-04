package application.model.dao.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private static final String HEADER = "providerEmail,activityName,price,activityType,ratingScore,ratingCount,description,freeCancellation,bookNowPayLater,skipTheLine,duration,durationInMinutes";
    
    public ActivityDAOFile() {
    	UtilsFile.ensureFileExists(FILE_PATH, HEADER);
    }
    
	@Override
	public List<Activity> findTopActivities(List<Provider> providers) {
		List<Activity> topActivities = new ArrayList<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            for (Provider provider: providers) {
            	while ((line = reader.readLine()) != null) {
            		String[] parts = line.split(",");
                    if (parts[0].equals(provider.getEmail())) { 
                    	
                    }
                }
            }
        } catch (IOException e) {
        	throw new DAOException("Errore di ricerca del provider");
        }
		
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
				parts[1], 
				Double.parseDouble(parts[2]), 
				ActivityType.fromString(parts[3]), 
				provider, 
				new ActivityRating(
						Double.parseDouble(parts[4]), 
						Integer.parseInt(parts[4])
						), 
				new ActivityOtherInformation (
						parts[6], 
						parts[7], 
						parts[8], 
						parts[9], 
						Integer.parseInt(parts[10]), 
						parts[11]
						), 
				null
				); 
	}
}

package application.model.dao.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import application.exception.DAOException;
import application.model.entity.Activity;
import application.model.entity.ActivityAvailableDates;
import application.model.entity.ActivityOtherInformation;
import application.model.entity.ActivityRating;
import application.model.entity.Provider;
import application.model.entity.ProviderPersonalInfo;
import application.model.enums.ActivityType;
import application.model.enums.ProviderType;

public class UtilsFile {
	private static final String DATES_FILE_PATH = "data/AvailableDates.csv";
	
	private UtilsFile() {}  

	// Assicura che un file esista e abbia un'intestazione
    public static void ensureFileExists(String filePath, String header) {
    	
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                Files.createDirectories(Paths.get(file.getParent())); 
                Files.write(file.toPath(), Collections.singleton(header), StandardOpenOption.CREATE);
                
            } catch (IOException e) {
            	throw new DAOException("Errore nella creazione del file: " + e);
            }
        }
    }
    
    //Helpers 
    
    public static Provider providerHelper(String[] parts) {
        return new Provider(
        		parts[0], 
    			parts[1], 
    			parts[2],
    			ProviderType.fromString(parts[3]),
    			Integer.parseInt(parts[4]),
    			new ProviderPersonalInfo(
    					parts[5], 
            			parts[6], 
            			parts[7]
    					)
    			);
    }
    
    public static Activity activityHelper(String[] parts, Provider provider) {
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
	
    public static ActivityAvailableDates availableDatesHelper(Activity activity) throws IOException {
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

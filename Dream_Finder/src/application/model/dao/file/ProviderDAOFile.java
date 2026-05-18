package application.model.dao.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import application.exception.DAOException;
import application.model.dao.ProviderDAO;
import application.model.entity.Activity;
import application.model.entity.ActivityAvailableDates;
import application.model.entity.ActivityOtherInformation;
import application.model.entity.ActivityRating;
import application.model.entity.Provider;
import application.model.entity.ProviderPersonalInfo;
import application.model.entity.Traveler;
import application.model.enums.ActivityType;
import application.model.enums.ProviderType;

public class ProviderDAOFile implements ProviderDAO {
	
	private static final String PROVIDER_FILE_PATH = "data/Provider.csv";
    private static final String PROVIDER_HEADER = "email,password,providerName,providerType,nOfferedActivities,location,name,surname";
    private static final String ACTIVITY_FILE_PATH = "data/Activity.csv";
    private static final String ACTIVITY_HEADER = "providerEmail,activityName,price,activityType,rating,nRating,description,freeCancellation,bookNowPayLater,skipTheLine,duration,durationInMinutes";
    private static final String DATES_FILE_PATH = "data/AvailableDates.csv";
    private static final String DATES_HEADER = "activityName,providerEmail,aDay,nPlaces";
    
    public ProviderDAOFile() {
    	UtilsFile.ensureFileExists(PROVIDER_FILE_PATH, PROVIDER_HEADER);
    	UtilsFile.ensureFileExists(ACTIVITY_FILE_PATH, ACTIVITY_HEADER);
    	UtilsFile.ensureFileExists(DATES_FILE_PATH, DATES_HEADER);
    }
    
	@Override
	public List<Provider> findTopProviders() {
		List<Provider> providers= new ArrayList<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(PROVIDER_FILE_PATH))) {
            String line = reader.readLine(); //Viene letta la riga di intestazione per saltarla
            
            while ((line = reader.readLine()) != null) {
            	String[] parts = line.split(",");
            	Provider provider = UtilsFile.providerHelper(parts);
            	
            	providers.add(provider);
            }
        } catch (IOException e) {
        	throw new DAOException("Errore di ricerca del provider");
        }
		
		return providers.stream()
				.sorted((p1, p2) -> Double.compare(p2.getProviderRate(), p1.getProviderRate()))
				.limit(5)
				.collect(Collectors.toList());
	}

	@Override
	public List<Provider> providersList() {
		List<Provider> providers= new ArrayList<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(PROVIDER_FILE_PATH))) {
            String line = reader.readLine(); //Viene letta la riga di intestazione per saltarla
            
            while ((line = reader.readLine()) != null) {
            	String[] parts = line.split(",");
            	Provider provider = UtilsFile.providerHelper(parts);
            	
            	providers.add(provider);
            }
        } catch (IOException e) {
        	throw new DAOException("Errore di ricerca del provider");
        }
		
		for (Provider provider: providers) {
        	try (BufferedReader activityReader = new BufferedReader(new FileReader(ACTIVITY_FILE_PATH))) {
        		String line = activityReader.readLine(); //Viene letta la riga di intestazione per saltarla
        		
        		while ((line = activityReader.readLine()) != null) {
            		String[] parts = line.split(",");
                    if (parts[0].equals(provider.getEmail())) {
                    	this.addActivityHelper(parts, provider);
                    }
                }
        	} catch (IOException e) {
            	throw new DAOException("Errore di ricerca delle attività");
            }
        }
		
		return providers;
	}

	@Override
	public Provider findByActivity(Activity activity) {		
		List<Provider> providers= this.providersList();
		
		for(Provider provider : providers) {
			for (Activity a : provider.getActivities()) {
				if (a.getActivityName().equals(activity.getActivityName())) {
					return provider;
				}
			}
		}
		return null;
		
	}

	@Override
	public Provider findByEmail(String email) {
		Provider provider = null;
		
		// 1. Cerca il provider nel file Provider.csv
		try (BufferedReader reader = new BufferedReader(new FileReader(PROVIDER_FILE_PATH))) {
			String line = reader.readLine(); // Salta la riga di intestazione
			
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				// parts[0] è l'email
				if (parts[0].equals(email)) {
					provider = UtilsFile.providerHelper(parts);
					break; // Interruzione della ricerca dopo aver trovato il provider
				}
			}
		} catch (IOException e) {
			throw new DAOException("Errore di ricerca del provider per email");
		}
		
		// Se il provider non è stato trovato, ritorniamo null
		if (provider == null) {
			return null;
		}
		
		// 2. Carica le attività associate a questo provider dal file Activity.csv
		try (BufferedReader activityReader = new BufferedReader(new FileReader(ACTIVITY_FILE_PATH))) {
			String line = activityReader.readLine(); // Salta la riga di intestazione
			
			while ((line = activityReader.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts[0].equals(provider.getEmail())) {
					this.addActivityHelper(parts, provider);
				}
			}
		} catch (IOException e) {
			throw new DAOException("Errore di ricerca delle attività associate al provider");
		}
		
		return provider;
	}
	
	//Helpers 
	
	private void addActivityHelper(String[] parts, Provider provider) throws IOException {
		Activity activity = UtilsFile.activityHelper(parts, provider);
		activity.setAvaibleDates(UtilsFile.availableDatesHelper(activity));
		
		provider.addActivity(
				activity.getActivityName(), 
				activity.getPrice(), 
				activity.getActivityType(), 
				activity.getRating(), 
				activity.getOtherInfo(), 
				activity.getAvaibleDates()
		);
	}
}

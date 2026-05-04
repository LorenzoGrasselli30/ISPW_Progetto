package application.model.dao.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import application.exception.DAOException;
import application.model.dao.ProviderDAO;
import application.model.entity.Activity;
import application.model.entity.Provider;
import application.model.entity.ProviderPersonalInfo;
import application.model.entity.Traveler;
import application.model.enums.ProviderType;

public class ProviderDAOFile implements ProviderDAO {
	
	private static final String FILE_PATH = "data/provider.csv";
    private static final String HEADER = "email,password,providerName,providerType,nOfferedActivities,location,name,surname";
    
    public ProviderDAOFile() {
    	UtilsFile.ensureFileExists(FILE_PATH, HEADER);
    }
    
	@Override
	public List<Provider> findTopProviders() {
		List<Provider> providers= new ArrayList<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
            	String[] parts = line.split(",");
            	Provider provider = new Provider(
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
            	
            	providers.add(provider);
            }
        } catch (IOException e) {
        	throw new DAOException("Errore di ricerca del provider");
        }
		
		return providers.values().stream()
				.sorted((p1, p2) -> Double.compare(p2.getProviderRate(), p1.getProviderRate()))
				.limit(5)
				.collect(Collectors.toList());
	}

	@Override
	public List<Provider> providersList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Provider findByActivity(Activity activity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Provider findByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}

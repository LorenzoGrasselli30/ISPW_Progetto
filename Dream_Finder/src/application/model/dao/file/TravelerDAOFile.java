package application.model.dao.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

import application.exception.DAOException;
import application.model.dao.TravelerDAO;
import application.model.entity.Traveler;

public class TravelerDAOFile implements TravelerDAO {
	
	private static final String FILE_PATH = "data/traveler.csv";
    private static final String HEADER = "email,password,username,name,surname,dob";
    
    public TravelerDAOFile() {
    	UtilsFile.ensureFileExists(FILE_PATH, HEADER);
    }
    
	@Override
	public Traveler findByEmail(String formattedEmail) {
		Traveler newTraveler = null;
		
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(formattedEmail)) { 
                    newTraveler = new Traveler(parts[0], parts[1], parts[2], parts[3], parts[4], LocalDate.parse(parts[5]));
                }
            }
        } catch (IOException e) {
        	throw new DAOException("Errore di ricerca del traveler");
        }
		
		return newTraveler;
	}

}

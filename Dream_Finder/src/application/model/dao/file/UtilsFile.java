package application.model.dao.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;

import application.exception.DAOException;

public class UtilsFile {
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
}

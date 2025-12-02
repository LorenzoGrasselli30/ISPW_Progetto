package model.dao;

import application.configuration.AppConfig;
import model.dao.db.TravelerDAODB;
import model.dao.db.UserDAODB;
import model.dao.demo.TravelerDAODemo;
import model.dao.demo.UserDAODemo;
import model.dao.file.TravelerDAOFile;
import model.dao.file.UserDAOFile;

public class FactoryDAO {
	
	private FactoryDAO() {} //Pattern singleton
	
	private static FactoryDAO singletonInstance = null; //Pattern singleton
	
	private static final String MODE_DEMO = "demo";
	private static final String MODE_FILE = "file";  
	private static final String MODE_EXCEPTION = "Mode not initialized";
	private static String mode = AppConfig.getInstance().getMode(); 
	
	public static synchronized FactoryDAO getFactoryInstance() { //Pattern singleton
		if (singletonInstance == null) {
			singletonInstance = new FactoryDAO();
		}
		return singletonInstance;
	}
	
	private UserDAO userIstance;
	
	public UserDAO getUserDAO() {
		if (mode == null) {					
			throw new IllegalStateException(MODE_EXCEPTION);
		}
	    	
		if (MODE_DEMO.equals(mode) || "".equals(mode)) { 
	    	    if (userIstance == null) {  
	    	    	userIstance = new UserDAODemo();
	    	    }
	    	    return userIstance;           
	    	    
	    	} else if (MODE_FILE.equals(mode)) {
	    		return new UserDAOFile();
	    		
	    	} else {
	        	return new UserDAODB();
	        }
	    }
	 
}

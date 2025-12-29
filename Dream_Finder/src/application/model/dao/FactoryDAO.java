package application.model.dao;

import application.configuration.AppConfig;
import application.model.dao.db.ActivityDAODB;
import application.model.dao.db.BookingDAODB;
import application.model.dao.db.ProviderDAODB;
import application.model.dao.db.ReceiptDAODB;
import application.model.dao.db.TravelerDAODB;
import application.model.dao.db.UserDAODB;
import application.model.dao.demo.ActivityDAODemo;
import application.model.dao.demo.BookingDAODemo;
import application.model.dao.demo.ProviderDAODemo;
import application.model.dao.demo.ReceiptDAODemo;
import application.model.dao.demo.TravelerDAODemo;
import application.model.dao.demo.UserDAODemo;
import application.model.dao.file.ActivityDAOFile;
import application.model.dao.file.BookingDAOFile;
import application.model.dao.file.ProviderDAOFile;
import application.model.dao.file.ReceiptDAOFile;
import application.model.dao.file.TravelerDAOFile;
import application.model.dao.file.UserDAOFile;

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
	
	private ActivityDAO activityDAO;
	
	public ActivityDAO getActivityDAO() {
		if (mode == null && providerDAO == null) {					
			throw new IllegalStateException(MODE_EXCEPTION);
		}
	    	
		if (MODE_DEMO.equals(mode) || "".equals(mode)) { 
	    	    if (activityDAO == null) {  
	    	    	activityDAO = new ActivityDAODemo(providerDAO);
	    	    }
	    	    return activityDAO;           
	    	    
	    	} else if (MODE_FILE.equals(mode)) {
	    		return new ActivityDAOFile();
	    		
	    	} else {
	        	return new ActivityDAODB();
	        }
	    }
	
	private ProviderDAO providerDAO;
	
	public ProviderDAO getProviderDAO() {
		if (mode == null) {					
			throw new IllegalStateException(MODE_EXCEPTION);
		}
	    	
		if (MODE_DEMO.equals(mode) || "".equals(mode)) { 
	    	    if (providerDAO == null) {  
	    	    	providerDAO = new ProviderDAODemo();
	    	    }
	    	    return providerDAO;           
	    	    
	    	} else if (MODE_FILE.equals(mode)) {
	    		return new ProviderDAOFile();
	    		
	    	} else {
	        	return new ProviderDAODB();
	        }
	}
	
	private ReceiptDAO receiptDAO;
	
	public ReceiptDAO getReceiptDAO() {
		if (mode == null) {					
			throw new IllegalStateException(MODE_EXCEPTION);
		}
	    	
		if (MODE_DEMO.equals(mode) || "".equals(mode)) { 
	    	    if (receiptDAO == null) {  
	    	    	receiptDAO = new ReceiptDAODemo();
	    	    }
	    	    return receiptDAO;           
	    	    
	    	} else if (MODE_FILE.equals(mode)) {
	    		return new ReceiptDAOFile();
	    		
	    	} else {
	        	return new ReceiptDAODB();
	        }
	}
	
	private TravelerDAO travelerDAO;
	
	public TravelerDAO getTravelerDAO() {
		if (mode == null) {					
			throw new IllegalStateException(MODE_EXCEPTION);
		}
	    	
		if (MODE_DEMO.equals(mode) || "".equals(mode)) { 
	    	    if (travelerDAO == null) {  
	    	    	travelerDAO = new TravelerDAODemo();
	    	    }
	    	    return travelerDAO;           
	    	    
	    	} else if (MODE_FILE.equals(mode)) {
	    		return new TravelerDAOFile();
	    		
	    	} else {
	        	return new TravelerDAODB();
	        }
	}
	
	private BookingDAO bookingDAO;
	
	public BookingDAO getBookingDAO() {
		if (mode == null) {					
			throw new IllegalStateException(MODE_EXCEPTION);
		}
	    	
		if (MODE_DEMO.equals(mode) || "".equals(mode)) { 
	    	    if (bookingDAO == null) {  
	    	    	bookingDAO = new BookingDAODemo();
	    	    }
	    	    return bookingDAO;           
	    	    
	    	} else if (MODE_FILE.equals(mode)) {
	    		return new BookingDAOFile();
	    		
	    	} else {
	        	return new BookingDAODB();
	        }
	}
}
	 


package application.model.dao.demo;

import java.util.HashMap;
import java.util.Map;

import application.model.dao.ActivityDAO;
import application.model.entity.Activity;
import application.model.entity.User;
import application.model.enums.UserRole;

public class ActivityDAODemo implements ActivityDAO{
	
	private Map<String, User> activities = new HashMap<>();
	
	public ActivityDAODemo() {
    	//Metodo che chiama la creazione di una serie di provider con delle attività
    }
    
    private void initializeActivityDemo() {
    	
    }

	@Override
	public Activity findTopActivities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveNewActivity() {
		// TODO Auto-generated method stub
		
	} 
    
}

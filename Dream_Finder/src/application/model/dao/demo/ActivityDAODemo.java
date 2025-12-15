package application.model.dao.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.model.dao.ActivityDAO;
import application.model.entity.Activity;
import application.model.entity.Provider;

public class ActivityDAODemo implements ActivityDAO{
	
	private Map<String, Activity> activities = new HashMap<>();
	
	public ActivityDAODemo() {
		
    }
    
	@Override
	public List<Activity> findTopActivities(List<Provider> providers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveNewActivity() {
		// TODO Auto-generated method stub
		
	} 
    
}

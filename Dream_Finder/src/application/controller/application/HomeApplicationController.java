package application.controller.application;

import application.model.dao.ActivityDAO;
import application.model.dao.FactoryDAO;

public class HomeApplicationController {
	
	private ActivityDAO activityDAO;
	
	public HomeApplicationController() {
		this.activityDAO= FactoryDAO.getFactoryInstance().getActivityDAO();
	}
	
	//Chiama la factory per prendere una lista di activity DAO con rating più alto
	public void fetchActivities() {
		
	}
	
	
	
}

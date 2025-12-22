package application.controller.application;

import java.util.ArrayList;
import java.util.List;

import application.model.bean.ActivityDTO;
import application.model.dao.ActivityDAO;
import application.model.dao.FactoryDAO;
import application.model.dao.ProviderDAO;
import application.model.entity.Activity;
import application.model.entity.Provider;

public class HomeApplicationController {
	
	private ProviderDAO providerDAO;
	private ActivityDAO activityDAO;
	
	public HomeApplicationController() {
		this.providerDAO= FactoryDAO.getFactoryInstance().getProviderDAO();
		this.activityDAO= FactoryDAO.getFactoryInstance().getActivityDAO();
	}
	
	//Chiama la factory per prendere una lista di activity DAO con rating più alto
	public List<ActivityDTO> fetchActivities() {
		List<Provider> providers= providerDAO.findTopProviders();
		List<Activity> activities= activityDAO.findTopActivities(providers);
		
		List<ActivityDTO> activityDTO= new ArrayList(); //activityDTO è un oggetto con troppi attributi che in questo caso molti sono null (va creato una activityPreviewDTO)
		for (Activity activity: activities) {
			ActivityDTO newActivity= new ActivityDTO();
			newActivity.setActivityName(activity.getActivityName());
			newActivity.setDescription(activity.getDescription());
			newActivity.setPrice(activity.getPrice());
			newActivity.setRate(activity.getRate());
			newActivity.setnRating(activity.getnRating());
			newActivity.setProviderName(activity.getProvider().getProviderName());
			
			activityDTO.add(newActivity);
		}
		
		return activityDTO;
	}
}

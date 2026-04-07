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
		
		for (Provider provider : providers) {
			System.out.println("----------------------------------------------------------");
			System.out.println(provider.getProviderUser().getEmail());
			System.out.println(provider.getProviderUser().getPassword());
			System.out.println(provider.getProviderUser().getUserRole());
			System.out.println(provider.getProviderName());
			System.out.println(provider.getProviderType());
			System.out.println(provider.getnOfferedActivities());
			System.out.println(provider.getLocation());
			System.out.println(provider.getName());
			System.out.println(provider.getSurname());
			System.out.println(provider.getProviderRate());
			System.out.println("----------------------------------------------------------");
		}
		
		List<Activity> activities= activityDAO.findTopActivities(providers);
		
		for (Activity activity : activities) {
			System.out.println("----------------------------------------------------------");
			System.out.println(activity.getActivityName());
			System.out.println(activity.getPrice());
			System.out.println(activity.getActivityType());
			System.out.println(activity.getOtherInfo());
			System.out.println(activity.getOtherInfo());

			System.out.println(activity.getOtherInfo());

			System.out.println(activity.getOtherInfo());
			System.out.println(activity.getOtherInfo());
			System.out.println(activity.getOtherInfo());

			System.out.println(activity.getRating());
			System.out.println("----------------------------------------------------------");
		}
		
		List<ActivityDTO> activityDTO= new ArrayList<>(); 
		for (Activity activity: activities) {
			ActivityDTO newActivity= new ActivityDTO();
			newActivity.setActivityName(activity.getActivityName());
			newActivity.setDescription(activity.getOtherInfo().getDescription());
			newActivity.setPrice(activity.getPrice());
			newActivity.setRate(activity.getRating().getRate());
			newActivity.setnRating(activity.getRating().getnRating());
			newActivity.setProviderName(activity.getProvider().getProviderName());
			
			activityDTO.add(newActivity);
		}
		
		return activityDTO;
	}
}

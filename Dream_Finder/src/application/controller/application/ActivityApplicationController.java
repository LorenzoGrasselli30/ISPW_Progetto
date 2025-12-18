package application.controller.application;

import java.util.List;

import application.model.bean.ActivityDTO;
import application.model.dao.ActivityDAO;
import application.model.dao.FactoryDAO;
import application.model.entity.Activity;

public class ActivityApplicationController {
	
	private ActivityDAO activityDAO;
	
	public ActivityApplicationController() {
		this.activityDAO= FactoryDAO.getFactoryInstance().getActivityDAO();
	}
	
	public ActivityDTO fetchActivityInfo(String activityName, String providerName) {
		
		Activity newActivityInfo= activityDAO.findByProvider(activityName, providerName);
		
		System.out.println(newActivityInfo.getActivityName());
		System.out.println(newActivityInfo.getProviderName());
		System.out.println(newActivityInfo.getDuration());
		
		ActivityDTO newActivityDTO= new ActivityDTO();
		
		return newActivityDTO;
	}
}

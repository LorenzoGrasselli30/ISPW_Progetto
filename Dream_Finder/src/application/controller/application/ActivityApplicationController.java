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
		
		ActivityDTO newActivityDTO= new ActivityDTO();
		newActivityDTO.setActivityName(newActivityInfo.getActivityName());
		newActivityDTO.setActivityType(newActivityInfo.getActivityType());
		newActivityDTO.setDescription(newActivityInfo.getDescription());
		newActivityDTO.setDuration(newActivityInfo.getDuration());
		newActivityDTO.setTimeInMinutes(newActivityInfo.getTimeInMinutes());
		newActivityDTO.setnRating(newActivityInfo.getnRating());
		newActivityDTO.setRate(newActivityInfo.getRate());
		newActivityDTO.setPrice(newActivityInfo.getPrice());
		newActivityDTO.setSkipLine(newActivityInfo.getSkipLine());
		newActivityDTO.setFreeCancellation(newActivityInfo.getFreeCancellation());
		newActivityDTO.setPayLater(newActivityInfo.getPayLater());
		newActivityDTO.setProviderName(newActivityInfo.getProviderName());
		
		return newActivityDTO;
	}
}

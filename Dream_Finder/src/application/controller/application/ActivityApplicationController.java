package application.controller.application;

import java.util.ArrayList;
import java.util.List;

import application.model.bean.ActivityDTO;
import application.model.bean.ReceiptDTO;
import application.model.dao.ActivityDAO;
import application.model.dao.FactoryDAO;
import application.model.dao.ReceiptDAO;
import application.model.entity.Activity;
import application.model.entity.Receipt;
import application.model.enums.ActivityType;

public class ActivityApplicationController {
	
	private ActivityDAO activityDAO;
	private ReceiptDAO receiptDAO;
	
	public ActivityApplicationController() {
		this.activityDAO= FactoryDAO.getFactoryInstance().getActivityDAO();
		this.receiptDAO= FactoryDAO.getFactoryInstance().getReceiptDAO();
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
		newActivityDTO.setProviderName(newActivityInfo.getProvider().getProviderName());
		
		return newActivityDTO;
	}

	public List<ActivityDTO> fetchRelatedInfo(String activityName, ActivityType activityType, String providerName) {
		
		List<Activity> newActivities= activityDAO.findRelatedActivities(activityName, activityType, providerName);
		
		List<ActivityDTO> relatedActivity= new ArrayList();
		for (Activity activity: newActivities) {
			ActivityDTO newActivity= new ActivityDTO();
			newActivity.setActivityName(activity.getActivityName());
			newActivity.setDescription(activity.getDescription());
			newActivity.setnRating(activity.getnRating());
			newActivity.setRate(activity.getRate());
			newActivity.setPrice(activity.getPrice());
			newActivity.setProviderName(activity.getProvider().getProviderName());
			
			relatedActivity.add(newActivity);
		}
		
		return relatedActivity;
	}

	public Boolean createQuotation(ReceiptDTO receiptDTO) {
		
		// 1. Converti il DTO in Entity (diminuisce l'accoppiamento)
		Receipt receipt = new Receipt(receiptDTO.getTravelerName(), receiptDTO.getTravelerSurname(), receiptDTO.getProviderName(), receiptDTO.getnFullTicket(), 
				receiptDTO.getnReducedTicket(), receiptDTO.getShuttlePrice(), receiptDTO.getGuidePrice(), receiptDTO.getTotalPrice());
			
		Boolean result= receiptDAO.saveQuotation(receipt);
		
		return result;
	}
}

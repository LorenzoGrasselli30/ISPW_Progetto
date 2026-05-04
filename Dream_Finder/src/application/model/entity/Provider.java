package application.model.entity;

import java.util.ArrayList;
import java.util.List;

import application.model.enums.ActivityType;
import application.model.enums.ProviderType;
import application.model.enums.UserRole;

public class Provider extends User {
	//Composizione con Activity
	private List<Activity> activities;
	
	private String providerName;
	private ProviderType providerType;
	
	private ProviderPersonalInfo personalInfo;
	
	private Integer nOfferedActivities;
	private Double providerRate;

	public Provider(String email, String password, String providerName,
			ProviderType providerType, Integer nOfferedActivities, ProviderPersonalInfo personalInfo) {
		
		super(email, password, UserRole.PROVIDER);
		this.activities = new ArrayList<>();
		this.providerName = providerName;
		this.providerType = providerType;
		this.nOfferedActivities = nOfferedActivities;
		this.personalInfo = personalInfo;
		this.providerRate= 0.0;
	}
	
	public void addActivity(String activityName, Double price, ActivityType activityType,
			ActivityRating activityRating, ActivityOtherInformation otherInfo, ActivityAvailableDates availableDates) {
		activities.add(new Activity(activityName, price, activityType, this, activityRating, otherInfo, availableDates));
		
		this.nOfferedActivities+=1;
		
		this.setProviderRate();
	}
	
	public void setProviderRate() {
		Double resultRate= 0.0;
		
		for (Activity activity : this.activities) {
			resultRate += activity.getRating().getRate();
	    }
		
		resultRate = resultRate / (this.nOfferedActivities);
		
		this.providerRate= resultRate;
	}
	
	public Double getProviderRate() {
		return providerRate;
	}
	
	public List<Activity> getActivities() {
		return activities;
	}
	
	public String getProviderName() {
		return providerName;
	}

	public ProviderType getProviderType() {
		return providerType;
	}

	public Integer getnOfferedActivities() {
		return nOfferedActivities;
	}

	public ProviderPersonalInfo getPersonalInfo() {
		return personalInfo;
	}

}

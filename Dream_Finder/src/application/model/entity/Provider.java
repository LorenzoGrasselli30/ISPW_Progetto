package application.model.entity;

import java.util.ArrayList;
import java.util.List;

import application.model.enums.ProviderType;
import application.model.enumsm.ActivityType;

public class Provider {
	
	private List<Activity> activities;
	
	private String email;
	private String password;
	
	private String providerName;
	private ProviderType providerType;
	private Integer nOfferedActivities;
	private String location;
	private String name;
	private String surname;
	
	private Double providerRate;

	public Provider(String email, String password, String providerName,
			ProviderType providerType, Integer nOfferedActivities, String location, String name, String surname, Double providerRate) {
		this.activities = new ArrayList<>();
		this.email = email;
		this.password = password;
		this.providerName = providerName;
		this.providerType = providerType;
		this.nOfferedActivities = nOfferedActivities;
		this.location = location;
		this.name = name;
		this.surname = surname;
		this.providerRate= providerRate;
	}
	
	public void addActivity(String providerName, String description, Integer duration, Boolean timeInMinutes,
			ActivityType activityType, Boolean freeCancellation, Boolean payLater, Boolean skipLine, Integer nRating,
			Double rate) {
		activities.add(new Activity(providerName, description, duration, timeInMinutes, activityType, freeCancellation, payLater,
				skipLine, nRating, rate));
		
		this.nOfferedActivities+=1;
		this.setProviderRate();
	}
	
	public void setProviderRate() {
		Double resultRate= 0.0;
		
		for (Activity activity : this.activities) {
			resultRate += activity.getRate();
	    }
		
		resultRate = resultRate / (this.nOfferedActivities);
		
		this.providerRate= resultRate;
	}
}

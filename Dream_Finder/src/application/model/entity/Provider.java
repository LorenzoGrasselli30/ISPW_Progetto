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
	private Integer nOfferedActivities;
	private String location;
	private String name;
	private String surname;
	
	private Double providerRate;

	public Provider(String email, String password, String providerName,
			ProviderType providerType, Integer nOfferedActivities, String location, String name, String surname) {
		
		super(email, password, UserRole.PROVIDER);
		this.activities = new ArrayList<>();
		this.providerName = providerName;
		this.providerType = providerType;
		this.nOfferedActivities = nOfferedActivities;
		this.location = location;
		this.name = name;
		this.surname = surname;
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

	public String getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}
}

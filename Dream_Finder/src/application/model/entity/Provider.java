package application.model.entity;

import java.util.ArrayList;
import java.util.List;

import application.model.enums.ProviderType;

public class Provider {
	
	private List<Activity> activities;
	
	private String providerName;
	private ProviderType providerType;
	private Integer nOfferedActivities;
	private String location;
	private String name;
	private String surname;
	private String email;
	private Double providerRate;
	
	public Provider(List<Activity> activities, String providerName, ProviderType providerType,
			Integer nOfferedActivities, String location, String name, String surname, String email,
			Double providerRate) {
		
		this.activities = new ArrayList<>();
		this.providerName = providerName;
		this.providerType = providerType;
		this.nOfferedActivities = nOfferedActivities;
		this.location = location;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.providerRate = 0.0;
	}
	
}

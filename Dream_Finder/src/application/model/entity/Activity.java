package application.model.entity;

import application.model.enumsm.ActivityType;

public class Activity {
	private String providerName;
	private String description;
	private Integer duration;
	private Boolean timeInMinutes;
	private ActivityType activityType;
	private Boolean freeCancellation;
	private Boolean payLater;
	private Boolean skipLine;
	private Integer nRating;
	private Double rate;
	
	public Activity(String providerName, String description, Integer duration, Boolean timeInMinutes,
			ActivityType activityType, Boolean freeCancellation, Boolean payLater, Boolean skipLine, Integer nRating,
			Double rate) {
		this.providerName = providerName;
		this.description = description;
		this.duration = duration;
		this.timeInMinutes = timeInMinutes;
		this.activityType = activityType;
		this.freeCancellation = freeCancellation;
		this.payLater = payLater;
		this.skipLine = skipLine;
		this.nRating = 0;
		this.rate = 0.0;
	}
	
}

package application.model.entity;

import application.model.enumsm.ActivityType;

public class Activity {
	private String activityName;
	private String description;
	private Integer duration;
	private Boolean timeInMinutes;
	private ActivityType activityType;
	private Boolean freeCancellation;
	private Boolean payLater;
	private Boolean skipLine;
	private Integer nRating;
	private Double rate;

	public Activity(String activityName, String description, Integer duration, Boolean timeInMinutes,
			ActivityType activityType, Boolean freeCancellation, Boolean payLater, Boolean skipLine, Integer nRating,
			Double rate) {
		this.activityName = activityName;
		this.description = description;
		this.duration = duration;
		this.timeInMinutes = timeInMinutes;
		this.activityType = activityType;
		this.freeCancellation = freeCancellation;
		this.payLater = payLater;
		this.skipLine = skipLine;
		this.nRating = nRating;
		this.rate = rate;
	}
	
	public Double getRate() {
		return rate;
	}
}

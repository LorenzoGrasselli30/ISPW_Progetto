package application.model.bean;

import application.model.enums.ActivityType;

public class ActivityDTO {
	private String activityName;
	private String description;
	private Double price;
	private Integer duration;
	private Boolean timeInMinutes;
	private ActivityType activityType;
	private Boolean freeCancellation;
	private Boolean payLater;
	private Boolean skipLine;
	private Integer nRating;
	private Double rate;
	private String providerName;
	
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public Boolean getTimeInMinutes() {
		return timeInMinutes;
	}
	public void setTimeInMinutes(Boolean timeInMinutes) {
		this.timeInMinutes = timeInMinutes;
	}
	public ActivityType getActivityType() {
		return activityType;
	}
	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}
	public Boolean getFreeCancellation() {
		return freeCancellation;
	}
	public void setFreeCancellation(Boolean freeCancellation) {
		this.freeCancellation = freeCancellation;
	}
	public Boolean getPayLater() {
		return payLater;
	}
	public void setPayLater(Boolean payLater) {
		this.payLater = payLater;
	}
	public Boolean getSkipLine() {
		return skipLine;
	}
	public void setSkipLine(Boolean skipLine) {
		this.skipLine = skipLine;
	}
	public Integer getnRating() {
		return nRating;
	}
	public void setnRating(Integer nRating) {
		this.nRating = nRating;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	
	
}

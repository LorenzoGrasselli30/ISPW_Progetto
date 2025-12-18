package application.model.entity;

import application.model.enums.ActivityType;

public class Activity {
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

	public Activity(String activityName, String description, Double price, Integer duration, Boolean timeInMinutes,
			ActivityType activityType, Boolean freeCancellation, Boolean payLater, Boolean skipLine, Integer nRating,
			Double rate, String providerName) {
		this.activityName = activityName;
		this.description = description;
		this.price= price;
		this.duration = duration;
		this.timeInMinutes = timeInMinutes;
		this.activityType = activityType;
		this.freeCancellation = freeCancellation;
		this.payLater = payLater;
		this.skipLine = skipLine;
		this.nRating = nRating;
		this.rate = rate;
		this.providerName= providerName;
	}
	
	public Double getRate() {
		return rate;
	}

	public String getActivityName() {
		return activityName;
	}

	public String getDescription() {
		return description;
	}

	public Integer getDuration() {
		return duration;
	}

	public Boolean getTimeInMinutes() {
		return timeInMinutes;
	}

	public ActivityType getActivityType() {
		return activityType;
	}

	public Boolean getFreeCancellation() {
		return freeCancellation;
	}

	public Boolean getPayLater() {
		return payLater;
	}

	public Boolean getSkipLine() {
		return skipLine;
	}

	public Integer getnRating() {
		return nRating;
	}

	public String getProviderName() {
		return providerName;
	}

	public Double getPrice() {
		return price;
	}
	
	public int calculateRelevanceScore(Activity target, ActivityType referenceType, String referenceProvider) {
        int score = 0;
        
        boolean sameProvider = target.getProviderName().equals(referenceProvider);
        boolean sameType = target.getActivityType() == referenceType;

        if (sameProvider) {
            score += 1; // Aumenta priorità per lo stesso provider
        }
        if (sameType) {
            score += 1; // Aumenta priorità per lo stesso tipo
        }
        
        return score;
    }
	
}

package application.model.entity;

import application.model.enums.ActivityType;

public class Activity {
	private String activityName;
	private Double price;
	private ActivityType activityType;
	private ActivityOtherInformation otherInfo;
	private ActivityRating rating;
	
	//Relazione di composizione quindi Activity deve mantenere un'istanza del provider associato
	private Provider provider;
	
	//Utilizzato per il controllo della disponibilità 
	private ActivityAvailableDates availableDates;
	
	public Activity(String activityName, Double price, ActivityType activityType, Provider provider, ActivityRating rating, 
			ActivityOtherInformation otherInfo, ActivityAvailableDates availableDates) {
		this.activityName = activityName;
		this.price= price;
		this.activityType = activityType;
		this.otherInfo = otherInfo;
		this.rating = rating;
		this.provider= provider;
		this.availableDates = availableDates;
	}

	public String getActivityName() {
		return activityName;
	}

	public ActivityType getActivityType() {
		return activityType;
	}

	public Provider getProvider() {
		return provider;
	}

	public Double getPrice() {
		return price;
	}
	
	public ActivityOtherInformation getOtherInfo() {
		return otherInfo;
	}
	
	public ActivityRating getRating() {
		return rating;
	}
	
	public int calculateRelevanceScore(Activity target, ActivityType referenceType, String referenceProvider) {
        int score = 0;
        
        boolean sameProvider = target.getProvider().getProviderName().equals(referenceProvider);
        boolean sameType = target.getActivityType() == referenceType;

        if (sameProvider) {
            score += 1; // Aumenta priorità per lo stesso provider
        }
        if (sameType) {
            score += 1; // Aumenta priorità per lo stesso tipo
        }
        
        return score;
    }
	
	public ActivityAvailableDates getAvaibleDates() {
		return availableDates;
	}

	public void setAvaibleDates(ActivityAvailableDates avaibleDates) {
		this.availableDates = avaibleDates;
	}
	
}

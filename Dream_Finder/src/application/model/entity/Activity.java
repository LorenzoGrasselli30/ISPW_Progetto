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
	
	/*
	public Double calculateAddedServices(Activity activity, int nPeople, Boolean shuttleService, Boolean guideTour) {
		//Il servizio navetta ha tariffa fissa e viene moltiplicata per il numero di persone 
		//La guida viene calcolata in base alla durata dell'attività 10$/60 min
		Double priceResult= 0.0;
		
		if (guideTour) {
			if (activity.getTimeInMinutes()) {
				priceResult+= (10.0*activity.getDuration());
			} else {
				Double timeInMinute= (activity.getDuration()*60.0);
				priceResult+= (10.0*timeInMinute);
			}
		}
		
		if (shuttleService) {
			priceResult+= (5.0*nPeople);
		}
		
		return priceResult;
	}
	*/
}

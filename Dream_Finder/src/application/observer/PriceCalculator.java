package application.observer;

import application.model.bean.ActivityDTO;

public class PriceCalculator extends Subject {
	
	 private Double price;

	 public Double getPrice() {
		 return price;
	 }

	 public void setPrice(Double price) {
		 this.price = price;
		 // Notifica tutti gli observer registrati
		 notifyObservers();
	 }
	 
	 public void calculatePrice(ActivityDTO activity, int nFullTicket, int nReducedTicket, Boolean guideTour, Boolean shuttleService) {
		 
		Double estimatedPrice = (nFullTicket * activity.getPrice()) + (nReducedTicket * (activity.getPrice() / 3.0));
		 
		//Il servizio navetta ha tariffa fissa e viene moltiplicata per il numero di persone 
		//La guida viene calcolata in base alla durata dell'attività 6$/1h a persona
			
		if (guideTour) {
			if (activity.getTimeInMinutes()) {
				Double hours= (activity.getDuration()/60.0);
				estimatedPrice+= (10.0*hours)*(nFullTicket+nReducedTicket);
			} else {
				estimatedPrice+= (10.0*activity.getDuration())*(nFullTicket+nReducedTicket);
			}
		}
			
		if (shuttleService) {
			estimatedPrice+= (5.0*(nFullTicket+nReducedTicket));
		}
			
		setPrice(estimatedPrice);
	 } 
}

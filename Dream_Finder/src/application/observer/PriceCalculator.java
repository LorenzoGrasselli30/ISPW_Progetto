package application.observer;

import application.model.bean.ActivityDTO;

public class PriceCalculator extends Subject {
	
	 private Double totalPrice;
	 private Double shuttlePrice;
	 private Double guidePrice;
	 
	 
	 public Double getPrice() {
		 return totalPrice;
	 	}
	 
	 public Double getShuttlePrice() {
			return shuttlePrice;
		}
	 
	 public Double getGuidePrice() {
			return guidePrice;
		}
	 
	 public void setPrice(Double totalPrice, Double shuttlePrice, Double guidePrice) {
		 this.totalPrice = totalPrice;
		 this.shuttlePrice = shuttlePrice;
		 this.guidePrice = guidePrice;
		 
		 // Notifica tutti gli observer registrati
		 notifyObservers();
	 }
	 
	 public void calculatePrice(ActivityDTO activity, int nFullTicket, int nReducedTicket, Boolean guideTour, Boolean shuttleService) {
		 
		Double estimatedPrice = (nFullTicket * activity.getPrice()) + (nReducedTicket * (activity.getPrice() / 3.0));
		Double estimatedGuidePrice = 0.0;
		Double estimatedShuttlePrice = 0.0;
		
		//Il servizio navetta ha tariffa fissa e viene moltiplicata per il numero di persone 
		//La guida viene calcolata in base alla durata dell'attività 6$/1h a persona
			
		if (guideTour) {
			if (activity.getTimeInMinutes()) {
				Double hours= (activity.getDuration()/60.0);
				estimatedGuidePrice= (10.0*hours)*(nFullTicket+nReducedTicket);
			} else {
				
				estimatedGuidePrice= (10.0*activity.getDuration())*(nFullTicket+nReducedTicket);
			}
		}
			
		if (shuttleService) {
			estimatedShuttlePrice= (5.0*(nFullTicket+nReducedTicket));
		}
		
		estimatedPrice+= (estimatedGuidePrice+estimatedShuttlePrice);
		
		setPrice(estimatedPrice, estimatedShuttlePrice, estimatedGuidePrice);
	 }
}

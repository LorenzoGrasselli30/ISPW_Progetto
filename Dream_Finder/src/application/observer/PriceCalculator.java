package application.observer;

public class PriceCalculator extends Subject {
	
	private Double price;

	 public Double getSuccessRate() {
		 return price;
	 }

	 public void setSuccessRate(Double newSuccessRate) {
		 this.price = price;
		 // Notifica tutti gli observer registrati
		 notifyObservers();
	 }
	 
	 public void calculatePrice(Double defaultPrice, int nFullTicket, int nReducedTicket, Boolean guideTour, Boolean shuttleService) {
		 Double estimatedPrice= 0.0;
		 
		 
	 }
	 
}

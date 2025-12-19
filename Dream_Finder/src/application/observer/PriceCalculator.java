package application.observer;

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
	 
	 public void calculatePrice(Double defaultPrice, int nFullTicket, int nReducedTicket, Boolean guideTour, Boolean shuttleService) {
		 
		 Double estimatedPrice = (nFullTicket * defaultPrice) + (nReducedTicket * (defaultPrice / 3.0));
		 
		 setPrice(estimatedPrice);
	 }
	 
}

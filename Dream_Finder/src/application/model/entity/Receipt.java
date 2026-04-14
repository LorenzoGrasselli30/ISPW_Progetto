package application.model.entity;

public class Receipt {
	
	private Provider provider;
	
	//Informazioni
	private Booking bookingInfo;
	
	//Informazioni della carta
	private CardInformation card;
	
	//Informazioni su Stripe
	private StripeInformation stripe;
	
	public Receipt(Provider provider, Booking bookingInfo, CardInformation card, StripeInformation stripe) {
		this.provider = provider;
		this.bookingInfo= bookingInfo;
		this.card = card;
		this.stripe = stripe;
	}

	public Provider getProvider() {
		return provider;
	}

	public CardInformation getCard() {
		return card;
	}

	public StripeInformation getStripe() {
		return stripe;
	}

	public Booking getBookingInformation() {
		return bookingInfo;
	}
}

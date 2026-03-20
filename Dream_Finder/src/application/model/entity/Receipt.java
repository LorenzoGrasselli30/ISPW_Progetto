package application.model.entity;

public class Receipt {
	
	private Provider provider;
	
	//Informazioni
	private BookingPriceInformation priceInformation;
	
	//Informazioni della carta
	private CardInformation card;
	
	//Informazioni su Stripe
	private StripeInformation stripe;
	
	public Receipt(Provider provider, BookingPriceInformation priceInformation, CardInformation card, StripeInformation stripe) {
		this.provider = provider;
		this.priceInformation= priceInformation;
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

	public BookingPriceInformation getPriceInformation() {
		return priceInformation;
	}
}

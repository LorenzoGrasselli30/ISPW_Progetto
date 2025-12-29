package application.model.entity;

public class Receipt {
	private Provider provider;
	private int nFullTicket;
	private int nReducedTicket;
	private Double shuttlePrice;
	private Double guidePrice;
	private Double totalPrice;
	
	//Informazioni della carta
	private String cardNumber;
	private String expiredDate;
	private String ownerName;
	
	//Informazioni su Stripe
	private String paymentID;
	private String paymentDescription;
	private String paymentOutcome;
	
	public Receipt(Provider provider, int nFullTicket, int nReducedTicket, Double shuttlePrice, Double guidePrice,
			Double totalPrice, String cardNumber, String expiredDate, String ownerName, String paymentID, String paymentDescription, String paymentOutcome) {
		this.provider = provider;
		this.nFullTicket = nFullTicket;
		this.nReducedTicket = nReducedTicket;
		this.shuttlePrice = shuttlePrice;
		this.guidePrice = guidePrice;
		this.totalPrice = totalPrice;
		this.cardNumber = cardNumber;
		this.expiredDate = expiredDate;
		this.ownerName = ownerName;
		this.paymentID = paymentID;
		this.paymentDescription = paymentDescription;
		this.paymentOutcome = paymentOutcome;
	}

	public Provider getProviderName() {
		return provider;
	}

	public int getnFullTicket() {
		return nFullTicket;
	}

	public int getnReducedTicket() {
		return nReducedTicket;
	}

	public Double getShuttlePrice() {
		return shuttlePrice;
	}

	public Double getGuidePrice() {
		return guidePrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public String getExpiredDate() {
		return expiredDate;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public String getPaymentID() {
		return paymentID;
	}

	public String getPaymentDescription() {
		return paymentDescription;
	}

	public String getPaymentOutcome() {
		return paymentOutcome;
	}
	
}

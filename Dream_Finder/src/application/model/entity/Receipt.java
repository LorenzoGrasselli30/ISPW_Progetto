package application.model.entity;

public class Receipt {
	private String providerName;
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
	
	public Receipt(String providerName, int nFullTicket, int nReducedTicket, Double shuttlePrice, Double guidePrice,
			Double totalPrice, String cardNumber, String expiredDate, String ownerName, String paymentID, String paymentDescription, String paymentOutcome) {
		this.providerName = providerName;
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
	
	public void setCardInformation (String cardNumber, String cvv, String expiredDate, String ownerName) {
		this.cardNumber= cardNumber;
		this.expiredDate= expiredDate;
		this.ownerName= ownerName;
	}

	public String getProviderName() {
		return providerName;
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
	
}

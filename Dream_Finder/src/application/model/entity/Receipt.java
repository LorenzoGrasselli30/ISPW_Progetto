package application.model.entity;

public class Receipt {
	private String travelerName;
	private String travelerSurname;
	private String providerName;
	private int nFullTicket;
	private int nReducedTicket;
	private Double shuttlePrice;
	private Double guidePrice;
	private Double totalPrice;
	
	//Informazioni della carta
	private String cardNumber;
	private String cvv;
	private String expiredDate;
	private String ownerName;
	
	public Receipt(String travelerName, String travelerSurname, String providerName, int nFullTicket,
			int nReducedTicket, Double shuttlePrice, Double guidePrice, Double totalPrice) {
		this.travelerName = travelerName;
		this.travelerSurname = travelerSurname;
		this.providerName = providerName;
		this.nFullTicket = nFullTicket;
		this.nReducedTicket = nReducedTicket;
		this.shuttlePrice = shuttlePrice;
		this.guidePrice = guidePrice;
		this.totalPrice = totalPrice;
		this.cardNumber = null;
		this.cvv = null;
		this.expiredDate = null;
		this.ownerName = null;
	}
	
	public void setCardInformation (String cardNumber, String cvv, String expiredDate, String ownerName) {
		this.cardNumber= cardNumber;
		this.cvv= cvv;
		this.expiredDate= expiredDate;
		this.ownerName= ownerName;
	}

	public String getTravelerName() {
		return travelerName;
	}

	public String getTravelerSurname() {
		return travelerSurname;
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

	public String getCvv() {
		return cvv;
	}

	public String getExpiredDate() {
		return expiredDate;
	}

	public String getOwnerName() {
		return ownerName;
	}
	
}

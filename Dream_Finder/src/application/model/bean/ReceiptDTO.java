package application.model.bean;

public class ReceiptDTO {
	private String providerName;
	private int nFullTicket;
	private int nReducedTicket;
	private Double totalPrice;
	private Double guidePrice;
	private Double shuttlePrice;
	
	private String cardNumber;
	private String expiredDate;
	private String ownerName;
	
	private String paymentID;
	private String paymentDescription;
	private String paymentOutcome;
	
	public String getProviderName() {
		return providerName;
	}
	
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	
	public int getnFullTicket() {
		return nFullTicket;
	}
	
	public void setnFullTicket(int nFullTicket) {
		this.nFullTicket = nFullTicket;
	}
	
	public int getnReducedTicket() {
		return nReducedTicket;
	}
	
	public void setnReducedTicket(int nReducedTicket) {
		this.nReducedTicket = nReducedTicket;
	}
	
	public Double getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public Double getGuidePrice() {
		return guidePrice;
	}
	
	public void setGuidePrice(Double guidePrice) {
		this.guidePrice = guidePrice;
	}
	
	public Double getShuttlePrice() {
		return shuttlePrice;
	}
	
	public void setShuttlePrice(Double shuttlePrice) {
		this.shuttlePrice = shuttlePrice;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public String getExpiredDate() {
		return expiredDate;
	}
	
	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}
	
	public String getOwnerName() {
		return ownerName;
	}
	
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	public String getPaymentID() {
		return paymentID;
	}
	
	public void setPaymentID(String paymentID) {
		this.paymentID = paymentID;
	}
	
	public String getPaymentDescription() {
		return paymentDescription;
	}
	
	public void setPaymentDescription(String paymentDescription) {
		this.paymentDescription = paymentDescription;
	}
	
	public String getPaymentOutcome() {
		return paymentOutcome;
	}
	
	public void setPaymentOutcome(String paymentOutcome) {
		this.paymentOutcome = paymentOutcome;
	}
	
}

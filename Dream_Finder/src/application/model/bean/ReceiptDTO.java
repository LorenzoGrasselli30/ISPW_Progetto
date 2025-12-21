package application.model.bean;

public class ReceiptDTO {
	private String travelerName;
	private String travelerSurname;
	private String providerName;
	private int nFullTicket;
	private int nReducedTicket;
	private Double shuttlePrice;
	private Double guidePrice;
	private Double totalPrice;
	
	public String getTravelerName() {
		return travelerName;
	}
	
	public void setTravelerName(String travelerName) {
		this.travelerName = travelerName;
	}
	
	public String getTravelerSurname() {
		return travelerSurname;
	}
	
	public void setTravelerSurname(String travelerSurname) {
		this.travelerSurname = travelerSurname;
	}
	
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
	
	public Double getShuttlePrice() {
		return shuttlePrice;
	}
	
	public void setShuttlePrice(Double shuttlePrice) {
		this.shuttlePrice = shuttlePrice;
	}
	
	public Double getGuidePrice() {
		return guidePrice;
	}
	
	public void setGuidePrice(Double guidePrice) {
		this.guidePrice = guidePrice;
	}
	
	public Double getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
}

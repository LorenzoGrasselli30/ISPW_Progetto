package application.model.bean;

public class PaymentOutcomeDTO {
	private String paymentID;
	private String paymentDescription;
	private String paymentOutcome;
	
	public String getID() {
		return paymentID;
	}
	
	public void setID(String paymentID) {
		this.paymentID = paymentID;
	}
	
	public String getDescription() {
		return paymentDescription;
	}
	
	public void setDescription(String paymentDescription) {
		this.paymentDescription = paymentDescription;
	}
	
	public String getOutcome() {
		return paymentOutcome;
	}
	
	public void setOutcome(String paymentOutcome) {
		this.paymentOutcome = paymentOutcome;
	}

}

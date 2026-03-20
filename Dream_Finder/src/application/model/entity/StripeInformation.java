package application.model.entity;

public class StripeInformation {
	private String paymentID;
	private String paymentDescription;
	private String paymentOutcome;
	
	public StripeInformation(String paymentID, String paymentDescription, String paymentOutcome) {
		this.paymentID = paymentID;
		this.paymentDescription = paymentDescription;
		this.paymentOutcome = paymentOutcome;
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

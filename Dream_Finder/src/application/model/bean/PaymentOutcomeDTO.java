package application.model.bean;

public class PaymentOutcomeDTO {
	private String paymentID;
	private String paymentDescription;
	private String paymentOutcome;
	
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
